package com.prasanna.auctionsniper;

/**
 * Created by gopinithya on 05/09/15.
 */
public class AuctionSniper implements AuctionEventListner {

    private final SniperListner sniperListner;
    private final Auction auction;
    private boolean isWinning = false;

    public AuctionSniper(Auction auction, SniperListner sniperListner) {

        this.auction = auction;
        this.sniperListner = sniperListner;
    }

    @Override
    public void auctionClosed() {

        if (isWinning) {
            sniperListner.sniperWon();
        } else {
            sniperListner.sniperLost();
        }
    }

    @Override
    public void currentPrice(PriceSource priceSource, int price, int increment) {

        switch (priceSource) {
            case FromOtherBidder:
                auction.bid(price + increment);
                sniperListner.sniperBidding();
                break;
            case FromSnipper:
                isWinning = true;
                sniperListner.sniperWinning();
                break;
        }
    }

    @Override
    public void snipperWinning() {

        sniperListner.sniperWinning();
    }
}
