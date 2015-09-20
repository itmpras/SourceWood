package com.prasanna.auctionsniper;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Test;

/**
 * Created by gopinithya on 05/09/15.
 */

public class AuctionSniperTest {
    private JUnitRuleMockery ruleMockery = new JUnitRuleMockery();
    private SniperListner sniperListner = ruleMockery.mock(SniperListner.class);
    private Auction auction = ruleMockery.mock(Auction.class);

    private AuctionSniper auctionSniper = new AuctionSniper(auction, sniperListner);

    @Test
    public void reportsLostWhenAuctionLost() throws Exception {

        ruleMockery.checking(new Expectations() {
                                 {
                                     exactly(1).of(sniperListner).sniperLost();
                                 }
                             }
        );

        auctionSniper.auctionClosed();
        ruleMockery.assertIsSatisfied();
    }

    @Test
    public void bidsHigherAndReportsBiddingWhenPricesArrives() throws Exception {

        final int price = 1000;
        final int increment = 10;

        ruleMockery.checking(new Expectations() {
            {
                one(auction).bid(price + increment);
                atLeast(1).of(sniperListner).sniperBidding();

            }
        });

        auctionSniper.currentPrice(price, increment);
        ruleMockery.assertIsSatisfied();
    }
}
