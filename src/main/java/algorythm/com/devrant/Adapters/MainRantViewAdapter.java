package algorythm.com.devrant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import algorythm.com.devrant.Globals;
import algorythm.com.devrant.R;
import algorythm.com.devrant.RantView;
import algorythm.com.devrant.rantAPI.*;
import java.util.ArrayList;

public class MainRantViewAdapter extends RecyclerView.Adapter<MainRantViewAdapter.ViewHolder>{

    private ArrayList<Rant> rants;
    private Context context;

    public MainRantViewAdapter(Context context, ArrayList<Rant> rants){
        this.context = context;
        this.rants = rants;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.rant_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Rant currentRant = rants.get(position);
        holder.score.setText(String.valueOf(currentRant.getScore()));
        if(currentRant.getText().length() > 250){
            holder.rant.setText(currentRant.getText().substring(0, 250) + "... [Read more]");
        }
        else{
            holder.rant.setText(currentRant.getText());
        }

        holder.tagsArea.removeAllViews();

        int dp5 = (int)((context.getResources().getDisplayMetrics().density + 0.5) * 5);
        ArrayList<String> tags = currentRant.getTags();
        int i = 0;
        while(i < tags.size()){
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(dp5, dp5, 0, dp5);
            textView.setLayoutParams(layoutParams);
            textView.setText(tags.get(i));
            textView.setPadding(dp5, dp5, dp5, dp5);
            textView.setBackgroundResource(R.drawable.tag_background);
            holder.tagsArea.addView(textView);
            i++;
        }
        View spacer = new View(context);
        spacer.setLayoutParams(new LinearLayout.LayoutParams(dp5, LinearLayout.LayoutParams.MATCH_PARENT));
        holder.tagsArea.addView(spacer);

        // onClickListeners go here!
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == holder.parentLayout){
                    Globals.currentRant = currentRant;
                    Intent intent = new Intent(context, RantView.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        Button increment, decrement;
        TextView score, rant;
        LinearLayout parentLayout, tagsArea;
        public ViewHolder(View itemView) {
            super(itemView);
            increment = itemView.findViewById(R.id.rantIncrement);
            decrement = itemView.findViewById(R.id.rantDecrement);
            score = itemView.findViewById(R.id.rantScore);
            rant = itemView.findViewById(R.id.rantText);
            parentLayout = itemView.findViewById(R.id.rantLayoutParent);
            tagsArea = itemView.findViewById(R.id.rantTags);
        }
    }

}
