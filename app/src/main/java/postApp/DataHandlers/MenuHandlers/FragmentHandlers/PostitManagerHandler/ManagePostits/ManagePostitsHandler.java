package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.ManagePostits;

import java.util.Observable;
import java.util.Observer;
import android.os.Handler;
import postApp.DataHandlers.Postits.DeletePostit;
import postApp.DataHandlers.Postits.EditPostit;
import postApp.DataHandlers.Postits.ReadPostits;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostits.ManagePostitsPresenter;

/**
 * Created by Emanuel on 19/11/2016.
 */

public class ManagePostitsHandler implements Observer {

    private ReadPostits readPostits;
    private DeletePostit deletpostit;
    private EditPostit editPostit;
    ManagePostitsPresenter ManagePostitsPresenter;

    public ManagePostitsHandler(ManagePostitsPresenter ManagePostitsPresenter){
        this.ManagePostitsPresenter = ManagePostitsPresenter;
    }
    public void ReadPost(String user){
        readPostits = new ReadPostits(user);
        readPostits.addObserver(this);
    }
    public void DeletePost(String idOne){
        deletpostit = new DeletePostit(idOne);
        deletpostit.getDeletedStatus();
    }

    public void EditPost(String text, String idOne){
        editPostit = new EditPostit(text, idOne);
        editPostit.getEditedStatus();
    }

    @Override
    public void update(Observable observable, Object data) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ManagePostitsPresenter.DoneLoading();
            }
        }, 1000); // 3000 milliseconds delay
        ManagePostitsPresenter.StartLoadingPost(readPostits.getPostitArray());
    }

}
