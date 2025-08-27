package controller;

import model.Message;
import model.Topic;
import model.TopicSubscriber;
import subscriber.ISubscriber;

public class TopicSubscriberController implements Runnable {
    private final TopicSubscriber topicSubscriber;

    public TopicSubscriberController(TopicSubscriber topicSubscriber) {
        this.topicSubscriber = topicSubscriber;
    }

    @Override
    public void run() {
        Topic topic = topicSubscriber.getTopic();
        ISubscriber subscriber = topicSubscriber.getSubscriber();

        while (true) {
            Message messageToProcess = null;
            synchronized (topicSubscriber) {
                // Wait until new messages
                while (topicSubscriber.getOffset().get() >=  topic.getMessages().size()) {
                    try {
                        topicSubscriber.wait();
                    } catch (InterruptedException r) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                int currentOffset = topicSubscriber.getOffset().getAndIncrement();
                messageToProcess = topic.getMessages().get(currentOffset);
            }

            try {
                subscriber.onMessage(messageToProcess);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
