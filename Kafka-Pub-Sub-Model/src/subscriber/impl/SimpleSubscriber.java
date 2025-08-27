package subscriber.impl;

import model.Message;
import subscriber.ISubscriber;

public class SimpleSubscriber implements ISubscriber {
    private final String id;

    public SimpleSubscriber(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void onMessage(Message message) throws InterruptedException {
        System.out.println("Subscriber with ID: " + id + " received " + message.getMessage());
        Thread.sleep(500);
    }
}
