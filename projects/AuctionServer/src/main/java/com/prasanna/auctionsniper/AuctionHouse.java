package com.prasanna.auctionsniper;

/**
 * Created by prasniths on 27/12/15.
 */
public interface AuctionHouse {
    Auction auctionFor(String itemId);

    void disconnect();
}
