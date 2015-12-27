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

    public void startBiddingIn(final FakeAuctionServer... auctions) {

        startSniper(auctions);


        for (FakeAuctionServer auction : auctions) {
            String itemId = auction.getItemId();
            driver.startBiddingFor(itemId);
            driver.showsSniperStatus(auction.getItemId(), 0, 0, SniperTableModel.STATUS_JOINING);
        }
    }

    private void startSniper(final FakeAuctionServer[] auctions) {
        Thread thread = new Thread() {

            @Override
            public void run() {

                try {
                    Main.main(arguments(auctions));
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
    }


    protected static String[] arguments(FakeAuctionServer... auctionServers) {
        String[] argument = new String[auctionServers.length + 3];
        argument[0] = XMPP_HOST;
        argument[1] = SNIPER_ID;
        argument[2] = SNIPER_PASSWORD;

        for (int i = 0; i < auctionServers.length; i++) {
            argument[i + 3] = auctionServers[i].getItemId();
        }

        return argument;

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

        driver.showsSniperStatus(auctionServer.getItemId(), lastPrice, lastBid, SniperTableModel.STATUS_BIDDING);
    }

    public void showsSniperHasWonAuction(FakeAuctionServer auctionServer, int lastPrice) {

        driver.showsSniperStatus(auctionServer.getItemId(), lastPrice, lastPrice, SniperTableModel.STATUS_WON);
    }

    public void hasShownSniperIsWinning(FakeAuctionServer auctionServer, int winningBid) {

        driver.showsSniperStatus(auctionServer.getItemId(), winningBid, winningBid, SniperTableModel.STATUS_WINNING);
    }
}
