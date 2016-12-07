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
import android.widget.Button;
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
    String expireat;
    ImageView imageView;
    TextView textview;
    TextView expiresat;
    AlertDialog.Builder builderDelete;
    AlertDialog.Builder builderEdit;
    PageFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mirror_postit_fragment_view, container, false);
        builderDelete = new AlertDialog.Builder(getActivity());
        builderEdit = new AlertDialog.Builder(getActivity());
        expiresat = (TextView)view.findViewById(R.id.expiresat);
        textview = (TextView)view.findViewById(R.id.editTypedtext);
        imageView = (ImageView)view.findViewById(R.id.manageImageview);
        Button EditPostit = (Button)view.findViewById(R.id.editcheckmark);
        Button DeletePostit = (Button)view.findViewById(R.id.deletepostitbutton);
        Bundle bundle = getArguments();
        expireat = bundle.getString("Timestamp");
        message = bundle.getString("Text");
        id = bundle.getString("ID");
        color = bundle.getString("Color");

        textview.setText(message);
        expiresat.setText(expireat);
        presenter = new PageFragmentPresenter(this,color);


        DeletePostit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builderDelete.show();
            }
        });
        EditPostit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builderEdit.show();
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

    public void buildDelete(){
        builderDelete.setTitle("Confirm");
        builderDelete.setMessage("Are you sure you want to delete this postit?");

        builderDelete.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                presenter.DeletePostit(((NavigationActivity) getActivity()).getMirror(), id);

                dialog.dismiss();
            }
        });

        builderDelete.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builderDelete.create();
    }

    public void buildEdit(){
        builderEdit.setTitle("Confirm");
        builderEdit.setMessage("Are you sure you want to edit this postit?");

        builderEdit.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                presenter.EditPostit(((NavigationActivity) getActivity()).getMirror(), id, textview.getText().toString());

                dialog.dismiss();
            }
        });

        builderEdit.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builderEdit.create();
    }
    public void ShowMessage(String S){
        Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
    }
    public void NoMirror(){
        Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();

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

