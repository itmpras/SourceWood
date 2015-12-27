package com.prasanna.auctionsniper;

import com.prasanna.auctionsniper.ui.SniperTableModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by prasniths on 27/12/15.
 */
public class SniperLauncher implements UserRequestListner {

    private final AuctionHouse auctionHouse;
    private final SniperTableModel sniperTableModel;
    private Set<Auction> notToBeGCd = new HashSet<Auction>();

    public SniperLauncher(AuctionHouse auctionHouse, SniperTableModel sniperTableModel) {
        this.auctionHouse = auctionHouse;
        this.sniperTableModel = sniperTableModel;
    }

    public void joinAuction(String itemid) {
        Auction xmppAuction = auctionHouse.auctionFor(itemid);
        SniperSnapshot joinning = SniperSnapshot.joinning(itemid);
        SwingThreadSniperListner swingThreadSniperListner = new SwingThreadSniperListner(sniperTableModel);
        sniperTableModel.addSniper(joinning);
        xmppAuction.addActionEventListner(new AuctionSniper(xmppAuction, swingThreadSniperListner, joinning));
        this.notToBeGCd.add(xmppAuction);
        xmppAuction.join();
    }

}
