import controller.KafkaController;
import model.Message;
import model.Topic;
import publisher.impl.SimplePublisher;
import subscriber.impl.SimpleSubscriber;

public class Main {
    public static void main(String[] args) {
        KafkaController kafkaController = new KafkaController();

        Topic topic1 = kafkaController.createTopic("Topic1");
        Topic topic2 = kafkaController.createTopic("Topic2");

        SimpleSubscriber subscriber1 = new SimpleSubscriber("Subscriber1");
        SimpleSubscriber subscriber2 = new SimpleSubscriber("Subscriber2");
        SimpleSubscriber subscriber3 = new SimpleSubscriber("Subscriber3");

        kafkaController.subscribe(subscriber1, topic1.getId());
        kafkaController.subscribe(subscriber1, topic2.getId());
        kafkaController.subscribe(subscriber2, topic1.getId());
        kafkaController.subscribe(subscriber3, topic2.getId());

        SimplePublisher publisher1 = new SimplePublisher("Publisher1", kafkaController);
        SimplePublisher publisher2 = new SimplePublisher("Publisher2", kafkaController);

        publisher1.publish(topic1.getId(), new Message("Message m1"));
        publisher1.publish(topic1.getId(), new Message("Message m2"));
        publisher1.publish(topic2.getId(), new Message("Message m3"));

        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        publisher2.publish(topic2.getId(), new Message("Message m4"));
        publisher1.publish(topic1.getId(), new Message("Message m5"));

        kafkaController.resetOffset(topic1.getId(), subscriber1,0);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        kafkaController.shutdown();
    }
}