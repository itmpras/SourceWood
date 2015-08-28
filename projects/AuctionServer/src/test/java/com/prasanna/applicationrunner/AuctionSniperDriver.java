package com.prasanna.applicationrunner;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;
import com.prasanna.auctionsniper.ui.Main;
import com.prasanna.auctionsniper.ui.MainWindow;
import org.hamcrest.core.Is;

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

        new JLabelDriver(this, named(MainWindow.SNIPER_LABEL_NAME)).hasText(Is.is(status));
    }
}
