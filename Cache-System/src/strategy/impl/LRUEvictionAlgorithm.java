package strategy.impl;

import strategy.IEvictionAlgorithm;
import util.DoublyLinkedList;
import util.DoublyLinkedListNode;

import java.util.HashMap;
import java.util.Map;

public class LRUEvictionAlgorithm<K> implements IEvictionAlgorithm<K> {
    private final DoublyLinkedList<K> doublyLinkedList;
    private final Map<K, DoublyLinkedListNode<K>> keyToNodeMap;

    public LRUEvictionAlgorithm() {
        doublyLinkedList = new DoublyLinkedList<>();
        keyToNodeMap = new HashMap<>();
    }

    @Override
    public synchronized void keyAccessed(K key) throws Exception {
        if (keyToNodeMap.containsKey(key)) {
            DoublyLinkedListNode<K> node = keyToNodeMap.get(key);
            doublyLinkedList.detachNode(node);
            doublyLinkedList.addNodeAtHead(node);
        } else {
            DoublyLinkedListNode<K> newNode = new DoublyLinkedListNode<>(key);
            doublyLinkedList.addNodeAtHead(newNode);
            keyToNodeMap.put(key, newNode);
        }
    }

    @Override
    public synchronized K evictKey() throws Exception {
        DoublyLinkedListNode<K> nodeToEvict = doublyLinkedList.getTail();
        if (nodeToEvict == null) {
            return null;
        }

        K evictKey = nodeToEvict.getValue();
        doublyLinkedList.removeTail();
        keyToNodeMap.remove(evictKey);
        return evictKey;
    }
}
