package ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import adapter.MyWinsAdapter;
import akilliyazilim.whichone.R;
import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import model.User;
import model.Win;
import task.MyWinsTask;
import util.ApplicationPreferences;
import view.RobotoBlackTextview;
import view.RobotoLightTextView;
import view.RobotoThinTextView;

/**
 * Created by mertsimsek on 11/01/15.
 */
public class MyWinsFragment extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener{

    private CircularProgressBar progress;
    private RobotoLightTextView textview_wins_count;
    private RobotoLightTextView textview_plain_text;
    private ImageView imageview_like;
    private ListView listview_wins;
    private MyWinsAdapter adapter;
    private MaterialDialog dialog;
    private Win current_temp_win;

    public MyWinsFragment() {
    }

    public static MyWinsFragment newInstance(){
        return new MyWinsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_wins,container,false);
        initializeViews(v);
        return v;
    }

    private void initializeViews(View v){
        progress = (CircularProgressBar) v.findViewById(R.id.progress_wins);
        listview_wins = (ListView) v.findViewById(R.id.listview_wins);
        textview_wins_count = (RobotoLightTextView) v.findViewById(R.id.textview_wins_count);
        textview_plain_text = (RobotoLightTextView) v.findViewById(R.id.textview_wins_plain_text_you);
        imageview_like = (ImageView) v.findViewById(R.id.imageview_wins_heart);
        listview_wins.setOnItemClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new MyWinsAdapter(getActivity().getApplicationContext());
        listview_wins.setAdapter(adapter);
        loadWins();
    }

    private void loadWins(){
        new MyWinsTask(ApplicationPreferences.getInstance(getActivity().getApplicationContext()).getUser().getUser_id()){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress.setVisibility(View.VISIBLE);
                textview_wins_count.setVisibility(View.INVISIBLE);
                imageview_like.setVisibility(View.INVISIBLE);
                textview_plain_text.setVisibility(View.INVISIBLE);
            }

            @Override
            protected void onPostExecute(ArrayList<Win> wins) {
                super.onPostExecute(wins);
                adapter.setList(wins);
                String gender = ApplicationPreferences.getInstance(getActivity().getApplicationContext()).getUser().getUser_sex().equals("2") ? getResources().getString(R.string.man) : getResources().getString(R.string.women);
                textview_wins_count.setText(wins.size() + " " + gender);
                progress.setVisibility(View.INVISIBLE);
                textview_wins_count.setVisibility(View.VISIBLE);
                imageview_like.setVisibility(View.VISIBLE);
                textview_plain_text.setVisibility(View.VISIBLE);
            }
        }.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        current_temp_win = (Win)parent.getAdapter().getItem(position);
        createWinDialog(current_temp_win).show();
    }

    private MaterialDialog createWinDialog(Win win){
        dialog = new MaterialDialog.Builder(getActivity())
                .customView(R.layout.dialog_my_wins_item)
                .build();

        CircleImageView image_voter_profile = (CircleImageView) dialog.getCustomView().findViewById(R.id.imageview_dialog_voter_profile);
        RobotoLightTextView textview_voter_name = (RobotoLightTextView) dialog.getCustomView().findViewById(R.id.textview_dialog_voter_name);
        RobotoThinTextView textview_voter_live = (RobotoThinTextView) dialog.getCustomView().findViewById(R.id.textview_dialog_voter_live);
        RobotoBlackTextview textview_send_message = (RobotoBlackTextview) dialog.getCustomView().findViewById(R.id.textview_dialog_voter_send_message);
        RobotoBlackTextview textview_add_on_facebook = (RobotoBlackTextview) dialog.getCustomView().findViewById(R.id.textview_dialog_voter_add_friend);

        Picasso.with(getActivity().getApplicationContext()).load(win.getVoter().getImage_url()).into(image_voter_profile);
        textview_voter_name.setText(win.getVoter().getUser_name() + " " + win.getVoter().getUser_surname() + "(" + win.getVoter().getUser_age() + ")");
        textview_voter_live.setText(win.getVoter().getUser_live());

        textview_send_message.setOnClickListener(this);
        textview_add_on_facebook.setOnClickListener(this);

        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textview_dialog_voter_add_friend:
                //Toast.makeText(getActivity().getApplicationContext(),getResources().getString(R.string.no_available_yet),Toast.LENGTH_SHORT).show();
                sendFacebookFriendRequest(current_temp_win.getVoter());
                break;
            case R.id.textview_dialog_voter_send_message:
                Toast.makeText(getActivity().getApplicationContext(),getResources().getString(R.string.no_available_yet),Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void sendFacebookFriendRequest(User voter){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(voter.getProfile_url()));
        getActivity().startActivity(browserIntent);
    }

}
