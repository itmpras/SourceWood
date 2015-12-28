package com.prasanna.auctionsniper.xmpp;

import com.prasanna.auctionsniper.Auction;
import com.prasanna.auctionsniper.AuctionHouse;
import com.prasanna.auctionsniper.ui.Main;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * Created by prasniths on 27/12/15.
 */
public class XmppAuctionHouse implements AuctionHouse {
    private XMPPConnection connection;

    private XmppAuctionHouse(XMPPConnection connection) {
        this.connection = connection;
    }

    public static XmppAuctionHouse connect(String xmppHost, String userName, String password) throws XMPPException

    {

        XMPPConnection xmppConnection = new XMPPConnection(xmppHost);
        xmppConnection.connect();
        xmppConnection.login(userName, password, Main.RESOURCE);
        return new XmppAuctionHouse(xmppConnection);

    }

    public Auction auctionFor(String itemId) {
        return new XMPPAuction(connection, itemId);
    }

    public void disconnect() {
        connection.disconnect();
    }
}
