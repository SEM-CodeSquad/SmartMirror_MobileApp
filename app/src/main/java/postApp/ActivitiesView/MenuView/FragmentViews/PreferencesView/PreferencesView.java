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
import android.widget.Button;
import android.widget.CompoundButton;
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
    Switch deviceswitch;
    Switch weatherswitch;
    Switch postitswitch;
    Switch greetingsswitch;
    PreferencesPresenter presenter;
    Button publish;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.preferences, container, false);

        disableswitch = (Switch) myView.findViewById(R.id.disableswitch);
        busswitch = (Switch) myView.findViewById(R.id.busswitch);
        newsswitch = (Switch) myView.findViewById(R.id.newsswitch);
        clockswitch = (Switch) myView.findViewById(R.id.clockswitch);
        deviceswitch = (Switch) myView.findViewById(R.id.deviceswitch);
        weatherswitch = (Switch) myView.findViewById(R.id.weatherswitch);
        postitswitch = (Switch) myView.findViewById(R.id.postswitch);
        greetingsswitch = (Switch) myView.findViewById(R.id.greetingsswitch);
        publish = (Button) myView.findViewById(R.id.prefpub);
        presenter = new PreferencesPresenter(this);

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.PrefBtn();
            }
        });
        disableswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    presenter.DisBtnTrue();
                } else {
                    presenter.DisBtnFalse();
                }
            }
        });

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

    public void DisBtnTrue(){
        busswitch.setChecked(true);
        newsswitch.setChecked(true);
        postitswitch.setChecked(true);
        greetingsswitch.setChecked(true);
        clockswitch.setChecked(true);
        deviceswitch.setChecked(true);
        weatherswitch.setChecked(true);
    }
    public void DisBtnFalse(){
        busswitch.setChecked(false);
        newsswitch.setChecked(false);
        postitswitch.setChecked(false);
        greetingsswitch.setChecked(false);
        clockswitch.setChecked(false);
        deviceswitch.setChecked(false);
        weatherswitch.setChecked(false);
    }
    public void PublishPrefs(){
        presenter.PublishPrefs(((NavigationActivity) getActivity()).getMirror(), ((NavigationActivity) getActivity()).getUser(), Boolean.toString(newsswitch.isChecked()),
                Boolean.toString(busswitch.isChecked()),
                Boolean.toString(weatherswitch.isChecked()), Boolean.toString(clockswitch.isChecked()),  Boolean.toString(deviceswitch.isChecked()),
                Boolean.toString(greetingsswitch.isChecked()), Boolean.toString(postitswitch.isChecked()));
    }
}
