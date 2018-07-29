package algorythm.com.devrant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import algorythm.com.devrant.rantAPI.*;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        final Intent intent = new Intent(this, MainActivity.class);

        final TextView testTextView = (TextView) findViewById(R.id.testText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);

                /*final Handler handler = new Handler(Looper.getMainLooper());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Starting the fucking request");
                        final String response = devRant.httpsPost("https://devrant.com/api/users/auth-token?app=3&username=AlgoRythm&password=notrealpassword");
                        System.out.println(response);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                testTextView.setText(response);
                            }
                        });
                    }
                }).start();*/
            }
        });

    }

}
