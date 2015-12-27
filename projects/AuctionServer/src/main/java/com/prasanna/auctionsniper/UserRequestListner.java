package com.prasanna.auctionsniper;

import java.util.EventListener;

/**
 * Created by prasniths on 26/12/15.
 */
public interface UserRequestListner extends EventListener {
    void joinAuction(String itemid);
}
