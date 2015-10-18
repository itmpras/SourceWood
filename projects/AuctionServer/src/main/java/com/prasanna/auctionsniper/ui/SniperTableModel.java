package com.prasanna.auctionsniper.ui;

import com.prasanna.auctionsniper.SniperListner;
import com.prasanna.auctionsniper.SniperSnapshot;

import javax.swing.table.AbstractTableModel;

/**
 * Created by gopinithya on 23/09/15.
 */
public class SniperTableModel extends AbstractTableModel implements SniperListner {

    public static final String STATUS_JOINING = "Joining";
    public static final String STATUS_LOST = "Lost";
    public static final String STATUS_WON = "Won";
    public static final String STATUS_BIDDING = "Bidding";
    public static final String STATUS_WINNING = "Winning";

    private static String[] STATUS_TEXT = {STATUS_JOINING, STATUS_BIDDING, STATUS_WINNING, STATUS_LOST, STATUS_WON};
    private volatile String statusText = STATUS_JOINING;
    public final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, SniperState.JOINING);
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

        return Column.at(columnIndex).valueIn(sniperSnapshot);

    }

    @Override
    public String getColumnName(int column) {

        return Column.at(column).name;
    }

    public void sniperStateChanged(SniperSnapshot newState) {

        sniperSnapshot = newState;
        statusText = textFor(newState);
        fireTableRowsUpdated(0, 0);
    }

    public static String textFor(SniperSnapshot newState) {

        return STATUS_TEXT[newState.sniperState.ordinal()];
    }
}
