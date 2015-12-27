package com.prasanna.auctionsniper;

import com.prasanna.applicationrunner.ApplicationRunner;
import com.prasanna.auctionserver.FakeAuctionServer;
import com.prasanna.auctionsniper.xmpp.XMPPAuction;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

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

    @Test
    public void receivesEventsFromAuctionServerAfterJoining() throws Exception {
        auction.startSellingItem();
        CountDownLatch auctionClosed = new CountDownLatch(1);
        XMPPConnection xmppConnection = getConnection();
        Auction xmppAuction = new XMPPAuction(xmppConnection, auction.getItemId());
        xmppAuction.addActionEventListner(auctionClosedListner(auctionClosed));
        xmppAuction.join();
        auction.hasReceivedJoinRequstFromSniper(ApplicationRunner.SNIPER_ID);
        auction.announceClosed();
        assertTrue("Should have closed", auctionClosed.await(2, TimeUnit.SECONDS));

    }

    public XMPPConnection getConnection() throws XMPPException {
        XMPPConnection xmppConnection = new XMPPConnection(ApplicationRunner.XMPP_HOST);
        xmppConnection.connect();
        xmppConnection.login(ApplicationRunner.SNIPER_ID, ApplicationRunner.SNIPER_PASSWORD, "Auction");
        return xmppConnection;
    }

    private AuctionEventListner auctionClosedListner(final CountDownLatch auctionClosed) {
        return new AuctionEventListner() {
            public void auctionClosed() {
                auctionClosed.countDown();
            }

            public void currentPrice(PriceSource priceSource, int price, int increment) {

            }
        };
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
