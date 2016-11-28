package postApp.ActivitiesView.MenuView.FragmentViews.ExtraInfoView;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;

/**
 * Created by adinH on 2016-10-26.
 */
public class AboutView extends Fragment {
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.about, container, false);
        return myView;
    }
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("About");
    }
}