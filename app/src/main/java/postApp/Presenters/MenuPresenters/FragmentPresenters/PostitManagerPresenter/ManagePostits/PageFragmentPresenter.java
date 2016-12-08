package postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostits;

import android.view.View;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.ManagePostits.PageFragment;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.ManagePostits.PageFragmentHandler;

/**
 * Created by adinH on 2016-11-30.
 */

public class PageFragmentPresenter {
    PageFragment PageFragment;
    PageFragmentHandler PageFragmentHandler;

    public PageFragmentPresenter(PageFragment PageFragment, String color){
        this.PageFragment = PageFragment;
        this.PageFragmentHandler = new PageFragmentHandler(this);
        PageFragment.buildDelete();
        PageFragment.buildEdit();
        SetColor(color);
    }
    public void ShowMessage(String S){
        PageFragment.ShowMessage(S);
    }
    public void NoMirror(){
        PageFragment.NoMirror();
    }
    public void DeletePostit(String topic, String ID){
        PageFragmentHandler.DeletePostit(topic, ID);
    }
    public void EditPostit(String topic, String id, String Text){
        PageFragmentHandler.EditPostit(topic, id, Text);
    }
    public void BlueClick(){
        PageFragment.ColorBlue();
    }
    public void PinkClick(){
        PageFragment.ColorPink();
    }
    public void PurpleClick(){
        PageFragment.ColorPurple();
    }
    public void YellowClick(){
        PageFragment.ColorYellow();
    }
    public void GreenClick(){
        PageFragment.ColorGreen();
    }
    public void OrangeClick(){
        PageFragment.ColorOrange();
    }
    public void SetColor(String S){
        PageFragmentHandler.SetColor(S);

    }
    public void HideKeyboard(View V){
        PageFragment.hideKeyboard(V);
    }

    public void ReloadScreen(){
        PageFragment.ReloadScreen();
    }
}
