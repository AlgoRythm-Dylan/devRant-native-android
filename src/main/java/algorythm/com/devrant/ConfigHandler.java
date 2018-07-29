package algorythm.com.devrant;

import org.json.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ConfigHandler {

    static UserConfig loadConfig(File file){
        StringBuilder text = new StringBuilder();

        UserConfig userConfig = new UserConfig();

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while((line = br.readLine()) != null){
                text.append(line);
                text.append("\n");
            }

            br.close();

            try {
                JSONObject obj = new JSONObject(text.toString());
                userConfig.setAuthToken(obj.getString("authToken"));
                return userConfig;
            }
            catch(JSONException e){
                return userConfig;
            }
        }
        catch(IOException e){
            return userConfig;
        }
    }

}
