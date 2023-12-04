/*
Name: Connor Byers
Prof: Blanche Cohen
Class: CS2050
Dectription: Node for Hash Table (Array for Program11 works more like a
linked list than an Array because the initial idea was to do open addressing
but chaining seemed more intuitive to me later)
Node holds a Value (String for the words; each node holds a single word so key can be calculated)
and a Key (int that is calculated from value for each node when getHashTable is used in main)
and then the method Put will automatically hash it based on the size of the table (an argument for
table size must be declared for a table object to be initialized)


 */
public class Node {
    private int key;
    private String value;
    private Node next;

    public Node(int key, String value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}