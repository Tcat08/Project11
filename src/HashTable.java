

import java.util.*;



public class HashTable {
    private Node[] array;
    private int size;
    private int[] keyToIndex;  // Added for key-to-index mapping


    public HashTable(int size) {
        this.size = size;
        this.array = new Node[size];
        this.keyToIndex = new int[size];
    }

    public int hashFunction(int key) {
        // A simple hash function
        int hash = 0;

        for (int i = 0; i < key; i++) {
            hash = ((31 * hash) + key) % size;
        }

        return hash;
    }

    public void put(int key, String value) {
        int index = hashFunction(key);
        Node newNode = new Node(key, value);

        // Handle collision by chaining, not open addressing
        if (array[index] == null) {
            array[index] = newNode;
        } else {
            newNode.setNext(array[index]);
            array[index] = newNode;
        }
    }

    public String get(int key) {
        int index = hashFunction(key);
        Node current = array[index];

        while (current != null) {
            if (current.getKey() == key) {
                return current.getValue();
            }
            current = current.getNext();
        }

        return null; // Key not found
    }

    public Set<Integer> keySet() {
        Set<Integer> keys = new HashSet<>();

        for (Node node : array) {
            while (node != null) {
                keys.add(node.getKey());
                node = node.getNext();
            }
        }

        return keys;
    }

    public int getIndexForKey(int key) {
        return keyToIndex[key];
    }

    public Node getNodeAtIndex(int index, int position) {
        if (index >= 0 && index < size) {
            Node current = array[index];

            // Traverse the linked list until you reach the desired position
            for (int i = 0; i < position && current != null; i++) {
                current = current.getNext();
            }

            return current;
        } else {
            // Handle invalid index, for example, return null or throw an exception
            return null;
        }
    }

    public int getKeyByValue(String value) {
        for (Node node : array) {
            while (node != null) {
                if (node.getValue().equals(value)) {
                    return node.getKey();
                }
                node = node.getNext();
            }
        }
        return -1; // Value not found
    }

}