package postApp;

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
import postApp.Controllers.NavigationActivity;
import postApp.Network.DataAccess.Login;

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
        Button login = (Button)findViewById(R.id.login);
        usrname = (EditText)findViewById(R.id.loginuser);
        passwrd = (TextView)findViewById(R.id.loginpassword);
        forgot = (TextView)findViewById(R.id.fgtpass);
        regi = (TextView)findViewById(R.id.regibtn);

        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegister();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLogin(usrname.getText().toString(), passwrd.getText().toString());
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
    Switches to navigationactivity
     */
    public void OnLogin(String User, String Pass){
        Login log = new Login(User, Pass);
        if(log.getStatus() == true){
            Intent intent = new Intent(this, NavigationActivity.class);
            startActivity(intent);

        }
        else{
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
    private void onRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    private void onForgotten(){

    }
}
