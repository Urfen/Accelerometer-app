package se.arvidbodkth.laboration3;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorReader sensorReader;
    private ImageView imageView;
    private String lastToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource();

        sensorReader = new SensorReader(this);



        lastToast = "";
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorReader.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorReader.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorReader.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.start) {
            sensorReader.startListening();
            return true;
        }
        if (id == R.id.stop) {
            sensorReader.stopListening();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showToast(String msg) {
        if(!msg.equals(lastToast)) {
            Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            toast.show();
            lastToast = msg;
        }
    }
}
