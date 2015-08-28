package com.prasanna.auctionsniper.ui;

import javax.swing.*;

/**
 * Created by gopinithya on 28/08/15.
 */
public class Main {

    public static final String STATUS_JOINING = "Joining";
    public static final String STATUS_LOST = "Lost";

    private MainWindow ui;

    public Main() throws Exception {

        startUI();
    }

    public static void main(String args[]) throws Exception {

        main("", "", "", "");
    }

    public static void main(String xmppHost, String userName, String password, String itemId) throws Exception {

        Main main = new Main();
    }

    public void startUI() throws Exception {

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {

                ui = new MainWindow();
            }
        });

    }
}
