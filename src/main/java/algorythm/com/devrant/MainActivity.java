package algorythm.com.devrant;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import java.util.ArrayList;
import algorythm.com.devrant.Adapters.MainRantViewAdapter;
import algorythm.com.devrant.rantAPI.*;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<Rant> rants = new ArrayList<Rant>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Begin setting up the rant view
        final RecyclerView recyclerView = findViewById(R.id.mainRantViewer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the filter button and its onclick listener
        ImageButton filterButton = findViewById(R.id.rantMainFilterButton);
        registerForContextMenu(filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.showContextMenu();
            }
        });

        // When the user clicks the hamburger menu, the drawer opens
        final DrawerLayout mainDrawerLayout = findViewById(R.id.mainDrawerLayout);
        ImageButton hamburger = findViewById(R.id.mainHamburger);

        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        // Load a few rants (This is a test still)
        final Context thisContext = this;
        final Handler handler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable(){
            @Override
            public void run(){
                rants = devRant.getRants();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(new MainRantViewAdapter(thisContext, rants));
                    }
                });
            }
        }).start();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Filter by");
        menu.add(0, 1, 0, "Algo");
        menu.add(0, 2, 0, "Recent");
        menu.add(0, 3, 0, "Top");
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == 1){
            // Fucking sort by Algo
        }
        else if(menuItem.getItemId() == 2){
            // Fucking sort by recent
        }
        else{
            // Fucking sort by TOP
        }
        // Dunno, sum shit.
        return true;
    }

}
