package postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter;

import android.view.View;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.PostitView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.PostitHandler;

/**
 * Created by Emanuel on 19/11/2016.
 */

public class PostitPresenter {

    PostitView PostitView;
    PostitHandler PostitHandler;

    public PostitPresenter(PostitView PostitView){
        this.PostitView = PostitView;
        this.PostitHandler = new PostitHandler(PostitView, this);
        PostitView.BuildColorChoice();
        SetColor("yellow");
    }
    public void ShowMessage(String S){
        PostitView.ShowMessage(S);
    }
    public void NoMirror(){
        PostitView.NoMirror();
    }
    public void PublishPostit(String text, String topic, String user){
        PostitHandler.PublishPostit(text,topic, user);
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
        PostitView.NoEcho();
    }
    public void AwaitEcho(){
        PostitHandler.AwaitEcho();
    }


}
