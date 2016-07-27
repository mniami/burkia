package pl.guideme.burkia.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.components.ChangeFragment_;
import pl.guideme.burkia.view.components.base.ComponentContainer;
import pl.guideme.burkia.view.components.dispatchers.DrawerDispatcher;
import pl.guideme.burkia.view.components.mainrecycleview.MainRecyclerView_;
import pl.guideme.burkia.view.components.toolbar.MainToolbar_;

@EActivity
public class MainActivity extends AppCompatActivity {
    @Bean
    protected ComponentContainer componentContainer;
    @Bean
    protected DrawerDispatcher drawerDispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(view);

        componentContainer.onCreate(this, view,
                MainRecyclerView_.getInstance_(getApplicationContext()),
                MainToolbar_.getInstance_(getApplicationContext()),
                ChangeFragment_.getInstance_(getApplicationContext())
        );
        drawerDispatcher.onCreate(this, getApplicationContext(), componentContainer);
    }

    @Override
    public void onResume() {
        componentContainer.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        drawerDispatcher.onPause();
        componentContainer.onPause();
        super.onPause();
    }
}
