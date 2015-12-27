package com.prasanna.auctionserver;

import com.prasanna.applicationrunner.ApplicationRunner;
import com.prasanna.auctionsniper.ui.Main;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.core.StringContains;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

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

    public XMPPConnection getXmppConnection() {
        return xmppConnection;
    }

    public void startSellingItem() throws XMPPException {

        xmppConnection.connect();
        String user = String.format(ITEM_ID_AS_LOGIN, itemId);
        xmppConnection.login(user, PASSWORD, RESOURCE);
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

    public void hasReceivedJoinRequstFromSniper(String userName) throws InterruptedException {

        receivedAMessageMatching(equalTo(String.format(
                Main.JOIN_COMMAND_FORMAT, userName)), userName);
    }

    private void receivedAMessageMatching(Matcher<String> stringMatcher, String user) throws InterruptedException {

        singleMessageListener.receivedAMessageAs(stringMatcher);
        assertThat(currentChat.getParticipant(), containsString(user));
    }

    public void announceClosed() throws XMPPException {

        Message closeMessge = new Message();
        closeMessge.setBody("SOLVersion: 1.1; Event: CLOSE;");
        currentChat.sendMessage(closeMessge);
    }

    public void closeAuction() {

        xmppConnection.disconnect();
    }

    public String getItemId() {

        return itemId;
    }

    public void reportPrice(int price, int minimumIncrement, String otherBidder) throws XMPPException {

        Message priceMessage = new Message();
        priceMessage.setBody(String.format(Main.PRICE_COMMAND_FORMAT,
                price, minimumIncrement, otherBidder));
        currentChat.sendMessage(
                priceMessage);
    }

    public void hasReceivedBid(int price, String sniperId) throws InterruptedException {

        assertThat(currentChat.getParticipant(), containsString(sniperId));
        receivedAMessageMatching(is(equalTo(
                String.format(Main.BID_COMMAD_FORMAT, price))), sniperId);
    }
}

