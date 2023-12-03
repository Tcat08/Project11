

public class RBTNode {
    String data; // holds the key
    RBTNode parent; // pointer to the parent
    RBTNode left; // pointer to left child
    RBTNode right; // pointer to right child
    int color; // 1 . Red, 0 . Black
    int count;

    public RBTNode() { // constructor
        this.data="";
        this.parent = null;
        this.left = null;
        this.right = null;
        this.color = 0; // 0 is default Black
    }

    public RBTNode(String data){
        this.data = data;
        this.parent = null;
        this.left = null;
        this.right = null;
        this.color = 1; // 1 is inputs are red to not match root and swap later
        this.count = 1;

    }

    public String getData() { //get methods for each
        return data;
    }

    public RBTNode getParent() {
        return parent;
    }

    public RBTNode getLeft() {
        return left;
    }

    public RBTNode getRight() {
        return right;
    }

    public int getColor() {
        return color;

    }
}