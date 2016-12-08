package postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.HidePostitPresenter;

public class HidePostitView extends Fragment {
    View myView;
    HidePostitPresenter presenter;
    Button publish;
    RadioButton blue;
    RadioButton yellow;
    RadioButton green;
    RadioButton purple;
    RadioButton pink;
    RadioButton orange;
    RadioButton all;
    ProgressDialog progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.hidepostit, container, false);

        progress = new ProgressDialog(getActivity());

        publish = (Button) myView.findViewById(R.id.updatecheck);
        presenter = new HidePostitPresenter(this, ((NavigationActivity) getActivity()).getMirror());
        blue = (RadioButton)  myView.findViewById(R.id.bluecheck);
        yellow = (RadioButton)  myView.findViewById(R.id.yellowcheck);
        green = (RadioButton)  myView.findViewById(R.id.greencheck);
        purple = (RadioButton)  myView.findViewById(R.id.purplecheck);
        pink = (RadioButton)  myView.findViewById(R.id.pinkcheck);
        orange = (RadioButton)  myView.findViewById(R.id.orangecheck);
        all = (RadioButton)  myView.findViewById(R.id.allcheck);

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.FilterPost(((NavigationActivity) getActivity()).getMirror(), ((NavigationActivity) getActivity()).getUser(),
                        Boolean.toString(yellow.isChecked()), Boolean.toString(blue.isChecked()), Boolean.toString(orange.isChecked()),
                        Boolean.toString(pink.isChecked()), Boolean.toString(green.isChecked()), Boolean.toString(purple.isChecked()));
            }
        });

        return myView;
    }

    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Filter Postits");
    }
    public void displaySuccPub(String S){
        Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
    }
    public void NoMirrorChosen(){
        // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
        Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
    }
    /**
     * Loading method that shows a progressdialog
     */
    public void Loading(){
        progress.setMessage("Filtering postits");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

    /**
     * method that dismisses the progressbar
     */
    public void DoneLoading(){
        progress.dismiss();
    }

    /**
     * Method that shows a alertdialog that says fail.
     */
    public void UnsuccessfulPublish(){
        //if user types wrong login we show a alertdialog some text
        new AlertDialog.Builder(getActivity())
                .setTitle("Failed to update filter of postits")
                .setMessage("No answer received")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }



}
