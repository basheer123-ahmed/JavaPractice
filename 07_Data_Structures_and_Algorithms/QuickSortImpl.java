/*
 * Program 91: Quick Sort Implementation
 *
 * Problem Statement:
 *   Implement Quick Sort with Lomuto and Hoare partition schemes.
 *   Show different pivot strategies: first, last, random, median-of-three.
 *
 * Sample Input:  [10, 7, 8, 9, 1, 5]
 * Sample Output:
 *   Sorted (Lomuto): [1, 5, 7, 8, 9, 10]
 *   Sorted (Hoare) : [1, 5, 7, 8, 9, 10]
 *
 * Time Complexity:  Average O(n log n), Worst case O(n²) with bad pivot
 * Space Complexity: O(log n) avg for recursion stack
 */
import java.util.Arrays;
import java.util.Random;

public class QuickSortImpl {

    static Random rng = new Random(42);

    // ============================================================
    // 1. Lomuto Partition (pivot = last element)
    // ============================================================
    static int partitionLomuto(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    static void quickSortLomuto(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partitionLomuto(arr, low, high);
            quickSortLomuto(arr, low, pi - 1);
            quickSortLomuto(arr, pi + 1, high);
        }
    }

    // ============================================================
    // 2. Hoare Partition (pivot = first element, more efficient)
    // ============================================================
    static int partitionHoare(int[] arr, int low, int high) {
        int pivot = arr[low];
        int i = low - 1;
        int j = high + 1;
        while (true) {
            do { i++; } while (arr[i] < pivot);
            do { j--; } while (arr[j] > pivot);
            if (i >= j) return j;
            swap(arr, i, j);
        }
    }

