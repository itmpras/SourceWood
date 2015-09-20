package com.prasanna.auctionsniper;

import com.prasanna.auctionsniper.ui.Main;
import com.prasanna.auctionsniper.ui.MainWindow;

import javax.swing.*;

/**
 * Created by gopinithya on 20/09/15.
 */
public class SniperStateDisplayer implements SniperListner {
    private final MainWindow mainWindow;

    public SniperStateDisplayer(MainWindow ui) {

        mainWindow = ui;
    }

    @Override
    public void sniperLost() {

        updateUIStatus(Main.STATUS_LOST);
    }

    private void updateUIStatus(final String status) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                mainWindow.showStatus(status);
            }
        });
    }

    @Override
    public void sniperBidding() {

        updateUIStatus(Main.STATUS_BIDDING);
    }
}
