package com.prasanna.applicationrunner;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * Created by gopinithya on 26/08/15.
 */
public class AuctionSniper {
    private String xmppHost;
    private String userName;
    private String password;
    private String itemId;

    public static void main(String xmppHost, String userName, String password, String itemId) {

        AuctionSniper auctionSniper = new AuctionSniper(xmppHost, userName, password, itemId);
        auctionSniper.startSniper();
    }

    private AuctionSniper(String xmppHost, String userName, String password, String itemId) {

        this.xmppHost = xmppHost;
        this.userName = userName;
        this.password = password;
        this.itemId = itemId;
    }

    public void startSniper() {

        XMPPConnection xmppConnection = new XMPPConnection(xmppHost);
        try {
            xmppConnection.login(userName, password, "Action");

        } catch (XMPPException e) {
            e.printStackTrace();
        }

    }
}
