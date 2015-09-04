package com.prasanna.auctionsniper.ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by gopinithya on 28/08/15.
 */

public class MainWindow extends JFrame {

    public static final String MAIN_WINDOW_NAME = "Auction Sniper";
    public static final String SNIPER_LABEL_NAME = "Action";

    private final JLabel sniperStatus = createLabel(Main.STATUS_JOINING);

    public MainWindow() throws HeadlessException {

        super();
        setName(MAIN_WINDOW_NAME);
        setTitle(MAIN_WINDOW_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(sniperStatus);
        setVisible(true);
        setBounds(10, 20, 500, 100);
    }

    private static JLabel createLabel(String text) {

        JLabel jlabel = new JLabel(text);
        jlabel.setName(SNIPER_LABEL_NAME);
        jlabel.setBorder(new LineBorder(Color.BLACK));
        return jlabel;
    }

    public void showStatus(String status) {
        sniperStatus.setText(status);
    }
}
