package postApp.DataHandlers.Postits;

import android.content.Context;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author Emanuel on 21/11/2016.
 */

public class StorePostits {

    private Context appContext;
    private String text;
    private String color;
    private String id;
    private String file = "SavedPostits.txt";
    private String postit;

    public StorePostits(Context applicationContext, String text, String color, String id) {
        this.appContext = applicationContext;
        this.text = text;
        this.color = color;
        this.id = id;
        this.postit = color + "/" + id + "/" + text;
        Store();
    }

    private void Store(){
        try {
            FileOutputStream fos;
            fos = appContext.openFileOutput(file, Context.MODE_APPEND);
            fos.write(postit.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
