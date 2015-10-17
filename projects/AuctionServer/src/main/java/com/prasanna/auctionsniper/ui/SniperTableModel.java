package com.prasanna.auctionsniper.ui;

import com.prasanna.auctionsniper.SniperSnapshot;

import javax.swing.table.AbstractTableModel;

/**
 * Created by gopinithya on 23/09/15.
 */
public class SniperTableModel extends AbstractTableModel {

    private static String[] STATUS_TEXT = {Main.STATUS_JOINING, Main.STATUS_BIDDING,Main.STATUS_WINNING};
    private volatile String statusText = Main.STATUS_JOINING;
    private final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, SniperState.JOINING);
    private volatile SniperSnapshot sniperSnapshot = STARTING_UP;

    @Override
    public int getRowCount() {

        return 1;
    }

    @Override
    public int getColumnCount() {

        return Column.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (Column.at(columnIndex)) {
            case ITEM_IDENTIFIER:
                return sniperSnapshot.itemID;
            case LAST_BID:
                return sniperSnapshot.lastBid;
            case LAST_PRICE:
                return sniperSnapshot.lastPrice;
            case SNIPER_STATE:
                return statusText;
        }

        throw new IllegalArgumentException(" Invalid Column");
    }

    public void setStatusText(String text) {

        statusText = text;
        fireTableCellUpdated(0, 0);
    }

    public void snipperStatusChanged(SniperSnapshot newState) {

        sniperSnapshot = newState;
        statusText = STATUS_TEXT[newState.sniperState.ordinal()];
        fireTableRowsUpdated(0, 0);
    }
}
