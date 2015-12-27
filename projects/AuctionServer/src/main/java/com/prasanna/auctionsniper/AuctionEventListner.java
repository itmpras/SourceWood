package com.prasanna.auctionsniper;

import java.util.EventListener;

/**
 * Created by gopinithya on 04/09/15.
 */
public interface AuctionEventListner extends EventListener {

    enum PriceSource {
        FromSnipper, FromOtherBidder;
    }

    void auctionClosed();

    void currentPrice(PriceSource priceSource, int price, int increment);
}
