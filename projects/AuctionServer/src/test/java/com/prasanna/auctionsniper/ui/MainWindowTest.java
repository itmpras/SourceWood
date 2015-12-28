package com.prasanna.auctionsniper.ui;

import com.objogate.wl.swing.probe.ValueMatcherProbe;
import com.prasanna.applicationrunner.AuctionSniperDriver;
import com.prasanna.auctionsniper.SniperPortFolio;
import com.prasanna.auctionsniper.UserRequestListner;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by prasniths on 26/12/15.
 */
public class MainWindowTest {
    private final SniperPortFolio sniperPortFolio = new SniperPortFolio();
    private final MainWindow mainWindow = new MainWindow(sniperPortFolio);
    private final AuctionSniperDriver auctionSniperDriver = new AuctionSniperDriver(100);

    @Test
    public void makeUserRequestWhenJoinButtonClicked() throws Exception {
        String item = "n-item-id";
        final ValueMatcherProbe<String> probe = new ValueMatcherProbe<String>(
                equalTo(item), "join request");

        mainWindow.addUserRequestListner(
                new UserRequestListner() {
                    public void joinAuction(String itemid) {
                        probe.setReceivedValue(itemid);
                    }
                }
        );

        auctionSniperDriver.startBiddingFor(item);
        auctionSniperDriver.check(probe);
    }
}