package com.prasanna.auctionsniper.ui;

import com.prasanna.auctionsniper.*;
import com.prasanna.auctionsniper.xmpp.XMPPAuction;
import com.prasanna.auctionsniper.xmpp.XmppAuctionHouse;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static final String RESOURCE = "Auction";
    public static final String ITEM_ID_AS_LOGIN = "auction-item-%s";
    public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + RESOURCE;

    public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Event: JOIN, User: %s ";
    public static final String BID_COMMAD_FORMAT = "SOLVersion: 1.1; Event: BID, Price: %d ";
    public static final String PRICE_COMMAND_FORMAT = "SOLVersion: 1.1; Event: PRICE; " +
            "CurrentPrice: %d; Increment: %d; Bidder: %s ";

    private MainWindow ui;
    private AuctionSniper auctionSniper;
    public static final String SNIPER_ID = "sniper";
    private final SniperTableModel sniperTableModel = new SniperTableModel();

    public Main() throws Exception {
        startUI();
    }

    public static void main(String args[]) throws Exception {

        String hostName = args[0];
        String userName = args[1];
        String password = args[2];

        XmppAuctionHouse xmppAuctionHouse = XmppAuctionHouse.connect(hostName, userName, password);

        final Main main = new Main();
        main.disconnectConnectionOnClose(xmppAuctionHouse);
        main.addUserRequestListnerForConnection(xmppAuctionHouse);

    }

    private void addUserRequestListnerForConnection(final AuctionHouse auctionHouse) {
        ui.addUserRequestListner(new SniperLauncher(auctionHouse, sniperTableModel));
    }


    private void disconnectConnectionOnClose(final AuctionHouse auctionHouse) {

        ui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {

                super.windowClosed(e);
                auctionHouse.disconnect();
            }
        });
    }


    public void startUI() throws Exception {

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {

                ui = new MainWindow(sniperTableModel);
            }
        });

    }

}




