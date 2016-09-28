package pl.guideme.componentslib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Activity extends AppCompatActivity {
    private boolean created;
    private boolean visible;

    @Override
    public void onCreate(Bundle savedInstanceState){
        created = true;
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onResume(){
        visible = true;
        super.onResume();
    }
    @Override
    public void onPause(){
        visible = false;
        super.onPause();
    }
    @Override
    public void onDestroy(){
        created = true;
        super.onDestroy();
    }

    public boolean isCreated(){
        return created;
    }

    public boolean isVisible() {
        return visible;
    }
}
