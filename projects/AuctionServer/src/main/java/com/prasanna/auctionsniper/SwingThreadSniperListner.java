package com.prasanna.auctionsniper;

import com.prasanna.auctionsniper.ui.Main;
import com.prasanna.auctionsniper.ui.MainWindow;
import com.prasanna.auctionsniper.ui.SniperTableModel;

import javax.swing.*;

/**
 * Created by gopinithya on 20/09/15.
 */
public class SwingThreadSniperListner implements SniperListner {
    private final SniperTableModel sniperTableModel;

    public SwingThreadSniperListner(SniperTableModel sniperTableModel) {

        this.sniperTableModel = sniperTableModel;
    }

    private void updateUIStatus(final SniperSnapshot state) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                sniperTableModel.sniperStateChanged(state);
            }
        });
    }

    public void sniperStateChanged(SniperSnapshot sniperSnapshot) {

        updateUIStatus(sniperSnapshot);
    }
}
