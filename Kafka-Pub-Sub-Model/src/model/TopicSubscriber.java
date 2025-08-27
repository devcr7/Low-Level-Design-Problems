package model;

import subscriber.ISubscriber;

import java.util.concurrent.atomic.AtomicInteger;

// this is a thread / event(independent) not a concrete subscriber, it takes target subscriber as a member
// and is created on each subscribe event trigger

public class TopicSubscriber {
    private final Topic topic;
    private final ISubscriber subscriber;
    private final AtomicInteger offset;

    public TopicSubscriber(Topic topic, ISubscriber subscriber) {
        this.topic = topic;
        this.subscriber = subscriber;
        offset = new AtomicInteger(0);
    }

    public Topic getTopic() {
        return topic;
    }

    public ISubscriber getSubscriber() {
        return subscriber;
    }

    public AtomicInteger getOffset() {
        return offset;
    }
}
