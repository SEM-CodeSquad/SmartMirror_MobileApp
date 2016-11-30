package postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostits;

import org.json.simple.JSONArray;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.ManagePostits.ManagePostitsView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.ManagePostits.ManagePostitsHandler;

/**
 * Created by Emanuel on 19/11/2016.
 */

public class ManagePostitsPresenter {
    ManagePostitsView ManagePostitsView;
    ManagePostitsHandler ManagePostitsHandler;
    public ManagePostitsPresenter(ManagePostitsView ManagePostitsView){
        this.ManagePostitsView = ManagePostitsView;
        this.ManagePostitsHandler = new ManagePostitsHandler(this);
        Loading();
    }
    public void FetchPost(String User){
        ManagePostitsHandler.ReadPost(User);
    }
    public void EditPost(String text,  String id){
        ManagePostitsHandler.EditPost(text, id);
    }
    public void DeletePost(String id){
        ManagePostitsHandler.DeletePost(id);
    }
    public void DoneLoading(){
        ManagePostitsView.DoneLoading();
    }
    public void Loading(){
        ManagePostitsView.Loading();
    }
    public void StartLoadingPost(JSONArray PostIts){
        ManagePostitsView.UpdateGuiPost(PostIts);
    }
}
