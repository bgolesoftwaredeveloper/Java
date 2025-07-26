/**
 * @file Main.java
 * @author Braiden Gole
 * @version 1.0
 * @date 2025-07-26
 *
 * @brief Demonstrates the usage of the Z Algorithm for pattern matching.
 *
 * This program serves as the entry point to test the Z Algorithm implementation.
 * It searches for all occurrences of a given pattern in a specified text using the
 * static `search()` method from the `ZAlgorithm` class and prints the resulting match positions.
 *
 * Features:
 * - Hardcoded example for demonstration purposes
 * - Invokes the Z Algorithm for linear-time pattern matching
 * - Prints all 0-based positions where the pattern occurs in the text
 *
 * Input:
 * - A predefined pattern and text string
 *
 * Output:
 * - List of starting indices (0-based) where the pattern occurs in the text
 *
 * Usage:
 * Compile and run the program to view the positions of pattern matches.
 */
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String text = "abxabcabcaby";
        String pattern = "abcaby";

        List<Integer> occurrences = ZAlgorithm.search(pattern, text);

        System.out.println("Pattern found at positions: " + occurrences);
    }
}