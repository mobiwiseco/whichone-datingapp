package task;

import android.os.AsyncTask;

import java.util.ArrayList;

import api.Api;
import model.User;

/**
 * Created by mertsimsek on 10/01/15.
 */
public class NewMatchTask extends AsyncTask<Void,Void,ArrayList<User>>{

    String user_id;

    public NewMatchTask(String user_id) {
        this.user_id = user_id;
    }

    @Override
    protected ArrayList<User> doInBackground(Void... params) {
        ArrayList<User> match_users = null;
        match_users = Api.getInstance().getNewMatch(user_id);
        return match_users;
    }
}
