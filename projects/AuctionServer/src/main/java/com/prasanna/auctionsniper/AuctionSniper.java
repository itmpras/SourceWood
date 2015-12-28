package com.prasanna.auctionsniper;

/**
 * Created by gopinithya on 05/09/15.
 */
public class AuctionSniper implements AuctionEventListner {

    private SniperListner sniperListner;
    private final Auction auction;
    private boolean isWinning = false;
    private SniperSnapshot sniperSnapshot;

    public AuctionSniper(Auction auction, String itemID) {
        this.auction = auction;
        sniperSnapshot = SniperSnapshot.joinning(itemID);
    }

    public void addSniperListner(SniperListner sniperListner) {
        this.sniperListner = sniperListner;
    }

    public void auctionClosed() {
        sniperSnapshot = sniperSnapshot.closed();
        notifyChange();
    }

    public SniperSnapshot getSniperSnapshot() {
        return sniperSnapshot;
    }

    private void notifyChange() {
        sniperListner.sniperStateChanged(sniperSnapshot);
    }

    public void currentPrice(PriceSource priceSource, int price, int increment) {

        switch (priceSource) {
            case FromOtherBidder:
                int bid = price + increment;
                auction.bid(bid);
                sniperSnapshot = this.sniperSnapshot.bidding(price, bid);
                break;
            case FromSnipper:
                isWinning = true;
                sniperSnapshot = this.sniperSnapshot.winning(price);
                break;
        }
        notifyChange();
    }
}
