package se.arvidbodkth.laboration3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private SensorReader sensorReader;

    private TextView textView, textView2;
    private Button buttonStart, buttonStop;

    private String sysMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);

        buttonStart = (Button) findViewById(R.id.startButton);
        buttonStop = (Button) findViewById(R.id.stopButton);

        sysMsg = new String();

        sensorReader = new SensorReader(this);

    }

    public void onStartButtonClicked(View v){
        sensorReader.startListening();
    }

    public void onStopButtonClicked(View v){
        sensorReader.stopListening();
    }



    @Override
    protected void onStart() {
        super.onStart();
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

    public void setTextView(String msg){
        textView.setText(msg);
    }

    public void setTextView2(String msg){
        textView2.setText(msg);
    }

    public void showToast(String msg) {
        if(!sysMsg.equals(msg)) {
            System.out.println(msg);
            sysMsg = msg;
        }
        //Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        //toast.show();
    }
}
