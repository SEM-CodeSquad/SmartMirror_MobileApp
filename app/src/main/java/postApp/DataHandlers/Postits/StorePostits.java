package postApp.DataHandlers.Postits;


import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;

import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.PostitHandler;

/**
 * @author Emanuel on 21/11/2016.
 */

public class StorePostits {
    private String postit;

    public void StorePostits(){
        postit= PostitHandler.getPostitInfo();
        store();
    }

    private void store(){
//        try {
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("psotits.txt", Context.MODE_PRIVATE));
//            outputStreamWriter.write(postit);
//            outputStreamWriter.close();
//        }
//        catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
    }

}
