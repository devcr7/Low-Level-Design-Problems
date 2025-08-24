package util;

public class DoublyLinkedListNode<V> {
    private V value;
    private DoublyLinkedListNode<V> prev;
    private DoublyLinkedListNode<V> next;

    public DoublyLinkedListNode(V value) {
        this.value = value;
        this.prev = null;
        this.next = null;
    }

    public V getValue() {
        return value;
    }

    public DoublyLinkedListNode<V> getPrev() {
        return prev;
    }

    public DoublyLinkedListNode<V> getNext() {
        return next;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void setPrev(DoublyLinkedListNode<V> prev) {
        this.prev = prev;
    }

    public void setNext(DoublyLinkedListNode<V> next) {
        this.next = next;
    }
}
