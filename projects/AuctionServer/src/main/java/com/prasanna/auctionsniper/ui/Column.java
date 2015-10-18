package com.prasanna.auctionsniper.ui;

import com.prasanna.auctionsniper.SniperSnapshot;

/**
 * Created by gopinithya on 23/09/15.
 */
public enum Column {
    ITEM_IDENTIFIER("Item") {
        @Override
        public Object valueIn(SniperSnapshot snapshot) {

            return snapshot.itemID;
        }
    },
    LAST_PRICE("Last Price") {
        @Override
        public Object valueIn(SniperSnapshot snapshot) {

            return snapshot.lastPrice;

        }
    },
    LAST_BID("Last Bid") {
        @Override
        public Object valueIn(SniperSnapshot snapshot) {

            return snapshot.lastBid;
        }
    },
    SNIPER_STATE("State") {
        @Override
        public Object valueIn(SniperSnapshot snapshot) {

            return SniperTableModel.textFor(snapshot);
        }
    };

    public static Column at(int offset) {

        return values()[offset];
    }

    Column(String name) {

        this.name = name;
    }

    abstract public Object valueIn(SniperSnapshot snapshot);

    public final String name;

}
