package postApp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import adin.postApp.R;
import postApp.Activities.NavigationActivity.NavigationActivity;
import postApp.DataHandlers.Network.DataBase.Login;

/**
 * Created by adinH on 2016-11-07.
 */
public class LoginActivity extends AppCompatActivity {

    EditText usrname;
    TextView passwrd;
    TextView regi;
    TextView forgot;
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

        //set a onclicklistener to the register button that calls OnRegister
        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegister();
            }
        });

        //set a onclicklistener to the login button that calls OnLogin with the username and password typed by the user
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLogin(usrname.getText().toString(), passwrd.getText().toString());
            }
        });
        //set a onclicklistener to the forgot pass button that calls onForgotten
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onForgotten();
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
    /*
    Switches to navigationactivity if a user correctly logs in
     */
    public void OnLogin(String User, String Pass){
        Login log = new Login(User, Pass);
        if(log.getStatus() == true){
            //if we log in we swithc to navigationActivity
            Intent intent = new Intent(this, NavigationActivity.class);
            //we add in a fetchable user when we start the activity
            intent.putExtra("user", User);
            startActivity(intent);
        }
        else{
            //if user types wrong login we show a alertdialog some text
            new AlertDialog.Builder(this)
                    .setTitle("Access denied")
                    .setMessage("Wrong username or password")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();
        }
    }
    //starts the register intent
    private void onRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    //starts the forgotten pass intent
    private void onForgotten(){
        Intent intent = new Intent(this, SecretQActivity.class);
        startActivity(intent);
    }
}
