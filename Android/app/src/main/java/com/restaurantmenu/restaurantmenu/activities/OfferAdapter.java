package com.restaurantmenu.restaurantmenu.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restaurantmenu.restaurantmenu.R;
import com.restaurantmenu.restaurantmenu.core.ImageLoader;
import com.restaurantmenu.restaurantmenu.contracts.Offer;
import com.restaurantmenu.restaurantmenu.services.datas.OfferType;

import java.util.List;

/**
 * Created by dszcz_000 on 2015-08-05.
 */
public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    public interface OfferAdapterListener {
        void offerClicked(OfferWrap offer);
    }

    private final int layoutResource;
    private List<OfferWrap> offers;
    private ImageLoader imageLoader;
    private OfferAdapterListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public interface ViewHolderListener {
            void offerClicked(int offerId);
        }
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imageView;
        public TextView descriptionView;
        public TextView shortDescriptionView;
        public ImageView circleImage;
        public int offerId;
        private ViewHolderListener listener;

        public ViewHolder(View v, ViewHolderListener listener) {
            super(v);
            this.listener = listener;

            textView = (TextView)v.findViewById(R.id.offerName);
            imageView = (ImageView)v.findViewById(R.id.offerImage);
            descriptionView = (TextView)v.findViewById(R.id.offerDescription);
            circleImage = (ImageView)v.findViewById(R.id.circleImage);
            shortDescriptionView = (TextView)v.findViewById(R.id.offerShortDescription);

            if (circleImage != null) {
                circleImage.setOnClickListener(this);
            }
            if (imageView != null) {
                imageView.setOnClickListener(this);
            }
            if (textView != null){
                textView.setOnClickListener(this);
            }
            if (descriptionView != null){
                descriptionView.setOnClickListener(this);
            }
            if (shortDescriptionView != null){
                shortDescriptionView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            listener.offerClicked(offerId);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OfferAdapter(List<OfferWrap> offers, ImageLoader imageLoader, int layoutResource, OfferAdapterListener listener) {
        this.offers = offers;
        this.layoutResource = layoutResource;
        this.imageLoader = imageLoader;
        this.listener = listener;
    }

    public void setOffers(List<OfferWrap> offers) {
        this.offers = offers;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OfferAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(layoutResource, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v, new ViewHolder.ViewHolderListener() {
            @Override
            public void offerClicked(int offerId) {
                listener.offerClicked(offers.get(offerId));
            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        OfferWrap offer = offers.get(position);

        if (holder.imageView != null) {
            Context context = holder.imageView.getContext();
            imageLoader.DisplayImage(offer.getImageUrl(), R.drawable.abc_ab_share_pack_mtrl_alpha, holder.imageView, context);
        }
        holder.offerId = position;
        if (holder.textView != null) {
            holder.textView.setText(offer.getName());
        }
        if (holder.descriptionView != null) {
            holder.descriptionView.setText(offer.getDescription());
        }
        if (holder.shortDescriptionView != null) {
            holder.shortDescriptionView.setText(offer.getDescription());
        }
        if (holder.circleImage != null) {
            holder.circleImage.setImageResource(R.mipmap.btn_circle_disable);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (offers != null) {
            return offers.size();
        }
        return 0;
    }
}