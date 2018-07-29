package algorythm.com.devrant.Caching;

/**
 * Time-based cache items
 */
public abstract class CacheItem {

    long timeCreated, lifespan;
    private String name, path;

    public static long SECONDS(int amt){
        return amt * 1000;
    }

    public static long MINUTES(int amt){
        return SECONDS(60) * amt;
    }

    public static long HOURS(int amt){
        return MINUTES(60) * amt;
    }

    public static long DAYS(int amt){
        return HOURS(24) * amt;
    }

    public static long WEEKS(int amt){
        return DAYS(7) * amt;
    }

    public static long YEARS(int amt){
        return DAYS(365) * amt;
    }

    public CacheItem(){
        setLifespan(WEEKS(2));
        touch();
    }

    public CacheItem(String name, String path, long lifespan){
        setLifespan(lifespan);
        setPath(path);
        touch();
    }

    public void touch(){
        timeCreated = System.currentTimeMillis();
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public long getLifespan() {
        return lifespan;
    }

    public void setLifespan(long lifespan) {
        this.lifespan = lifespan;
    }

    public boolean isValid(){
        return lifespan > (System.currentTimeMillis() - timeCreated);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public abstract void invalidate();
}
