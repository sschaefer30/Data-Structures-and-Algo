import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
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
public class CircularSinglyLinkedList<T> {


    // Do not add new instance variables or modify existing ones.
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add  he new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (size == 0) {
            addToEmpty(data);
            size++;
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            CircularSinglyLinkedListNode<T> nodeBeforeIndex = iterateUntil(index);
            CircularSinglyLinkedListNode<T> newNode;
            newNode = new CircularSinglyLinkedListNode<>(data, nodeBeforeIndex.getNext());
            nodeBeforeIndex.setNext(newNode);
            size++;
        }
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (size == 0) {
            addToEmpty(data);
        } else {
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(null, head.getNext());
            head.setNext(newNode);
            newNode.setData(head.getData());
            head.setData(data);
        }
        size++;
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (size == 0) {
            addToEmpty(data);
        } else {
            CircularSinglyLinkedListNode<T> newNode;
            newNode = new CircularSinglyLinkedListNode<>(head.getData(), head.getNext());
            head.setNext(newNode);
            head.setData(data);
            head = newNode;
        }
        size++;
    }

    /**
     * Helpler method for adding to empty CLL.
     * @param data Data import.
     */
    private void addToEmpty(T data) {
        CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(data);
        head = newNode;
        newNode.setNext(head);
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        if (index == 0) {
            return removeFromFront();
        } else if (index + 1 == size) {
            return removeFromBack();
        } else {
            CircularSinglyLinkedListNode<T> nodeBefore = iterateUntil(index);
            T removed = nodeBefore.getNext().getData();
            nodeBefore.setNext(nodeBefore.getNext().getNext());
            size--;
            return removed;
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty");
        }
        T removed = head.getData();
        if (size == 1) {
            clear();
        } else {
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
            size--;
        }
        return removed;
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty");
        }
        if (size == 1) {
            T removed = head.getData();
            clear();
            return removed;
        } else {
            CircularSinglyLinkedListNode<T> nodeBefore = iterateUntil(size - 1);
            T removed = nodeBefore.getNext().getData();
            nodeBefore.setNext(head);
            size--;
            return removed;
        }
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (index == 0) {
            return head.getData();
        } else {
            CircularSinglyLinkedListNode<T> nodeBefore = iterateUntil(index);
            return nodeBefore.getNext().getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (size == 0) {
            throw new NoSuchElementException("Data not found");
        }
        CircularSinglyLinkedListNode<T> current = head;
        CircularSinglyLinkedListNode<T> prev = null;
        if (current.getData().equals(data)) {
            return removeFromFront();
        }
        for (int i = 0; i < size - 1; i++) {
            T nextData = current.getNext().getData();
            if (nextData.equals(data)) {
                prev = current;
            }
            current = current.getNext();
        }
        if (prev != null) {
            T removed = prev.getNext().getData();
            prev.setNext(prev.getNext().getNext());
            size--;
            return removed;
        }
        throw new NoSuchElementException("Data not found");
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] arr = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            arr[i] = current.getData();
            current = current.getNext();
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Iterates through the CLL until
     * @param index Index to iterate to.
     * @return Node that is iterated to. (index - 1)
     */
    private CircularSinglyLinkedListNode<T> iterateUntil(int index) {
        CircularSinglyLinkedListNode<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.getNext();
        }
        return current;
    }
}
