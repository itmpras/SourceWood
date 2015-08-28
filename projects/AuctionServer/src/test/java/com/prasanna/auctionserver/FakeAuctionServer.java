package com.prasanna.auctionserver;

import org.jivesoftware.smack.*;

/**
 * Created by gopinithya on 26/08/15.
 */
public class FakeAuctionServer {

    public static final String XMPP_HOSTNAME = "localhost";
    public static final String ITEM_ID_AS_LOGIN = "auction-item-%s";
    public static final String PASSWORD = "auction";
    public static final String RESOURCE = "Auction";
    private final String itemId;
    private XMPPConnection xmppConnection;
    private Chat currentChat;

    public FakeAuctionServer(String item) {

        itemId = item;
        xmppConnection = new XMPPConnection(XMPP_HOSTNAME);
    }

    public void startSellingItem() throws XMPPException {

        xmppConnection.connect();
        xmppConnection.login(String.format(ITEM_ID_AS_LOGIN, itemId), PASSWORD, RESOURCE);
        xmppConnection.getChatManager().addChatListener(
                new ChatManagerListener() {
                    @Override
                    public void chatCreated(Chat chat, boolean b) {
                        currentChat = chat;
                    }
                }
        );

    }

    public static void main(String args[]) throws XMPPException {

        FakeAuctionServer fakeAuctionServer = new FakeAuctionServer("54321");
        fakeAuctionServer.startSellingItem();
    }

    public void hasReceivedJoinRequstFromSniper() {

    }

    public void announceClosed() {

    }

    public void closeAuction() {

    }

    public String getItemId() {

        return itemId;
    }
}

