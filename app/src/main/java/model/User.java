package model;

import android.util.Log;

/**
 * Created by mertsimsek on 09/01/15.
 */
public class User {
    String match_id;
    String user_id;
    String user_name;
    String user_surname;
    int user_age;
    String user_sex;
    String user_live;
    String user_gcm;
    String image_url;
    String social_id;
    String profile_url;

    public User() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_surname() {
        return user_surname;
    }

    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }

    public int getUser_age() {
        return user_age;
    }

    public void setUser_age(int user_age) {
        this.user_age = user_age;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        if(user_sex.toLowerCase().contains("female")){
            this.user_sex = "2";
        }
        else
            this.user_sex = "1";
    }

    public String getUser_live() {
        return user_live;
    }

    public void setUser_live(String user_live) {
        Log.v("TEST",user_live);
        this.user_live = user_live;
    }

    public String getUser_gcm() {
        return user_gcm;
    }

    public void setUser_gcm(String user_gcm) {
        this.user_gcm = user_gcm;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getSocial_id() {
        return social_id;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    @Override
    public String toString() {
        return this.getSocial_id() + this.getUser_name() + this.getUser_surname() + this.getUser_age() + this.getUser_sex() + this.getUser_live() + this.getImage_url();
    }
}
