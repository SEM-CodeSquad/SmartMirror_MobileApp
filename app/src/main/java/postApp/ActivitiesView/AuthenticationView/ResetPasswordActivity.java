package postApp.ActivitiesView.AuthenticationView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import adin.postApp.R;
import postApp.Presenters.AuthenticationPresenters.ResetPasswordPresenter;

/**
 * Created by adinH on 2016-11-07.
 */
public class ResetPasswordActivity extends AppCompatActivity {

    EditText passwrd;
    EditText confpass;
    String user;
    private ResetPasswordPresenter presenter;

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
        Button cancel = (Button)findViewById(R.id.cancelresetbtn);
        passwrd = (EditText)findViewById(R.id.newpassrest);
        confpass = (EditText)findViewById(R.id.confirmreset);
        presenter = new ResetPasswordPresenter(this);



        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    presenter.CheckPasswords(user, passwrd.getText().toString(), confpass.getText().toString());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnCancel();
            }
        });
        //if the usrname has no focus hide it!
        confpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
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
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return true;
    }
}
