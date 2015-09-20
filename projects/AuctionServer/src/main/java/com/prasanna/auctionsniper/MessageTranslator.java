package com.prasanna.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

/**
 * Created by gopinithya on 04/09/15.
 */
public interface MessageTranslator {
    public void processMessage(Chat unusedChat, Message closeMessage);
}
