package postApp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import adin.postApp.R;
import postApp.Controllers.NavigationActivity;

public class MainActivity extends AppCompatActivity {

    String password = "Adin";
    String username = "Adin";
    EditText usrname;
    TextView passwrd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        Button login = (Button)findViewById(R.id.login);
        usrname = (EditText)findViewById(R.id.loginuser);
        passwrd = (TextView)findViewById(R.id.loginpassword);

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

    public void OnLogin(String User, String Pass){
        if(username.equals(User) && password.equals(Pass)){
            Intent intent = new Intent(this, NavigationActivity.class);
            startActivity(intent);
        }
    }
}
