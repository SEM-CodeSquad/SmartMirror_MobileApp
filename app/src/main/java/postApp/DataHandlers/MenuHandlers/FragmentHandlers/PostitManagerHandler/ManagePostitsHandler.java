package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler;

import org.json.simple.JSONArray;

import postApp.DataHandlers.Postits.DeletePostit;
import postApp.DataHandlers.Postits.EditPostit;
import postApp.DataHandlers.Postits.ReadPostits;

/**
 * Created by Emanuel on 19/11/2016.
 */

public class ManagePostitsHandler {

    private ReadPostits readPostits;
    private DeletePostit deletpostit;
    private EditPostit editPostit;

    public String ReadPost(String user){

        readPostits = new ReadPostits(user);
        System.out.println(readPostits.getPostitArray().toString());
        return "r";
    }
    public void DeletePost(String idOne){
        deletpostit = new DeletePostit(idOne);
        deletpostit.getDeletedStatus();
    }

    public void EditPost(String text, String idOne){
        editPostit = new EditPostit(text, idOne);
        editPostit.getEditedStatus();
    }

}
