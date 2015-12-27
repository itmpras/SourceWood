package com.prasanna.applicationrunner;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.*;
import com.objogate.wl.swing.gesture.GesturePerformer;
import com.prasanna.auctionsniper.ui.MainWindow;
import org.hamcrest.core.Is;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import static com.objogate.wl.swing.matcher.IterableComponentsMatcher.matching;
import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;
import static java.lang.String.valueOf;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by gopinithya on 26/08/15.
 */
public class AuctionSniperDriver extends JFrameDriver {

    public AuctionSniperDriver(int timeOut) {

        super(new GesturePerformer(), JFrameDriver.topLevelFrame(named(MainWindow.MAIN_WINDOW_NAME),
                showingOnScreen()),
                new AWTEventQueueProber(timeOut, 100));

    }

    public void showsSniperStatus(String status) {

        new JTableDriver(this).hasCell(withLabelText(equalTo(status)));
    }

    public void showsSniperStatus(String itemID, int lastPrice, int lastBid, String statusText) {

        new JTableDriver(this).hasRow(
                matching(withLabelText(itemID), withLabelText(valueOf(lastPrice)),
                        withLabelText(valueOf(lastBid)), withLabelText(statusText))
        );
    }

    public void hasColumnTitles() {

        JTableHeaderDriver headers = new JTableHeaderDriver(this, JTableHeader.class);
        headers.hasHeaders(matching(withLabelText("Item"), withLabelText("Last Price"), withLabelText("Last Bid"), withLabelText("State")));

    }

    public void startBiddingFor(String itemID) {
        itemIDField().replaceAllText(itemID);
        bidButton().click();
    }

    private JTextFieldDriver itemIDField() {
        JTextFieldDriver newItemId = new JTextFieldDriver(this, JTextField.class, named(MainWindow.NEW_ITEM_ID_NAME));
        newItemId.focusWithMouse();
        return newItemId;
    }

    private JButtonDriver bidButton() {
        return new JButtonDriver(this, JButton.class, named(MainWindow.JOIN_BUTTON_NAME));
    }
}
