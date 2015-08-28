package com.prasanna.auctionserver;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

/**
 * Created by gopinithya on 26/08/15.
 */
public class FakeAuctionServer {

    public static final String XMPP_HOSTNAME = "localhost";
    public static final String ITEM_ID_AS_LOGIN = "auction-item-%s";
    public static final String PASSWORD = "auction";
    public static final String RESOURCE = "Auction";
    public static final String ITEM_ID = "54321";

    private final String itemId;
    private XMPPConnection xmppConnection;
    private SingleMessageListener singleMessageListener;
    private Chat currentChat;

    public FakeAuctionServer(String item) {

        itemId = item;
        xmppConnection = new XMPPConnection(XMPP_HOSTNAME);
        singleMessageListener = new SingleMessageListener();
    }

    public void startSellingItem() throws XMPPException {

        xmppConnection.connect();
        xmppConnection.login(String.format(ITEM_ID_AS_LOGIN, itemId), PASSWORD, RESOURCE);
        xmppConnection.getChatManager().addChatListener(
                new ChatManagerListener() {
                    @Override
                    public void chatCreated(Chat chat, boolean b) {

                        currentChat = chat;
                        currentChat.addMessageListener(singleMessageListener);
                    }
                }
        );

    }

    public static void main(String args[]) throws XMPPException {

        FakeAuctionServer fakeAuctionServer = new FakeAuctionServer(ITEM_ID);
        fakeAuctionServer.startSellingItem();
    }

    public void hasReceivedJoinRequstFromSniper() throws InterruptedException {

        singleMessageListener.receivedAMessage();
    }

    public void announceClosed() throws XMPPException {

        currentChat.sendMessage(new Message());
    }

    public void closeAuction() {

        xmppConnection.disconnect();
    }

    public String getItemId() {

        return itemId;
    }
}

