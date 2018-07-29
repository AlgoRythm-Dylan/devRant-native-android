package algorythm.com.devrant.Adapters;

import algorythm.com.devrant.*; // Different packages. Needed for R
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import algorythm.com.devrant.rantAPI.*;

/*

    This is a complex adapter because there will be two different type of view holders
    used. This is so that we can use recycling - a comment thread can have 100+ comments,
    and the performance hit for rendering all of those is not something I am willing to
    accept.

    RecyclerViews are not bound to a specific type of view holder, thankfully. This enables
    me to come up with a spearate view for the rant (at the very top - RantViewHolder) and
    and each comment (Under the rant - CommentViewHolder)

    This is one of the largest reasons I chose a devRantData class over just separate classes.
    In this instance, we can hold both rants and comments in the same ArrayList, data. This is
    pretty much the only reason this entire recycling behavior is possible.

 */
public class RantViewAdapter extends RecyclerView.Adapter {

    private ArrayList<devRantData> data;

    public RantViewAdapter(ArrayList<devRantData> data){
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Can use switch/ case here. Won't.
        if(viewType == devRantData.RANT){
            return new RantViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.rant_view_rant_layout, parent, false
            ));
        }
        else{
            // It's a comment.
            return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.comment_layout, parent, false
            ));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        devRantData currentData = data.get(position);
        if(currentData.getType() == devRantData.RANT){
            // It's a rant
            final Rant rant = (Rant) currentData;
            RantViewHolder rantHolder = (RantViewHolder) holder;
            rantHolder.score.setText(String.valueOf(currentData.getScore()));
            rantHolder.username.setText(currentData.getAuthor().getName());
            rantHolder.userScore.setText(String.valueOf(currentData.getAuthor().getScore()));
            rantHolder.text.setText(currentData.getText());
            Bitmap profilePic = rant.getAuthor().getProfilePic();
            if(profilePic != null){
                RoundedBitmapDrawable rbd = RoundedBitmapDrawableFactory.create(
                        rantHolder.profilePicture.getResources(), profilePic);
                rbd.setCircular(true);
                rantHolder.profilePicture.setImageDrawable(rbd);
            }
            else{
                final ImageView imageView = rantHolder.profilePicture;
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
                        imageView.setImageBitmap(bitmap);
                        Canvas canvas = new Canvas(bitmap);
                        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                        paint.setColor(rant.getAuthor().getBackgroundColor());
                        float size = imageView.getWidth() / 2;
                        canvas.drawCircle(size, size, size, paint);
                    }
                });
            }
            // Now, the tags. Remove all the previous tags that may be there
            rantHolder.tagsArea.removeAllViews();
            // Grab the resources from any of the controls. This case, by random choice, the score
            int dp5 = (int)((rantHolder.score.getResources().getDisplayMetrics().density + 0.5) * 5);
            ArrayList<String> tags = rant.getTags();
            for(int i = 0; i < tags.size(); i++){
                TextView textView = new TextView(rantHolder.score.getContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(dp5, dp5, 0, dp5);
                textView.setLayoutParams(layoutParams);
                textView.setText(tags.get(i));
                textView.setPadding(dp5, dp5, dp5, dp5);
                textView.setBackgroundResource(R.drawable.tag_background);
                rantHolder.tagsArea.addView(textView);
            }
            View spacer = new View(rantHolder.score.getContext());
            spacer.setLayoutParams(new LinearLayout.LayoutParams(dp5, LinearLayout.LayoutParams.MATCH_PARENT));
            rantHolder.tagsArea.addView(spacer);
        }
        else{
            // It's a comment
            final Comment comment = (Comment) currentData;
            CommentViewHolder commentHolder = (CommentViewHolder) holder;
            commentHolder.score.setText(String.valueOf(currentData.getScore()));
            commentHolder.username.setText(currentData.getAuthor().getName());
            commentHolder.userScore.setText(String.valueOf(currentData.getAuthor().getScore()));
            commentHolder.text.setText(currentData.getText());
            if(comment.getAuthor().isPlusplus()){
                commentHolder.isPlusPlus.setVisibility(View.VISIBLE);
            }
            else{
                commentHolder.isPlusPlus.setVisibility(View.INVISIBLE);
            }
            // Grab the profile picture from the author. This can be null
            Bitmap profilePic = comment.getAuthor().getProfilePic();
            if(profilePic != null){
                // If the bitmap is not null, then the user has a profile pic
                // RoundedBitmapDrawable is used here. A major advantage being the widget
                // doesnt need to be inflated to apply the RBD
                RoundedBitmapDrawable rbd = RoundedBitmapDrawableFactory.create(
                        commentHolder.profilePicture.getResources(), profilePic);
                rbd.setCircular(true);
                commentHolder.profilePicture.setImageDrawable(rbd);
            }
            else{
                // If the bitmap IS null, the user does not have a profile pic (Or it was not loaded)
                // So we will draw the profile's background color
                final ImageView imageView = commentHolder.profilePicture;
                // The ImageView needs to be inflated before we can draw it it so we can get accurate
                // dimenisons (non-zero)
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        // Create a bitmap to draw to, and attach it to the ImageView widget
                        Bitmap bitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
                        imageView.setImageBitmap(bitmap);
                        // Create a canvas from the bitmap
                        Canvas canvas = new Canvas(bitmap);
                        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                        // Get the background color from the user
                        paint.setColor(comment.getAuthor().getBackgroundColor());
                        // This variable can be used for the midpoint (x and y) and the circle radius
                        float size = imageView.getWidth() / 2;
                        canvas.drawCircle(size, size, size, paint);
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position){
        return data.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // The type of holder that will hold the rant at the top of the activity
    public static class RantViewHolder extends RecyclerView.ViewHolder {

        public TextView score, username, userScore, text;
        public ImageView profilePicture;
        public LinearLayout tagsArea;
        public RantViewHolder(View view){
            super(view);
            score = view.findViewById(R.id.rantViewRantScore);
            username = view.findViewById(R.id.rantViewUsername);
            userScore = view.findViewById(R.id.rantViewUserScore);
            text = view.findViewById(R.id.rantViewText);
            profilePicture = view.findViewById(R.id.rantViewProfilePic);
            tagsArea = view.findViewById(R.id.rantViewTagArea);
        }

    }

    // The type of holder that will hold the comments under the rant view
    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        public TextView score, username, userScore, text, isPlusPlus;
        public ImageView profilePicture;
        public CommentViewHolder(View view){
            super(view);
            score = view.findViewById(R.id.commentScore);
            username = view.findViewById(R.id.commentUsername);
            userScore = view.findViewById(R.id.commentUserScore);
            text = view.findViewById(R.id.commentText);
            isPlusPlus = view.findViewById(R.id.commentIsPlusPlus);
            profilePicture = view.findViewById(R.id.commentProfilePic);
        }

    }

}
