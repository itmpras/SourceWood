package com.prasanna.auctionsniper;

import com.prasanna.applicationrunner.ApplicationRunner;
import com.prasanna.auctionserver.FakeAuctionServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by gopinithya on 26/08/15.
 */
public class AuctionSniperEndToEndTest {

    private FakeAuctionServer auction;
    private ApplicationRunner application;

    @Before
    public void setUp() throws Exception {

        auction = new FakeAuctionServer("54321");
        application = new ApplicationRunner();
    }

    @Test
    public void auctionSnipperJoinsTillAuctionCloses() throws Exception {

        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequstFromSniper(ApplicationRunner.SNIPER_ID);
        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

    @Test
    public void snipperMakesHigherBidButLoses() throws Exception {

        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequstFromSniper(ApplicationRunner.SNIPER_ID);
        auction.reportPrice(1000, 98, "OtherBidder");
        application.hasShownSnipperIsBidding();
        auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_ID);
        auction.announceClosed();
        application.showsSniperHasLostAuction();

    }

    @After
    public void closeAuction() throws Exception {

        auction.closeAuction();
    }

    @After
    public void closeApplication() throws Exception {

        application.close();
    }
}
