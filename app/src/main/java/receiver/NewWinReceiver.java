package receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import akilliyazilim.whichone.LoginActivity;
import akilliyazilim.whichone.R;
import api.ApiConstants;
import model.User;

/**
 * Created by mertsimsek on 12/01/15.
 */
public class NewWinReceiver extends ParsePushBroadcastReceiver {

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);

        User voter = new User();

        try {
            String action = intent.getAction();
            String channel = intent.getExtras().getString("com.parse.Channel");
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            voter.setUser_id(json.getString(ApiConstants.RESPONSE_VOTER_ID));
            voter.setUser_name(json.getString(ApiConstants.RESPONSE_VOTER_NAME));
            voter.setUser_surname(json.getString(ApiConstants.RESPONSE_VOTER_SURNAME));
            voter.setUser_age(Integer.parseInt(json.getString(ApiConstants.RESPONSE_VOTER_AGE)));
            voter.setUser_sex(json.getString(ApiConstants.RESPONSE_VOTER_SEX));
            voter.setUser_live(json.getString(ApiConstants.RESPONSE_VOTER_LIVE));
            voter.setSocial_id(json.getString(ApiConstants.RESPONSE_VOTER_SOCIAL_ID));
            voter.setProfile_url(json.getString(ApiConstants.POST_PARAMETER_VOTER_PROFILE_URL));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        createNotification(context,voter);
    }

    private void createNotification(Context context, User voted) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] vibrate = {0, 100, 200, 300};
        Notification notification = new Notification.Builder(context).setContentTitle(voted.getUser_name() + voted.getUser_surname())
                .setContentText(voted.getUser_name() + context.getResources().getString(R.string.notif_text))
                .setSmallIcon(R.drawable.app_logo)
                .setContentIntent(pendingIntent).setSound(sound).setVibrate(vibrate).setAutoCancel(true)
                .build();

        notificationManager.notify(Integer.parseInt(voted.getSocial_id().substring(0, 3)), notification);
    }
}
