import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
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
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data ==  null) {
            throw new IllegalArgumentException("Data set is null");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        size = data.size();
        int i = 1;
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Data point in data set is null");
            }
            backingArray[i] = element;
            i++;
        }
        int index = data.size() / 2;
        for (int j = index; j > 0; j--) {
            downHeap(j);
        }
    }

    /**
     * helper method
     * @param index Current index
     */
    private void downHeap(int index) {
        if (leftChildIndex(index) > size && rightChildIndex(index) > size) {
            return;
        }
        T childLeft = backingArray[leftChildIndex(index)];
        T childRight = backingArray[rightChildIndex(index)];
        T parent = backingArray[index];
        if (rightChildIndex(index) > size) {
            if (childLeft.compareTo(parent) > 0) {
                backingArray[index] = childLeft;
                backingArray[index * 2] = parent;
                downHeap(leftChildIndex(index));
            }
        } else {
            if (childLeft.compareTo(parent) > 0 || childRight.compareTo(parent) > 0) {
                if (childRight.compareTo(childLeft) > 0) {
                    backingArray[index] = childRight;
                    backingArray[(index * 2) + 1] = parent;
                    downHeap(rightChildIndex(index));
                } else {
                    backingArray[index] = childLeft;
                    backingArray[index * 2] = parent;
                    downHeap(leftChildIndex(index));
                }
            }
        }
    }

    /**
     *  left child index
     * @param index Index of parent
     * @return Index of left child of parent at index
     */
    private int leftChildIndex(int index) {
        return 2 * index;
    }

    /**
     * right child index
     * @param index Index of parent
     * @return Index of right child of parent at index
     */
    private int rightChildIndex(int index) {
        return (2 * index) + 1;
    }

    /**
     * parent index
     * @param index Index of child
     * @return Index of parent of child at index
     */
    private int parentIndex(int index) {
        return index / 2;
    }
    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (size == (backingArray.length - 1)) {
            T[] temp = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }
        size++;
        backingArray[size] = data;
        upHeap(size);
    }

    /**
     * upHeap helper method
     * @param index Current index
     */
    private void upHeap(int index) {
        T current = backingArray[index];
        while (index > 1 && current.compareTo(backingArray[parentIndex(index)]) > 0) {
            backingArray[index] = backingArray[parentIndex(index)];
            index = parentIndex(index);
        }
        backingArray[index] = current;
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Empty heap");
        }
        T removed = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        downHeap(1);
        return removed;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            throw new NoSuchElementException("Empty heap");
        }
        T max = backingArray[1];
        for (int i = 2; i < size + 1; i++) {
            if (backingArray[i].compareTo(max) > 0) {
                max = backingArray[i];
            }
        }
        return max;
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
