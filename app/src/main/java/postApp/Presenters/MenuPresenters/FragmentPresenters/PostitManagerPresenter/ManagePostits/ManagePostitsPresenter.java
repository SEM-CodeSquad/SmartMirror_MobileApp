package postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostits;

import org.json.simple.JSONArray;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.ManagePostits.ManagePostitsView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.ManagePostits.ManagePostitsHandler;

/**
 * A class for interacting with the handler and presenting the view
 */

public class ManagePostitsPresenter {
    private ManagePostitsView ManagePostitsView;
    private ManagePostitsHandler ManagePostitsHandler;

    /**
     * Constructor that injects the presenter into the handler and gets the view. It also starts a loading screen when instantiated
     * @param ManagePostitsView
     */
    public ManagePostitsPresenter(ManagePostitsView ManagePostitsView){
        this.ManagePostitsView = ManagePostitsView;
        this.ManagePostitsHandler = new ManagePostitsHandler(this);
        Loading();
    }

    /**
     * We call the handlers function the fetch the postits
     * @param User the user that we fetch for
     */
    public void FetchPost(String User){
        ManagePostitsHandler.ReadPost(User);
    }

    /**
     * Tell the view to we are done with loading
     */
    public void DoneLoading(){
        ManagePostitsView.DoneLoading();
    }

    /**
     * Tell the view to start a progressdialog showing that we are loading
     */
    public void Loading(){
        ManagePostitsView.Loading();
    }

    /**
     * Tell the view to start loading the postit
     * @param PostIts pass a array of postits
     */
    public void StartLoadingPost(JSONArray PostIts){
        ManagePostitsView.UpdateGuiPost(PostIts);
    }
}
