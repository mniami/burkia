package pl.guideme.burkia.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.components.ChangeFragment_;
import pl.guideme.burkia.view.components.base.ComponentContainer;
import pl.guideme.burkia.view.components.drawers.DrawerComponent_;
import pl.guideme.burkia.view.components.feeds.FeedListComponent_;
import pl.guideme.burkia.view.components.toolbar.ToolbarComponent_;

@EActivity
public class MainActivity extends AppCompatActivity {
    @Bean
    protected ComponentContainer componentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(view);

        componentContainer.onCreate(this, view,
                FeedListComponent_.getInstance_(getApplicationContext()),
                ToolbarComponent_.getInstance_(getApplicationContext()),
                ChangeFragment_.getInstance_(getApplicationContext()),
                DrawerComponent_.getInstance_(getApplicationContext())
        );
        FeedListComponent_.getInstance_(getApplicationContext()).show();
    }

    @Override
    public void onResume() {
        componentContainer.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        componentContainer.onPause();
        super.onPause();
    }
}
