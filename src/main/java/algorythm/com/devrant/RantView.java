package algorythm.com.devrant;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import algorythm.com.devrant.rantAPI.*;
import algorythm.com.devrant.Adapters.RantViewAdapter;

public class RantView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rant_view);

        final Rant currentRant = Globals.currentRant;

        // The comments area
        final RecyclerView rantViewArea = findViewById(R.id.rantViewArea);
        rantViewArea.setLayoutManager(new LinearLayoutManager(this));
        rantViewArea.setHasFixedSize(true);

        final Handler handler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable(){
            @Override
            public void run() {
                // Complete the rant by reloading it
                // (complete rants have comments)
                currentRant.reload();
                final RantViewAdapter adapter = new RantViewAdapter(currentRant.toData());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        rantViewArea.setAdapter(adapter);
                    }
                });
            }
        }).start();


    }

}
