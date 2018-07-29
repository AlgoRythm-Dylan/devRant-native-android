package algorythm.com.devrant.rantAPI;

import android.graphics.Bitmap;
import android.graphics.Color;

import org.json.*;
import java.util.ArrayList;

import algorythm.com.devrant.Caching.ImageCacheItem;
import algorythm.com.devrant.Globals;
import algorythm.com.devrant.Network;

public class Rant extends devRantData {

    private int numComments;
    private ArrayList<String> tags;
    private ArrayList<Comment> comments;
    private String attachedImageURL;

    /**
     * Default constructor
     */
    public Rant(){
        setType(RANT);
    }

    /**
     * Construct a rant from the essential items. Does not contact the API
     * @param id Rant ID
     * @param score Rant score (Not user score)
     * @param text Rant text
     * @param author Rant creator
     * @param numComments Number of comments on rant
     * @param tags Rant's tags. Null means a rant has no comments
     * @param edited If the rant has been edited or not
     */
    public Rant(int id, int score, String text, Profile author, int numComments,
                ArrayList<String> tags, boolean edited){
        this();
        setId(id);
        setScore(score);
        setText(text);
        setAuthor(author);
        setEdited(edited);
        this.tags = tags;
        this.numComments = numComments;
    }

    /**
     * Loads a rant from an ID. Contacts the API for information
     * @param id
     */
    public Rant(int id){
        this();
        setId(id);
        reload();
    }

    /**
     * Download all information on this rant by contacting the API.
     * Can be used to complete a rant, or to grab the most current
     * information related to it.
     */
    public void reload(){
        try{
            // Download and parse the main data
            JSONObject response = new JSONObject(devRant.httpsGet(devRant.rantURL(id)));
            JSONObject rant = response.getJSONObject("rant");
            JSONArray comments = response.getJSONArray("comments");
            JSONArray tags = rant.getJSONArray("tags");
            // Update the comments
            numComments = comments.length();
            // Update the main data related to the rant
            setText(rant.getString("text"));
            setScore(rant.getInt("score"));
            setVoteState(rant.getInt("vote_state"));
            setEdited(rant.getBoolean("edited"));
            try{
                attachedImageURL = rant.getJSONObject("attached_image").getString("url");
            }
            catch(JSONException e){
                attachedImageURL = null;
            }
            // Set the profile up
            setAuthor(new Profile(
                    rant.getInt("user_id"),
                    rant.getInt("user_score"),
                    rant.getString("user_username")
            ));

            JSONObject ranterAvatar = rant.getJSONObject("user_avatar");
            try{
                String imageLocation = ranterAvatar.getString("i");
                if(!Globals.profilePictureCache.contains(imageLocation)){
                    // Download the image
                    Bitmap profilePic = Network.downloadImage(devRant.avatarURL(imageLocation));
                    Globals.profilePictureCache.add(new ImageCacheItem(imageLocation, profilePic));
                    author.setProfilePic(profilePic);
                }
                else{
                    author.setProfilePic(Globals.profilePictureCache.getImage(imageLocation));
                }
            }
            catch(JSONException e){
                // That's all right.
            }
            author.setBackgroundColor(Color.parseColor(
                    "#" + ranterAvatar.getString("b")
            ));

            // Convert the comments into Comment objects
            this.comments = new ArrayList<>();
            for(int i = 0; i < comments.length(); i++){
                JSONObject currentComment = comments.getJSONObject(i);
                Profile commentAuthorProfile = new Profile(currentComment.getInt("user_id"),
                        currentComment.getInt("user_score"),
                        currentComment.getString("user_username"));

                JSONObject userAvatar = currentComment.getJSONObject("user_avatar");
                try{

                    String imageLocation = userAvatar.getString("i");
                    if(!Globals.profilePictureCache.contains(imageLocation)){
                        // Download the image
                        Bitmap profilePic = Network.downloadImage(devRant.avatarURL(imageLocation));
                        Globals.profilePictureCache.add(new ImageCacheItem(imageLocation, profilePic));
                        commentAuthorProfile.setProfilePic(profilePic);
                        //System.out.println("[DEVRANT] Image was NOT cached:" + commentAuthorProfile.getName());
                    }
                    else{
                        commentAuthorProfile.setProfilePic(Globals.profilePictureCache.getImage(imageLocation));
                        //System.out.println("[DEVRANT] Image was cached: " + commentAuthorProfile.getName());
                    }
                }
                catch(JSONException e){
                    // The user didn't have a profile picture. That's okay. We'll draw their profile
                    // color, which we parse on the next line.
                }
                commentAuthorProfile.setBackgroundColor(Color.parseColor(
                        "#" + userAvatar.getString("b")
                ));


                this.comments.add(new Comment(
                        currentComment.getInt("id"),
                        currentComment.getInt("score"),
                        currentComment.getString("body"),
                        commentAuthorProfile
                ));
            }
            // Finally, convert the JSONArray of tags into an ArrayList of Strings
            this.tags = new ArrayList<>();
            for(int i = 0; i < tags.length(); i++){
                this.tags.add(tags.getString(i));
            }

        }
        catch (JSONException e){
            // Whoops - Fail elegantly
            setText("Problem loading rant");
            e.printStackTrace();
        }
    }

    /**
     * Convert a rant into devRantData for use in rant views (For recycling)
     * @return An ArrayList of devRantData for use in GUI
     */
    public ArrayList<devRantData> toData(){
        ArrayList<devRantData> data = new ArrayList<>();
        data.add(this);
        if(comments != null){
            for(int i = 0; i < comments.size(); i++){
                data.add(comments.get(i));
            }
        }
        return data;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String getAttachedImageURL() {
        return attachedImageURL;
    }

    public void setAttachedImageURL(String attachedImageURL) {
        this.attachedImageURL = attachedImageURL;
    }

}
