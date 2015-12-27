package com.prasanna.auctionsniper.ui;

import com.prasanna.auctionsniper.*;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

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
    private Set<Chat> notToBeGCd = new HashSet<Chat>();
    private AuctionSniper auctionSniper;
    private AuctionMessageTranslator auctionMessageTranslator;
    public static final String SNIPER_ID = "sniper";
    private final SniperTableModel sniperTableModel = new SniperTableModel();

    public Main() throws Exception {


        startUI();
    }

    public static void main(String args[]) throws Exception {

        String hostName = args[0];
        String userName = args[1];
        String password = args[2];
        XMPPConnection xmppConnection = connectTo(hostName, userName, password);
        final Main main = new Main();
        main.disconnectConnectionOnClose(xmppConnection);
        main.addUserRequestListnerForConnection(xmppConnection);

       /* for (int i = 3; i < args.length; i++) {
            main.joinAuctionWith(xmppConnection, args[i]);
        } */
    }

    private void addUserRequestListnerForConnection(final XMPPConnection xmppConnection) {
        ui.addUserRequestListner(new UserRequestListner() {
            public void joinAuction(String itemid) {
                joinAuctionWith(xmppConnection, itemid);
            }
        });
    }


    private void joinAuctionWith(XMPPConnection xmppConnection, String itemId) {
        final Chat chat = xmppConnection.getChatManager().createChat(getAutionId(itemId, xmppConnection), null);
        XMPPAuction xmppAuction = new XMPPAuction(chat);
        SwingThreadSniperListner swingThreadSniperListner = new SwingThreadSniperListner(sniperTableModel);
        SniperSnapshot joinning = SniperSnapshot.joinning(itemId);
        sniperTableModel.addSniper(joinning);
        auctionSniper = new AuctionSniper(xmppAuction, swingThreadSniperListner, joinning);
        auctionMessageTranslator = new AuctionMessageTranslator(SNIPER_ID, auctionSniper);
        this.notToBeGCd.add(chat);
        chat.addMessageListener(auctionMessageTranslator);

        xmppAuction.join(getUserFromConnection(xmppConnection));
    }

    private String getUserFromConnection(XMPPConnection xmppConnection) {
        String user = xmppConnection.getUser();
        int indexOf = user.indexOf("@");
        if (indexOf != -1) {
            user = user.substring(0, indexOf);
        }
        return user;
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

                ui = new MainWindow(sniperTableModel);
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




