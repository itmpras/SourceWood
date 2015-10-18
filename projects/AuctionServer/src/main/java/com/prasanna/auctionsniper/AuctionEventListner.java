package com.prasanna.auctionsniper;

/**
 * Created by gopinithya on 04/09/15.
 */
public interface AuctionEventListner {

    enum PriceSource {
        FromSnipper, FromOtherBidder;
    }

    void auctionClosed();

    void currentPrice(PriceSource priceSource, int price, int increment);
}
