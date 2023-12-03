// Red Black Tree implementation in Java
// data structure that represents a node in the tree

// class RedBlackTree implements the operations in Red Black Tree
public class RedBlackTree {
    private RBTNode root;
    private RBTNode TNULL;
    public final int RED = 1;
    public final int BLACK = 0;
    int nodeCount = 0;

    private String preOrderHelper(RBTNode node) {
        StringBuilder result = new StringBuilder();
        if (node != TNULL) {
            if (node != null) {
                result.append(node.getData()).append(" ");
                result.append(preOrderHelper(node.getLeft()));
                result.append(preOrderHelper(node.getRight()));
            }
        }
        return result.toString();
    }



    private String inOrderHelper(RBTNode node) {
        StringBuilder result = new StringBuilder();
        if (node != TNULL) {
            if (node != null) {
                result.append(inOrderHelper(node.getLeft()));
                result.append(node.getData()).append(" ");
                result.append(inOrderHelper(node.getRight()));
            }
        }
        return result.toString();
    }

    private String postOrderHelper(RBTNode node) {
        StringBuilder result = new StringBuilder();
        if (node != TNULL) {
            if (node != null) {
                result.append(postOrderHelper(node.getLeft()));
                result.append(postOrderHelper(node.getRight()));
                result.append(node.getData()).append(" ");
            }
        }
        return result.toString();
    }

    private RBTNode searchTreeHelper(RBTNode node, String key) {
// base case
        if (node == TNULL || key.equals(node.data)) {
            return node;
        }
// search left subtree
        if (key.compareTo(node.data) < 0) {
            return searchTreeHelper(node.left, key);
        }
// search right subtree
        return searchTreeHelper(node.right, key);
    }

