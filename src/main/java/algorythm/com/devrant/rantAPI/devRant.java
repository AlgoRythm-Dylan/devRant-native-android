package algorythm.com.devrant.rantAPI;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.json.*;

import algorythm.com.devrant.Caching.ImageCache;
import algorythm.com.devrant.Caching.ImageCacheItem;
import algorythm.com.devrant.Globals;
import algorythm.com.devrant.Network;

import javax.net.ssl.HttpsURLConnection;

public class devRant {

    protected static final String API = "https://devrant.com/api/";
    protected static final String RANT = "devrant/rants/";
    protected static final String RANTS = "devrant/rants";
    protected static final String USERS = "users/";
    protected static final String USER_ID = "get-user-id/";
    public static String APP_STRING = "3";

    protected static String httpsGet(String location){
        try{
            URL url = new URL(location);
            URLConnection urlConnection = url.openConnection();
            InputStream in = urlConnection.getInputStream();
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");

        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String httpsPost(String location){
        try{
            URL url = new URL(location);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            InputStream in = urlConnection.getInputStream();
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");

        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Rant> getRants() {
        // This is what will be returned
        ArrayList<Rant> rants = new ArrayList<>();

        // Download a few rants...
        String rawData = httpsGet("https://www.devrant.com/api/devrant/rants?app=3");

        try{
            // Gotta get that rant
            JSONObject json = new JSONObject(rawData);
            JSONArray rantsArray = json.getJSONArray("rants");
            int i = 0;
            while(i < rantsArray.length()){
                // For ease of access
                JSONObject currentRant = rantsArray.getJSONObject(i);

                // Convert the JSONArray into an ArrayList of strings
                ArrayList<String> tags = new ArrayList<>();
                JSONArray arrTags = currentRant.getJSONArray("tags");
                int j = 0;
                while(j < arrTags.length()){
                    tags.add(arrTags.getString(j));
                    j++;
                }

                Profile ranterProfile = new Profile(currentRant.getInt("user_id"),
                        currentRant.getInt("user_score"), currentRant.getString("user_username"));


                // Parsey parsey parse
                Rant newRant = new Rant(
                    currentRant.getInt("id"),
                    currentRant.getInt("score"),
                    currentRant.getString("text"),
                    ranterProfile,
                    currentRant.getInt("num_comments"),
                    tags,
                    currentRant.getBoolean("edited"));
                i++;
                rants.add(newRant);
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        // Phew, finally give 'em back
        return rants;
    }

    public Profile login(String username, String password){
        String response = httpsPost(API);
        return null;
    }

    public static String userToIDURL(String username){
        return API + USER_ID + "?username=" + username + "&app=" + APP_STRING;
    }

    public static String userURL(int id){
        return API + USERS + String.valueOf(id) + "?app=" + APP_STRING;
    }

    public static String rantURL(int id){
        return API + RANT + String.valueOf(id) + "?app=" + APP_STRING;
    }

    public static String avatarURL(String location){
        return "https://devrant.com/avatars/" + location;
    }

}
