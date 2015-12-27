package com.prasanna.auctionsniper.ui;

import com.prasanna.auctionsniper.SniperSnapshot;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Test;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class SniperTableModelTest {

    private JUnitRuleMockery ruleMockery = new JUnitRuleMockery();
    private TableModelListener tableModelListener = ruleMockery.mock(TableModelListener.class);
    private SniperTableModel sniperTableModel = new SniperTableModel();

    @Before
    public void setUp() throws Exception {

        sniperTableModel.addTableModelListener(tableModelListener);
    }

    @Test
    public void hasEnoughColumns() throws Exception {

        assertThat(sniperTableModel.getColumnCount(), equalTo(Column.values().length));
    }

    @Test
    public void setSnipperValuesInColumns() throws Exception {

        String item_id = "item_id";
        SniperSnapshot joining = SniperSnapshot.joinning(item_id);
        SniperSnapshot bidding = joining.bidding(555, 666);
        ruleMockery.checking(
                new Expectations() {
                    {
                        allowing(tableModelListener).tableChanged(with(anyInsertionEvent()));
                        one(tableModelListener).tableChanged(with(aChangeInRow(0)));
                    }


                }
        );

        sniperTableModel.addSniper(joining);
        sniperTableModel.sniperStateChanged(bidding);
        assertRowMatchesState(0, bidding);
        ruleMockery.assertIsSatisfied();
    }

    private Matcher<TableModelEvent> anyInsertionEvent() {
        return new FeatureMatcher<TableModelEvent, Integer>(equalTo(TableModelEvent.INSERT), "Insert", "Row" +
                "") {
            @Override
            protected Integer featureValueOf(TableModelEvent tableModelEvent) {
                return tableModelEvent.getType();
            }
        };

    }

    private void assertRowMatchesState(int row, SniperSnapshot newState) {
        assertColumnEquals(row, Column.ITEM_IDENTIFIER, newState.itemID);
        assertColumnEquals(row, Column.LAST_PRICE, newState.lastPrice);
        assertColumnEquals(row, Column.LAST_BID, newState.lastBid);
        assertColumnEquals(row, Column.SNIPER_STATE, SniperTableModel.textFor(newState));
    }

    @Test
    public void notifesListnerWhenAddingASniper() throws Exception {

        SniperSnapshot joinning = SniperSnapshot.joinning("item123");

        ruleMockery.checking(
                new Expectations() {
                    {
                        oneOf(tableModelListener).tableChanged(with(any(TableModelEvent.class)));
                    }
                }
        );

        assertEquals(0, sniperTableModel.getRowCount());
        sniperTableModel.addSniper(joinning);

        assertEquals(1, sniperTableModel.getRowCount());
        assertRowMatchesState(0, joinning);

    }

    @Test
    public void holdsSnipersInAdditionOrder() throws Exception {

        ruleMockery.checking(
                new Expectations() {
                    {
                        ignoring(tableModelListener);
                    }
                }
        );

        sniperTableModel.addSniper(SniperSnapshot.joinning("item0"));
        sniperTableModel.addSniper(SniperSnapshot.joinning("item1"));

        assertColumnEquals(0, Column.ITEM_IDENTIFIER, "item0");
        assertColumnEquals(1, Column.ITEM_IDENTIFIER, "item1");
    }

    private void assertColumnEquals(int row, Column column, Object value) {

        assertEquals(value, sniperTableModel.getValueAt(row, column.ordinal()));
    }

    private Matcher<TableModelEvent> aChangeInRow(int row) {

        return new FeatureMatcher<TableModelEvent, Integer>(equalTo(row), "Change in Row", "Row" +
                "") {
            @Override
            protected Integer featureValueOf(TableModelEvent tableModelEvent) {
                return tableModelEvent.getFirstRow();
            }
        };

    }

}