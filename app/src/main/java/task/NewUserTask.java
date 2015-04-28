package task;

import android.content.Context;
import android.os.AsyncTask;

import api.Api;
import model.User;

/**
 * Created by mertsimsek on 10/01/15.
 */
public class NewUserTask extends AsyncTask<Void,Void,Void> {

    User user;
    private Context context;

    public NewUserTask(User user,Context context) {
        this.user = user;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Api.getInstance().addNewUser(user,context);
        return null;
    }
}
