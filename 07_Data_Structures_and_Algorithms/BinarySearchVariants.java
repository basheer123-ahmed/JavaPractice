/*
 * Program 92: Binary Search Variants
 *
 * Problem Statement:
 *   Implement multiple binary search variants:
 *   - Standard binary search
 *   - First/last occurrence
 *   - Count occurrences
 *   - Floor and ceiling
 *   - Search in rotated sorted array
 *   - Find peak element
 *
 * Sample Input:  [1, 2, 2, 2, 3, 4, 5]  target=2
 * Sample Output:
 *   First occurrence: 1
 *   Last  occurrence: 3
 *   Count           : 3
 *
 * Time Complexity:  O(log n) for all variants
 * Space Complexity: O(1)
 */
import java.util.Arrays;

public class BinarySearchVariants {

    // ============================================================
    // 1. Standard Binary Search — returns index or -1
    // ============================================================
    static int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if      (arr[mid] == target) return mid;
            else if (arr[mid] < target)  low  = mid + 1;
            else                         high = mid - 1;
        }
        return -1;
    }

    // ============================================================
    // 2. First Occurrence of target
    // ============================================================
    static int firstOccurrence(int[] arr, int target) {
        int low = 0, high = arr.length - 1, result = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                result = mid;
                high = mid - 1; // keep searching left
            } else if (arr[mid] < target) low  = mid + 1;
            else                          high = mid - 1;
        }
        return result;
    }

    // ============================================================
    // 3. Last Occurrence of target
    // ============================================================
    static int lastOccurrence(int[] arr, int target) {
        int low = 0, high = arr.length - 1, result = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                result = mid;
                low = mid + 1; // keep searching right
            } else if (arr[mid] < target) low  = mid + 1;
            else                          high = mid - 1;
        }
        return result;
    }

    // ============================================================
    // 4. Count Occurrences
    // ============================================================
    static int countOccurrences(int[] arr, int target) {
        int first = firstOccurrence(arr, target);
        if (first == -1) return 0;
        return lastOccurrence(arr, target) - first + 1;
    }

    // ============================================================
    // 5. Floor — largest element <= target
    // ============================================================
    static int floor(int[] arr, int target) {
        int low = 0, high = arr.length - 1, result = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] <= target) {
                result = arr[mid];
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    // ============================================================
    // 6. Ceiling — smallest element >= target
    // ============================================================
    static int ceiling(int[] arr, int target) {
        int low = 0, high = arr.length - 1, result = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= target) {
                result = arr[mid];
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

    // ============================================================
    // 7. Search in Rotated Sorted Array
    // e.g., [4,5,6,7,0,1,2] rotated version of [0,1,2,4,5,6,7]
    // ============================================================
    static int searchRotated(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) return mid;
            // Left half is sorted
            if (arr[low] <= arr[mid]) {
                if (arr[low] <= target && target < arr[mid]) high = mid - 1;
                else                                         low  = mid + 1;
            } else {
                // Right half is sorted
                if (arr[mid] < target && target <= arr[high]) low  = mid + 1;
                else                                          high = mid - 1;
            }
        }
        return -1;
    }

    // ============================================================
    // 8. Find Minimum in Rotated Sorted Array
    // ============================================================
    static int findMinRotated(int[] arr) {
        int low = 0, high = arr.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > arr[high]) low  = mid + 1;
            else                      high = mid;
        }
        return arr[low];
    }

    // ============================================================
    // 9. Find Peak Element (greater than its neighbors)
    // ============================================================
    static int findPeak(int[] arr) {
        int low = 0, high = arr.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] < arr[mid + 1]) low  = mid + 1;
            else                         high = mid;
        }
        return low; // returns index
    }

    // ============================================================
    // 10. Square Root using Binary Search
    // ============================================================
    static int sqrtBinarySearch(int n) {
        if (n < 2) return n;
        int low = 1, high = n / 2, result = 1;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (mid * mid == n) return (int) mid;
            if (mid * mid < n) { result = (int) mid; low  = (int)(mid + 1); }
            else                                       high = (int)(mid - 1);
        }
        return result; // floor of sqrt
    }

    public static void main(String[] args) {

        System.out.println("========== Standard Binary Search ==========");
        int[] sorted = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
        System.out.println("Array: " + Arrays.toString(sorted));
        System.out.println("Search  7: index " + binarySearch(sorted, 7));
        System.out.println("Search 13: index " + binarySearch(sorted, 13));
        System.out.println("Search  6: index " + binarySearch(sorted, 6));

        System.out.println("\n========== First/Last Occurrence & Count ==========");
        int[] withDups = {1, 2, 2, 2, 3, 3, 4, 5, 5, 5, 5};
        System.out.println("Array: " + Arrays.toString(withDups));
        int[] targets = {1, 2, 3, 5, 6};
        for (int t : targets) {
            System.out.printf("target=%d | first=%2d last=%2d count=%d%n",
                    t, firstOccurrence(withDups, t), lastOccurrence(withDups, t), countOccurrences(withDups, t));
        }

        System.out.println("\n========== Floor and Ceiling ==========");
        int[] arr = {1, 3, 5, 7, 9, 11};
        System.out.println("Array: " + Arrays.toString(arr));
        int[] queries = {0, 1, 4, 6, 7, 10, 11, 12};
        for (int q : queries) {
            System.out.printf("target=%2d | floor=%2d ceiling=%2d%n", q, floor(arr, q), ceiling(arr, q));
        }

        System.out.println("\n========== Search in Rotated Sorted Array ==========");
        int[][] rotated = {
            {4, 5, 6, 7, 0, 1, 2},
            {3, 4, 5, 1, 2},
            {1}
        };
        int[][] rotTargets = {{0,3,8}, {4,2,6}, {1,0}};
        for (int r = 0; r < rotated.length; r++) {
            System.out.println("Array: " + Arrays.toString(rotated[r]));
            for (int t : rotTargets[r]) {
                System.out.println("  Search " + t + ": index " + searchRotated(rotated[r], t));
            }
        }

        System.out.println("\n========== Minimum in Rotated Array ==========");
        int[][] rotArrays = {{4,5,6,7,0,1,2}, {3,4,5,1,2}, {1,2,3,4,5}};
        for (int[] a : rotArrays) {
            System.out.println("Array: " + Arrays.toString(a) + " | Min: " + findMinRotated(a));
        }

        System.out.println("\n========== Peak Element ==========");
        int[][] peakArrays = {{1,2,3,1}, {1,2,1,3,5,6,4}, {1,2,3}};
        for (int[] a : peakArrays) {
            int pi = findPeak(a);
            System.out.println("Array: " + Arrays.toString(a) + " | Peak index: " + pi + " value: " + a[pi]);
        }

        System.out.println("\n========== Square Root via Binary Search ==========");
        int[] sqInputs = {0, 1, 4, 8, 9, 16, 25, 36, 50, 100};
        for (int n : sqInputs) {
            System.out.printf("sqrt(%3d) = %d%n", n, sqrtBinarySearch(n));
        }
    }
}
