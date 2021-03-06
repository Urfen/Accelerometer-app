package se.arvidbodkth.laboration3;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Arvid Bodin and Mattias Grehnik on 2015-12-04.
 *
 */
public class MainActivity extends AppCompatActivity {

    private SensorReader sensorReader;
    private AnimationDrawable animationDrawable;
    private ImageView imageView;
    private String lastToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.imageView);


        imageView.setBackgroundResource(R.drawable.left1);


        sensorReader = new SensorReader(this);

        lastToast = "";
    }

    public void showImage(int nr){
        switch (nr){
            case 0:
                imageView.setBackgroundResource(R.drawable.left1);
                break;
            case 1:
                imageView.setBackgroundResource(R.drawable.left2);
                break;
            case 2:
                imageView.setBackgroundResource(R.drawable.left3);
                break;
            case 3:
                imageView.setBackgroundResource(R.drawable.left4);
                break;
            case 4:
                imageView.setBackgroundResource(R.drawable.left5);
                break;
            case 5:
                imageView.setBackgroundResource(R.drawable.left6);
                break;
            case 6:
                imageView.setBackgroundResource(R.drawable.left7);
                break;
            case 7:
                imageView.setBackgroundResource(R.drawable.left8);
                break;
            case 8:
                imageView.setBackgroundResource(R.drawable.left9);
                break;
            case 9:
                imageView.setBackgroundResource(R.drawable.left10);
                break;
        }
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

    public void startAnimation(){
        imageView.setBackgroundResource(R.drawable.animation);
        animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

    public boolean isAnimationRunnig(){
        if(animationDrawable != null) return animationDrawable.isRunning();
        else return false;
    }

    public void stopAnimation(boolean stop){
        animationDrawable.stop();
    }

    public void showToast(String msg) {
        if(!msg.equals(lastToast)) {
            Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            toast.show();
            lastToast = msg;
        }
    }
}
