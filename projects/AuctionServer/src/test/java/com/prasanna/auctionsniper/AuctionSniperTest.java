package com.prasanna.auctionsniper;

import com.prasanna.auctionsniper.ui.SniperState;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * Created by gopinithya on 05/09/15.
 */

public class AuctionSniperTest {

    public static final String ITEM_ID = "item-id";
    private JUnitRuleMockery ruleMockery = new JUnitRuleMockery();
    private States sniperState;
    private SniperListner sniperListner = ruleMockery.mock(SniperListner.class);
    private Auction auction = ruleMockery.mock(Auction.class);
    private AuctionSniper auctionSniper = new AuctionSniper(auction, sniperListner, SniperSnapshot.joinning(ITEM_ID));

    @Before
    public void setUp() throws Exception {

        sniperState = ruleMockery.states("sniper");
    }

    @Test
    public void reportsLostWhenAuctionClosesImmediately() throws Exception {

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
    public void reportsLostWhenAuctionClosesWhileBidding() throws Exception {

        ruleMockery.checking(new Expectations() {
                                 {
                                     ignoring(auction);
                                     allowing(sniperListner).sniperStateChanged(with(aSniperThatIs(SniperState.BIDDING)));
                                     then(sniperState.is("Bidding"));
                                     atLeast(1).of(sniperListner).sniperLost();
                                     when(sniperState.is("Bidding"));

                                 }
                             }
        );

        auctionSniper.currentPrice(AuctionEventListner.PriceSource.FromOtherBidder, 123, 45);
        auctionSniper.auctionClosed();
        ruleMockery.assertIsSatisfied();
    }

    @Test
    public void reportsWinningAfterBiddingHigher() throws Exception {

        ruleMockery.checking(new Expectations() {
                                 {
                                     ignoring(auction);
                                     allowing(sniperListner).sniperStateChanged(with(aSniperThatIs(SniperState.BIDDING)));
                                     then(sniperState.is("Bidding"));
//                                     atLeast(1).of(sniperListner).sniperStateChanged(new SniperSnapshot(ITEM_ID, 125, 125, SniperState.WINNING));
                                     atLeast(1).of(sniperListner).sniperStateChanged(with(aSniperThatIs(SniperState.WINNING)));
                                     when(sniperState.is("Bidding"));
                                 }
                             }
        );

        auctionSniper.currentPrice(AuctionEventListner.PriceSource.FromOtherBidder, 123, 45);
        auctionSniper.currentPrice(AuctionEventListner.PriceSource.FromSnipper, 125, 45);
        ruleMockery.assertIsSatisfied();
    }

    @Test
    public void reportsBiddingAfterReceivingPriceFromOtherBidder() throws Exception {

        ruleMockery.checking(new Expectations() {
                                 {
                                     ignoring(auction);
                                     then(sniperState.is("Winning"));
                                     atLeast(1).of(sniperListner).sniperStateChanged(with(any(SniperSnapshot.class)));
                                     when(sniperState.is("Winning"));
                                 }
                             }
        );

        auctionSniper.currentPrice(AuctionEventListner.PriceSource.FromOtherBidder, 123, 45);
        ruleMockery.assertIsSatisfied();
    }

    @Test
    public void repostsBiddingAfterReceivingBid() throws Exception {

        ruleMockery.checking(new Expectations() {
                                 {
                                     ignoring(auction);
                                     allowing(sniperListner).sniperBidding(with(aSniperThatIs(SniperState.BIDDING)));
                                     then(sniperState.is("Bidding"));
                                     atLeast(1).of(sniperListner).sniperWinning(with(any(SniperSnapshot.class)));
                                     when(sniperState.is("Bidding"));

                                 }
                             }
        );

    }

    @Test
    public void reportsWinstWhenAuctionCloses() throws Exception {

        ruleMockery.checking(new Expectations() {
                                 {
                                     ignoring(auction);
                                     allowing(sniperListner).sniperStateChanged(with(any(SniperSnapshot.class)));
                                     then(sniperState.is("Winning"));
                                     atLeast(1).of(sniperListner).sniperWon();
                                     when(sniperState.is("Winning"));

                                 }
                             }
        );

        auctionSniper.currentPrice(AuctionEventListner.PriceSource.FromSnipper, 123, 45);
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
                atLeast(1).of(sniperListner).sniperStateChanged(new SniperSnapshot(ITEM_ID, price, price + increment, SniperState.BIDDING));

            }
        });

        auctionSniper.currentPrice(AuctionEventListner.PriceSource.FromOtherBidder, price, increment);
        ruleMockery.assertIsSatisfied();
    }

    @Test
    public void reportsIsWinningWhenCurrentBidComesFromSnipper() throws Exception {

        ruleMockery.checking(new Expectations() {
            {
                one(sniperListner).sniperStateChanged(with(any(SniperSnapshot.class)));
            }

        });

        auctionSniper.currentPrice(AuctionEventListner.PriceSource.FromSnipper, 1097, 100);
    }

    private Matcher<SniperSnapshot> aSniperThatIs(final SniperState sniperState) {

        return new FeatureMatcher<SniperSnapshot, SniperState>(equalTo(sniperState), "Sniper That is", "was") {
            @Override
            protected SniperState featureValueOf(SniperSnapshot sniperSnapshot) {

                return sniperSnapshot.sniperState;
            }
        };
    }
}
