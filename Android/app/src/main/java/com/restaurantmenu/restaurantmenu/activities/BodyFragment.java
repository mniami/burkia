package com.restaurantmenu.restaurantmenu.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;
import com.restaurantmenu.restaurantmenu.R;
import com.restaurantmenu.restaurantmenu.core.ImageLoader;
import com.restaurantmenu.restaurantmenu.services.datas.OfferType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.fragment.RoboFragment;

/**
 * Created by dszcz_000 on 22.08.2015.
 */
public class BodyFragment extends RoboFragment {

    public interface BodyFragmentListener {
        void offerClicked(OfferWrap offer);
    }

    private RecyclerView mRecyclerView;
    private OfferAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<BodyFragmentListener> listeners = new ArrayList<>();
    private Map<Integer, OfferAdapter> adapterMap = new HashMap<>();

    @Inject private ImageLoader imageLoader;

    public void setOffers(List<OfferWrap> offers) {
        int layout = getOffersAdapter(offers);
        if (adapterMap.containsKey(layout)){
            mAdapter = adapterMap.get(layout);
        }
        else {
            mAdapter = new OfferAdapter(offers, imageLoader, layout, new OfferAdapter.OfferAdapterListener() {
                @Override
                public void offerClicked(OfferWrap offer) {
                    onOfferClicked(offer);
                }
            });
            adapterMap.put(layout, mAdapter);
        }
        mAdapter.setOffers(offers);
        mRecyclerView.setAdapter(mAdapter);
    }

    private int getOffersAdapter(List<OfferWrap> offers){
        if (offers.size() == 0){
            return R.layout.offerv_dense;
        }
        OfferWrap offerWrap = offers.get(0);

        if (offerWrap.getType() == OfferType.Name){
            return R.layout.offerv_name;
        }
        else if (offerWrap.getType() == OfferType.Dense){
            return R.layout.offerv_dense;
        }
        else {
            return R.layout.offerv_full;
        }
    }

    public void addListener(BodyFragmentListener listener) {
        listeners.add(listener);
    }

    public void removeListener(BodyFragmentListener listener){
        listeners.remove(listener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.body_view, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.offersListView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    private void onOfferClicked(OfferWrap offer) {
        for (BodyFragmentListener l : listeners){
            l.offerClicked(offer);
        }
    }
}