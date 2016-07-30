package pl.guideme.burkia.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.components.base.ComponentContainer;
import pl.guideme.burkia.view.components.DrawerComponent_;
import pl.guideme.burkia.view.components.FeedListComponent;
import pl.guideme.burkia.view.components.FeedListComponent_;
import pl.guideme.burkia.view.components.ToolbarComponent_;
import pl.guideme.burkia.view.components.FragmentComponent_;

@EActivity
public class MainActivity extends AppCompatActivity {
    @Bean
    protected ComponentContainer componentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View view = findViewById(android.R.id.content);

        FeedListComponent feedListComponent = FeedListComponent_.getInstance_(getApplicationContext());
        componentContainer.onCreate(this, view,
                feedListComponent,
                ToolbarComponent_.getInstance_(getApplicationContext()),
                FragmentComponent_.getInstance_(getApplicationContext()),
                DrawerComponent_.getInstance_(getApplicationContext())
        );
        if (savedInstanceState == null || savedInstanceState.isEmpty()) {
            feedListComponent.show();
        }
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
