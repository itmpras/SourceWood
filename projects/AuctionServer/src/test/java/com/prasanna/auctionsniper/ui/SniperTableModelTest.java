package com.prasanna.auctionsniper.ui;

import com.prasanna.auctionsniper.SniperSnapshot;
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

        ruleMockery.checking(
                new Expectations() {
                    {
                        oneOf(tableModelListener).tableChanged(with(any(TableModelEvent.class)));
                    }
                }
        );

        String item_id = "item_id";
        sniperTableModel.sniperStateChanged(new SniperSnapshot(item_id, 555, 666, SniperState.BIDDING));

        assertColumnEquals(Column.ITEM_IDENTIFIER, item_id);
        assertColumnEquals(Column.LAST_PRICE, 555);
        assertColumnEquals(Column.LAST_BID, 666);
        assertColumnEquals(Column.SNIPER_STATE, SniperTableModel.STATUS_BIDDING);
    }

    private void assertColumnEquals(Column column, Object value) {

        int row = 0;
        assertEquals(value, sniperTableModel.getValueAt(row, column.ordinal()));
    }

}