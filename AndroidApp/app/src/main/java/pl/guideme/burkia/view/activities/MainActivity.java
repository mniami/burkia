package pl.guideme.burkia.view.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.components.DrawerComponent_;
import pl.guideme.burkia.view.components.FeedListComponent;
import pl.guideme.burkia.view.components.FeedListComponent_;
import pl.guideme.burkia.view.components.FragmentComponent_;
import pl.guideme.burkia.view.components.ToolbarComponent_;
import pl.guideme.componentslib.Activity;
import pl.guideme.componentslib.ActivityAction;
import pl.guideme.componentslib.Component;
import pl.guideme.componentslib.ComponentContainer;
import pl.guideme.data.ConnectivityReceiver;
import pl.guideme.data.logs.Log;

@EActivity
public class MainActivity extends Activity {
    private static final Log log = Log.withName("MainActivity");
    @Bean
    protected ComponentContainer mComponentContainer;
    @Bean
    protected ConnectivityReceiver mConnectivityReceiver;

    @Override
    public void onBackPressed() {
        log.info(()->"Pop back stack called");
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
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (mComponentContainer.isEmpty()) {
            initializeComponentContainer();
        }

        log.info(()->"Activity created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConnectivityReceiver.init((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE));

        onActivityAction(ActivityAction.Created);

        if (savedInstanceState == null || savedInstanceState.isEmpty()) {
            mComponentContainer.get(FeedListComponent.class).show();
        }
    }

    @Override
    public void onResume() {
        onActivityAction(ActivityAction.Resumed);
        super.onResume();
    }

    @Override
    public void onPause() {
        onActivityAction(ActivityAction.Paused);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        onActivityAction(ActivityAction.Destroyed);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        onActivityAction(ActivityAction.Stopped);
        super.onStop();
    }

    private void initializeComponentContainer() {
        mComponentContainer.initialize(this,
                FeedListComponent_.getInstance_(getApplicationContext()),
                ToolbarComponent_.getInstance_(getApplicationContext()),
                FragmentComponent_.getInstance_(getApplicationContext()),
                DrawerComponent_.getInstance_(getApplicationContext())
        );
    }

    private void onActivityAction(ActivityAction activityAction) {
        for (Component component : mComponentContainer.getComponents()) {
            component.onActivityAction(this, activityAction);
        }
    }
}
