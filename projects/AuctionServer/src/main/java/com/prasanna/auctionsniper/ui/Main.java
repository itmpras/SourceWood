package com.prasanna.auctionsniper.ui;

import com.prasanna.auctionsniper.*;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

public class Main {

    public static final String STATUS_JOINING = "Joining";
    public static final String STATUS_LOST = "Lost";
    public static final String STATUS_WON = "Won";
    public static final String STATUS_BIDDING = "Bidding";
    public static final String RESOURCE = "Auction";
    public static final String ITEM_ID_AS_LOGIN = "auction-item-%s";
    public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + RESOURCE;

    public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Event: JOIN, User: %s ";
    public static final String BID_COMMAD_FORMAT = "SOLVersion: 1.1; Event: BID, Price: %d ";
    public static final String PRICE_COMMAND_FORMAT = "SOLVersion: 1.1; Event: PRICE; " +
            "CurrentPrice: %d; Increment: %d; Bidder: %s ";
    public static final String STATUS_WINNING = "Winning";

    private MainWindow ui;
    private Chat notToBeGCd;
    private AuctionSniper auctionSniper;
    private AuctionMessageTranslator auctionMessageTranslator;
    public static final String SNIPER_ID = "sniper";

    public Main() throws Exception {

        startUI();
    }

    public static void main(String args[]) throws Exception {

        main("", "", "", "");
    }

    public static void main(String xmppHost, String userName, String password, String itemId) throws Exception {

        final Main main = new Main();
        main.joinAuction(xmppHost, userName, password, itemId, main);
    }

    private void joinAuction(String xmppHost, String userName, String password, String itemId, final Main main) throws XMPPException {

        XMPPConnection xmppConnection = connectTo(xmppHost, userName, password);
        disconnectConnectionOnClose(xmppConnection);

        final Chat chat = xmppConnection.getChatManager().createChat(getAutionId(itemId, xmppConnection), null);
        XMPPAuction xmppAuction = new XMPPAuction(chat);
        SniperStateDisplayer sniperStateDisplayer = new SniperStateDisplayer(this.ui);
        auctionSniper = new AuctionSniper(xmppAuction, sniperStateDisplayer, SniperSnapshot.joinning(itemId));
        auctionMessageTranslator = new AuctionMessageTranslator(SNIPER_ID, auctionSniper);
        this.notToBeGCd = chat;
        chat.addMessageListener(auctionMessageTranslator);
        xmppAuction.join(userName);
    }

    private void disconnectConnectionOnClose(final XMPPConnection xmppConnection) {

        ui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {

                super.windowClosed(e);
                xmppConnection.disconnect();
            }
        });
    }

    private static String getAutionId(String itemId, XMPPConnection xmppConnection) {

        return String.format(AUCTION_ID_FORMAT, itemId, xmppConnection.getServiceName());
    }

    private static XMPPConnection connectTo(String xmppHost, String userName, String password) throws XMPPException {

        XMPPConnection xmppConnection = new XMPPConnection(xmppHost);
        xmppConnection.connect();
        xmppConnection.login(userName, password, RESOURCE);
        return xmppConnection;
    }

    public void startUI() throws Exception {

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {

                ui = new MainWindow();
            }
        });

    }

    public static class XMPPAuction implements Auction {

        private final Chat chat;

        public XMPPAuction(Chat chat) {

            this.chat = chat;
        }

        @Override
        public void join(String userName) {

            String joinMessage = String.format(JOIN_COMMAND_FORMAT, userName);
            sendMessage(joinMessage);
        }

        @Override
        public void bid(int amount) {

            String message = String.format(BID_COMMAD_FORMAT, amount);
            sendMessage(message);

        }

        private void sendMessage(String message) {

            Message bidMessage = new Message();
            bidMessage.setBody(message);
            try {
                // TODO to refactor
                chat.sendMessage(bidMessage);
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
    }

}




