package com.prasanna.auctionsniper;

import com.prasanna.auctionsniper.ui.SniperState;

/**
 * Created by gopinithya on 23/09/15.
 */
public class SniperSnapshot {
    public final String itemID;
    public final int lastPrice;
    public final int lastBid;
    public final SniperState sniperState;

    public SniperSnapshot(String itemID, int lastPrice, int lastBid, SniperState sniperState) {

        this.itemID = itemID;
        this.lastPrice = lastPrice;
        this.lastBid = lastBid;
        this.sniperState = sniperState;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof SniperSnapshot)) return false;

        SniperSnapshot that = (SniperSnapshot) o;

        if (lastBid != that.lastBid) return false;
        if (lastPrice != that.lastPrice) return false;
        if (itemID != null ? !itemID.equals(that.itemID) : that.itemID != null) return false;
        if (sniperState != that.sniperState) return false;

        return true;
    }

    public boolean isForSameItemAs(SniperSnapshot sniperSnapshot) {
        return this.itemID.equals(sniperSnapshot.itemID);
    }


    @Override
    public int hashCode() {

        int result = itemID != null ? itemID.hashCode() : 0;
        result = 31 * result + lastPrice;
        result = 31 * result + lastBid;
        result = 31 * result + (sniperState != null ? sniperState.hashCode() : 0);
        return result;
    }

    public SniperSnapshot bidding(int price, int bid) {

        return new SniperSnapshot(itemID, price, bid, SniperState.BIDDING);
    }

    public SniperSnapshot winning(int price) {

        return new SniperSnapshot(itemID, price, price, SniperState.WINNING);
    }

    public static SniperSnapshot joinning(String itemID) {

        return new SniperSnapshot(itemID, 0, 0, SniperState.JOINING);
    }

    public SniperSnapshot closed() {

        return new SniperSnapshot(itemID, lastPrice, lastBid, sniperState.whenAuctionClosed());
    }

}
