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

import adin.postApp.R;
import postApp.Presenters.AuthenticationPresenters.SecretQPresenter;

/**
 * Class that handles the secret question view
 */
public class SecretQActivity extends AppCompatActivity {

    private EditText usrname;
    private EditText secret;
    private SecretQPresenter presenter;
    private ProgressDialog progress;

    /**
     * When we switch to this activity the oncreate method is ran, which instantiates buttons
     * sets onclick listeners.
     * @param savedInstanceState the saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress = new ProgressDialog(this);

        setContentView(R.layout.secretq);
        Button confirm = (Button)findViewById(R.id.confsecretq);
        Button cancel = (Button)findViewById(R.id.cancelsecretq);
        usrname = (EditText)findViewById(R.id.usernamesecret);
        secret = (EditText)findViewById(R.id.secretqanswer);

        presenter = new SecretQPresenter(this);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnSecret(usrname.getText().toString(), secret.getText().toString());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnCancel();
            }
        });
        usrname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.hideKeyboard(v);
                }
            }
        });
        //if the passwrd has no focus hide it!
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
     * if the credentials match we switch to resetpasswordactivity
     * @param User we want to pass to the ResetPasswordActivity
     */
    public void CorrectCredentials(String User) {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        intent.putExtra("user", User);
        startActivity(intent);
    }

    /**
     * If credentials don't match we display a alertdialog saying that
     */
    public void WrongCredentials(){
        new AlertDialog.Builder(this)
                .setMessage("Wrong Username or Question Answer")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }
    /**
     * Loading method that shows a progressdialog
     */
    public void Loading(){
        progress.setMessage("Checking if Username and question are correct");
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
     * Hide keyboard
     * @param view where we are hiding the keyboard
     */
    public void HideKeyboard(View view){
        InputMethodManager inputMethodManager =(InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /**
     * When user pressed cancel buttons this method is called which switches screen
     */
    public void OnCancel(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
