package postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostits;

import android.view.View;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.ManagePostits.PageFragment;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.ManagePostits.PageFragmentHandler;

/**
 * The presenter for the pagefragment and the interactor with the pagefragmenthandler
 */

public class PageFragmentPresenter {
    private PageFragment PageFragment;
    private PageFragmentHandler PageFragmentHandler;

    /**
     * We instatiate the PageFragmentHandler and injects this presenter. We build two alertdialogs and we set the color of the postits
     * @param PageFragment The view passed
     * @param color The color to set
     */
    public PageFragmentPresenter(PageFragment PageFragment, String color, String topic, String user){
        this.PageFragment = PageFragment;
        this.PageFragmentHandler = new PageFragmentHandler(this, topic, user);
        PageFragment.buildDelete();
        PageFragment.buildEdit();
        SetColor(color);
    }

    /**
     * Call the view to show a message
     * @param S The message
     */
    public void loading(String S){
        PageFragment.Loading(S);
    }

    /**
     * Method for calling the views NoMirror function
     */
    public void NoMirror(){
        PageFragment.NoMirror();
    }

    /**
     * Tell the handler to delete a postit
     * @param topic Pass the topic
     * @param ID PAss the ID
     */
    public void DeletePostit(String topic, String ID, String user){
        PageFragmentHandler.DeletePostit(topic, ID, user);
    }

    /**
     * Tell the handler to edit a Postit
     * @param topic The topic we are publishing to
     * @param id The Id of the postit we want to edit
     * @param Text The text we want to change
     */
    public void EditPostit(String topic, String id, String Text, String user){
        PageFragmentHandler.EditPostit(topic, id, Text, user);
    }

    /**
     * Method to call the views Color function
     */
    public void BlueClick(){
        PageFragment.ColorBlue();
    }
    /**
     * Method to call the views Color function
     */
    public void PinkClick(){
        PageFragment.ColorPink();
    }
    /**
     * Method to call the views Color function
     */
    public void PurpleClick(){
        PageFragment.ColorPurple();
    }
    /**
     * Method to call the views Color function
     */
    public void YellowClick(){
        PageFragment.ColorYellow();
    }
    /**
     * Method to call the views Color function
     */
    public void GreenClick(){
        PageFragment.ColorGreen();
    }
    /**
     * Method to call the views Color function
     */
    public void OrangeClick(){
        PageFragment.ColorOrange();
    }
    /**
     * Call the handlers function to setColor
     * @param S the string with the color name
     */
    private void SetColor(String S){
        PageFragmentHandler.SetColor(S);

    }

    /**
     * Call the views function to hide the keyboard
     * @param V The view passed
     */
    public void HideKeyboard(View V){
        PageFragment.hideKeyboard(V);
    }

    /**
     * Call the views function to reloadscreen
     */
    public void ReloadScreen(){
        PageFragment.ReloadScreen();
    }

    public void NoEcho(String s){
        PageFragment.UnsuccessfulPublish(s);
    }

    public void DoneLoading() {
        PageFragment.DoneLoading();
    }
}
