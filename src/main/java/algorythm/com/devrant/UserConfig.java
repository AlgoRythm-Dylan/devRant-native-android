package algorythm.com.devrant;

public class UserConfig {

    private String authToken = "";

    UserConfig(){

    }

    String getAuthToken(){
        return authToken;
    }

    void setAuthToken(String authToken){
        // Disapprove of null strings
        if(authToken == null){
            this.authToken = "";
            return;
        }
        this.authToken = authToken;
    }

}
