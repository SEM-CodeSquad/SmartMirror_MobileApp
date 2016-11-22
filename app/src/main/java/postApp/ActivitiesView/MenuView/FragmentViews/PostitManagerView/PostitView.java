package postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.JsonHandler.JsonBuilder;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.PostitPresenter;

/**
 used for posting postits
 */
public class PostitView extends Fragment {


    EditText typedtext;
    View myView;
    PostitPresenter presenter;
    AlertDialog.Builder builder;
    ImageView PostitImage;
    /*
    When we change to this fragment OnCreateView is started which gets buttons, builds alertdialogs and everything required for this view.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.postit, container, false);
        //instantiate the views
        final ImageButton colorbutton = (ImageButton)myView.findViewById(R.id.colorbutton);
        final ImageButton checkmark = (ImageButton)myView.findViewById(R.id.checkmark);
        PostitImage = (ImageView)myView.findViewById(R.id.ImageView);
        typedtext = (EditText)myView.findViewById(R.id.typedText);
        builder = new AlertDialog.Builder(getActivity());
        presenter = new PostitPresenter(this);

        //when colorbutton is pressed in the UI we show the color builder.
        colorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               presenter.ShowColors();
            }
        });

        /*
        This is used for posting a postit with starting the Retrieve data class with all the args we need.
         */
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.PublishPostit(typedtext.getText().toString(),  ((NavigationActivity) getActivity()).getMirror());

            }
        });

        /*
        Remove focus from typedtext when clicked on a diffrent place meaning we will hide keyboard when not typing
         */
        typedtext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.HideKeyboard(v);
                }
            }
        });

        return myView;
    }

    public void BuildColorChoice(){

        //Set the title to chose color
        builder.setTitle("Chose color");
        //Building the alertdialog with 6 options
        builder.setItems(new CharSequence[]
                        {"Blue", "Green", "Yellow", "Orange", "Purple", "Pink"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                //we change post it color when publishing to broker
                                presenter.SetColor("blue");
                                presenter.BlueClick();
                                break;
                            case 1:
                                //we change variable color1[0] when publishing to broker
                                presenter.SetColor("green");
                                presenter.GreenClick();
                                break;
                            case 2:
                                //we change variable color1[0] when publishing to broker
                                presenter.SetColor("yellow");
                                presenter.YellowClick();
                                break;
                            case 3:
                                //we change variable color1[0] when publishing to broker
                                presenter.SetColor("orange");
                                presenter.OrangeClick();
                                break;
                            case 4:
                                //we change variable color1[0] when publishing to broker
                                presenter.SetColor("purple");
                                presenter.PurpleClick();
                                break;
                            case 5:
                                //we change variable color1[0] when publishing to broker
                                presenter.SetColor("pink");
                                presenter.PinkClick();
                                break;
                        }
                    }
                });
        builder.create();
    }
    public void ColorBlue(){
        //Change the picture color to match the switch chosen
        PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_blue));
        Toast.makeText(getActivity(), "Chose Blue", Toast.LENGTH_SHORT).show();
    }
    public void ColorPink(){
        //Change the picture color to match the switch chosen
        PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_pink));
        Toast.makeText(getActivity(), "Chose Pink", Toast.LENGTH_SHORT).show();
    }
    public void ColorPurple(){
        //Change the picture color to match the switch chosen
        PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_purple));
        Toast.makeText(getActivity(), "Chose Purple", Toast.LENGTH_SHORT).show();
    }
    public void ColorGreen(){
        //Change the picture color to match the switch chosen
        PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_green));
        Toast.makeText(getActivity(), "Chose Green", Toast.LENGTH_SHORT).show();
    }
    public void ColorOrange(){
        //Change the picture color to match the switch chosen
        PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_orange));
        Toast.makeText(getActivity(), "Chose Orange", Toast.LENGTH_SHORT).show();
    }
    public void ColorYellow(){
        //Change the picture color to match the switch chosen
        PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_yellow));
        Toast.makeText(getActivity(), "Chose Yellow", Toast.LENGTH_SHORT).show();
    }
    public void ShowMessage(String S){
        Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
    }
    public void NoMirror(){
        Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();

    }
    public void NoEcho(){
        Toast.makeText(getActivity(), "Publishing failed.", Toast.LENGTH_SHORT).show();
    }

    public void ShowColors(){
        builder.show();
    }
    /*
    Hides keyboard
    @param view The view passed to hide keyboard
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
