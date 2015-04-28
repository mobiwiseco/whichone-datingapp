package ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import akilliyazilim.whichone.R;
import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.flipimageview.library.FlipImageView;
import model.User;
import push.PushMessageCreator;
import task.NewMatchTask;
import task.VoteMatchTask;
import util.ApplicationPreferences;
import view.RobotoLightTextView;
import view.RobotoRegularButton;
import view.RobotoRegularTextView;

/**
 * Created by mertsimsek on 10/01/15.
 */
public class MatchFragment extends Fragment implements FlipImageView.OnFlipListener, View.OnClickListener {

    private ImageView imageview_nodata_heart;
    private RobotoLightTextView textview_nodata_head_1;
    private RobotoLightTextView textview_nodata_head_2;
    private RobotoLightTextView textview_nodata_head_3;
    private RobotoRegularButton button_nodata_share;

    private ArrayList<User> matched_user;
    private User user_me;

    private View imageview_middle_dot;
    private RobotoLightTextView textview_versus;

    private CircleImageView imageview_profile_pic_1;
    private CircleImageView imageview_profile_pic_2;

    private CircularProgressBar progress_profile_1;
    private CircularProgressBar progress_profile_2;

    private FlipImageView imageview_like_pic_1;
    private FlipImageView imageview_like_pic_2;

    private RobotoLightTextView textview_profile_name_1;
    private RobotoLightTextView textview_profile_name_2;

    private FlipImageView imageview_none;

    private RevealLayout mRevealLayout;
    private int flip_control = 0;
    private boolean first_view = true;

    private boolean is_profile_pic_1_loaded;
    private boolean is_profile_pic_2_loaded;

    private View rootView;

    public MatchFragment() {
    }

