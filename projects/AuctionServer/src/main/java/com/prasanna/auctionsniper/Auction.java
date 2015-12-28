package com.prasanna.auctionsniper;

/**
 * Created by gopinithya on 05/09/15.
 */
public interface Auction {

    void bid(int amount);

    void join();

    void addActionEventListner(AuctionEventListner eventListner);

}

