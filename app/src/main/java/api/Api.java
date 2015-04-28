package api;

import android.content.Context;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import model.User;
import model.Win;
import util.ApplicationPreferences;

/**
 * Created by mertsimsek on 09/01/15.
 */
public class Api {

    private static Api instance = null;

    private Api() {

    }

    public static Api getInstance(){
        if(instance == null)
            instance = new Api();
        return instance;
    }

    public void addNewUser(User user,Context context){
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ApiConstants.URL_NEW_USER);
        HttpResponse response = null;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);

        try {
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_KEY,ApiConstants.KEY));
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_NAME,user.getUser_name()));
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_SURNAME,user.getUser_surname()));
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_AGE,String.valueOf(user.getUser_age())));
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_SEX,user.getUser_sex()));
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_LIVE,user.getUser_live()));
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_GCM,user.getUser_gcm()));
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_IMAGE_URL,user.getImage_url()));
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_SOCIAL_ID,user.getSocial_id()));
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_PROFILE_URL,user.getProfile_url()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            response = httpclient.execute(httppost);
            JSONObject json_onject = inputStreamToJson(response.getEntity().getContent());
            user.setUser_id(json_onject.getString(ApiConstants.RESPONSE_USER_ID));
            ApplicationPreferences.getInstance(context).saveUser(user);

        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void voteMatch(String match_id, String winner_id, String voter_id){
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ApiConstants.URL_VOTE_MATCH);
        HttpResponse response = null;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);

        try {
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_KEY,ApiConstants.KEY));
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_MATCH_ID,match_id));
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_WINNER_ID,winner_id));
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_VOTER_ID,voter_id));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

    }

    public ArrayList<Win> getMyWins(String user_id){
        ArrayList<Win> wins = new ArrayList<>();
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ApiConstants.URL_GET_WINS);
        HttpResponse response = null;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

        try {
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_KEY,ApiConstants.KEY));
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_USER_ID,user_id));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            JSONObject json_object = inputStreamToJson(response.getEntity().getContent());
            JSONArray json_array_losers = json_object.getJSONArray(ApiConstants.RESPONSE_ARRAY_NAME_LOSERS);

            for (int i = 0 ; i<json_array_losers.length() ; i++){
                JSONObject json_loser = json_array_losers.getJSONObject(i);
                Win win = new Win();

                User loser = new User();
                loser.setUser_id(json_loser.getString(ApiConstants.RESPONSE_USER_ID));
                loser.setUser_name(json_loser.getString(ApiConstants.RESPONSE_USER_NAME));
                loser.setUser_surname(json_loser.getString(ApiConstants.RESPONSE_SURNAME));
                loser.setUser_age(Integer.parseInt(json_loser.getString(ApiConstants.RESPONSE_AGE)));
                loser.setUser_sex(json_loser.getString(ApiConstants.RESPONSE_SEX));
                loser.setUser_live(json_loser.getString(ApiConstants.RESPONSE_LIVE));
                loser.setUser_gcm(json_loser.getString(ApiConstants.RESPONSE_GCM));
                loser.setImage_url(json_loser.getString(ApiConstants.RESPONSE_IMAGE_URL));
                loser.setSocial_id(json_loser.getString(ApiConstants.RESPONSE_SOCIAL_ID));
                loser.setProfile_url(json_loser.getString(ApiConstants.POST_PARAMETER_PROFILE_URL));
                win.setLoser(loser);

                User voter = new User();
                voter.setUser_id(json_loser.getString(ApiConstants.RESPONSE_VOTER_ID));
                voter.setUser_name(json_loser.getString(ApiConstants.RESPONSE_VOTER_NAME));
                voter.setUser_surname(json_loser.getString(ApiConstants.RESPONSE_VOTER_SURNAME));
                voter.setUser_age(Integer.parseInt(json_loser.getString(ApiConstants.RESPONSE_VOTER_AGE)));
                voter.setUser_sex(json_loser.getString(ApiConstants.RESPONSE_VOTER_SEX));
                voter.setUser_live(json_loser.getString(ApiConstants.RESPONSE_VOTER_LIVE));
                voter.setUser_gcm(json_loser.getString(ApiConstants.RESPONSE_VOTER_GCM));
                voter.setImage_url(json_loser.getString(ApiConstants.RESPONSE_VOTER_IMAGE_URL));
                voter.setSocial_id(json_loser.getString(ApiConstants.RESPONSE_VOTER_SOCIAL_ID));
                voter.setProfile_url(json_loser.getString(ApiConstants.POST_PARAMETER_VOTER_PROFILE_URL));
                win.setVoter(voter);

                wins.add(win);

            }

        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return wins;
    }

    public ArrayList<User> getNewMatch(String user_id){
        ArrayList<User> match_users = new ArrayList<>();
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ApiConstants.URL_GET_NEW_MATCH);
        HttpResponse response = null;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

        try {
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_KEY,ApiConstants.KEY));
            nameValuePairs.add(new BasicNameValuePair(ApiConstants.POST_PARAMETER_USER_ID,user_id));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            JSONObject json_object = inputStreamToJson(response.getEntity().getContent());
            JSONArray json_array = json_object.getJSONArray(ApiConstants.RESPONSE_ARRAY_NAME_MATCH);

            for (int i = 0 ; i<json_array.length(); i++){
                JSONObject json_match_user = json_array.getJSONObject(i);
                User user = new User();
                user.setMatch_id(json_match_user.getString(ApiConstants.RESPONSE_MATCH_ID));
                user.setUser_id(json_match_user.getString(ApiConstants.RESPONSE_USER_ID));
                user.setUser_name(json_match_user.getString(ApiConstants.RESPONSE_USER_NAME));
                user.setUser_surname(json_match_user.getString(ApiConstants.RESPONSE_SURNAME));
                user.setUser_age(Integer.parseInt(json_match_user.getString(ApiConstants.RESPONSE_AGE)));
                user.setUser_sex(json_match_user.getString(ApiConstants.RESPONSE_SEX));
                user.setUser_live(json_match_user.getString(ApiConstants.RESPONSE_LIVE));
                user.setUser_gcm(json_match_user.getString(ApiConstants.RESPONSE_GCM));
                user.setImage_url(json_match_user.getString(ApiConstants.RESPONSE_IMAGE_URL));
                user.setSocial_id(json_match_user.getString(ApiConstants.RESPONSE_SOCIAL_ID));
                user.setProfile_url(json_match_user.getString(ApiConstants.RESPONSE_PROFILE_URL));
                match_users.add(user);
            }

        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return match_users;
    }

    public JSONObject inputStreamToJson(InputStream is) throws IOException, JSONException
    {
        JSONObject json_object = null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        StringBuilder json = new StringBuilder();
        json.append(reader.readLine() + "\n");
        String read = "";

        while ((read = reader.readLine()) != null)
            json.append(read + "\n");

        JSONTokener tokener = new JSONTokener(json.toString());
        Object json_helper = tokener.nextValue();
        if (json_helper != null && json_helper instanceof JSONObject)
            json_object = (JSONObject) json_helper;

        reader.close();
        return json_object;
    }


}
