package postApp.ActivitiesView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import adin.postApp.R;
import postApp.Presenters.SecretQPresenter;

/**
 * Created by adinH on 2016-11-07.
 */
public class SecretQActivity extends AppCompatActivity {

    private EditText usrname;
    private EditText secret;
    private SecretQPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secretq);
        Button confirm = (Button)findViewById(R.id.confsecretq);
        Button cancel = (Button)findViewById(R.id.cancelsecretq);
        usrname = (EditText)findViewById(R.id.usernamesecret);
        secret = (EditText)findViewById(R.id.secretqanswer);

        presenter = new SecretQPresenter(this);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnSecret(usrname.getText().toString(), secret.getText().toString());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnCancel();
            }
        });
        usrname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.hideKeyboard(v);
                }
            }
        });
        //if the passwrd has no focus hide it!
        secret.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
