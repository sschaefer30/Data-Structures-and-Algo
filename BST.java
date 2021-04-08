import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
 *
 * @author Scott Schaefer
 * @version 1.0
 * @userid sschaefer30 (i.e. gburdell3)
 * @GTID 903286025 (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        for (T element : data) {
            add(element);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (root == null) {
            root = new BSTNode<T>(data);
            size++;
        } else {
            add(data, root);
        }
    }

    /**
     * Recursive helper method for add.
     * @param data Data in new node.
     * @param node Node for each iteration.
     * @return Sent back to original method.
     */
    private BSTNode<T> add(T data, BSTNode<T> node) {
        if (node == null) {
            size++;
            return new BSTNode<T>(data);
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(data, node.getRight()));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) == 0) {
            return node;
        }
        return node;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = remove(data, root, dummy);
        if (dummy.getData() == null) {
            throw new NoSuchElementException("Data not found in BST");
        } else {
            return dummy.getData();
        }
    }

    /**
     * Recursive helper method for remove.
     *
     * @param node Node for recursion.
     * @param data Data to check for.
     * @param dummy Dummy node with removed data.
     * @return Returns node currently at.
     */
    private BSTNode<T> remove(T data, BSTNode<T> node, BSTNode<T> dummy) {
        if (node == null) {
            return null;
        }
        if (node.getData().compareTo(data) == 0) {
            size--;
            if (node.getLeft() == null && node.getRight() == null) {
                T removedData = node.getData();
                dummy.setData(removedData);
                return null;
            } else if (node.getLeft() == null) {
                T removedData = node.getData();
                dummy.setData(removedData);
                return node.getRight();
            } else if (node.getRight() == null) {
                T removedData = node.getData();
                dummy.setData(removedData);
                return node.getLeft();
            } else {
                T removedData = node.getData();
                dummy.setData(removedData);
                BSTNode<T> dummy2 = new BSTNode<>(null);
                node.setRight(removeSuccessor(node.getRight(), dummy2));
                node.setData(dummy2.getData());
            }
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(remove(data, node.getRight(), dummy));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(data, node.getLeft(), dummy));
        }
        return node;
    }

    /**
     * Two child case helper method.
     * @param node The beginning node.
     * @param dummy2 Dummy2 for successor.
     * @return Value of successor
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> node, BSTNode<T> dummy2) {
        if (node.getLeft() == null) {
            dummy2.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(removeSuccessor(node.getLeft(), dummy2));
        }
        return node;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (root == null) {
            throw new NoSuchElementException("Data not found");
        }
        BSTNode<T> curr = root;
        T gotData = get(data, curr);
        if (gotData == null) {
            throw new NoSuchElementException("Data not found");
        } else {
            return gotData;
        }
    }

    /**
     * Get helper method.
     * @param data Data to be found.
     * @param curr Current node.
     * @return Node to be recursed.
     */
    private T get(T data, BSTNode<T> curr) {
        if (curr == null) {
            return null;
        }
        if (curr.getData().compareTo(data) == 0) {
            return curr.getData();
        } else if (data.compareTo(curr.getData()) > 0) {
            return get(data, curr.getRight());
        } else  {
            return get(data, curr.getLeft());
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        BSTNode<T> curr = root;
        T found = contains(data, curr);
        return !(found == null);
    }

    /**
     * Contains helper method.
     * @param data Data to be found.
     * @param curr Current node.
     * @return Node to be recursed.
     */
    private T contains(T data, BSTNode<T> curr) {
        if (curr == null) {
            return null;
        }
        if (curr.getData().compareTo(data) == 0) {
            return curr.getData();
        } else if (data.compareTo(curr.getData()) > 0) {
            return contains(data, curr.getRight());
        } else  {
            return contains(data, curr.getLeft());
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        BSTNode<T> curr = root;
        ArrayList<T> arr = new ArrayList<>();
        preorder(curr, arr);
        return arr;
    }

    /**
     * preorder helper method.
     * @param curr Current node.
     * @param arr ArrayList to store node data.
     */
    private void preorder(BSTNode<T> curr, ArrayList<T> arr) {
        if (curr == null) {
            return;
        }
        arr.add(curr.getData());
        preorder(curr.getLeft(), arr);
        preorder(curr.getRight(), arr);
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        BSTNode<T> curr = root;
        ArrayList<T> arr = new ArrayList<>();
        inorder(curr, arr);
        return arr;
    }

    /**
     * Helper method for inorder.
     * @param curr Current node.
     * @param arr Arraylist to be returned.
     */
    private void inorder(BSTNode<T> curr, ArrayList<T> arr) {
        if (curr == null) {
            return;
        }
        inorder(curr.getLeft(), arr);
        arr.add(curr.getData());
        inorder(curr.getRight(), arr);
    }
    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        BSTNode<T> curr = root;
        ArrayList<T> arr = new ArrayList<>();
        postorder(curr, arr);
        return arr;
    }
    /**
     * Helper method for postorder.
     * @param curr Current node.
     * @param arr Arraylist to be returned.
     */
    private void postorder(BSTNode<T> curr, ArrayList<T> arr) {
        if (curr == null) {
            return;
        }
        postorder(curr.getLeft(), arr);
        postorder(curr.getRight(), arr);
        arr.add(curr.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        ArrayList<T> arr = new ArrayList<>();
        Queue<BSTNode<T>> q = new LinkedList<>();
        q.add(root);
        while (!(q.isEmpty())) {
            BSTNode<T> curr = q.poll();
            arr.add(curr.getData());
            if (curr.getLeft() != null) {
                q.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                q.add(curr.getRight());
            }
        }
        return arr;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return height(root);
    }

    /**
     * height helper method.
     * @param curr Current node.
     * @return Returns depth.
     */
    private int height(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        int hl = height(curr.getLeft());
        int hr = height(curr.getRight());
        return 1 + Math.max(hl, hr);
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Determines if a binary tree is a valid BST.
     *
     * This must be done recursively. Do NOT modify the tree passed in.
     *
     * If the order property is violated, this method may not need to traverse
     * the entire tree to function properly, so you should only traverse the
     * branches of the tree necessary to find order violations and only do so once.
     * Failure to do so will result in an efficiency penalty.
     *
     * EXAMPLES: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * isBST(50) should return true, since for every node, the node's left
     * subtree is less than the node's data, and the node's right subtree is
     * greater than the node's data.
     *
     *             20
     *           /    \
     *         21      38
     *        /          \
     *       6          50
     *        \
     *         12
     *
     * isBST(20) should return false, since 21 is in 20's left subtree.
     *
     *
     * Should have a worst-case running time of O(n).
     *
     * @param node the root of the binary tree
     * @return true if the tree with node as the root is a valid BST,
     *         false otherwise
     */
    public boolean isBST(BSTNode<T> node) {
        if (node == null) {
            return true;
        }
        return isBST(node, null, null);
    }

    /**
     * Helper method for isBST.
     * @param node Current node.
     * @param min Remembering the min.
     * @param max Remembering the max.
     * @return is this a BST.
     */
    private boolean isBST(BSTNode<T> node, T min, T max) {
        if (node == null) {
            return true;
        }
        if (min != null) {
            if (node.getData().compareTo(min) <= 0) {
                return false;
            }
        }
        if (max != null) {
            if (node.getData().compareTo(max) >= 0) {
                return false;
            }
        }
        return (isBST(node.getLeft(), min, node.getData())
                && isBST(node.getRight(), node.getData(), max));
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
