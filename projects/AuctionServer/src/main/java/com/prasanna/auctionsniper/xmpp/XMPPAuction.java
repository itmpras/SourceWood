package com.prasanna.auctionsniper.xmpp;

import com.prasanna.auctionsniper.Announcer;
import com.prasanna.auctionsniper.Auction;
import com.prasanna.auctionsniper.AuctionEventListner;
import com.prasanna.auctionsniper.ui.Main;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

/**
 * Created by prasniths on 27/12/15.
 */
public class XMPPAuction implements Auction {

    private final Chat chat;
    private final XMPPConnection xmppConnection;
    private final Announcer<AuctionEventListner> auctionEventListnerAnnouncer = Announcer.to(AuctionEventListner.class);


    public XMPPAuction(XMPPConnection xmppConnection, String itemId) {
        this.xmppConnection = xmppConnection;
        this.chat = xmppConnection.getChatManager().createChat(getAutionId(itemId, xmppConnection), null);
        chat.addMessageListener(new AuctionMessageTranslator(Main.SNIPER_ID, auctionEventListnerAnnouncer.announce()));
    }

    @Override
    public void join() {

        String userFromConnection = getUserFromConnection(xmppConnection);
        String joinMessage = String.format(Main.JOIN_COMMAND_FORMAT, userFromConnection);
        sendMessage(joinMessage);
    }

    @Override
    public void bid(int amount) {

        String message = String.format(Main.BID_COMMAD_FORMAT, amount);
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

    private  static String getAutionId(String itemId, XMPPConnection xmppConnection) {

        return String.format(Main.AUCTION_ID_FORMAT, itemId, xmppConnection.getServiceName());
    }


}