    // fix the rb tree modified by the delete operation
    private void fixDelete(RBTNode x) {
        RBTNode s;
        while (x != root && x.color == BLACK) {
            if (x == x.parent.left) {
                s = x.parent.right;
                if (s.color == RED) {
// case 3.1
                    s.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }
                if (s.left.color == BLACK && s.right.color == BLACK) {
// case 3.2
                    s.color = RED;
                    x = x.parent;
                } else {
                    if (s.right.color == BLACK) {
// case 3.3
                        s.left.color = BLACK;
                        s.color = RED;
                        rightRotate(s);
                        s = x.parent.right;
                    }
// case 3.4
                    s.color = x.parent.color;
                    x.parent.color = BLACK;
                    s.right.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.left;
                if (s.color == RED) {
// case 3.1
                    s.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }
                if (s.right.color == BLACK && s.right.color == BLACK) {
// case 3.2
                    s.color = RED;
                    x = x.parent;
                } else {
                    if (s.left.color == BLACK) {
// case 3.3
                        s.right.color = BLACK;
                        s.color = RED;
                        leftRotate(s);
                        s = x.parent.left;
                    }
// case 3.4
                    s.color = x.parent.color;
                    x.parent.color = BLACK;
                    s.left.color = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK;
    }

    private void rbTransplant(RBTNode u, RBTNode v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private void deleteNodeHelper(RBTNode node, String key) {
// find the node containing key
        RBTNode z = TNULL;
        RBTNode x, y;
        String data = node.data;

        while (node != TNULL) {
            if (data.compareTo(root.data) == 0) { //   int comparison = data.compareTo(key);
                z = node;
            }
            if (data.compareTo(root.data) <= 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        if (z == TNULL) {
            System.out.println("Couldn't find key in the tree");
            return;
        }
        y = z;
        int yOriginalColor = y.color;
        if (z.left == TNULL) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == BLACK) {
            fixDelete(x);
        }
    }

    // fix the red-black tree
    private void fixInsert(RBTNode k) {
        RBTNode u;
        while (k.parent.color == RED) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left; // uncle
                if (u.color == RED) {
// case 3.1
                    u.color = BLACK;
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
// case 3.2.2
                        k = k.parent;
                        rightRotate(k);
                    }
// case 3.2.1
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right; // uncle
                if (u.color == RED) {
// mirror case 3.1
                    u.color = BLACK;
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
// mirror case 3.2.2
                        k = k.parent;
                        leftRotate(k);
                    }
// mirror case 3.2.1
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = BLACK;
    }

    private void printHelper(RBTNode root, String indent, boolean last) {
// print the tree structure on the screen
        if (root != TNULL) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += " ";
            } else {
                System.out.print("L----");
                indent += "| ";
            }
            String sColor = root.color == 1 ? "RED" : "BLACK";
            System.out.println(root.data + "(" + sColor + ")");
            printHelper(root.left, indent, false);
            printHelper(root.right, indent, true);
        }
    }

    private void printHelperLimited(RBTNode root, String indent, boolean last, int currentDepth, int maxDepth) {
        if (currentDepth > maxDepth || root == TNULL) {
            return;
        }

        System.out.print(indent);
        if (last) {
            System.out.print("R----");
            indent += " ";
        } else {
            System.out.print("L----");
            indent += "| ";
        }

        String sColor = root.color == 1 ? "RED" : "BLACK";
        System.out.println(root.data + "(" + sColor + ")");

        printHelperLimited(root.left, indent, false, currentDepth + 1, maxDepth);
        printHelperLimited(root.right, indent, true, currentDepth + 1, maxDepth);
    }

    public RedBlackTree() {
        TNULL = new RBTNode();
        TNULL.color = BLACK;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    // Pre-Order traversal
// Node.Left Subtree.Right Subtree
    public String preorder() {
        return preOrderHelper(this.root);
    }

    // In-Order traversal
// Left Subtree . Node . Right Subtree
    public String inorder() {
        return inOrderHelper(this.root);
    }

    // Post-Order traversal
// Left Subtree . Right Subtree . Node
    public String postorder() {
        return postOrderHelper(this.root);
    }

    // search the tree for the key k
// and return the corresponding node
    public RBTNode searchTree(String k) {
        return searchTreeHelper(this.root, k);
    }

    // find the node with the minimum key
    public RBTNode minimum(RBTNode node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

    // find the node with the maximum key
    public RBTNode maximum(RBTNode node) {
        while (node.right != TNULL) {
            node = node.right;
        }
        return node;
    }

    // find the successor of a given node
    public RBTNode successor(RBTNode x) {
// if the right subtree is not null,
// the successor is the leftmost node in the
// right subtree
        if (x.right != TNULL) {
            return minimum(x.right);
        }
// else it is the lowest ancestor of x whose
// left child is also an ancestor of x.
        RBTNode y = x.parent;
        while (y != TNULL && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    // find the predecessor of a given node
    public RBTNode predecessor(RBTNode x) {
// if the left subtree is not null,
// the predecessor is the rightmost node in the
// left subtree
        if (x.left != TNULL) {
            return maximum(x.left);
        }
        RBTNode y = x.parent;
        while (y != TNULL && x == y.left) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    // rotate left at node x
    public void leftRotate(RBTNode x) {
        RBTNode y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    // rotate right at node x
    public void rightRotate(RBTNode x) {
        RBTNode y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }



    // insert the key to the tree in its appropriate position
// and fix the tree
    public void insert(String key) {
// Ordinary Binary Search Insertion
        RBTNode node = new RBTNode();
        node.parent = null;
        node.data = key;
        node.left = TNULL;
        node.right = TNULL;
        node.color = 1; // new node must be 1 (red)
        RBTNode y = null;
        RBTNode x = this.root;
        //If the node already exists, increment the count
        while (x != TNULL) {
            y = x;
            if (key.compareTo(x.data) < 0) {
                x = x.left;
            } else if (key.compareTo(x.data) > 0) {
                x = x.right;
            } else {
                // Node already exists, increment count and return
                x.count++;
                return;
            }
        }
// y is parent of x
        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data.compareTo(y.data) < 0) {
            y.left = node;
        } else {
            y.right = node;
        }
// if the grandparent is null, simply return
        if (node.parent == null) {
            node.color = 0; // 0 default black for root (only node with null parent)
            return;
        }

        if (node.parent.parent == null){
            return;
        }

// Fix the tree
        fixInsert(node);
// Increment counter after inserting a node
        nodeCount++;
    }

    public RBTNode getRoot() {
        return this.root;
    }
    public int getNodeCount() {
        return nodeCount;
    }

    public int getWordCount(String word){
        RBTNode node = searchTreeHelper(this.root, word);
        return (node != TNULL) ? node.count : 0;

    }

    private int getBlackHeight(RBTNode node) {
        if (node == TNULL) {
            return 1; // Height of TNULL is 1 (black height)
        }

        int leftBlackHeight = getBlackHeight(node.left);
        int rightBlackHeight = getBlackHeight(node.right);

        // Check if the black heights are consistent and return the maximum
        if (leftBlackHeight != rightBlackHeight) {
            throw new IllegalStateException("Red-Black Tree is not balanced!");
        }

        return leftBlackHeight + (node.color == 0 ? 1 : 0);
    }

    public int getBlackHeight() {
        return Math.max(getBlackHeight(root), 1) - 1;
    }



    private int getHeight(RBTNode node) {
        if (node == null) {
            return -1; // Height of an empty tree is -1
        }

        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public int getHeight() {
        return Math.max(getHeight(root), 1) - 1;
    }


    public int maxNodes() { //this is just (2^n)-1 where n is the height, but we already found the height above
        int height = getHeight(root); // for this red black tree, the max height should match the actual height
        return (int) Math.pow(2, height) - 1;
    }

    // delete the node from the tree
    public void deleteNode(String data) {
        deleteNodeHelper(this.root, data);
    }

    // print the tree structure on the screen
    public void prettyPrint() {
        printHelper(this.root, "", true);
    }

    public void prettyPrintLimited(int maxDepth) {
        printHelperLimited(this.root, "", true, 0, maxDepth);
    }



}
