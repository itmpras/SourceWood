package com.prasanna.auctionserver;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;


public class SingleMessageListener implements MessageListener {
    private final ArrayBlockingQueue<Message> messageArrayBlockingQueue = new ArrayBlockingQueue<Message>(1);

    @Override
    public void processMessage(Chat chat, Message message) {

        messageArrayBlockingQueue.add(message);
    }

    public void receivedAMessageAs(Matcher<String> stringMatcher) throws InterruptedException {

        Message message = messageArrayBlockingQueue.poll(5, TimeUnit.SECONDS);
        assertThat(message, hasProperty("body", stringMatcher));

    }
}
