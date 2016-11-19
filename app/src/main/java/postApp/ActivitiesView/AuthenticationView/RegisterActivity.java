package postApp.ActivitiesView.AuthenticationView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.AuthenticationPresenters.RegisterPresenter;

public class RegisterActivity extends AppCompatActivity {

    EditText usrname;
    TextView passwrd;
    EditText secret;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeracc);
        //initilize the views
        Button reg = (Button)findViewById(R.id.regbtn);
        Button cancel = (Button)findViewById(R.id.regclc);
        usrname = (EditText)findViewById(R.id.reguser);
        passwrd = (EditText)findViewById(R.id.regpass);
        secret = (EditText)findViewById(R.id.regsecret);
        presenter = new RegisterPresenter(this);


        //on click Listener for the register button that calls OnRegister()
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnRegister(usrname.getText().toString(), passwrd.getText().toString(), secret.getText().toString());
            }
        });
        //on click Listener for the register button that calls OnCancel()
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnCancel();
            }
        });
        //if the usrname has no focus hide it!
        usrname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                   presenter.hideKeyboard(v);
                }
            }
        });
        //if the passwrd has no focus hide it!
        passwrd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.hideKeyboard(v);
                }
            }
        });
        //if the secret has no focus hide it!
        secret.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.hideKeyboard(v);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return true;
    }

    public void onCancel() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void HideKeyboard(View view){
        InputMethodManager inputMethodManager =(InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void SuccessfulRegister(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void UnsuccessfulRegister(){
        new AlertDialog.Builder(this)
                .setMessage("Username already chosen")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();

    }
}
