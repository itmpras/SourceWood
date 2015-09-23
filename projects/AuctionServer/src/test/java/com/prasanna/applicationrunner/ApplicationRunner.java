package com.prasanna.applicationrunner;

import com.prasanna.auctionserver.FakeAuctionServer;
import com.prasanna.auctionsniper.ui.Main;

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
        driver.showsSniperStatus(Main.STATUS_JOINING);
    }

    public void showsSniperHasLostAuction() {

        driver.showsSniperStatus(Main.STATUS_LOST);
    }

    public void close() {

        if (driver != null) {
            driver.dispose();
        }
    }

    public void hasShownSnipperIsBidding() {

        driver.showsSniperStatus(Main.STATUS_BIDDING);
    }

    public void showsSniperHasWonAuction() {

        driver.showsSniperStatus(Main.STATUS_WON);
    }

    public void hasShownSniperIsWinning() {
        driver.showsSniperStatus(Main.STATUS_WINNING);
    }
}
