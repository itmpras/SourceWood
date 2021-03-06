package com.prasanna.auctionsniper.xmpp;

import com.prasanna.auctionsniper.AuctionEventListner;
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
    public static final String CURRENT_PRICE = "CurrentPrice";
    public static final String INCREMENT = "Increment";
    public static final String BIDDER = "Bidder";
    private AuctionEventListner auctionEventListner;
    private final String snipperID;

    public AuctionMessageTranslator(String snipperId, AuctionEventListner auctionEventListner) {

        this.snipperID = snipperId;
        this.auctionEventListner = auctionEventListner;
    }

    @Override
    public void processMessage(Chat unusedChat, Message message) {

        AuctionEvent auctionEvent = AuctionEvent.fromMessage(message);

        if (CLOSE_EVENT.equals(auctionEvent.type())) {
            auctionEventListner.auctionClosed();
        } else if (CURENT_PRICE_EVENT.equals(auctionEvent.type())) {
            auctionEventListner.currentPrice(getPriceSource(auctionEvent.isFrom()), auctionEvent.currentPrice(), auctionEvent.increment());
        }
    }

    private AuctionEventListner.PriceSource getPriceSource(String bidder) {

        if (snipperID.equals(bidder))
            return AuctionEventListner.PriceSource.FromSnipper;

        return AuctionEventListner.PriceSource.FromOtherBidder;
    }

    private static class AuctionEvent {

        private HashMap<String, String> messageMap;

        private AuctionEvent(HashMap<String, String> messageMap) {

            this.messageMap = messageMap;
        }

        public String type() {

            return getString(EVENT);
        }

        public int currentPrice() {

            return getInt(CURRENT_PRICE);
        }

        public int increment() {

            return getInt(INCREMENT);
        }

        public String isFrom() {

            return getString(BIDDER);
        }

        private int getInt(String currentPrice) {

            String value = messageMap.get(currentPrice);
            return Integer.parseInt(value);
        }

        private String getString(String key) {

            return messageMap.get(key);
        }

        public static AuctionEvent fromMessage(Message message) {

            return new AuctionEvent(unpackRawMessage(message));
        }

        private static HashMap<String, String> unpackRawMessage(Message message) {

            HashMap<String, String> messagePair = new HashMap<String, String>();
            String body = message.getBody();
            String[] split = body.split(";");
            for (String tokens : split) {
                String[] pairs = tokens.split(":");
                messagePair.put(pairs[0].trim(), pairs[1].trim());
            }
            return messagePair;
        }
    }

}


