package algorythm.com.devrant.rantAPI;

/**
 * A datatype which houses the common items to all devRant
 * data (rants and comments, currently)
 */
public class devRantData {

    public static final int RANT = 0;
    public static final int COMMENT = 1;

    protected String text;
    protected Profile author;
    protected int score, voteState, id, type;
    protected boolean edited;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Profile getAuthor() {
        return author;
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getVoteState() {
        return voteState;
    }

    public void setVoteState(int voteState) {
        this.voteState = voteState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
