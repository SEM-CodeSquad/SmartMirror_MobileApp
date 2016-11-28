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
import postApp.Presenters.AuthenticationPresenters.LoginPresenter;

/**
 * Created by adinH on 2016-11-07.
 */
public class LoginActivity extends AppCompatActivity {

    EditText usrname;
    TextView passwrd;
    TextView regi;
    TextView forgot;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        //initilize the views
        Button login = (Button)findViewById(R.id.login);
        usrname = (EditText)findViewById(R.id.loginuser);
        passwrd = (TextView)findViewById(R.id.loginpassword);
        forgot = (TextView)findViewById(R.id.fgtpass);
        regi = (TextView)findViewById(R.id.regibtn);
        presenter = new LoginPresenter(this);


        //set a onclicklistener to the register button that calls OnRegister
        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegister();
            }
        });

        //set a onclicklistener to the login button that calls OnLogin with the username and password typed by the user
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnLogin(usrname.getText().toString(), passwrd.getText().toString());
            }
        });
        //set a onclicklistener to the forgot pass button that calls onForgotten
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onForgotten();
            }
        });

        //if the usrname has no focus hide it!
        usrname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
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
    }

    @Override
    public void onBackPressed() {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void onForgotten() {
        Intent intent = new Intent(this, SecretQActivity.class);
        startActivity(intent);
    }

    public void OnRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void HideKeyboard(View view){
        InputMethodManager inputMethodManager =(InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void SuccessfulLogin(String User, String bus, String weather, String news){
        //if we log in we swithc to navigationActivity
        Intent intent = new Intent(this, NavigationActivity.class);
        //we add in a fetchable user when we start the activity
        intent.putExtra("user", User);
        intent.putExtra("bus", bus);
        intent.putExtra("weather", weather);
        intent.putExtra("user", news);
        System.out.println(news);
        startActivity(intent);
    }

    public void UnsuccessfulLogin(){
        //if user types wrong login we show a alertdialog some text
        new AlertDialog.Builder(this)
                .setTitle("Access denied")
                .setMessage("Wrong username or password")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
