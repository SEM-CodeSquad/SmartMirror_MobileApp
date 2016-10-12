package postApp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import adin.postApp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton checkmark = (ImageButton) findViewById(R.id.checkmark);
        Button colorblue = (Button) findViewById(R.id.buttonblue);
        Button coloryellow = (Button) findViewById(R.id.buttonyellow);
        Button colorgreen = (Button)findViewById(R.id.buttongreen);
        final EditText titletext = (EditText)findViewById(R.id.titletext);
        final EditText typedtext = (EditText)findViewById(R.id.typedText);
        final ImageView PostitImage = (ImageView)findViewById(R.id.ImageView);
        final String[] Color = new String[1];

        assert checkmark != null;
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sendpostit post = new Sendpostit();
                String text = typedtext.getText().toString();
                String title = titletext.getText().toString();
                String urlParameters = "Hostname: codehigh ClientId: new client Topic:New topic Body: {\"Postit\": {\"Color\": \" " + Color[0] + "\",\"Title\": \"" + titletext + "\",\"Text\": \"" + typedtext + "\"}} end";
                String myUrl = "http://codehigh.ddns.net:5000/";
                //String myUrl = "http://localhost:5600/";

                post.executePost(myUrl, urlParameters);
            }
        });


        colorblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Color[0] = "Blue";
                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.bluepost));
            }
        });

        coloryellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Color[0] = "Yellow";
                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.yellowpost));
            }
        });

        colorgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Color[0] = "Green";
                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.greenpost));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long

        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
