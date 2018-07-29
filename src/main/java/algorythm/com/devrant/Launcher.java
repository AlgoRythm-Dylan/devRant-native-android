package algorythm.com.devrant;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;
import algorythm.com.devrant.Caching.ImageCache;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Globals.profilePictureCache = new ImageCache();

        File configFile = new File(getApplicationContext().getFilesDir(), "config.txt");
        UserConfig userConfig = ConfigHandler.loadConfig(configFile);

        if(userConfig.getAuthToken().equals("")){

            final Intent intent = new Intent(this, Login.class);
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 2000);
        }
    }
}
