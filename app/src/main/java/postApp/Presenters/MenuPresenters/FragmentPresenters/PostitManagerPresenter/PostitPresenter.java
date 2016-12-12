package postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter;

import android.view.View;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.PostitView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.PostitHandler;

/**
 * Created by Emanuel on 19/11/2016.
 */

public class PostitPresenter {

    private PostitView PostitView;
    private PostitHandler PostitHandler;

    public PostitPresenter(PostitView PostitView, String mirrorid){
        this.PostitView = PostitView;
        this.PostitHandler = new PostitHandler(this, mirrorid);
        PostitView.BuildColorChoice();
        SetColor("yellow");
    }
    public void NoMirror(){
        PostitView.NoMirror();
    }
    public void PublishPostit(String text, String topic, String user, String date){
        PostitHandler.PublishPostit(text,topic, user, date);
    }
    public void BlueClick(){
        PostitView.ColorBlue();
    }
    public void PinkClick(){
        PostitView.ColorPink();
    }
    public void PurpleClick(){
        PostitView.ColorPurple();
    }
    public void YellowClick(){
        PostitView.ColorYellow();
    }
    public void GreenClick(){
        PostitView.ColorGreen();
    }
    public void OrangeClick(){
        PostitView.ColorOrange();
    }
    public void ShowColors(){
        PostitView.ShowColors();
    }
    public void SetColor(String S){
        PostitHandler.SetColor(S);
    }
    public void HideKeyboard(View V){
        PostitView.hideKeyboard(V);
    }
    public void NoEcho(){
        PostitView.UnsuccessfulPublish();
    }
    public void Loading() {
        PostitView.Loading();
    }

    public void DoneLoading() {
        PostitView.DoneLoading();
    }
}
