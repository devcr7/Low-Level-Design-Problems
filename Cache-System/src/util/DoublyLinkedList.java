package util;

public class DoublyLinkedList<V> {
    private DoublyLinkedListNode<V> head;
    private DoublyLinkedListNode<V> tail;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    // tail stores the LRU node
    public void addNodeAtHead(DoublyLinkedListNode<V> node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            head.setPrev(node);
            node.setNext(head);
            head = node;
        }
        node.setNext(null);
    }

    public void detachNode(DoublyLinkedListNode<V> node) {
        if (node == null) return;
        if (node.getPrev() != null) {
            node.getPrev().setNext(node.getNext());
        } else {
            head = node.getNext();
        }

        if (node.getNext() != null) {
            node.getNext().setPrev(node.getPrev());
        } else {
            tail = node.getPrev();
        }

        node.setPrev(null);
        node.setNext(null);
    }

    public DoublyLinkedListNode<V> getTail() {
        return tail;
    }

    public void removeTail() {
        if (tail != null) {
            if (tail.getPrev() != null) {
                tail = tail.getPrev();
                tail.setNext(null);
            } else {
                head = null;
                tail = null;
            }
        }
    }
}
