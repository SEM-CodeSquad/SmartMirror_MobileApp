package postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.Postits.ReadPostits;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostitsPresenter;

/**
 * Created by adinH on 2016-10-26.
 */
public class ManagePostitsView extends Fragment {
    View myView;
    ManagePostitsPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.mirror_postit, container, false);
        presenter = new ManagePostitsPresenter(this);
        FetchPost();
        presenter.DeletePost("33b77ede-006c-4bd9-a38a-ff565b47513f");
        return myView;
    }

    public void FetchPost(){
        presenter.FetchPost(((NavigationActivity) getActivity()).getUser());
    }
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Manage Mirror Postits");
    }
}
