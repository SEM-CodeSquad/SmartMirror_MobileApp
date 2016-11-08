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
import postApp.Network.DataAccess.Registration;
import postApp.Network.DataAccess.ResetPassword;

/**
 * Created by adinH on 2016-11-07.
 */
public class ResetPasswordActivity extends AppCompatActivity {

    EditText passwrd;
    EditText confpass;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("user");
            //The key argument here must match that used in the other activity
        }
        setContentView(R.layout.resetpass);
        Button reset = (Button)findViewById(R.id.confresetbtn);
        Button cancel = (Button)findViewById(R.id.regclc);
        passwrd = (EditText)findViewById(R.id.newpassrest);
        confpass = (EditText)findViewById(R.id.confirmreset);



        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    checkpasses(user, passwrd.getText().toString(), confpass.getText().toString());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCancel();
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

    private void checkpasses(String user, String pass, String confpass){
        if(pass.equals(confpass)){
            OnReset(pass, user);
        }
        else{
            new AlertDialog.Builder(this)
                    .setTitle("Passwords not matching")
                    .setMessage("Please type again the passwords")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();
        }
    }
    private void OnReset(String User, String Pass){
        ResetPassword reg = new ResetPassword(User, Pass);
        if(reg.getPasswordResetStatus()){
            System.out.println("here");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else{
            new AlertDialog.Builder(this)
                    .setMessage("Some error")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();

        }
    }
    private void OnCancel(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
