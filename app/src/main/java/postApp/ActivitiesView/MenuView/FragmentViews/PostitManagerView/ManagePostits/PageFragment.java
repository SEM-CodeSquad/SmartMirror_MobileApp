package postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.ManagePostits;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostits.PageFragmentPresenter;

/**
 * Created by adinH on 2016-11-30.
 */

public class PageFragment extends Fragment {
    String message;
    String color;
    String id;
    ImageView imageView;
    TextView textview;
    AlertDialog.Builder builder;
    PageFragmentPresenter presenter;
    public PageFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mirror_postit_fragment_view, container, false);
        builder = new AlertDialog.Builder(getActivity());
        textview = (TextView)view.findViewById(R.id.editTypedtext);
        imageView = (ImageView)view.findViewById(R.id.manageImageview);
        ImageButton checkmark = (ImageButton)view.findViewById(R.id.editcheckmark);
        ImageButton colors = (ImageButton)view.findViewById(R.id.managepostitcolonbutton);
        Bundle bundle = getArguments();
        message = bundle.getString("Text");
        id = bundle.getString("ID");
        color = bundle.getString("Color");
        textview.setText(message);
        presenter = new PageFragmentPresenter(this,color);


        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.DeletePostit(((NavigationActivity) getActivity()).getMirror(), id);
                //presenter.AwaitEcho();
            }
        });

        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.ShowColors();
            }
        });
        textview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.HideKeyboard(v);
                }
            }
        });

        return view;
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
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_blue));
    }
    public void ColorPink(){
        //Change the picture color to match the switch chosen
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_pink));
    }
    public void ColorPurple(){
        //Change the picture color to match the switch chosen
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_purple));
    }
    public void ColorGreen(){
        //Change the picture color to match the switch chosen
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_green));
    }
    public void ColorOrange(){
        //Change the picture color to match the switch chosen
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_orange));
    }
    public void ColorYellow(){
        //Change the picture color to match the switch chosen
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_yellow));
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
    public void ReloadScreen(){
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new ManagePostitsView()).addToBackStack(null).commit();
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
