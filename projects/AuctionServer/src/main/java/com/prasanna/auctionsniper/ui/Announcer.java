package com.prasanna.auctionsniper.ui;

import com.prasanna.auctionsniper.UserRequestListner;

import java.util.ArrayList;

/**
 * Created by prasniths on 26/12/15.
 */
public class Announcer {
    private ArrayList<UserRequestListner> receiver;

    public Announcer() {
        this.receiver = new ArrayList<UserRequestListner>();
    }

    public void addListner(UserRequestListner listner) {
        receiver.add(listner);
    }

    public void announce(String message) {

        for (UserRequestListner listner : receiver) {
            listner.joinAuction(message);
        }

    }

}
