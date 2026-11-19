/*
 * Program 54: Word Frequency Counter
 * 
 * Problem Statement:
 *   Count the frequency of each word in a sentence using HashMap.
 *   Also find the most frequent and least frequent words.
 *   Handle punctuation, case-insensitivity, and multiple spaces.
 * 
 * Sample Input:  "the cat sat on the mat the cat"
 * Sample Output:
 *   Word Frequencies:
 *     the  -> 3
 *     cat  -> 2
 *     sat  -> 1
 *     on   -> 1
 *     mat  -> 1
 *   Most Frequent : "the" (3 times)
 *   Least Frequent: "sat" (1 time)   [or "on" or "mat"]
 * 
 * Explanation:
 *   - Split the sentence by whitespace.
 *   - Normalize to lowercase and strip punctuation.
 *   - Use a HashMap to count occurrences of each word.
 *   - Iterate the map to find max/min frequency entries.
 * 
 * Time Complexity: O(n) where n = total characters in the string
 * Space Complexity: O(k) where k = number of unique words
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WordFrequencyCounter {

    // =========================================================================
    // Count word frequencies using HashMap
    // =========================================================================
    public static Map<String, Integer> countWordFrequency(String sentence) {
        Map<String, Integer> frequencyMap = new HashMap<>();

        if (sentence == null || sentence.trim().isEmpty()) {
            return frequencyMap;
        }

        // Remove punctuation, convert to lowercase, split by whitespace
        String cleaned = sentence.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
        String[] words = cleaned.trim().split("\\s+");

        for (String word : words) {
            if (!word.isEmpty()) {
                frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
            }
        }

        return frequencyMap;
    }

    // =========================================================================
    // Find the most frequent word(s)
    // =========================================================================
    public static List<Map.Entry<String, Integer>> findMostFrequent(Map<String, Integer> freqMap) {
        if (freqMap.isEmpty()) return Collections.emptyList();

        int maxFreq = Collections.max(freqMap.values());
        List<Map.Entry<String, Integer>> result = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
            if (entry.getValue() == maxFreq) {
                result.add(entry);
            }
        }
        return result;
    }

    // =========================================================================
    // Find the least frequent word(s)
    // =========================================================================
    public static List<Map.Entry<String, Integer>> findLeastFrequent(Map<String, Integer> freqMap) {
        if (freqMap.isEmpty()) return Collections.emptyList();

        int minFreq = Collections.min(freqMap.values());
        List<Map.Entry<String, Integer>> result = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
            if (entry.getValue() == minFreq) {
                result.add(entry);
            }
        }
        return result;
    }

    // =========================================================================
    // Sort frequency map by value (descending) then by key (alphabetical)
    // =========================================================================
    public static LinkedHashMap<String, Integer> sortByFrequency(Map<String, Integer> freqMap) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freqMap.entrySet());

        entries.sort((a, b) -> {
            int cmp = b.getValue().compareTo(a.getValue()); // descending by frequency
            if (cmp != 0) return cmp;
            return a.getKey().compareTo(b.getKey());        // alphabetical for ties
        });

        LinkedHashMap<String, Integer> sorted = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entries) {
            sorted.put(entry.getKey(), entry.getValue());
        }
        return sorted;
    }

    // =========================================================================
    // Sort frequency map alphabetically by word
    // =========================================================================
    public static TreeMap<String, Integer> sortAlphabetically(Map<String, Integer> freqMap) {
        return new TreeMap<>(freqMap);
    }

    // =========================================================================
    // Display frequency as a simple bar chart
    // =========================================================================
    public static void displayBarChart(Map<String, Integer> freqMap) {
        if (freqMap.isEmpty()) {
            System.out.println("  (empty)");
            return;
        }

        int maxKeyLen = freqMap.keySet().stream().mapToInt(String::length).max().orElse(0);

        for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
            String bar = "█".repeat(entry.getValue());
            System.out.printf("  %-" + maxKeyLen + "s | %-20s (%d)%n",
                    entry.getKey(), bar, entry.getValue());
        }
    }

    // =========================================================================
    // Count unique words
    // =========================================================================
    public static int countUniqueWords(Map<String, Integer> freqMap) {
        return freqMap.size();
    }

    // =========================================================================
    // Count total words
    // =========================================================================
    public static int countTotalWords(Map<String, Integer> freqMap) {
        return freqMap.values().stream().mapToInt(Integer::intValue).sum();
    }

    // =========================================================================
    // Main
    // =========================================================================
    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  Word Frequency Counter                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        String[] testSentences = {
            "the cat sat on the mat the cat",
            "To be or not to be that is the question",
            "Java is great and Java is powerful and Java is everywhere",
            "Hello hello HELLO hElLo",
            "one",
            "",
            "Buffalo buffalo Buffalo buffalo buffalo buffalo Buffalo buffalo"
        };

        for (String sentence : testSentences) {
            System.out.println("Input: \"" + sentence + "\"");
            System.out.println("-".repeat(60));

            Map<String, Integer> freqMap = countWordFrequency(sentence);

            if (freqMap.isEmpty()) {
                System.out.println("  (no words found)");
                System.out.println();
                continue;
            }

            // Sort by frequency (descending)
            LinkedHashMap<String, Integer> sorted = sortByFrequency(freqMap);

            // Display counts
            System.out.println("  Word Frequencies (sorted by count):");
            for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
                System.out.printf("    %-15s -> %d%n", "\"" + entry.getKey() + "\"", entry.getValue());
            }

            // Statistics
            System.out.println();
            System.out.println("  Total words : " + countTotalWords(freqMap));
            System.out.println("  Unique words: " + countUniqueWords(freqMap));

            // Most frequent
            List<Map.Entry<String, Integer>> most = findMostFrequent(freqMap);
            System.out.print("  Most frequent: ");
            for (int i = 0; i < most.size(); i++) {
                System.out.print("\"" + most.get(i).getKey() + "\" (" + most.get(i).getValue() + ")");
                if (i < most.size() - 1) System.out.print(", ");
            }
            System.out.println();

            // Least frequent
            List<Map.Entry<String, Integer>> least = findLeastFrequent(freqMap);
            System.out.print("  Least frequent: ");
            for (int i = 0; i < least.size(); i++) {
                System.out.print("\"" + least.get(i).getKey() + "\" (" + least.get(i).getValue() + ")");
                if (i < least.size() - 1) System.out.print(", ");
            }
            System.out.println();

            // Bar chart
            System.out.println();
            System.out.println("  Bar Chart:");
            displayBarChart(sorted);

            System.out.println();
        }

        // =====================================================================
        // Alphabetical sort demo
        // =====================================================================
        System.out.println("=== Alphabetical Sort Demo ===");
        String demoSentence = "Java is great and Java is powerful and Java is everywhere";
        Map<String, Integer> demoFreq = countWordFrequency(demoSentence);
        TreeMap<String, Integer> alphabetical = sortAlphabetically(demoFreq);
        System.out.println("Input: \"" + demoSentence + "\"");
        for (Map.Entry<String, Integer> entry : alphabetical.entrySet()) {
            System.out.printf("  %-15s -> %d%n", entry.getKey(), entry.getValue());
        }
    }
}
