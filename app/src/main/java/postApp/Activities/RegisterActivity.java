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
import postApp.DataHandlers.Network.DataBase.Registration;

public class RegisterActivity extends AppCompatActivity {

    EditText usrname;
    TextView passwrd;
    EditText secret;
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


        //on click Listener for the register button that calls OnRegister()
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnRegister(usrname.getText().toString(), passwrd.getText().toString(), secret.getText().toString());
            }
        });
        //on click Listener for the register button that calls OnCancel()
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

    //on register that takes a user, pass and a secret answer
    private void OnRegister(String User, String Pass, String Secret){
        Registration reg = new Registration(User, Pass, Secret);
        //if we return that the username is not in use we switch to login class since we know the account making was succesfull
        if(reg.getInUse() == false){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        //a alertdialog displaying it is already chosen
        else{
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
    //canceling should switchback to loginactivity
    private void OnCancel(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
