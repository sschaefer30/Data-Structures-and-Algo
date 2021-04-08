import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        for (T element : data) {
            add(element);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (root == null) {
            root = new AVLNode<T>(data);
            size++;
        } else {
            root = add(data, root);
        }
    }

    /**
     * Helper method for add.
     * @param data Data to be added.
     * @param node Current node.
     * @return Next node.
     */
    private AVLNode<T> add(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(data, node.getRight()));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) == 0) {
            return node;
        }

        node.setHeight(Math.max(heightLeft(node), heightRight(node)) + 1);
        node.setBalanceFactor(balance(node));

        if (node.getBalanceFactor() > 1 && data.compareTo(node.getLeft().getData()) < 0) {
            return rotateRight(node);
        }
        if (node.getBalanceFactor() < -1 && data.compareTo(node.getRight().getData()) > 0) {
            return rotateLeft(node);
        }
        if (node.getBalanceFactor() > 1 && data.compareTo(node.getLeft().getData()) > 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }
        if (node.getBalanceFactor() < -1 && data.compareTo(node.getRight().getData()) < 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }
        return node;
    }

    /**
     * Height helper method for left child.
     * @param node Node for height.
     * @return height of node input
     */
    private int heightLeft(AVLNode<T> node) {
        if (node.getLeft() == null) {
            return -1;
        } else {
            return node.getLeft().getHeight();
        }
    }
    /**
     * Height helper method for right child.
     * @param node Node for height.
     * @return height of node input
     */
    private int heightRight(AVLNode<T> node) {
        if (node.getRight() == null) {
            return -1;
        } else {
            return node.getRight().getHeight();
        }
    }

    /**
     * balancing factor helper method
     * @param node Node input
     * @return Calculated balancing factor
     */
    private int balance(AVLNode<T> node) {
        return heightLeft(node) - heightRight(node);
    }
    /**
     * Rotates right around the specified node.
     * @param node Node to be rotated around.
     * @return Recursive return.
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> child = node.getLeft();
        AVLNode<T> grandChild = child.getRight();

        child.setRight(node);
        node.setLeft(grandChild);

        node.setHeight(Math.max(heightLeft(node), heightRight(node)) + 1);
        node.setBalanceFactor(balance(node));

        child.setHeight(Math.max(heightLeft(child), heightRight(child)) + 1);
        child.setBalanceFactor(balance(child));
        return child;
    }

    /**
     * Rotates left around the specified node.
     * @param node Node to be rotated around.
     * @return Recursive return.
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> child = node.getRight();
        AVLNode<T> grandChild = child.getLeft();

        child.setLeft(node);
        node.setRight(grandChild);

        node.setHeight(Math.max(heightLeft(node), heightRight(node)) + 1);
        node.setBalanceFactor(balance(node));

        child.setHeight(Math.max(heightLeft(child), heightRight(child)) + 1);
        child.setBalanceFactor(balance(child));
        return child;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *    simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     *    replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     *    replace the data, NOT successor. As a reminder, rotations can occur
     *    after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = remove(data, root, dummy);
        if (dummy.getData() == null) {
            throw new NoSuchElementException("Data not found in AVL");
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
    private AVLNode<T> remove(T data, AVLNode<T> node, AVLNode<T> dummy) {
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
                AVLNode<T> dummy2 = new AVLNode<>(null);
                node.setLeft(removePredecessor(node.getLeft(), dummy2));
                node.setData(dummy2.getData());
            }
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(remove(data, node.getRight(), dummy));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(data, node.getLeft(), dummy));
        }
        node.setHeight(Math.max(heightLeft(node), heightRight(node)) + 1);
        node.setBalanceFactor(balance(node));

        if (node.getBalanceFactor() > 1 && node.getLeft().getBalanceFactor() >= 0) {
            return rotateRight(node);
        }
        if (node.getBalanceFactor() < -1 && node.getRight().getBalanceFactor() <= 0) {
            return rotateLeft(node);
        }
        if (node.getBalanceFactor() > 1 && node.getLeft().getBalanceFactor() < 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }
        if (node.getBalanceFactor() < -1 && node.getRight().getBalanceFactor() > 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }
        return node;
    }

    /**
     * Two child case helper method.
     * @param node The beginning node.
     * @param dummy2 Dummy2 for successor.
     * @return Value of successor
     */
    private AVLNode<T> removePredecessor(AVLNode<T> node, AVLNode<T> dummy2) {
        if (node.getRight() == null) {
            dummy2.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(removePredecessor(node.getRight(), dummy2));
        }
        node.setHeight(Math.max(heightLeft(node), heightRight(node)) + 1);
        node.setBalanceFactor(balance(node));
        return node;
    }
    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
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
        AVLNode<T> curr = root;
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
    private T get(T data, AVLNode<T> curr) {
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
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        AVLNode<T> curr = root;
        T found = contains(data, curr);
        return !(found == null);
    }
    /**
     * Contains helper method.
     * @param data Data to be found.
     * @param curr Current node.
     * @return Node to be recursed.
     */
    private T contains(T data, AVLNode<T> curr) {
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
     * Returns the height of the root of the tree. Do NOT compute the height
     * recursively. This method should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not contain duplicate data, and the data of a branch
     * should be listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        AVLNode<T> curr = root;
        ArrayList<T> arr = new ArrayList<>();
        preorder(curr, arr);
        return arr;
    }

    /**
     * preorder helper method.
     * @param curr Current node.
     * @param arr ArrayList to store node data.
     */
    private void preorder(AVLNode<T> curr, ArrayList<T> arr) {
        if (curr == null) {
            return;
        }
        arr.add(curr.getData());
        if (curr.getBalanceFactor() > 0) {
            preorder(curr.getLeft(), arr);
        } else if (curr.getBalanceFactor() < 0) {
            preorder(curr.getRight(), arr);
        } else {
            preorder(curr.getLeft(), arr);
            preorder(curr.getRight(), arr);
        }
    }
    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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