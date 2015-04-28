package push;

import com.parse.ParsePush;

import org.json.JSONException;
import org.json.JSONObject;

import model.User;

/**
 * Created by mertsimsek on 12/01/15.
 */
public class PushMessageCreator {

    private static PushMessageCreator instance = null;

    private PushMessageCreator() {
    }

    public static PushMessageCreator getInstance(){
        if(instance == null)
            instance = new PushMessageCreator();
        return instance;
    }

    public void sendVotedUser(User voter,User voted) {

        JSONObject data = null;

        try {
            data = new JSONObject("{\"voter_id\": \"" + voter.getUser_id() +"\"," +
                    "\"voter_name\": \"" + voter.getUser_name()+ "\"," +
                    "\"voter_surname\": \"" + voter.getUser_surname()+ "\"," +
                    "\"voter_age\": \"" + voter.getUser_age()+ "\"," +
                    "\"voter_sex\": \"" + voter.getUser_sex()+ "\"," +
                    "\"voter_live\": \"" + voter.getUser_live()+ "\"," +
                    "\"voter_image_url\": \"" + voter.getImage_url()+ "\"," +
                    "\"voter_profile_url\": \"" + voter.getImage_url()+ "\"," +
                    "\"voter_social_id\": \"" + voter.getSocial_id() + "\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ParsePush push = new ParsePush();
        push.setChannel("whichone" + voted.getSocial_id());
        push.setData(data);
        push.sendInBackground();

    }
}
