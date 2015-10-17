package com.prasanna.auctionsniper.ui;

import com.prasanna.auctionsniper.SniperSnapshot;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by gopinithya on 28/08/15.
 */

public class MainWindow extends JFrame {

    public static final String MAIN_WINDOW_NAME = "Auction Sniper";
    public static final String SNIPER_LABEL_NAME = "Action";
    public static final String SNIPER_TABLE = "SniperTable";
    private final SniperTableModel sniperTableModel = new SniperTableModel();

    public MainWindow() throws HeadlessException {

        super();
        setName(MAIN_WINDOW_NAME);
        setTitle(MAIN_WINDOW_NAME);
        fillContentPanel(makeSniperTable());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void fillContentPanel(JTable jTable) {

        final Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(jTable), BorderLayout.CENTER);

    }

    private JTable makeSniperTable() {

        final JTable snipperTable = new JTable(sniperTableModel);
        snipperTable.setName(SNIPER_TABLE);
        return snipperTable;

    }

    private static JLabel createLabel(String text) {

        JLabel jlabel = new JLabel(text);
        jlabel.setName(SNIPER_LABEL_NAME);
        jlabel.setBorder(new LineBorder(Color.BLACK));
        return jlabel;
    }

    public void showStatus(String status) {

        sniperTableModel.setStatusText(status);
    }

    public static void main(String args[]) {

        MainWindow mainWindow = new MainWindow();
    }

    public void snipperStateChanged(SniperSnapshot state) {

        sniperTableModel.snipperStatusChanged(state);
    }
}
