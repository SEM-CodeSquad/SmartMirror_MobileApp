package postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.HidePostitView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.HidePostitHandler;

/**
 * Created by adinH on 2016-12-03.
 */

public class HidePostitPresenter {

    private HidePostitHandler HidePostitHandler;
    private HidePostitView HidePostitView;
    public HidePostitPresenter(HidePostitView HidePostitView, String topic){
        this.HidePostitView = HidePostitView;
        this.HidePostitHandler = new HidePostitHandler(this, topic);
    }

    public void FilterPost(String topic, String user, String yellow, String blue, String orange, String pink, String green, String purple){
        HidePostitHandler.FilterPost(topic, user, yellow, blue, orange, pink, green, purple);
    }
    public void ShowMessage(String S){
        HidePostitView.displaySuccPub(S);
    }
    public void NoMirror(){
        HidePostitView.NoMirrorChosen();
    }
    public void Loading() {
        HidePostitView.Loading();
    }
    public void NoEcho(){
        HidePostitView.UnsuccessfulPublish();
    }
    public void DoneLoading() {
        HidePostitView.DoneLoading();
    }

}
