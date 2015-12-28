package com.prasanna.auctionsniper;

import com.prasanna.auctionsniper.ui.SniperTableModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by prasniths on 27/12/15.
 */
public class SniperLauncher implements UserRequestListner {

    private final AuctionHouse auctionHouse;
    private final SniperCollector collector;

    public SniperLauncher(AuctionHouse auctionHouse, SniperCollector collector) {
        this.auctionHouse = auctionHouse;
        this.collector = collector;
    }

    public void joinAuction(String itemid) {
        Auction xmppAuction = auctionHouse.auctionFor(itemid);
        AuctionSniper sniper = new AuctionSniper(xmppAuction, itemid);
        xmppAuction.addActionEventListner(sniper);
        collector.addSniper(sniper);
        xmppAuction.join();
    }

}
