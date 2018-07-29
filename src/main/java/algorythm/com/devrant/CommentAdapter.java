package algorythm.com.devrant;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import algorythm.com.devrant.rantAPI.*;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private Rant rant;
    private Context context;

    public CommentAdapter(Context context, Rant rant){
        this.context = context;
        this.rant = rant;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.comment_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment currentComment = rant.getComments().get(position);
        Profile commentAuthor = currentComment.getAuthor();
        holder.username.setText(commentAuthor.getName());
        holder.commentScore.setText(String.valueOf(currentComment.getScore()));
        holder.userScore.setText(String.valueOf(commentAuthor.getScore()));
        holder.comment.setText(currentComment.getText());
    }

    @Override
    public int getItemCount() {
        return rant.getComments().size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username, commentScore, userScore, comment;
        public ViewHolder(View view){
            super(view);

            // Essentially parsing the given view, tbh
            username = view.findViewById(R.id.commentUsername);
            commentScore = view.findViewById(R.id.commentScore);
            userScore = view.findViewById(R.id.commentUserScore);
            comment = view.findViewById(R.id.commentText);
        }

    }

}
