package postApp.DataHandlers.Postits;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Emanuel on 21/11/2016.
 */

public class ReadPostits {

    private Context appContext;
    private String file = "SavedPostits.txt";
    private String[] postArray;

    public ReadPostits(Context applicationContext) {
        this.appContext = applicationContext;
    }

    private void ReadFile(String fileName)
    {
        FileInputStream fis = null;
        try {
            fis = appContext.openFileInput(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String counter;
        int count=0;
        try
        {
            while ((counter=br.readLine()) != null)
            {
                count++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        postArray = new String[count];

        String postLine;
        int i = 0;
        try
        {
            while((postLine=br.readLine())!=null)
            {
                this.postArray[i] = postLine;
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

    }

    public String[] getPostitArray(){
        return postArray;
    }

}
