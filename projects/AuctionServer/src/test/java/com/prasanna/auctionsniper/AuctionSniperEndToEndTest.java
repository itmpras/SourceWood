package com.prasanna.auctionsniper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by gopinithya on 26/08/15.
 */
public class AuctionSniperEndToEndTest {

    private FakeAuctionServer auction;
    private ApplicationRunner auctionSnipper;

    @Before
    public void setUp() throws Exception {

        auction = new FakeAuctionServer("item-123 ");
        auctionSnipper = new AppplicationRunner();
    }

    @Test
    public void auctionSnipperJoinsTillAuctionCloses() throws Exception {

        auction.startSellingItem();
        auctionSnipper.startBiddingIn(auction);
        auction.hasReceivedJoinRequstFromSniper();
        auction.announceClosed();
        auctionSnipper.showsSniperHasLostAuction();
    }

    @After
    public void closeAuction() throws Exception {

        auction.closeAuction();
    }

    @After
    public void closeApplication() throws Exception {

        auctionSnipper.close();

    }
}
