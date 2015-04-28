package ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.Session;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import adapter.NavigationDrawerAdapter;
import akilliyazilim.whichone.LoginActivity;
import akilliyazilim.whichone.R;
import de.hdodenhof.circleimageview.CircleImageView;
import model.DrawerItem;
import model.User;
import util.ApplicationPreferences;
import view.RobotoLightTextView;
import view.RobotoRegularButton;


/**
 * Created by mert simsek on 20/12/14.
 */
public class NavigationDrawerFragment extends Fragment implements NavigationDrawerCallbacks,AdapterView.OnItemClickListener, View.OnClickListener{

    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final String PREFERENCES_FILE = "general_mobile";
    private View mFragmentContainerView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationDrawerCallbacks mCallbacks;
    private boolean mUserLearnedDrawer;
    private int mCurrentSelectedPosition;
    private ListView listview_drawer;
    private NavigationDrawerAdapter adapter;
    private CircleImageView imageview_profile_image;
    private RobotoLightTextView textview_name_surname;
    private RobotoLightTextView textview_live;
    private RobotoRegularButton button_share;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        listview_drawer = (ListView) view.findViewById(R.id.listview_navigation);
        listview_drawer.setOnItemClickListener(this);

        imageview_profile_image = (CircleImageView) view.findViewById(R.id.imageview_navigation_profile);
        textview_name_surname = (RobotoLightTextView) view.findViewById(R.id.textview_navigation_name_surname);
        textview_live = (RobotoLightTextView) view.findViewById(R.id.textview_navigation_live);
        button_share = (RobotoRegularButton) view.findViewById(R.id.button_share);
        button_share.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<DrawerItem> list = new ArrayList<>();
        DrawerItem drawer_item_1 = new DrawerItem(getResources().getString(R.string.drawer_item_1),R.drawable.icon_double_hearts);
        DrawerItem drawer_item_2 = new DrawerItem(getResources().getString(R.string.drawer_item_2),R.drawable.icon_heart);
        DrawerItem drawer_item_3 = new DrawerItem(getResources().getString(R.string.drawer_item_3),R.drawable.icon_logout);
        DrawerItem drawer_item_4 = new DrawerItem(getResources().getString(R.string.drawer_item_4),R.drawable.icon_location);
        list.add(drawer_item_1);
        list.add(drawer_item_2);
        list.add(drawer_item_4);
        list.add(drawer_item_3);

        adapter = new NavigationDrawerAdapter(getActivity().getApplicationContext(),list);
        listview_drawer.setAdapter(adapter);

        User user = ApplicationPreferences.getInstance(getActivity().getApplicationContext()).getUser();
        if(user.getImage_url().length()>5)
            Picasso.with(getActivity().getApplicationContext()).load(user.getImage_url()).into(imageview_profile_image);
        textview_name_surname.setText(user.getUser_name() + " " + user.getUser_surname());
        textview_live.setText(user.getUser_live());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readSharedSetting(getActivity(), PREF_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) return;
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) return;
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveSharedSetting(getActivity(), PREF_USER_LEARNED_DRAWER, "true");
                }

                getActivity().invalidateOptionsMenu();
            }
        };

        if (!mUserLearnedDrawer)
            mDrawerLayout.openDrawer(mFragmentContainerView);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    void selectItem(int position) {
        if(mCurrentSelectedPosition != position){
            switch (position){
                case 0:
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, MatchFragment.newInstance()).commit();
                    break;
                case 1:
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, MyWinsFragment.newInstance()).commit();
                    break;
                case 2:
                    Toast.makeText(getActivity().getApplicationContext(),getResources().getString(R.string.no_available_yet),Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Session.getActiveSession().closeAndClearTokenInformation();
                    ApplicationPreferences.getInstance(getActivity().getApplicationContext()).saveUser(new User());
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                    getActivity().finish();
                    break;
                default:
                    break;
            }
        }
        mCurrentSelectedPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_share:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=akilliyazilim.whichone");
                share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(share, "Share via"));
                break;
            default:
                break;
        }
    }
}
