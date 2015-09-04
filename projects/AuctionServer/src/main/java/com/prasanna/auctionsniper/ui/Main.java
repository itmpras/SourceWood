package com.prasanna.auctionsniper.ui;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import javax.swing.*;

public class Main {

    public static final String STATUS_JOINING = "Joining";
    public static final String STATUS_LOST = "Lost";
    public static final String RESOURCE = "Auction";
    public static final String ITEM_ID_AS_LOGIN = "auction-item-%s";
    public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + RESOURCE;

    private MainWindow ui;
    private Chat notToBeGCd;

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
        Chat chat = xmppConnection.getChatManager().createChat(getAutionId(itemId, xmppConnection), new MessageListener() {
            @Override
            public void processMessage(Chat chat, Message message) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        main.ui.showStatus(STATUS_LOST);
                    }
                });

            }
        });

        this.notToBeGCd = chat;
        chat.sendMessage(new Message());
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
}
