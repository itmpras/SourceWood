package com.prasanna.applicationrunner;

import com.prasanna.auctionserver.FakeAuctionServer;

/**
 * Created by gopinithya on 26/08/15.
 */
public class ApplicationRunner {

    public static final String XMPP_HOST = "localhost";
    public static final String USER_NAME = "sniper";
    public static final String PASSWORD = "sniper";
    AuctionSniperDriver driver;

    public ApplicationRunner() {

    }

    public void startBiddingIn(final FakeAuctionServer auction) {

        Thread thread = new Thread() {

            @Override
            public void run() {

                AuctionSniper.main(XMPP_HOST, USER_NAME, PASSWORD, auction.getItemId());
            }
        };

        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.showsSniperStatus("Joining");
    }

    public void showsSniperHasLostAuction() {

    }

    public void close() {

    }
}
