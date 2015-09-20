package com.prasanna.auctionsniper;

import com.prasanna.auctionsniper.ui.Main;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by gopinithya on 04/09/15.
 */
@RunWith(JMock.class)
public class AuctionMessageTranslatorTest {

    private final Mockery context = new Mockery();
    private final AuctionEventListner auctionEventListner = context.mock(AuctionEventListner.class);
    private final AuctionMessageTranslator auctionMessageTransalator = new AuctionMessageTranslator(auctionEventListner);
    private static final Chat UNUSED_CHAT = null;

    @Test
    public void notifiesAuctionCloseWhenCloseMessageReceived() throws Exception {

        context.checking(new Expectations() {
            {
                oneOf(auctionEventListner).auctionClosed();
            }
        });

        Message closeMessage = new Message();
        closeMessage.setBody("SOLVersion: 1.1; Event: CLOSE;");
        auctionMessageTransalator.processMessage(UNUSED_CHAT, closeMessage);
    }

    @Test
    public void notifesBidDetailsWhenCurrentPriceMessageReceived() throws Exception {

        context.checking(new Expectations() {
            {
                exactly(1).of(auctionEventListner).currentPrice(197, 80);
            }
        });

        Message currentPriceMessage = new Message();
        currentPriceMessage.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 197; Increment: 80; Bidder: OtherBidder ");
        auctionMessageTransalator.processMessage(UNUSED_CHAT, currentPriceMessage);
    }

    @Test
    public void notifiesBidDetailsWhenCorrectPriceMessageReceived() throws Exception {

        context.checking(new Expectations() {
            {
                exactly(1).of(auctionEventListner).currentPrice(1000, 10);
            }
        });

        Message priceMessage = new Message();
        priceMessage.setBody(String.format(Main.PRICE_COMMAND_FORMAT,
                1000, 10, "Test"));
        auctionMessageTransalator.processMessage(UNUSED_CHAT, priceMessage);
    }
}



