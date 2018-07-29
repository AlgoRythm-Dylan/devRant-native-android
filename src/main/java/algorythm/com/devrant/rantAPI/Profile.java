package algorythm.com.devrant.rantAPI;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Profile {

    private int id, score, createdTime;
    private String name, skills, github, website, about, location;
    private boolean plusplus;
    private Bitmap profilePic;
    private int backgroundColor;

    public Profile(int id){
        // HTTPS request to devRant API
    }

    public Profile(String name){
        // Get ID, then load from there
    }

    public Profile(int id,
                   int score,
                   String name){
        this.id = id;
        this.score = score;
        this.name = name;
    }

    // Getters and setters!
    // Gotta love that object-oriented encapsulation /s
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isPlusplus() {
        return plusplus;
    }

    public void setPlusplus(boolean plusplus) {
        this.plusplus = plusplus;
    }

    public Bitmap getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Bitmap profilePic) {
        this.profilePic = profilePic;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
