package com.prasanna.auctionsniper.ui;

import com.objogate.exception.Defect;
import com.prasanna.auctionsniper.SniperListner;
import com.prasanna.auctionsniper.SniperSnapshot;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

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
    private ArrayList<SniperSnapshot> sniperSnapshots = new ArrayList<SniperSnapshot>();

    @Override
    public int getRowCount() {
        return sniperSnapshots.size();
    }

    @Override
    public int getColumnCount() {

        return Column.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        SniperSnapshot sniperSnapshot = sniperSnapshots.get(rowIndex);
        return Column.at(columnIndex).valueIn(sniperSnapshot);

    }

    @Override
    public String getColumnName(int column) {

        return Column.at(column).name;
    }

    public void sniperStateChanged(SniperSnapshot newState) {
        if (newState.sniperState == SniperState.JOINING) {
            addSniper(newState);
        } else {

            int row = rowMatching(newState);
            statusText = textFor(newState);
            sniperSnapshots.set(row, newState);
            fireTableRowsUpdated(row, row);
        }
    }

    public static String textFor(SniperSnapshot newState) {
        return STATUS_TEXT[newState.sniperState.ordinal()];
    }

    public void addSniper(SniperSnapshot joinning) {
        sniperSnapshots.add(joinning);
        statusText = textFor(joinning);
        int row = rowMatching(joinning);
        fireTableRowsInserted(row, row);
    }

    private int rowMatching(SniperSnapshot sniperSnapshot) {

        for (int i = 0; i < sniperSnapshots.size(); i++) {
            if (sniperSnapshot.isForSameItemAs(sniperSnapshots.get(i))) {
                return i;
            }
        }

        throw new Defect("Cannot find match for  " + sniperSnapshot);
    }
}
