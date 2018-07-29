package algorythm.com.devrant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Network {

    public static Bitmap downloadImage(String path){
        int fileLength = 0;
        try{
            URL url = new URL(path);
            URLConnection connection = url.openConnection();
            connection.connect();
            return BitmapFactory.decodeStream(connection.getInputStream());
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null; // Some issue occured
    }

}
