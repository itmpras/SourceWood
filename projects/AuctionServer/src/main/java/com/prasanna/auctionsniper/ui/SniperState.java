package com.prasanna.auctionsniper.ui;

import com.objogate.exception.Defect;

/**
 * Created by gopinithya on 24/09/15.
 */
public enum SniperState {
    JOINING {
        @Override
        public SniperState whenAuctionClosed() {

            return LOST;
        }
    },
    BIDDING {
        @Override
        public SniperState whenAuctionClosed() {

            return LOST;
        }
    },

    WINNING {
        @Override
        public SniperState whenAuctionClosed() {

            return WON;
        }
    },
    LOST,
    WON;

    public SniperState whenAuctionClosed() {

        throw new Defect("Auction is already close ");
    }
}
