package com.prasanna.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.HashMap;

/**
 * Created by gopinithya on 04/09/15.
 */
public class AuctionMessageTranslator implements MessageListener {
    public static final String EVENT = "Event";
    public static final String CLOSE_EVENT = "CLOSE";
    public static final String CURENT_PRICE_EVENT = "PRICE";
    private AuctionEventListner auctionEventListner;

    public AuctionMessageTranslator(AuctionEventListner auctionEventListner) {

        this.auctionEventListner = auctionEventListner;
    }

    @Override
    public void processMessage(Chat unusedChat, Message closeMessage) {

        HashMap<String, String> receivedMessage = unpackRawMessage(closeMessage);

        String event = receivedMessage.get(EVENT);
        if (CLOSE_EVENT.equals(event)) {
            auctionEventListner.auctionClosed();
        } else if (CURENT_PRICE_EVENT.equals(event)) {
            String currentPrice = receivedMessage.get("CurrentPrice");
            String increment = receivedMessage.get("Increment");
            auctionEventListner.currentPrice(Integer.parseInt(currentPrice), Integer.parseInt(increment));
        }
    }

    private HashMap<String, String> unpackRawMessage(Message closeMessage) {

        HashMap<String, String> messagePair = new HashMap<String, String>();
        String body = closeMessage.getBody();
        String[] split = body.split(";");
        for (String tokens : split) {
            String[] pairs = tokens.split(":");
            messagePair.put(pairs[0].trim(), pairs[1].trim());
        }
        return messagePair;
    }
}
