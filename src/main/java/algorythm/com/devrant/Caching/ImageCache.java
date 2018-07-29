package algorythm.com.devrant.Caching;

import android.graphics.Bitmap;

import java.util.HashMap;

public class ImageCache {

    private HashMap<String, ImageCacheItem> items;

    public ImageCache(){
        items = new HashMap<>();
    }

    public Bitmap getImage(String key){
        return items.get(key).getImage();
    }

    public void invalidate(String key){
        items.get(key).invalidate();
        items.remove(key);
    }

    public void add(ImageCacheItem item){
        items.put(item.getName(), item);
    }

    public boolean contains(String name){
        return items.containsKey(name);
    }

    public void checkValidity(){

    }

}
