package algorythm.com.devrant.rantAPI;


public class Comment extends devRantData{

    /**
     * Defaunt contructor for a comment
     */
    public Comment(){
        setType(COMMENT);
    }

    /**
     * Create a comment from the essential information. Does not contact the API
     * @param id The comment ID
     * @param score The score of the comment, not the user
     * @param text The comment's text
     * @param author The author of the comment
     */
    public Comment(int id, int score, String text, Profile author){
        this();
        setId(id);
        setScore(score);
        setText(text);
        setAuthor(author);
    }

    /**
     * Get a comment given an ID. Contancts the API for information
     * @param id The comment ID
     */
    public Comment(int id){
        // TODO
    }

}