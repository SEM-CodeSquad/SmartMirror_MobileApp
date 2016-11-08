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

/**
 * Created by adinH on 2016-11-07.
 */
public class RegisterActivity extends AppCompatActivity {

    EditText usrname;
    TextView passwrd;
    EditText secret;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeracc);
        Button login = (Button)findViewById(R.id.regbtn);
        Button cancel = (Button)findViewById(R.id.regclc);
        usrname = (EditText)findViewById(R.id.reguser);
        passwrd = (EditText)findViewById(R.id.regpass);
        secret = (EditText)findViewById(R.id.regsecret);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnRegister(usrname.getText().toString(), passwrd.getText().toString(), secret.getText().toString());
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

    private void OnRegister(String User, String Pass, String Secret){
        Registration reg = new Registration(User, Pass, Secret);
        if(reg.getInUse()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
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
    private void OnCancel(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
