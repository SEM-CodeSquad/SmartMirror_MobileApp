package postApp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.net.URL;

import adin.postApp.R;

public class MainActivity extends AppCompatActivity {

    EditText titletext;
    EditText typedtext;
    ImageButton checkmark;
    String text;
    String title;
    final String[] Color1 = new String[1];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titletext = (EditText)findViewById(R.id.titletext);
        typedtext = (EditText)findViewById(R.id.typedText);
        final Button colorblue = (Button) findViewById(R.id.buttonblue);
        final Button coloryellow = (Button) findViewById(R.id.buttonyellow);
        final Button colorgreen = (Button)findViewById(R.id.buttongreen);
        final ImageButton checkmark = (ImageButton)findViewById(R.id.checkmark);
        final ImageView PostitImage = (ImageView)findViewById(R.id.ImageView);

        assert checkmark != null;
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hi");
                text = typedtext.getText().toString();
                title = titletext.getText().toString();

                new Retrievedata().execute();
            }
        });


        colorblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Color1[0] = "Blue";
                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.bluepost));
            }
        });

        coloryellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Color1[0] = "Yellow";
                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.yellowpost));
            }
        });

        colorgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Color1[0] = "Green";
                PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.greenpost));
            }
        });

    }

    class Retrievedata extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try{
                System.out.println(text);
                System.out.println(Color1[0]);
                System.out.println(title);
                String yellow = "yellow";


                String messagestring = "{\"Postit\": {\"Color\": \" \" + yellow + \"\\\",\\\"Title\\\": \\\"\" + text + \"\\\",\\\"Text\\\": \\\"\" + title + \"\\\"}}";
                HttpRequestSender post = new HttpRequestSender("codehigh.ddns.net","new client", "test", messagestring );

                String myUrl = "http://codehigh.ddns.net:5000/";
                //String myUrl = "http://localhost:5600/";

                post.executePost(myUrl);
                return("hi");

            }
            catch(Exception e)
            {
                return "bye";
            }
        }
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
