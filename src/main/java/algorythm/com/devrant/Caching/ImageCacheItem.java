package algorythm.com.devrant.Caching;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

// NOTE: Memory-only cache for now.
public class ImageCacheItem extends CacheItem {

    private Bitmap image;

    public ImageCacheItem(String name, Bitmap image){
        setName(name);
        setImage(image);
        touch();
        //write();
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public void invalidate() {
        // Write to disk if it doesn't already exist
        /*File file = new File(getPath() + "/" + getName());
        file.delete();*/
    }

    public void write(){
        File file = new File(getPath() + "/" + getName());
        if(file.exists()){
            return;
        }
        // The image doesn't already exist. Cache it.
        FileOutputStream out;
        try{
            out = new FileOutputStream(getPath() + "/" + getName());
            image.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
