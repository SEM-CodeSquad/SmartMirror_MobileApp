package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.ManagePostits;

import java.util.Observable;
import java.util.Observer;
import android.os.Handler;
import postApp.DataHandlers.AppCommons.Postits.DeletePostit;
import postApp.DataHandlers.AppCommons.Postits.EditPostit;
import postApp.DataHandlers.AppCommons.Postits.ReadPostits;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostits.ManagePostitsPresenter;


/**
 * Class used as a handler for the ManagePostits presenter and ManagePostits view
 */
public class ManagePostitsHandler implements Observer {

    private ReadPostits readPostits;
    private ManagePostitsPresenter ManagePostitsPresenter;

    /**
     * Sets the presenter
     * @param ManagePostitsPresenter the presenter
     */
    public ManagePostitsHandler(ManagePostitsPresenter ManagePostitsPresenter){
        this.ManagePostitsPresenter = ManagePostitsPresenter;
    }

    /**
     * Starts a ReadPostits class
     * @param user The user we want to read postits from
     */
    public void ReadPost(String user){
        readPostits = new ReadPostits(user);
        readPostits.addObserver(this);
    }

    /**
     * On update we use the presenters method for doneloading and StartLoadingPost with the postitArray we get from readpostits class
     * @param observable the observable
     * @param data the data
     */
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
