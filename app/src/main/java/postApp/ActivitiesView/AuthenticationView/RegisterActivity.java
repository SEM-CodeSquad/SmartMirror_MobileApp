package postApp.ActivitiesView.AuthenticationView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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

/**
 * This activity handles the login view
 */
public class RegisterActivity extends AppCompatActivity {

    EditText usrname;
    TextView passwrd;
    EditText secret;
    private RegisterPresenter presenter;
    ProgressDialog progress;

    /**
     * When the activity is launched this is the oncreate method,
     * Iniatilize the buttons, edittext and set onclicklisteners
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeracc);
        progress = new ProgressDialog(this);
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

    /**
     * When back is pressed we call the superfucntions backpresed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Just creating a standard option menu
     * @param menu the menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * When user pressed cancel buttons this method is called which switches screen
     */
    public void onCancel() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Hide keyboard
     * @param view we are hiding the keyboard
     */
    public void HideKeyboard(View view){
        InputMethodManager inputMethodManager =(InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Loading method that shows a progressdialog
     */
    public void Loading(){
        progress.setMessage("Registring");
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
     * If its a succefull register we take the user back to loginactivity
     */
    public void SuccessfulRegister(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * A alertdialog method that shows that the name is not a email.
     */
    public void NotEmail(){
        new AlertDialog.Builder(this)
                .setMessage("Username must be a valid Email")
                .setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();

    }

    /**
     * If the username is already chosen we tell the user that
     */
    public void UnsuccessfulRegister(){
        new AlertDialog.Builder(this)
                .setMessage("Username already chosen")
                .setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();

    }
}
