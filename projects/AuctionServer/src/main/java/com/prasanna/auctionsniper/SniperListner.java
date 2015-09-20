package com.prasanna.auctionsniper;

import java.util.EventListener;

/**
 * Created by gopinithya on 05/09/15.
 */
public interface SniperListner extends EventListener {

    public void sniperLost();

    void sniperBidding();
}
