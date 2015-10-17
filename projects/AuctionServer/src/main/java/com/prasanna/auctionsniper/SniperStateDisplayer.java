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

    public void sniperLost() {

        updateUIStatus(Main.STATUS_LOST);
    }

    private void updateUIStatus(final String status) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                mainWindow.showStatus(status);
            }
        });
    }

    private void updateUIStatus(final SniperSnapshot state) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                mainWindow.snipperStateChanged(state);
            }
        });
    }

    public void sniperBidding(SniperSnapshot sniperSnapshot) {

        updateUIStatus(sniperSnapshot);
    }

    public void sniperWinning(SniperSnapshot sniperSnapshot) {

        //updateUIStatus(sniperSnapshot, Main.STATUS_WINNING);
    }

    public void sniperWon() {

        updateUIStatus(Main.STATUS_WON);
    }

    public void sniperStateChanged(SniperSnapshot sniperSnapshot) {

        updateUIStatus(sniperSnapshot);
    }
}
