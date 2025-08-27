package controller;

import model.Message;
import model.Topic;
import model.TopicSubscriber;
import publisher.IPublisher;
import subscriber.ISubscriber;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class KafkaController {
    private final Map<String, Topic> topicMap;
    private final Map<String, List<TopicSubscriber>> topicSubscribersMap;

    private final ExecutorService subscriberExecutor;
    private final AtomicInteger topicIdCounter;

    public KafkaController() {
        topicMap = new ConcurrentHashMap<>();
        topicSubscribersMap = new ConcurrentHashMap<>();
        subscriberExecutor = Executors.newCachedThreadPool();
        topicIdCounter = new AtomicInteger(0);
    }

    // a separate topic service can also be leveraged
    public Topic createTopic(String name) {
        String id = String.valueOf(topicIdCounter.incrementAndGet());
        Topic topic = new Topic(id, name);
        topicMap.put(id, topic);
        topicSubscribersMap.put(id, new CopyOnWriteArrayList<>());
        System.out.println("Created topic: " + name + " with ID: " +id);
        return topic;
    }

    public void subscribe(ISubscriber subscriber, String topicId) {
        Topic topic = topicMap.get(topicId);

        if (topic == null) {
            System.err.println("Topic with ID: " + topicId + " doesn't exist.");
            return;
        }
        TopicSubscriber ts = new TopicSubscriber(topic, subscriber); // imagine this as thread/event not a concrete subscriber
        topicSubscribersMap.get(topicId).add(ts); // it takes the target subscriber as it's member

        // Submit the subscriber task to executor so that it starts listening to topic messages immediately
        subscriberExecutor.execute(new TopicSubscriberController(ts));
        System.out.println("Subscriber " + subscriber.getId() + " subscriber to topic: " + topic.getName());
    }

    public void publish(IPublisher publisher, String topicId, Message message) {
        Topic topic = topicMap.get(topicId);

        if (topic == null) {
            throw new IllegalArgumentException("Topic with ID: " + topicId + "doen't exist.");
        }
        topic.addMessage(message);

        List<TopicSubscriber> subs = topicSubscribersMap.get(topicId); // remember these are TS threads not the subscriber itself, it carries data to subscriber
        for (TopicSubscriber topicSubscriber: subs) {
            synchronized (topicSubscriber) {
                topicSubscriber.notify();
            }
        }

        System.out.println("Message " + message.getMessage() + " published to topic " + topic.getName());
    }

    public void resetOffset(String topicId, ISubscriber subscriber, int newOffset) {
        List<TopicSubscriber> subscribers = topicSubscribersMap.get(topicId);

        if (subscribers == null) {
            System.err.println("Topic with ID: " + topicId + " doesn't exist.");
            return;
        }

        for (TopicSubscriber ts: subscribers) {
            if (ts.getSubscriber().getId().equals(subscriber.getId())) {
                ts.getOffset().set(newOffset);
                synchronized (ts) {
                    ts.notify();
                }
            }
            System.out.println("Offset for subscriber " + subscriber.getId() + " on topic " + ts.getTopic().getName() + "reset to " + newOffset);
            break;
        }
    }

    public void shutdown() {
        subscriberExecutor.shutdown();
        try {
            if (!subscriberExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                subscriberExecutor.shutdown();
            }
        } catch (InterruptedException e) {
            subscriberExecutor.shutdown();
        }
    }
}
