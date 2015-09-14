package com.restaurantmenu.restaurantmenu.activities;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.google.inject.Inject;
import com.restaurantmenu.restaurantmenu.R;
import com.restaurantmenu.restaurantmenu.core.EventAggregator;
import com.restaurantmenu.restaurantmenu.core.EventKeys;
import com.restaurantmenu.restaurantmenu.core.ISubscriber;
import com.restaurantmenu.restaurantmenu.core.ApplicationState;
import com.restaurantmenu.restaurantmenu.core.LocationProvider;
import com.restaurantmenu.restaurantmenu.core.MainApplication;
import com.restaurantmenu.restaurantmenu.services.datas.ConnectionOpener;
import com.restaurantmenu.restaurantmenu.services.datas.DataService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import roboguice.activity.RoboFragmentActivity;

public class MainActivity extends RoboFragmentActivity {

    @Inject private MainApplication app;
    @Inject private DataService dataService;
    @Inject private EventAggregator eventAggregator;
    @Inject private ConnectionOpener connectionOpener;
    @Inject private LocationProvider locationProvider;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler handler;
    private ISubscriber remoteConnectionEstablishedSubscriber;
    private BodyFragment bodyFragment;
    private OfferWrap currentOffer;
    private OfferWrap rootOffer;
    private String Title = "Restauracje";
    private LocationProvider.LocationProviderListener locationProviderListener;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        handler = new Handler();

        setContentView(R.layout.activity_main);

        bodyFragment = (BodyFragment)getSupportFragmentManager().findFragmentById(R.id.body_fragment);
        bodyFragment.addListener(new BodyFragment.BodyFragmentListener() {
            @Override
            public void offerClicked(OfferWrap offer) {
                getActionBar().setTitle(offer.getName());
                bodyFragment.setOffers(offer.getOffers());
                currentOffer = offer;
            }
        });

        remoteConnectionEstablishedSubscriber = new ISubscriber() {
            @Override
            public void occured(Object args) {
                initializeOffers();
            }
        };
        locationProviderListener = new LocationProvider.LocationProviderListener() {
            @Override
            public void locationRetreived(final String locationName) {
                getActionBar().setTitle(locationName);
                locationProvider.stop();
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        initializeOffers();
                    }
                });
            }
        };
        eventAggregator.add(EventKeys.RemoteConnectionEstablished, remoteConnectionEstablishedSubscriber);
        locationProvider.addListener(locationProviderListener);

        if (app.getState() == ApplicationState.nonStarted){
            app.start(getApplicationContext());
        }
        else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (connectionOpener.isConnectionOpened()) {
                        remoteConnectionEstablishedSubscriber.occured(null);
                    }
                }
            }).start();
        }
    }

    private void initializeOffers() {
        if (connectionOpener.isConnectionOpened()) {
            final OfferWrap root = OfferWrap.wrapToRoot(dataService.getOffers());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    rootOffer = root;
                    bodyFragment.setOffers(root.getOffers());
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
     public void onBackPressed() {
        if (currentOffer != rootOffer){
            currentOffer = currentOffer.parentOffer;
            refreshCurrentView();
        }
        else {
            moveTaskToBack(true);
        }
    }

    @Override
    protected void onDestroy() {
        eventAggregator.remove(EventKeys.RemoteConnectionEstablished, remoteConnectionEstablishedSubscriber);
        locationProvider.removeListener(locationProviderListener);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshCurrentView(){
        if (currentOffer != rootOffer) {
            getActionBar().setTitle(currentOffer.getName());
            bodyFragment.setOffers(currentOffer.getOffers());
        }
        else {
            getActionBar().setTitle(Title);
            bodyFragment.setOffers(rootOffer.getOffers());
        }
    }
}
