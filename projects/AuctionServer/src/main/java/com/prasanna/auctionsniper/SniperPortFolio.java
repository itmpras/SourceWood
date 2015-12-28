package com.prasanna.auctionsniper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prasniths on 28/12/15.
 */
public class SniperPortFolio implements SniperCollector {

    List<PortfolioListener> portfolioListener;

    public SniperPortFolio() {
        portfolioListener = new ArrayList<PortfolioListener>();
    }

    public void addPortfolioListener(PortfolioListener listener) {
        portfolioListener.add(listener);
    }

    public void addSniper(AuctionSniper sniper) {
        for (PortfolioListener listener : portfolioListener) {
            listener.SniperAdded(sniper);
        }

    }
}
