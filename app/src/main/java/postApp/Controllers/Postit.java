package postApp.Controllers;

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
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import adin.postApp.R;
import postApp.MqTTHandler.Retrievedata;

/**
 * Created by adinH on 2016-10-26.
 */
public class Postit extends Fragment {

    EditText titletext;
    EditText typedtext;
    String text;
    String title;
    final String[] Color1 = new String[1];
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.postit, container, false);
        final ImageButton colorbutton = (ImageButton)myView.findViewById(R.id.colorbutton);
        final ImageButton checkmark = (ImageButton)myView.findViewById(R.id.checkmark);
        final ImageView PostitImage = (ImageView)myView.findViewById(R.id.ImageView);
        titletext = (EditText)myView.findViewById(R.id.titletext);
        typedtext = (EditText)myView.findViewById(R.id.typedText);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Chose color");
        builder.setItems(new CharSequence[]
                        {"Blue", "Green", "Yellow"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println(Color1[0]);

                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                Color1[0] = "Blue";
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.bluepost));
                                Toast.makeText(getActivity(), "Chose Blue", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Color1[0] = "Green";
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.greenpost));
                                Toast.makeText(getActivity(), "Chose Green", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Color1[0] = "Yellow";
                                System.out.println(Color1[0]);
                                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.yellowpost));
                                Toast.makeText(getActivity(), "Chose Yellow", Toast.LENGTH_SHORT).show();
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
        });

        assert checkmark != null;
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = typedtext.getText().toString();
                title = titletext.getText().toString();
                Retrievedata R = new Retrievedata();
                String S = null;
                try {
                    S = R.execute(Color1[0], title, text).get();
                } catch (InterruptedException e) {
                    S = "Did not publish";
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    S = "Warning: Did Not Publish";
                }
                Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
            }
        });

        titletext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
