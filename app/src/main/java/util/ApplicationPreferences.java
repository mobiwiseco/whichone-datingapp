package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import model.User;

/**
 * Created by mertsimsek on 10/01/15.
 */
public class ApplicationPreferences {

    private static String KEY_USER_ID = "user_id";
    private static String KEY_USER_NAME = "name";
    private static String KEY_USER_SURNAME = "surname";
    private static String KEY_USER_AGE = "age";
    private static String KEY_USER_SEX = "sex";
    private static String KEY_USER_LIVE = "live";
    private static String KEY_USER_GCM = "gcm";
    private static String KEY_USER_IMAGE_URL = "image_url";
    private static String KEY_USER_SOCIAL_ID = "social_id";
    private static String KEY_USER_PROFILE_URL = "profile_url";
    private static String KEY_PLAY_STORE_SHOW_COUNT = "play_store";

    private static ApplicationPreferences instance = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private ApplicationPreferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    public static ApplicationPreferences getInstance(Context context){
        if(instance == null)
            instance = new ApplicationPreferences(context);
        return instance;
    }

    public void saveUser(User user){
        editor.putString(KEY_USER_ID,user.getUser_id());
        editor.putString(KEY_USER_NAME,user.getUser_name());
        editor.putString(KEY_USER_SURNAME,user.getUser_surname());
        editor.putInt(KEY_USER_AGE, user.getUser_age());
        editor.putString(KEY_USER_SEX, user.getUser_sex());
        editor.putString(KEY_USER_LIVE,user.getUser_live());
        editor.putString(KEY_USER_GCM,user.getUser_gcm());
        editor.putString(KEY_USER_IMAGE_URL,user.getImage_url());
        editor.putString(KEY_USER_SOCIAL_ID,user.getSocial_id());
        editor.putString(KEY_USER_PROFILE_URL,user.getProfile_url());
        editor.commit();
    }

    public User getUser(){
        User user = new User();
        user.setUser_id(sharedPreferences.getString(KEY_USER_ID,""));
        user.setUser_name(sharedPreferences.getString(KEY_USER_NAME,""));
        user.setUser_surname(sharedPreferences.getString(KEY_USER_SURNAME,""));
        user.setUser_age(sharedPreferences.getInt(KEY_USER_AGE,0));
        user.setUser_sex(sharedPreferences.getString(KEY_USER_SEX,""));
        user.setUser_live(sharedPreferences.getString(KEY_USER_LIVE,""));
        user.setUser_gcm(sharedPreferences.getString(KEY_USER_GCM,""));
        user.setImage_url(sharedPreferences.getString(KEY_USER_IMAGE_URL,""));
        user.setSocial_id(sharedPreferences.getString(KEY_USER_SOCIAL_ID,""));
        user.setProfile_url(sharedPreferences.getString(KEY_USER_PROFILE_URL,""));
        return user;
    }

    public int playMessageShownCount(){
        int result = sharedPreferences.getInt(KEY_PLAY_STORE_SHOW_COUNT,0);
        editor.putInt(KEY_PLAY_STORE_SHOW_COUNT,result+1);
        editor.commit();
        return  result;
    }
}
