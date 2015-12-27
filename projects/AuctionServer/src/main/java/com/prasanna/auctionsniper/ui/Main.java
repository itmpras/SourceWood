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
    private Set<XMPPAuction> notToBeGCd = new HashSet<XMPPAuction>();
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
        XMPPConnection xmppConnection = connectTo(hostName, userName, password);
        final Main main = new Main();
        main.disconnectConnectionOnClose(xmppConnection);
        main.addUserRequestListnerForConnection(xmppConnection);

    }

    private void addUserRequestListnerForConnection(final XMPPConnection xmppConnection) {
        ui.addUserRequestListner(new UserRequestListner() {
            public void joinAuction(String itemid) {
                joinAuctionWith(xmppConnection, itemid);
            }
        });
    }


    private void joinAuctionWith(XMPPConnection xmppConnection, String itemId) {

        XMPPAuction xmppAuction = new XMPPAuction(xmppConnection, itemId);
        SniperSnapshot joinning = SniperSnapshot.joinning(itemId);
        SwingThreadSniperListner swingThreadSniperListner = new SwingThreadSniperListner(sniperTableModel);
        sniperTableModel.addSniper(joinning);
        xmppAuction.addActionEventListner(new AuctionSniper(xmppAuction, swingThreadSniperListner, joinning));
        this.notToBeGCd.add(xmppAuction);
        xmppAuction.join();
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
        private final XMPPConnection xmppConnection;
        private final Announcer<AuctionEventListner> auctionEventListnerAnnouncer = Announcer.to(AuctionEventListner.class);


        public XMPPAuction(XMPPConnection xmppConnection, String itemId) {
            this.xmppConnection = xmppConnection;
            this.chat = xmppConnection.getChatManager().createChat(getAutionId(itemId, xmppConnection), null);
            chat.addMessageListener(new AuctionMessageTranslator(SNIPER_ID, auctionEventListnerAnnouncer.announce()));
        }

        @Override
        public void join() {

            String userFromConnection = getUserFromConnection(xmppConnection);
            String joinMessage = String.format(JOIN_COMMAND_FORMAT, userFromConnection);
            sendMessage(joinMessage);
        }

        @Override
        public void bid(int amount) {

            String message = String.format(BID_COMMAD_FORMAT, amount);
            sendMessage(message);

        }

        public void addActionEventListner(AuctionEventListner eventListner) {
            auctionEventListnerAnnouncer.addListener(eventListner);
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

        private String getUserFromConnection(XMPPConnection xmppConnection) {
            String user = xmppConnection.getUser();
            int indexOf = user.indexOf("@");
            if (indexOf != -1) {
                user = user.substring(0, indexOf);
            }
            return user;
        }

    }

}




