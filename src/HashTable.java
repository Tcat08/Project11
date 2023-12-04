/*
Name: Connor Byers
Prof: Blanche Cohen
Class: CS2050
Dectription: A hash table must be constructed with a size in mind, there is nothing for increasing or decreasing
the size when a load factor is reached, however

*The most important method is put, which calculates an Index for each value based on the size of the table
and calculated from the key

get will return a value based on the key if it exists
key set creates an array of the keys so everything on the table can be accessed and printed if need be
If the key is not an integer, it can generically be changed (this uses a utility, but it seemed like
the most efficient way to make a set of keys and either access the value or index from there)

*getNodeAtIndex is also very important; it is used to find both the value and key when given
only the index as argument, which is exactly what I'm trying to do with this program

you can also get key by value, but that should already be set in node since I'm starting with String values
from the Dracula file, so that's unused
 */

import java.util.*;



public class HashTable {
    private Node[] nodeArray;
    private int size;
    private int[] keyToIndex;  // Added for key-to-index mapping


    public HashTable(int size) {
        this.size = size;
        this.nodeArray = new Node[size];
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
        if (nodeArray[index] == null) {
            nodeArray[index] = newNode;
        } else {
            newNode.setNext(nodeArray[index]);
            nodeArray[index] = newNode;
        }
    }

    public String get(int key) {
        int index = hashFunction(key);
        Node current = nodeArray[index];

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

        for (Node node : nodeArray) {
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
            Node current = nodeArray[index];

            // Traverse the linked list until you reach the desired position
            for (int i = 0; i < position && current != null; i++) {
                current = current.getNext();
            }

            return current;
        } else {
            // May handle invalid index here, for example, return null or throw an exception
           return null;

        }
    }

    
    public int getKeyByValue(String value) {
        for (Node node : nodeArray) {
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