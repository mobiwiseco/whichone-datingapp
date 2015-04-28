package task;

import android.os.AsyncTask;

import api.Api;

/**
 * Created by mertsimsek on 10/01/15.
 */
public class VoteMatchTask extends AsyncTask<Void,Void,Void>{

    String match_id;
    String voter_id;
    String winner_id;

    public VoteMatchTask(String match_id, String voter_id, String winner_id) {
        this.match_id = match_id;
        this.voter_id = voter_id;
        this.winner_id = winner_id;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Api.getInstance().voteMatch(match_id,winner_id,voter_id);
        return null;
    }
}
