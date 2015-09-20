package com.prasanna.auctionsniper;

import org.jivesoftware.smack.XMPPException;

/**
 * Created by gopinithya on 05/09/15.
 */
public class AuctionSniper implements AuctionEventListner {

    private final SniperListner sniperListner;
    private final Auction auction;

    public AuctionSniper(Auction auction, SniperListner sniperListner) {

        this.auction = auction;
        this.sniperListner = sniperListner;
    }

    @Override
    public void auctionClosed() {

        sniperListner.sniperLost();
    }

    @Override
    public void currentPrice(int price, int increment) {

        auction.bid(price + increment);
        sniperListner.sniperBidding();
    }
}
