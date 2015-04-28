package ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.parse.ParsePush;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import adapter.WalkthroughAdapter;
import akilliyazilim.whichone.R;
import akilliyazilim.whichone.WhichoneActivity;
import model.User;
import util.ApplicationPreferences;
import view.RobotoFacebookButton;
import view.RobotoThinButton;

/**
 * Created by mertsimsek on 10/01/15.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager viewpager_walkthrough;
    private CirclePageIndicator pager_indicator;
    private RobotoFacebookButton button_login;
    private RobotoThinButton button_previous;
    private RobotoThinButton button_next;
    private Session mSession;


    private static final String TAG = "MainFragment";
    private UiLifecycleHelper uiHelper;
    private User user;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
        user = new User();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_walkthrough, container, false);

        button_login = (RobotoFacebookButton) v.findViewById(R.id.button_login);
        button_login.setBackgroundResource(R.drawable.button_next);
        button_login.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        button_login.setText(getResources().getString(R.string.login));
        button_login.setTextSize(23);
        button_login.setClickable(false);
        button_login.setVisibility(View.INVISIBLE);
        button_login.setFragment(this);
        button_login.setReadPermissions(Arrays.asList("user_about_me", "user_birthday", "user_location"));

        button_next = (RobotoThinButton) v.findViewById(R.id.button_next);
        button_previous = (RobotoThinButton) v.findViewById(R.id.button_previous);
        button_previous.setOnClickListener(this);
        button_next.setOnClickListener(this);

        viewpager_walkthrough = (ViewPager) v.findViewById(R.id.viewpager_walkthrough);
        pager_indicator = (CirclePageIndicator) v.findViewById(R.id.pager_indicator);
        pager_indicator.setOnPageChangeListener(this);

        float density = getResources().getDisplayMetrics().density;
        pager_indicator.setRadius(4 * density);
        pager_indicator.setPageColor(getResources().getColor(R.color.color_main_light));
        pager_indicator.setFillColor(getResources().getColor(R.color.color_main));

        viewpager_walkthrough.setAdapter(new WalkthroughAdapter(getActivity().getSupportFragmentManager()));
        pager_indicator.setViewPager(viewpager_walkthrough);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Session session = Session.getActiveSession();
        if (session != null && (session.isOpened() || session.isClosed())) {
            onSessionStateChange(session, session.getState(), null);
        }
        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
        if (state.isOpened() && (mSession == null || isSessionChanged(session))) {
            mSession = session;
            if (ApplicationPreferences.getInstance(getActivity().getApplicationContext()).getUser().getSocial_id().equals("")) {

                new Request(session, "/me", null, HttpMethod.GET, new Request.Callback() {


                    @Override
                    public void onCompleted(Response response) {

                        GraphObject graphObject = response.getGraphObject();
                        user.setSocial_id(graphObject.getProperty("id").toString());
                        user.setUser_name(graphObject.getProperty("first_name").toString());
                        user.setUser_surname(graphObject.getProperty("last_name").toString());
                        user.setUser_sex(graphObject.getProperty("gender").toString());
                        user.setProfile_url(graphObject.getProperty("link").toString());

                        //TODO birthday and location needs extra permission.
                        //TODO this feaure can be added. But in this version, not available.
                        //user.setUser_age(Utils.dateToAgeConverter(response.getGraphObject().getProperty("birthday").toString()));
                        //JSONObject json = (JSONObject) response.getGraphObject().getProperty("location");

                        /*try {
                            user.setUser_live(json.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

                        Bundle params = new Bundle();
                        params.putBoolean("redirect", false);
                        params.putString("height", "300");
                        params.putString("type", "large");
                        params.putString("width", "300");

                        new Request(session, "/me/picture", params, HttpMethod.GET, new Request.Callback() {
                            @Override
                            public void onCompleted(Response response) {

                                JSONObject json_data = null;
                                try {
                                    json_data = response.getGraphObject().getInnerJSONObject().getJSONObject("data");
                                    user.setImage_url(json_data.getString("url").toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ApplicationPreferences.getInstance(getActivity().getApplicationContext()).saveUser(user);
                                ParsePush.subscribeInBackground("whichone" + user.getSocial_id());
                                Intent i = new Intent(getActivity(), WhichoneActivity.class);
                                startActivity(i);
                                getActivity().finish();

                            }
                        }).executeAsync();
                    }
                }).executeAsync();

            }
            else{
                ParsePush.subscribeInBackground("whichone" + user.getSocial_id());
                Intent i = new Intent(getActivity(), WhichoneActivity.class);
                startActivity(i);
                getActivity().finish();
            }

        } else if (state.isClosed()) {
        }
    }

    private boolean isSessionChanged(Session session) {

        // Check if session state changed
        if (mSession.getState() != session.getState())
            return true;

        // Check if accessToken changed
        if (mSession.getAccessToken() != null) {
            if (!mSession.getAccessToken().equals(session.getAccessToken()))
                return true;
        } else if (session.getAccessToken() != null) {
            return true;
        }

        // Nothing changed
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_previous:
                viewpager_walkthrough.setCurrentItem(viewpager_walkthrough.getCurrentItem() - 1);
                button_next.setVisibility(View.VISIBLE);
                button_next.setClickable(true);
                button_login.setVisibility(View.INVISIBLE);
                button_login.setClickable(false);
                break;
            case R.id.button_next:
                if (viewpager_walkthrough.getCurrentItem() != 2) {
                    viewpager_walkthrough.setCurrentItem(viewpager_walkthrough.getCurrentItem() + 1);
                } else if (viewpager_walkthrough.getCurrentItem() == 2) {
                    button_next.setVisibility(View.INVISIBLE);
                    button_next.setClickable(false);
                    button_login.setVisibility(View.VISIBLE);
                    button_login.setClickable(true);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                button_login.setVisibility(View.INVISIBLE);
                button_previous.setVisibility(View.INVISIBLE);
                button_previous.setClickable(false);
                button_login.setClickable(false);
                button_next.setText(getResources().getString(R.string.next));
                button_next.setVisibility(View.VISIBLE);
                button_next.setClickable(true);
                break;
            case 1:
                button_previous.setVisibility(View.VISIBLE);
                button_previous.setClickable(true);
                button_next.setText(getResources().getString(R.string.next));
                button_login.setVisibility(View.INVISIBLE);
                button_login.setClickable(false);
                button_next.setVisibility(View.VISIBLE);
                button_next.setClickable(true);
                break;
            case 2:
                button_login.setVisibility(View.VISIBLE);
                button_login.setClickable(true);
                button_next.setVisibility(View.INVISIBLE);
                button_next.setClickable(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
