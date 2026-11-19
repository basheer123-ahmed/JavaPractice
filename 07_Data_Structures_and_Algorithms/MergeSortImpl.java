/*
 * Program 90: Merge Sort Implementation
 *
 * Problem Statement:
 *   Implement Merge Sort — a divide-and-conquer sorting algorithm.
 *   Also count inversions in an array using merge sort.
 *
 * Sample Input:  [38, 27, 43, 3, 9, 82, 10]
 * Sample Output:
 *   Sorted: [3, 9, 10, 27, 38, 43, 82]
 *   Inversions: 10
 *
 * Time Complexity:  O(n log n) all cases
 * Space Complexity: O(n) for temporary arrays
 */
import java.util.Arrays;

public class MergeSortImpl {

    // ============================================================
    // Standard Merge Sort
    // ============================================================
    static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left,     L, 0, n1);
        System.arraycopy(arr, mid + 1,  R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) arr[k++] = L[i++];
            else               arr[k++] = R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // ============================================================
    // Merge Sort with Step Visualization
    // ============================================================
    static int stepCount = 0;

    static void mergeSortVerbose(int[] arr, int left, int right, int depth) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        String indent = "  ".repeat(depth);
        System.out.printf("%sDivide: %s%n", indent, Arrays.toString(Arrays.copyOfRange(arr, left, right+1)));
        mergeSortVerbose(arr, left, mid,    depth + 1);
        mergeSortVerbose(arr, mid+1, right, depth + 1);
        merge(arr, left, mid, right);
        System.out.printf("%sMerge : %s%n", indent, Arrays.toString(Arrays.copyOfRange(arr, left, right+1)));
    }

    // ============================================================
    // Count Inversions using Merge Sort
    // An inversion is a pair (i,j) where i<j but arr[i]>arr[j]
    // ============================================================
    static long countInversions(int[] arr, int left, int right) {
        long inv = 0;
        if (left < right) {
            int mid = left + (right - left) / 2;
            inv += countInversions(arr, left, mid);
            inv += countInversions(arr, mid + 1, right);
            inv += mergeCount(arr, left, mid, right);
        }
        return inv;
    }

    static long mergeCount(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] L = Arrays.copyOfRange(arr, left, left + n1);
        int[] R = Arrays.copyOfRange(arr, mid + 1, mid + 1 + n2);

        long count = 0;
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
                count += (n1 - i); // all remaining L[] elements form inversions with R[j]
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
        return count;
    }

    // ============================================================
    // Merge Sort for Strings
    // ============================================================
    static void mergeSortStrings(String[] arr, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSortStrings(arr, left, mid);
        mergeSortStrings(arr, mid + 1, right);
        mergeStrings(arr, left, mid, right);
    }

    static void mergeStrings(String[] arr, int left, int mid, int right) {
        String[] L = Arrays.copyOfRange(arr, left, mid + 1);
        String[] R = Arrays.copyOfRange(arr, mid + 1, right + 1);
        int i = 0, j = 0, k = left;
        while (i < L.length && j < R.length) {
            if (L[i].compareTo(R[j]) <= 0) arr[k++] = L[i++];
            else                            arr[k++] = R[j++];
        }
        while (i < L.length) arr[k++] = L[i++];
        while (j < R.length) arr[k++] = R[j++];
    }

    public static void main(String[] args) {

        System.out.println("========== Merge Sort ==========");
        int[] arr1 = {38, 27, 43, 3, 9, 82, 10};
        System.out.println("Original: " + Arrays.toString(arr1));
        mergeSort(arr1, 0, arr1.length - 1);
        System.out.println("Sorted  : " + Arrays.toString(arr1));

        System.out.println("\n========== Merge Sort — Step-by-Step Visualization ==========");
        int[] arr2 = {5, 2, 8, 1, 9};
        System.out.println("Array: " + Arrays.toString(arr2));
        mergeSortVerbose(arr2, 0, arr2.length - 1, 0);
        System.out.println("Final : " + Arrays.toString(arr2));

        System.out.println("\n========== Merge Sort — Already Sorted ==========");
        int[] arr3 = {1, 2, 3, 4, 5, 6, 7, 8};
        mergeSort(arr3, 0, arr3.length - 1);
        System.out.println("Already sorted: " + Arrays.toString(arr3));

        System.out.println("\n========== Merge Sort — Reverse Sorted ==========");
        int[] arr4 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println("Original: " + Arrays.toString(arr4));
        mergeSort(arr4, 0, arr4.length - 1);
        System.out.println("Sorted  : " + Arrays.toString(arr4));

        System.out.println("\n========== Merge Sort — Strings ==========");
        String[] words = {"banana","apple","cherry","date","elderberry","fig"};
        System.out.println("Original: " + Arrays.toString(words));
        mergeSortStrings(words, 0, words.length - 1);
        System.out.println("Sorted  : " + Arrays.toString(words));

        System.out.println("\n========== Count Inversions ==========");
        int[][] testCases = {
            {2, 4, 1, 3, 5},
            {5, 4, 3, 2, 1},
            {1, 2, 3, 4, 5},
            {8, 3, 5, 1, 9, 2}
        };

        for (int[] tc : testCases) {
            int[] copy = Arrays.copyOf(tc, tc.length);
            long inv = countInversions(copy, 0, copy.length - 1);
            System.out.println("Array: " + Arrays.toString(tc) + " -> Inversions: " + inv);
        }

        System.out.println("\n========== Performance Test ==========");
        int n = 100_000;
        int[] large = new int[n];
        java.util.Random rng = new java.util.Random(42);
        for (int i = 0; i < n; i++) large[i] = rng.nextInt(1_000_000);
        long start = System.nanoTime();
        mergeSort(large, 0, large.length - 1);
        long elapsed = System.nanoTime() - start;
        System.out.printf("Merge Sort of %,d elements: %,d ms%n", n, elapsed / 1_000_000);
        System.out.println("Correctly sorted: " + isSorted(large));
    }

    static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++)
            if (arr[i] < arr[i-1]) return false;
        return true;
    }
}
