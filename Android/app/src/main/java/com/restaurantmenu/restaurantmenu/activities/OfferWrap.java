package com.restaurantmenu.restaurantmenu.activities;

import com.restaurantmenu.restaurantmenu.contracts.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dszcz_000 on 25.08.2015.
 */
public class OfferWrap {
    private Offer offer;
    public OfferWrap parentOffer;
    public List<OfferWrap> offers = new ArrayList<>();

    public OfferWrap(){

    }
    private void initialize(Offer offer, OfferWrap parent, List<OfferWrap> offers){
        this.offer = offer;
        this.parentOffer = parent;
        this.offers = offers;
    }

    public String getName(){
        return offer.getName();
    }

    public String getDescription(){
        return offer.getDescription();
    }

    public String getImageUrl(){
        return offer.getImageUrl();
    }

    public List<OfferWrap> getOffers(){
        return offers;
    }

    public int getType(){
        return offer.getType();
    }

    public static OfferWrap wrapToRoot(List<Offer> offers){
        OfferWrap parent = new OfferWrap();
        wrap(offers, parent);
        return parent;
    }

    private static void wrap(List<Offer> offers, OfferWrap parent){
        for (Offer o : offers){
            OfferWrap offerWrap = new OfferWrap();
            offerWrap.offer = o;
            offerWrap.parentOffer = parent;
            parent.offers.add(offerWrap);

            if (o.getOffers() != null) {
                wrap(o.getOffers(), offerWrap);
            }
        }
    }
}
