package com.prasanna.auctionsniper;

import java.util.EventListener;

/**
 * Created by prasniths on 28/12/15.
 */
public interface PortfolioListener extends EventListener {

    void SniperAdded(AuctionSniper sniper);
}
