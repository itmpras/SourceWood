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
    private FakeAuctionServer auction2;
    private ApplicationRunner application;

    @Before
    public void setUp() throws Exception {

        auction = new FakeAuctionServer("54321");
        auction2 = new FakeAuctionServer("65432");
        application = new ApplicationRunner();
    }

    @Test
    public void auctionSniperJoinsTillAuctionCloses() throws Exception {

        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequstFromSniper(ApplicationRunner.SNIPER_ID);
        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

    @Test
    public void sniperMakesHigherBidButLoses() throws Exception {

        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequstFromSniper(ApplicationRunner.SNIPER_ID);
        auction.reportPrice(1000, 98, "OtherBidder");
        application.hasShownSnipperIsBidding(auction, 1000, 1098);
        auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_ID);
        auction.announceClosed();
        application.showsSniperHasLostAuction();

    }

    @Test
    public void snipperMakesHigherBidAndWins() throws Exception {

        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequstFromSniper(ApplicationRunner.SNIPER_ID);
        auction.reportPrice(1000, 98, "OtherBidder");
        application.hasShownSnipperIsBidding(auction, 1000, 1098);
        auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_ID);
        auction.reportPrice(1098, 98, ApplicationRunner.SNIPER_ID);
        application.hasShownSniperIsWinning(auction, 1098);
        auction.announceClosed();
        application.showsSniperHasWonAuction(auction, 1098);

    }

    @Test
    public void snipperMakingMultipleBidsAndWins() throws Exception {

        auction.startSellingItem();
        auction2.startSellingItem();

        application.startBiddingIn(auction, auction2);
        auction.hasReceivedJoinRequstFromSniper(ApplicationRunner.SNIPER_ID);
        auction2.hasReceivedJoinRequstFromSniper(ApplicationRunner.SNIPER_ID);


        auction.reportPrice(1000, 98, "OtherBidder");
        auction2.reportPrice(1000, 98, "OtherBidder");

        application.hasShownSnipperIsBidding(auction, 1000, 1098);
        application.hasShownSnipperIsBidding(auction2, 1000, 1098);

        auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_ID);
        auction.reportPrice(1098, 98, ApplicationRunner.SNIPER_ID);

        auction2.hasReceivedBid(1098, ApplicationRunner.SNIPER_ID);
        auction2.reportPrice(1098, 98, ApplicationRunner.SNIPER_ID);

        application.hasShownSniperIsWinning(auction, 1098);
        application.hasShownSniperIsWinning(auction2, 1098);

        auction.announceClosed();
        auction2.announceClosed();

        application.showsSniperHasWonAuction(auction, 1098);
        application.showsSniperHasWonAuction(auction2, 1098);

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