    public static MatchFragment newInstance() {
        return new MatchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_match, container, false);
        rootView = v;
        user_me = ApplicationPreferences.getInstance(getActivity().getApplicationContext()).getUser();
        initializeViews(rootView);
        return rootView;
    }

    private void initializeViews(View v) {

        imageview_nodata_heart = (ImageView) v.findViewById(R.id.imageview_nodata_heart);
        textview_nodata_head_1 = (RobotoLightTextView) v.findViewById(R.id.textview_nodata_head_1);
        textview_nodata_head_2 = (RobotoLightTextView) v.findViewById(R.id.textview_nodata_head_2);
        textview_nodata_head_3 = (RobotoLightTextView) v.findViewById(R.id.textview_nodata_head_3);
        button_nodata_share = (RobotoRegularButton) v.findViewById(R.id.button_nodata_share);
        button_nodata_share.setOnClickListener(this);
        imageview_middle_dot = (View) v.findViewById(R.id.middle_dot);
        textview_versus = (RobotoLightTextView) v.findViewById(R.id.versus);

        imageview_profile_pic_1 = (CircleImageView) v.findViewById(R.id.imageview_profile_1);
        imageview_profile_pic_2 = (CircleImageView) v.findViewById(R.id.imageview_profile_2);
        progress_profile_1 = (CircularProgressBar) v.findViewById(R.id.progress_pic_1);
        progress_profile_2 = (CircularProgressBar) v.findViewById(R.id.progress_pic_2);
        imageview_profile_pic_1.setOnClickListener(this);
        imageview_profile_pic_2.setOnClickListener(this);
        progress_profile_1.setVisibility(View.INVISIBLE);
        progress_profile_2.setVisibility(View.INVISIBLE);

        imageview_like_pic_1 = (FlipImageView) v.findViewById(R.id.imageview_like_1);
        imageview_like_pic_2 = (FlipImageView) v.findViewById(R.id.imageview_like_2);

        imageview_none = (FlipImageView) v.findViewById(R.id.imageview_none);

        textview_profile_name_1 = (RobotoLightTextView) v.findViewById(R.id.textview_name_profile_1);
        textview_profile_name_2 = (RobotoLightTextView) v.findViewById(R.id.textview_name_profile_2);

        imageview_like_pic_1.setOnFlipListener(this);
        imageview_like_pic_2.setOnFlipListener(this);
        imageview_none.setOnFlipListener(this);

        imageview_like_pic_1.setClickable(true);
        imageview_like_pic_2.setClickable(true);
        imageview_none.setClickable(true);

        imageview_like_pic_1.setFlippedDrawable(getResources().getDrawable(R.drawable.icon_like_selected));
        imageview_like_pic_1.setInterpolator(new AccelerateDecelerateInterpolator());
        imageview_like_pic_1.setDuration(200);

        imageview_like_pic_2.setFlippedDrawable(getResources().getDrawable(R.drawable.icon_like_selected));
        imageview_like_pic_2.setInterpolator(new AccelerateDecelerateInterpolator());
        imageview_like_pic_2.setDuration(200);

        imageview_none.setFlippedDrawable(getResources().getDrawable(R.drawable.icon_none_selected_test));
        imageview_none.setInterpolator(new AccelerateDecelerateInterpolator());
        imageview_none.setDuration(200);

        mRevealLayout = (RevealLayout) v.findViewById(R.id.reveal_layout);

        is_profile_pic_1_loaded = false;
        is_profile_pic_2_loaded = false;

        int enter_count =  ApplicationPreferences.getInstance(getActivity().getApplicationContext()).playMessageShownCount();
        if(enter_count%10==0 || enter_count==0)
            createPlayStoreDialog();

        hideAll();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getNewMatch();
    }

    private void switchToNoDataView() {

        imageview_nodata_heart.setVisibility(View.VISIBLE);
        textview_nodata_head_1.setVisibility(View.VISIBLE);
        textview_nodata_head_2.setVisibility(View.VISIBLE);
        textview_nodata_head_3.setVisibility(View.VISIBLE);
        button_nodata_share.setVisibility(View.VISIBLE);
        button_nodata_share.setClickable(true);

        progress_profile_1.setVisibility(View.INVISIBLE);
        progress_profile_2.setVisibility(View.INVISIBLE);
        imageview_profile_pic_1.setVisibility(View.INVISIBLE);
        imageview_profile_pic_2.setVisibility(View.INVISIBLE);
        imageview_like_pic_1.setVisibility(View.INVISIBLE);
        imageview_like_pic_2.setVisibility(View.INVISIBLE);
        imageview_like_pic_1.setVisibility(View.GONE);
        imageview_like_pic_2.setVisibility(View.GONE);
        progress_profile_1.setVisibility(View.INVISIBLE);
        progress_profile_2.setVisibility(View.INVISIBLE);
        textview_profile_name_1.setVisibility(View.INVISIBLE);
        textview_profile_name_2.setVisibility(View.INVISIBLE);
        imageview_none.setVisibility(View.INVISIBLE);
        imageview_middle_dot.setVisibility(View.INVISIBLE);
        textview_versus.setVisibility(View.INVISIBLE);
        imageview_like_pic_1.setClickable(false);
        imageview_like_pic_2.setClickable(false);
        imageview_none.setClickable(false);
    }

    private void switchToMatchAvailableView() {

        imageview_nodata_heart.setVisibility(View.INVISIBLE);
        textview_nodata_head_1.setVisibility(View.INVISIBLE);
        textview_nodata_head_2.setVisibility(View.INVISIBLE);
        textview_nodata_head_3.setVisibility(View.INVISIBLE);
        button_nodata_share.setVisibility(View.INVISIBLE);
        button_nodata_share.setClickable(false);

        imageview_like_pic_1.setVisibility(View.VISIBLE);
        imageview_like_pic_2.setVisibility(View.VISIBLE);
        textview_profile_name_1.setVisibility(View.VISIBLE);
        textview_profile_name_2.setVisibility(View.VISIBLE);
        imageview_none.setVisibility(View.VISIBLE);
        imageview_middle_dot.setVisibility(View.VISIBLE);
        textview_versus.setVisibility(View.VISIBLE);

        if (first_view) {
            imageview_like_pic_1.setClickable(true);
            imageview_like_pic_2.setClickable(true);
            imageview_none.setClickable(true);
        }
    }

    private void hideAll() {
        imageview_nodata_heart.setVisibility(View.INVISIBLE);
        textview_nodata_head_1.setVisibility(View.INVISIBLE);
        textview_nodata_head_2.setVisibility(View.INVISIBLE);
        textview_nodata_head_3.setVisibility(View.INVISIBLE);
        button_nodata_share.setVisibility(View.INVISIBLE);
        button_nodata_share.setClickable(false);


        progress_profile_1.setVisibility(View.INVISIBLE);
        progress_profile_2.setVisibility(View.INVISIBLE);
        progress_profile_1.setVisibility(View.GONE);
        progress_profile_2.setVisibility(View.GONE);
        imageview_profile_pic_1.setVisibility(View.INVISIBLE);
        imageview_profile_pic_2.setVisibility(View.INVISIBLE);
        imageview_like_pic_1.setVisibility(View.INVISIBLE);
        imageview_like_pic_2.setVisibility(View.INVISIBLE);
        textview_profile_name_1.setVisibility(View.INVISIBLE);
        textview_profile_name_2.setVisibility(View.INVISIBLE);
        imageview_none.setVisibility(View.INVISIBLE);
        imageview_middle_dot.setVisibility(View.INVISIBLE);
        textview_versus.setVisibility(View.INVISIBLE);
        imageview_like_pic_1.setClickable(false);
        imageview_like_pic_2.setClickable(false);
        imageview_none.setClickable(false);
    }

    @Override
    public void onClick(View v) {
        /*if (matched_user != null) {
            switch (v.getId()) {
                case R.id.imageview_profile_1:
                    break;
                case R.id.imageview_profile_2:
                    break;
                default:
                    break;
            }
        }*/

        switch (v.getId()) {
            case R.id.button_nodata_share:
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

    @Override
    public void onClick(FlipImageView view) {
        is_profile_pic_1_loaded = false;
        is_profile_pic_2_loaded = false;
        imageview_like_pic_1.setClickable(false);
        imageview_like_pic_2.setClickable(false);
        imageview_none.setClickable(false);

        switch (view.getId()) {
            case R.id.imageview_like_1:
                voteMatch(0);
                break;
            case R.id.imageview_like_2:
                voteMatch(1);
                break;
            case R.id.imageview_none:
                break;
            default:
                break;
        }
    }

    @Override
    public void onFlipStart(FlipImageView view) {

    }

    @Override
    public void onFlipEnd(FlipImageView view) {
        if (flip_control == 0) {
            flip_control = 1;
            mRevealLayout.hide(1000);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getNewMatch();
                    mRevealLayout.show(1000);
                    imageview_like_pic_1.setFlipped(false);
                    imageview_like_pic_2.setFlipped(false);
                    imageview_none.setFlipped(false);
                    setAnimationControllerDefault();
                }
            }, 1100);
        }
    }

    private void setAnimationControllerDefault() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flip_control = 0;
            }
        }, 1000);
    }

    private void getNewMatch() {
        NewMatchTask task = new NewMatchTask(user_me.getUser_id()) {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                imageview_profile_pic_1.setVisibility(View.INVISIBLE);
                imageview_profile_pic_2.setVisibility(View.INVISIBLE);
                progress_profile_1.setVisibility(View.VISIBLE);
                progress_profile_2.setVisibility(View.VISIBLE);
                is_profile_pic_1_loaded = false;
                is_profile_pic_2_loaded = false;
            }

            @Override
            protected void onPostExecute(ArrayList<User> users) {
                super.onPostExecute(users);
                if (users.size() == 2) {
                    switchToMatchAvailableView();
                    matched_user = users;
                    Picasso.with(getActivity().getApplicationContext()).load(users.get(0).getImage_url()).into(target_profile_1);
                    Picasso.with(getActivity().getApplicationContext()).load(users.get(1).getImage_url()).into(target_profile_2);
                    textview_profile_name_1.setText(users.get(0).getUser_name());
                    textview_profile_name_2.setText(users.get(1).getUser_name());
                } else {
                    matched_user = new ArrayList<>();
                    switchToNoDataView();
                }
                first_view = false;
            }
        };
        task.execute();
    }

    private void voteMatch(final int i) {
        if (matched_user != null) {
            matched_user.get(0).getMatch_id();
            new VoteMatchTask(matched_user.get(0).getMatch_id(),
                    ApplicationPreferences.getInstance(getActivity().getApplicationContext()).getUser().getUser_id(),
                    matched_user.get(i).getUser_id()) {
                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    PushMessageCreator.getInstance().sendVotedUser(ApplicationPreferences.getInstance(getActivity().getApplicationContext()).getUser(), matched_user.get(i));
                }
            }.execute();
        }

    }

    private Target target_profile_1 = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            imageview_none.setClickable(true);
            progress_profile_1.setVisibility(View.INVISIBLE);
            imageview_profile_pic_1.setVisibility(View.VISIBLE);
            Picasso.with(getActivity().getApplicationContext()).load(matched_user.get(0).getImage_url()).into(imageview_profile_pic_1);
            is_profile_pic_1_loaded = true;
            if (is_profile_pic_1_loaded && is_profile_pic_2_loaded) {
                imageview_like_pic_1.setClickable(true);
                imageview_like_pic_2.setClickable(true);
                imageview_none.setClickable(true);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    private Target target_profile_2 = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            imageview_none.setClickable(true);
            progress_profile_2.setVisibility(View.INVISIBLE);
            imageview_profile_pic_2.setVisibility(View.VISIBLE);
            Picasso.with(getActivity().getApplicationContext()).load(matched_user.get(1).getImage_url()).into(imageview_profile_pic_2);
            is_profile_pic_2_loaded = true;
            if (is_profile_pic_1_loaded && is_profile_pic_2_loaded) {
                imageview_like_pic_1.setClickable(true);
                imageview_like_pic_2.setClickable(true);
                imageview_none.setClickable(true);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    private void createPlayStoreDialog(){
        final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .customView(R.layout.dialog_info)
                .build();


        RobotoRegularTextView text_header = (RobotoRegularTextView) dialog.getCustomView().findViewById(R.id.textview_info_dialog_header);
        RobotoLightTextView text_content = (RobotoLightTextView) dialog.getCustomView().findViewById(R.id.textview_info_dialog_content);
        RobotoRegularTextView text_action = (RobotoRegularTextView) dialog.getCustomView().findViewById(R.id.textview_info_dialog_action);

        text_header.setText(getResources().getString(R.string.dialog_info_beta_header));
        text_content.setText(getResources().getString(R.string.dialog_info_beta_content));
        text_action.setText(getResources().getString(R.string.dialog_info_beta_action));

        text_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
            }
        });


        dialog.show();
    }

}
