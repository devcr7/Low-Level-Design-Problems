package model;

import publisher.IPublisher;

// can leverage this class to publish messages concurrently via publisher like in topic subscriber
public class TopicPublisher {
    private final Topic topic;
    private final IPublisher publisher;

    public TopicPublisher(Topic topic, IPublisher publisher) {
        this.topic = topic;
        this.publisher = publisher;
    }

    public Topic getTopic() {
        return topic;
    }

    public IPublisher getPublisher() {
        return publisher;
    }
}

