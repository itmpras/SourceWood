package com.prasanna.auctionsniper;

import com.prasanna.auctionsniper.ui.SniperState;

/**
 * Created by gopinithya on 05/09/15.
 */
public class AuctionSniper implements AuctionEventListner {

    private final SniperListner sniperListner;
    private final Auction auction;
    private boolean isWinning = false;
    private SniperSnapshot sniperSnapshot;

    public AuctionSniper(Auction auction, SniperListner sniperListner, SniperSnapshot sniperSnapshot) {

        this.auction = auction;
        this.sniperListner = sniperListner;
        this.sniperSnapshot = sniperSnapshot;
      }

    public void auctionClosed() {

        if (isWinning) {
            sniperListner.sniperWon();
        } else {
            sniperListner.sniperLost();
        }
    }

    public void currentPrice(PriceSource priceSource, int price, int increment) {

        SniperSnapshot updatedSnapshot = null;
        switch (priceSource) {
            case FromOtherBidder:
                int bid = price + increment;
                auction.bid(bid);
                updatedSnapshot = this.sniperSnapshot.bidding(price, bid);
                 break;
            case FromSnipper:
                isWinning = true;
                updatedSnapshot =this.sniperSnapshot.winning(price);
                 break;
        }

        sniperListner.sniperStateChanged(updatedSnapshot);

    }

    public void snipperWinning() {

    }
}
