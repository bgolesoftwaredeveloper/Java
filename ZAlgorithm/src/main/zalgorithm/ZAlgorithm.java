/**
 * @file ZAlgorithm.java
 * @author Braiden Gole
 * @version 1.0
 * @date 2025-07-26
 *
 * @brief Java implementation of the Z Algorithm for efficient pattern matching in strings.
 *
 * This program implements the Z Algorithm, which constructs a Z-array that allows for fast
 * pattern matching in linear time. The Z-array at index i stores the length of the longest substring
 * starting at i that matches the prefix of the combined string (pattern + '$' + text).
 *
 * Key Features:
 * - Linear time complexity: O(n)
 * - Efficient substring matching
 * - Supports multiple occurrences of a pattern
 *
 * Input: Two strings — a pattern and a text
 * Output: List of starting indices (0-based) where the pattern appears in the text
 *
 * Limitations:
 * - The special separator character '$' must not appear in the input pattern or text
 * - Only exact matches are reported; no support for wildcards or approximate matches
 */
package zalgorithm;

import java.util.ArrayList;
import java.util.List;

public class ZAlgorithm {

    /**
     * Computes the Z-array for a given string.
     *
     * The Z-array represents the length of the longest substring starting at each position
     * that matches the prefix of the entire string. It is used in efficient pattern matching.
     *
     * @param input The input string, typically of the form "pattern$text"
     * @return An integer array Z where Z[i] is the length of the longest substring
     *         starting at i that matches the prefix of the input string
     */
    public static int[] computeZArray(String input) {
        int length = input.length();
        int[] zArray = new int[length];

        int left = 0;
        int right = 0;

        for (int index = 0; index < length; index++) {
            // If index is within the current [left, right] window, reuse previous values.
            if (index <= right) {
                zArray[index] = Math.min(right - index + 1, zArray[index - left]);
            }

            // Attempt to extend the Z-box from the current index.
            while (index + zArray[index] < length && input.charAt(zArray[index]) == input.charAt(index + zArray[index]))
            {
                zArray[index]++;
            }

            // Update [left, right] boundaries if a longer Z-box is found.
            if (index + zArray[index] - 1 > right) {
                left = index;
                right = index + zArray[index] - 1;
            }
        }

        return zArray;
    }

    /**
     * Searches for all occurrences of a pattern in the given text using the Z Algorithm.
     *
     * Concatenates the pattern and text with a separator, computes the Z-array, and
     * returns all starting positions in the text where the pattern occurs.
     *
     * @param pattern The pattern string to search for
     * @param text The target text in which to search for the pattern
     * @return A list of integer indices (0-based) where the pattern starts in the text
     */
    public static List<Integer> search(String pattern, String text) {
        String concat = pattern + "$" + text;

        int[] zArray = computeZArray(concat);

        List<Integer> result = new ArrayList<>();

        int patternLength = pattern.length();

        for (int index = patternLength - 1; index < zArray.length; index++) {
            if (zArray[index] == patternLength) {
                result.add(index - patternLength - 1);
            }
        }

        return result;
    }
}
