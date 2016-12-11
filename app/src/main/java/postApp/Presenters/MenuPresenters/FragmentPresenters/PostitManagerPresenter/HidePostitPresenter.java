package postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.HidePostitView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.HidePostitHandler;

/**
 * The class responsible for presenting the HidePostit view and interacting with the HidePostit Handler
 */

public class HidePostitPresenter {

    private HidePostitHandler HidePostitHandler;
    private HidePostitView HidePostitView;

    /**
     * We instantiate a HidePostitHandler with this presenter and the topic name
     * @param HidePostitView the view
     * @param topic the topic
     */
    public HidePostitPresenter(HidePostitView HidePostitView, String topic){
        this.HidePostitView = HidePostitView;
        this.HidePostitHandler = new HidePostitHandler(this, topic);
    }

    /**
     * Call the HidePostit handlers function to filter postits
     * @param topic topic to publish to
     * @param user the user
     * @param yellow color
     * @param blue color
     * @param orange color
     * @param pink color
     * @param green color
     * @param purple color
     */
    public void FilterPost(String topic, String user, String yellow, String blue, String orange, String pink, String green, String purple){
        HidePostitHandler.FilterPost(topic, user, yellow, blue, orange, pink, green, purple);
    }

    /**
     * Call the views function for no mirror chosen
     */
    public void NoMirror(){
        HidePostitView.NoMirrorChosen();
    }

    /**
     * Call the views function for the Loading screen
     */
    public void Loading() {
        HidePostitView.Loading();
    }

    /**
     * Call the views function when we receive no echo
     */
    public void NoEcho(){
        HidePostitView.UnsuccessfulPublish();
    }

    /**
     * Call the views function when we are done loading
     */
    public void DoneLoading() {
        HidePostitView.DoneLoading();
    }

}
