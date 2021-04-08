import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
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

public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        for (int k = 1; k < arr.length; k++) {
            for (int n = k; n > 0; n--) {
                if (comparator.compare(arr[n - 1], arr[n]) > 0) {
                    T temp = arr[n];
                    arr[n] = arr[n - 1];
                    arr[n - 1] = temp;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        boolean check = true;
        int end = arr.length - 1;
        int start = 1;
        while (check) {
            int newStart = start;
            int newEnd = end;
            check = false;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    newEnd = i + 1;
                    check = true;
                }
            }
            end = newEnd - 1;
            if (!check) {
                break;
            }
            check = false;
            for (int i = end; i > start; i--) {
                if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = temp;
                    newStart = i - 1;
                    check = true;
                }
            }
            start = newStart + 1;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        sort(arr, arr.length, comparator);
    }

    /**
     * Recursive helper method for mergeSort.
     * @param arr the array to be sorted
     * @param length length of array
     * @param <T> data type to sort
     * @param comparator the Comparator used to compare the data in arr
     */
    private static <T> void sort(T[] arr, int length, Comparator<T> comparator) {
        if (length == 1) {
            return;
        }
        int middle = length / 2;
        T[] left = (T[]) new Object[middle];
        T[] right = (T[]) new Object[length - middle];

        for (int i = 0; i < middle; i++) {
            left[i] = arr[i];
        }
        for (int i = middle; i < length; i++) {
            right[i - middle] = arr[i];
        }
        sort(left, middle, comparator);
        sort(right, length - middle, comparator);

        int a = 0;
        int b = 0;
        int c = 0;
        while (a < middle && b < length - middle) {
            if (comparator.compare(left[a], right[b]) <= 0) {
                arr[c++] = left[a++];
            } else {
                arr[c++] = right[b++];
            }
        }
        while (a < middle) {
            arr[c++] = left[a++];
        }
        while (b < length - middle) {
            arr[c++] = right[b++];
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        int max = max(arr);
        LinkedList<Integer>[] buckets = new LinkedList[19];
        int divisor = 1;
        while (max / divisor != 0) {
            for (int j : arr) {
                int digit = j / divisor % 10;
                if (buckets[digit + 9] == null) {
                    buckets[digit + 9 ] = new LinkedList<Integer>();
                }
                buckets[digit + 9].add(j);
            }
            int id = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (bucket != null && !bucket.isEmpty()) {
                    int removed = bucket.pop();
                    arr[id++] = removed;
                }
            }
            divisor = divisor * 10;
        }
    }

    /**
     * Max value in arr of integers.
     * @param arr Input array.
     * @return Max value.
     */
    private static int max(int[] arr) {
        int max = Math.abs(arr[0]);
        for (int e : arr) {
            if (Math.abs(e) > max) {
                max = Math.abs(e);
            }
        }
        return max;
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Array is null");
        }
        quickSort(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     * Recursive helper method for quickSort.
     * @param arr Array to be worked on.
     * @param start start index
     * @param end end index
     * @param <T> data type
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    private static <T> void quickSort(T[] arr, int start, int end, Comparator<T> comparator, Random rand) {
        if (start < end) {
            int pIndex = partition(arr, start, end, comparator, rand);
            quickSort(arr, start, pIndex - 1, comparator, rand);
            quickSort(arr, pIndex + 1, end, comparator, rand);
        }
    }

    /**
     * Swap method for quickSort.
     * @param arr Array to be worked.
     * @param i i-index
     * @param j j-index
     * @param <T> data type
     */
    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * partition for quickSort
     * @param arr Array to be worked on.
     * @param start start index of partition
     * @param end end index of partition
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param <T> data type
     * @return int pIndex used in recursive call above
     */
    private static <T> int partition(T[] arr, int start, int end, Comparator<T> comparator, Random rand) {
        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T pivot = arr[pivotIndex];
        swap(arr, pivotIndex, start);
        pivotIndex = start;
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(pivot, arr[i]) >= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        swap(arr, pivotIndex, j);
        return j;
    }
}