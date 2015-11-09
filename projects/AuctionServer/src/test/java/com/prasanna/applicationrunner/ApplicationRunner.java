package com.prasanna.applicationrunner;

import com.prasanna.auctionserver.FakeAuctionServer;
import com.prasanna.auctionsniper.ui.Main;
import com.prasanna.auctionsniper.ui.MainWindow;
import com.prasanna.auctionsniper.ui.SniperTableModel;

/**
 * Created by gopinithya on 26/08/15.
 */
public class ApplicationRunner {

    public static final String XMPP_HOST = "localhost";
    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    private AuctionSniperDriver driver;


    public ApplicationRunner() {

    }

    public void startBiddingIn(final FakeAuctionServer auction) {

        Thread thread = new Thread() {

            @Override
            public void run() {

                try {
                    Main.main(XMPP_HOST, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.hasTitle(MainWindow.MAIN_WINDOW_NAME);
        driver.hasColumnTitles();
        driver.showsSniperStatus(SniperTableModel.STATUS_JOINING);

    }

    public void showsSniperHasLostAuction() {

        driver.showsSniperStatus(SniperTableModel.STATUS_LOST);
    }

    public void close() {

        if (driver != null) {
            driver.dispose();
        }
    }

    public void hasShownSnipperIsBidding(FakeAuctionServer auctionServer, int lastPrice, int lastBid) {

        driver.showsSniperStatus(auctionServer.getItemId() , lastPrice, lastBid, SniperTableModel.STATUS_BIDDING);
    }

    public void showsSniperHasWonAuction(FakeAuctionServer auctionServer,int lastPrice) {

        driver.showsSniperStatus(auctionServer.getItemId(), lastPrice, lastPrice, SniperTableModel.STATUS_WON);
    }

    public void hasShownSniperIsWinning(FakeAuctionServer auctionServer,int winningBid) {

        driver.showsSniperStatus(auctionServer.getItemId(), winningBid, winningBid, SniperTableModel.STATUS_WINNING);
    }
}
