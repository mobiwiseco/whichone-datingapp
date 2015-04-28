package akilliyazilim.whichone;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

/**
 * Created by mertsimsek on 12/01/15.
 */
public class Application extends android.app.Application{

    public Application() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this,"APP_ID","CLIENT_KEY");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        PushService.setDefaultPushCallback(this, WhichoneActivity.class);
    }
}
