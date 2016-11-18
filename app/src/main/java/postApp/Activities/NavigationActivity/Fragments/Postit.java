package postApp.Activities.NavigationActivity.Fragments;

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

/**
 used for posting postits
 */
public class Postit extends Fragment {


    EditText typedtext;
    String topic;
    String uuid;
    String text;
    Switch important;
    String time;
    final String[] Color1 = new String[1];
    View myView;
    String idOne;

    /*
    When we change to this fragment OnCreateView is started which gets buttons, builds alertdialogs and everything required for this view.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idOne = UUID.randomUUID().toString();
        //set the original post it color to yellow
        Color1[0] = "yellow";
        myView = inflater.inflate(R.layout.postit, container, false);
        //instantiate the views
        final ImageButton colorbutton = (ImageButton)myView.findViewById(R.id.colorbutton);
        final ImageButton checkmark = (ImageButton)myView.findViewById(R.id.checkmark);
        final ImageView PostitImage = (ImageView)myView.findViewById(R.id.ImageView);
        typedtext = (EditText)myView.findViewById(R.id.typedText);
        important = ((Switch)myView.findViewById(R.id.postitswitch));

        //builders are dialog that pop up with option, each option outcome is in the switch()
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                                Color1[0] = "blue";
                                //Change the picture color to match the switch chosen
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_blue));
                                Toast.makeText(getActivity(), "Chose Blue", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                //we change variable color1[0] when publishing to broker
                                Color1[0] = "green";
                                //Change the picture color to match the switch chosen
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_green));
                                Toast.makeText(getActivity(), "Chose Green", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                //we change variable color1[0] when publishing to broker
                                Color1[0] = "yellow";
                                //Change the picture color to match the switch chosen
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_yellow));
                                Toast.makeText(getActivity(), "Chose Yellow", Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                //we change variable color1[0] when publishing to broker
                                Color1[0] = "post-it-orange";
                                //Change the picture color to match the switch chosen
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_orange));
                                Toast.makeText(getActivity(), "Chose Orange", Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                //we change variable color1[0] when publishing to broker
                                Color1[0] = "purple";
                                //Change the picture color to match the switch chosen
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_purple));
                                Toast.makeText(getActivity(), "Chose Purple", Toast.LENGTH_SHORT).show();
                                break;
                            case 5:
                                //we change variable color1[0] when publishing to broker
                                Color1[0] = "pink";
                                //Change the picture color to match the switch chosen
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_pink));
                                Toast.makeText(getActivity(), "Chose Pink", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });


        builder.create();
        //when colorbutton is pressed in the UI we show the color builder.
        colorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               builder.show();
            }
        });

        /*
        This is used for posting a postit with starting the Retrieve data class with all the args we need.
         */
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we get text, uuid, topic, and set a time here for the postit
                text = typedtext.getText().toString();
                uuid = ((NavigationActivity) getActivity()).getUUID();
                topic = ((NavigationActivity) getActivity()).getMirror();
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, 7);
                idOne = UUID.randomUUID().toString();

                String date = c.getTime().toString();
                if(important.isChecked() == true){
                    date = "none";
                }
                String importantstring = String.valueOf(important.isChecked());
                JsonBuilder R = new JsonBuilder();
                String S;
                if (topic != "No mirror chosen") {
                    try {
                        S = R.execute(topic, "postit", text, Color1[0], importantstring, date, idOne).get();
                    } catch (InterruptedException e) {
                        S = "Did not publish";
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        S = "Warning: Did Not Publish";
                    }
                    Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*
        Remove focus from typedtext when clicked on a diffrent place meaning we will hide keyboard when not typing
         */
        typedtext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        return myView;
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
