package task;

import android.os.AsyncTask;

import java.util.ArrayList;

import api.Api;
import model.Win;

/**
 * Created by mertsimsek on 10/01/15.
 */
public class MyWinsTask extends AsyncTask<Void,Void,ArrayList<Win>>{

    String user_id;

    public MyWinsTask(String user_id) {
        this.user_id = user_id;
    }

    @Override
    protected ArrayList<Win> doInBackground(Void... params) {
        ArrayList<Win> wins = null;
        wins = Api.getInstance().getMyWins(user_id);
        return wins;
    }
}
