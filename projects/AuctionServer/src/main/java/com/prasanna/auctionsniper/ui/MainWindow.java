package com.prasanna.auctionsniper.ui;

import com.prasanna.auctionsniper.Announcer;
import com.prasanna.auctionsniper.SniperPortFolio;
import com.prasanna.auctionsniper.SniperSnapshot;
import com.prasanna.auctionsniper.UserRequestListner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gopinithya on 28/08/15.
 */

public class MainWindow extends JFrame {

    public static final String MAIN_WINDOW_NAME = "Auction Sniper";
    public static final String SNIPER_LABEL_NAME = "Action";
    public static final String SNIPER_TABLE = "SniperTable";
    public static final String NEW_ITEM_ID_NAME = "item";
    public static final String JOIN_BUTTON_NAME = "Join";

    private final SniperTableModel sniperTableModel;
    private final Announcer<UserRequestListner> userRequest = Announcer.to(UserRequestListner.class);


    public MainWindow(SniperPortFolio portFolio) throws HeadlessException {

        super();
        this.sniperTableModel = new SniperTableModel();
        portFolio.addPortfolioListener(sniperTableModel);
        setName(MAIN_WINDOW_NAME);
        setTitle(MAIN_WINDOW_NAME);
        fillContentPanel(makeSniperTable(), makeControls());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setBounds(10, 10, 1000, 400);
    }

    private void fillContentPanel(JTable jTable, JPanel controls) {

        final Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(controls, BorderLayout.NORTH);
        contentPane.add(new JScrollPane(jTable), BorderLayout.CENTER);

    }

    private JPanel makeControls() {
        JPanel controls = new JPanel(new FlowLayout());
        final JTextField itemField = new JTextField();
        itemField.setColumns(25);
        itemField.setName(NEW_ITEM_ID_NAME);
        controls.add(itemField);

        JButton joinAuctionButton = new JButton("Join Auction");
        joinAuctionButton.setName(JOIN_BUTTON_NAME);
        joinAuctionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userRequest.announce().joinAuction(itemField.getText());
            }
        });
        controls.add(joinAuctionButton);
        return controls;

    }

    private JTable makeSniperTable() {

        final JTable snipperTable = new JTable(sniperTableModel);
        snipperTable.setName(SNIPER_TABLE);
        return snipperTable;

    }

    public void snipperStateChanged(SniperSnapshot state) {

        sniperTableModel.sniperStateChanged(state);
    }

    public void addUserRequestListner(UserRequestListner userRequestListner) {
        userRequest.addListener(userRequestListner);

    }
}
