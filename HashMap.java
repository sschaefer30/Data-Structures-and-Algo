import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of a HashMap.
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
public class HashMap<K, V> {

    /*
     * The initial capacity of the HashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the HashMap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new HashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new HashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = new MapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     *
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. Resize if the load factor (LF) is greater than max LF (it is okay
     * if the load factor is equal to max LF). For example, let's say the
     * array is of length 5 and the current size is 3 (LF = 0.6). For this
     * example, assume that no elements are removed in between steps. If
     * another entry is attempted to be added, before doing anything else,
     * you should check whether (3 + 1) / 5 = 0.8 is larger than the max LF.
     * It is, so you would trigger a resize before you even attempt to add
     * the data or figure out if it's a duplicate. Be careful to consider the
     * differences between integer and double division when calculating load
     * factor.
     *
     * When regrowing, resize the length of the backing table to
     * (2 * old length) + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key cannot be null, value cannot be null");
        }
        MapEntry<K, V> entry = new MapEntry<K, V>(key, value);
        double lF = getLF(size, table.length);
        int index = Math.abs(key.hashCode() % table.length);
        MapEntry<K, V> existing = table[index];
        if (existing == null) {
            if (lF > MAX_LOAD_FACTOR) {
                resizeBackingTable((2 * table.length) + 1);
            }
            table[index] = entry;
            size++;
            return null;
        } else {
            while (existing.getNext() != null) {
                if (existing.getNext().getKey().equals(key)) {
                    V old = existing.getValue();
                    existing.setValue(value);
                    return old;
                }
                existing = existing.getNext();
            }
            if (existing.getKey().equals(key)) {
                V old = existing.getValue();
                existing.setValue(value);
                return old;
            } else {
                if (lF > MAX_LOAD_FACTOR) {
                    resizeBackingTable((2 * table.length) + 1);
                }
                existing.setNext(entry);
                size++;
                return null;
            }
        }
    }

    /**
     * Calculates LF (loading factor).
     * @param n Size: #entries in map
     * @param bigN Array.length: capacity of backing array
     * @return LF, loading factor
     */
    private double getLF(int n, int bigN) {
        return ((n + 1) * 1.0) / (bigN * 1.0);
    }
    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key the key to remove
     * @return the value associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = Math.abs(key.hashCode() % table.length);
        MapEntry<K, V> existing = table[index];
        MapEntry<K, V> prev = null;
        while (existing != null) {
            if (existing.getKey().equals(key)) {
                break;
            }
            prev = existing;
            existing = existing.getNext();
        }
        if (existing == null) {
            throw new NoSuchElementException("key is not in map");
        }
        size--;
        if (prev != null) {
            prev.setNext(existing.getNext());
        } else {
            table[index] = existing.getNext();
        }
        return existing.getValue();
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = Math.abs(key.hashCode() % table.length);
        MapEntry<K, V> existing = table[index];
        if (existing == null) {
            throw new NoSuchElementException("key is not in map");
        }
        if (existing.getKey().equals(key)) {
            return existing.getValue();
        }
        while (existing != null && existing.getNext() != null && !(existing.getNext().getKey().equals(key))) {
            existing = existing.getNext();
        }
        if (existing.getNext() == null) {
            if (existing.getKey().equals(key)) {
                return existing.getValue();
            }
            throw new NoSuchElementException("key is not in map");
        }
        existing = existing.getNext();
        return existing.getValue();
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = Math.abs(key.hashCode() % table.length);
        MapEntry<K, V> existing = table[index];
        while (existing != null && existing.getNext() != null && !(existing.getNext().getKey().equals(key))) {
            existing = existing.getNext();
        }
        if (existing == null) {
            return false;
        }
        if (existing.getNext() == null) {
            if (existing.getKey().equals(key)) {
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            MapEntry<K, V> entry = table[i];
            if (entry == null) {
                continue;
            }
            while (entry.getNext() != null) {
                set.add(entry.getKey());
                entry = entry.getNext();
            }
            set.add(entry.getKey());
        }
        return set;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        ArrayList<V> arr = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            MapEntry<K, V> entry = table[i];
            if (entry == null) {
                continue;
            }
            while (entry.getNext() != null) {
                arr.add(entry.getValue());
                entry = entry.getNext();
            }
            arr.add(entry.getValue());
        }
        return arr;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("Length is less than size");
        }
        MapEntry<K, V>[] newTable = new MapEntry[length];
        for (int i = 0; i < size ; i++) {
            newTable[i] = table[i];
        }
        table = newTable;
    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the initial capacity and resets the
     * size.
     */
    public void clear() {
        size = 0;
        table = new MapEntry[INITIAL_CAPACITY];
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
