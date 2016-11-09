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
import postApp.Activities.NavigationActivity.NavigationActivity;
import postApp.DataHandlers.Network.MqTTHandler.Retrievedata;

/**
 * Created by adinH on 2016-10-26.
 * used for posting postits
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idOne = UUID.randomUUID().toString();
        Color1[0] = "post-it-yellow";
        myView = inflater.inflate(R.layout.postit, container, false);
        final ImageButton colorbutton = (ImageButton)myView.findViewById(R.id.colorbutton);
        final ImageButton checkmark = (ImageButton)myView.findViewById(R.id.checkmark);
        final ImageView PostitImage = (ImageView)myView.findViewById(R.id.ImageView);
        typedtext = (EditText)myView.findViewById(R.id.typedText);
        important = ((Switch)myView.findViewById(R.id.postitswitch));

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //builders are dialog that pop up with option, each option outcome is in the switch()
        builder.setTitle("Chose color");
        builder.setItems(new CharSequence[]
                        {"Blue", "Green", "Yellow", "Orange", "Purple", "Pink"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                Color1[0] = "post-it-blue";
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_blue));
                                Toast.makeText(getActivity(), "Chose Blue", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Color1[0] = "post-it-green";
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_green));
                                Toast.makeText(getActivity(), "Chose Green", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Color1[0] = "post-it-yellow";
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_yellow));
                                Toast.makeText(getActivity(), "Chose Yellow", Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                Color1[0] = "post-it-orange";
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_orange));
                                Toast.makeText(getActivity(), "Chose Orange", Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                Color1[0] = "post-it-purple";
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_purple));
                                Toast.makeText(getActivity(), "Chose Purple", Toast.LENGTH_SHORT).show();
                                break;
                            case 5:
                                Color1[0] = "post-it-pink";
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_pink));
                                Toast.makeText(getActivity(), "Chose Pink", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });


        builder.create();

        colorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               builder.show();
            }
        }); //when colorbutton is pressed in the UI we show the color builder.

        /*
        This is used for posting a postit with starting the Retrieve data class with all the args we need.
         */
        assert checkmark != null;
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                Retrievedata R = new Retrievedata();
                String S = null;
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
        Remove focus from textview when clicked on a diffrent place
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
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
