package pl.guideme.burkia.view.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import pl.guideme.burkia.R;
import pl.guideme.burkia.util.L;
import pl.guideme.burkia.view.components.DrawerComponent_;
import pl.guideme.burkia.view.components.FeedListComponent;
import pl.guideme.burkia.view.components.FeedListComponent_;
import pl.guideme.burkia.view.components.FragmentComponent_;
import pl.guideme.burkia.view.components.ToolbarComponent_;
import pl.guideme.componentslib.ComponentContainer;

@EActivity
public class MainActivity extends AppCompatActivity {
    @Bean
    protected ComponentContainer componentContainer;

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            MainActivity.super.onBackPressed();
                        }
                    }).create().show();
        } else {
            L.d("Pop back stack called");
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View view = findViewById(android.R.id.content);

        if (componentContainer.isEmpty()) {
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
    }
}
