package publisher.impl;

import controller.KafkaController;
import model.Message;
import publisher.IPublisher;

public class SimplePublisher implements IPublisher {
    private final String id;
    private final KafkaController kafkaController;

    public SimplePublisher(String id, KafkaController kafkaController) {
        this.id = id;
        this.kafkaController = kafkaController;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void publish(String topicId, Message message) throws IllegalArgumentException {
        System.out.println("Publisher with ID: " + id + " published: " + message.getMessage() + " to topic with ID: " + topicId);
        kafkaController.publish(this, topicId, message);
    }
}
