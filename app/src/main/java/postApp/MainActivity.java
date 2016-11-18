package postApp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import postApp.ActivitiesView.AuthenticationView.LoginActivity;

/*
Startup for the login screen, currently fixed values till a db gets implemented
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

    }
}
