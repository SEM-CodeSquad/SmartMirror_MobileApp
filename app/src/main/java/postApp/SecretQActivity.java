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
import postApp.Network.DataAccess.MatchAnswer;
import postApp.Network.DataAccess.Registration;

/**
 * Created by adinH on 2016-11-07.
 */
public class SecretQActivity extends AppCompatActivity {

    EditText usrname;
    EditText secret;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secretq);
        Button confirm = (Button)findViewById(R.id.confsecretq);
        Button cancel = (Button)findViewById(R.id.cancelsecretq);
        usrname = (EditText)findViewById(R.id.usernamesecret);
        secret = (EditText)findViewById(R.id.secretqanswer);



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSecret(usrname.getText().toString(), secret.getText().toString());
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

    private void OnSecret(String User, String Secret){
        MatchAnswer reg = new MatchAnswer(User, Secret);
        if(reg.getAnswerMatch()){
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            intent.putExtra("user", usrname.getText().toString());
            startActivity(intent);
        }
        else{
            new AlertDialog.Builder(this)
                    .setMessage("Wrong Username or Question Answer")
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
