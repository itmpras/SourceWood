package com.prasanna.auctionsniper.ui;

import com.prasanna.auctionsniper.SniperSnapshot;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColumnTest {

    @Test
    public void verifyAllColumnMapping() throws Exception {

        SniperSnapshot sniperSnapshot = new SniperSnapshot("Test ", 0, 0, SniperState.JOINING);
        for (Column column : Column.values()) {
            Object valueIn = column.valueIn(sniperSnapshot);
            assertNotNull(valueIn);
        }

    }
}