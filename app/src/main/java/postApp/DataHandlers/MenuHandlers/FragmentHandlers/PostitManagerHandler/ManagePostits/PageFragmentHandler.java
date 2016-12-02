package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.ManagePostits;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.ManagePostits.PageFragment;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.MqTTHandler.Echo;
import postApp.DataHandlers.AppCommons.Postits.DeletePostit;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostits.PageFragmentPresenter;

/**
 * Created by adinH on 2016-11-30.
 */

public class PageFragmentHandler implements Observer {
        private String color;
        private PageFragment PageFragment;
        private PageFragmentPresenter PageFragmentPresenter;
        private String text;
        private String idOne;
        private Echo echo;
        private long start;
        private long end;
        private String topic;
        DeletePostit RemovePost;


        public PageFragmentHandler(PageFragment PageFragment, PageFragmentPresenter PageFragmentPresenter){
            this.PageFragment = PageFragment;
            this.PageFragmentPresenter = PageFragmentPresenter;
        }



        public void SetColor(String color){
            this.color = color;

            if(color.equals("yellow")){
                PageFragmentPresenter.YellowClick();
            }
            else if(color.equals("purple")){
                PageFragmentPresenter.PurpleClick();
            }
            else if(color.equals("pink")){
                PageFragmentPresenter.PinkClick();
            }
            else if(color.equals("blue")){
                PageFragmentPresenter.BlueClick();
            }
            else if(color.equals("green")){
                PageFragmentPresenter.GreenClick();
            }
            else if(color.equals("orange")){
                PageFragmentPresenter.OrangeClick();
            }
        }

        public void DeletePostit(String topic, String UUID) {
            this.topic = topic;
            idOne = UUID;
            //AwaitEcho();
            JsonBuilder R = new JsonBuilder();
            String S;
            if (topic != "No mirror chosen") {
                try {
                    S = R.execute(topic, "postIt action", UUID, "delete", "none").get();
                } catch (InterruptedException e) {
                    S = "Did not publish";
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    System.out.println(e);
                    S = "Warning: Did Not Publish";
                }
                RemovePost();
                PageFragmentPresenter.ShowMessage(S);
            } else {
                PageFragmentPresenter.NoMirror();
            }
        }


        public void AwaitEcho(){
            String topic123 = "dit029/SmartMirror/" +topic + "/echo";
            echo = new Echo(topic123, topic);
            echo.addObserver(this);
            start = System.currentTimeMillis();
            end = start + 3*1000; // 3 seconds * 1000 ms/sec
            if (System.currentTimeMillis() > end){
                PageFragmentPresenter.NoEcho();
            }

        }
        public void RemovePost(){
            RemovePost = new DeletePostit(idOne);
            RemovePost.addObserver(this);
        }


        @Override
        public void update(Observable observable, Object data) {
            PageFragmentPresenter.ReloadScreen();  //returns boolean saved, if saved postit or not
        }
}
