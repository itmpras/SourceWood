package com.prasanna.applicationrunner;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;
import org.hamcrest.core.Is;

/**
 * Created by gopinithya on 26/08/15.
 */
public class AuctionSniperDriver extends JFrameDriver {

    public static final String SNIPER_LABEL_NAME = "SniperStatusName";
    public static final String MAIN_WINDOW = "MainWindow";

    public AuctionSniperDriver(int timeOut) {

        super(new GesturePerformer(), JFrameDriver.topLevelFrame(named(MAIN_WINDOW),
                        showingOnScreen()),
                new AWTEventQueueProber(timeOut, 100));

    }

    public void showsSniperStatus(String status) {

        new JLabelDriver(this, named(SNIPER_LABEL_NAME)).hasText(Is.is(status));
    }
}
