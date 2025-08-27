package controller;

import model.Message;
import model.Topic;
import publisher.IPublisher;

public class TopicPublisherController {
    private final Topic topic;
    private final IPublisher publisher;

    public TopicPublisherController(Topic topic, IPublisher publisher) {
        this.topic = topic;
        this.publisher = publisher;
    }

    // To have better logs specific to each publication, we can keep it synchronized, it's a design choice anyway
    public synchronized void publish(Message message, KafkaController kafkaController) {
        kafkaController.publish(publisher, topic.getId(), message);
        System.out.println("Publisher " + publisher.getId() + " published to topic: " + topic.getName());
    }
}