    static void quickSortHoare(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partitionHoare(arr, low, high);
            quickSortHoare(arr, low, pi);
            quickSortHoare(arr, pi + 1, high);
        }
    }

    // ============================================================
    // 3. Randomized Quick Sort (random pivot to avoid worst case)
    // ============================================================
    static void quickSortRandom(int[] arr, int low, int high) {
        if (low < high) {
            // Pick random pivot and move to end
            int randomIdx = low + rng.nextInt(high - low + 1);
            swap(arr, randomIdx, high);
            int pi = partitionLomuto(arr, low, high);
            quickSortRandom(arr, low, pi - 1);
            quickSortRandom(arr, pi + 1, high);
        }
    }

    // ============================================================
    // 4. Quick Sort with Median-of-Three pivot
    // ============================================================
    static int medianOfThree(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;
        // Sort arr[low], arr[mid], arr[high] and return median index
        if (arr[low] > arr[mid])  swap(arr, low, mid);
        if (arr[low] > arr[high]) swap(arr, low, high);
        if (arr[mid] > arr[high]) swap(arr, mid, high);
        // arr[mid] is now the median — move to end-1 position
        swap(arr, mid, high - 1);
        return arr[high - 1];
    }

    static void quickSortMedian(int[] arr, int low, int high) {
        if (low < high) {
            if (high - low < 3) {
                // Use insertion sort for tiny arrays
                insertionSort(arr, low, high);
                return;
            }
            int pivot = medianOfThree(arr, low, high);
            int i = low, j = high - 1;
            while (true) {
                while (arr[++i] < pivot) {}
                while (arr[--j] > pivot) {}
                if (i >= j) break;
                swap(arr, i, j);
            }
            swap(arr, i, high - 1);
            quickSortMedian(arr, low, i - 1);
            quickSortMedian(arr, i + 1, high);
        }
    }

    // ============================================================
    // 5. 3-Way Quick Sort (Dutch National Flag) — handles duplicates
    // ============================================================
    static void quickSort3Way(int[] arr, int low, int high) {
        if (low >= high) return;
        int pivot = arr[low];
        int lt = low, gt = high, i = low + 1;
        while (i <= gt) {
            if      (arr[i] < pivot) swap(arr, lt++, i++);
            else if (arr[i] > pivot) swap(arr, i,    gt--);
            else                     i++;
        }
        // arr[low..lt-1] < pivot, arr[lt..gt] = pivot, arr[gt+1..high] > pivot
        quickSort3Way(arr, low, lt - 1);
        quickSort3Way(arr, gt + 1, high);
    }

    // Helpers
    static void swap(int[] arr, int i, int j) {
        int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }

    static void insertionSort(int[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= low && arr[j] > key) arr[j+1] = arr[j--];
            arr[j+1] = key;
        }
    }

    static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) if (arr[i] < arr[i-1]) return false;
        return true;
    }

    public static void main(String[] args) {

        int[] original = {10, 7, 8, 9, 1, 5, 3, 6, 2, 4};
        System.out.println("Original          : " + Arrays.toString(original));

        int[] a1 = Arrays.copyOf(original, original.length);
        quickSortLomuto(a1, 0, a1.length - 1);
        System.out.println("Lomuto partition  : " + Arrays.toString(a1));

        int[] a2 = Arrays.copyOf(original, original.length);
        quickSortHoare(a2, 0, a2.length - 1);
        System.out.println("Hoare partition   : " + Arrays.toString(a2));

        int[] a3 = Arrays.copyOf(original, original.length);
        quickSortRandom(a3, 0, a3.length - 1);
        System.out.println("Randomized pivot  : " + Arrays.toString(a3));

        int[] a4 = Arrays.copyOf(original, original.length);
        quickSortMedian(a4, 0, a4.length - 1);
        System.out.println("Median-of-three   : " + Arrays.toString(a4));

        System.out.println("\n========== 3-Way Quick Sort (handles duplicates) ==========");
        int[] withDups = {4, 2, 7, 2, 4, 9, 1, 4, 7, 2};
        System.out.println("Original  : " + Arrays.toString(withDups));
        quickSort3Way(withDups, 0, withDups.length - 1);
        System.out.println("3-Way Sort: " + Arrays.toString(withDups));

        System.out.println("\n========== Edge Cases ==========");
        int[] single  = {42};
        int[] two     = {5, 1};
        int[] sorted  = {1, 2, 3, 4, 5};
        int[] reverse = {5, 4, 3, 2, 1};
        int[] allSame = {3, 3, 3, 3, 3};

        quickSortLomuto(single,  0, single.length - 1);
        quickSortLomuto(two,     0, two.length - 1);
        quickSortLomuto(sorted,  0, sorted.length - 1);
        quickSortLomuto(reverse, 0, reverse.length - 1);
        quickSort3Way  (allSame, 0, allSame.length - 1);

        System.out.println("Single  : " + Arrays.toString(single));
        System.out.println("Two     : " + Arrays.toString(two));
        System.out.println("Sorted  : " + Arrays.toString(sorted));
        System.out.println("Reverse : " + Arrays.toString(reverse));
        System.out.println("AllSame : " + Arrays.toString(allSame));

        System.out.println("\n========== Performance Comparison ==========");
        int n = 100_000;
        int[] data = new int[n];
        for (int i = 0; i < n; i++) data[i] = rng.nextInt(1_000_000);

        int[] d1 = Arrays.copyOf(data, n);
        long t1 = System.nanoTime();
        quickSortLomuto(d1, 0, n-1);
        System.out.printf("Lomuto  (%,d elements): %,d ms | sorted=%b%n", n, (System.nanoTime()-t1)/1_000_000, isSorted(d1));

        int[] d2 = Arrays.copyOf(data, n);
        long t2 = System.nanoTime();
        quickSortRandom(d2, 0, n-1);
        System.out.printf("Random  (%,d elements): %,d ms | sorted=%b%n", n, (System.nanoTime()-t2)/1_000_000, isSorted(d2));

        int[] d3 = Arrays.copyOf(data, n);
        long t3 = System.nanoTime();
        quickSort3Way(d3, 0, n-1);
        System.out.printf("3-Way   (%,d elements): %,d ms | sorted=%b%n", n, (System.nanoTime()-t3)/1_000_000, isSorted(d3));
    }
}
