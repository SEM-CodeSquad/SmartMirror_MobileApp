package postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView;

/**
 * Created by adinH on 2016-10-27.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.PreferencesPresenter;


public class PreferencesView extends Fragment {
        View myView;
    Switch disableswitch;
    Switch busswitch;
    Switch newsswitch;
    Switch clockswitch;
    Switch calenderswitch;
    Switch weatherswitch;
    Switch postitswitch;
    PreferencesPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.preferences, container, false);

        disableswitch = (Switch) myView.findViewById(R.id.disableswitch);
        busswitch = (Switch) myView.findViewById(R.id.busswitch);
        //newsswitch = (Switch) myView.findViewById(R.id.newsswitch);
        clockswitch = (Switch) myView.findViewById(R.id.clockswitch);
        calenderswitch = (Switch) myView.findViewById(R.id.disableswitch);
        weatherswitch = (Switch) myView.findViewById(R.id.disableswitch);
        postitswitch = (Switch) myView.findViewById(R.id.disableswitch);
        presenter = new PreferencesPresenter(this);
        return myView;
        }

    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Preferences");
    }
    public void displaySuccPub(String S){
        Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
    }
    public void NoMirrorChosen(){
            // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
            Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
    }
    public void PublishPrefs(){
        //presenter.PublishPrefs(topic, user, news, bus, weather, clock, calender, external);
    }
}
