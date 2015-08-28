package com.prasanna.auctionsniper.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gopinithya on 28/08/15.
 */
public class MainWindow extends JFrame {

    public static final String MAIN_WINDOW_NAME = "Auction Sniper";
    public static final String SNIPER_LABEL_NAME = "Action";

    public MainWindow() throws HeadlessException {

        super();
        setName(MAIN_WINDOW_NAME);
        setTitle(MAIN_WINDOW_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setBounds(10, 20, 500, 100);
    }
}
