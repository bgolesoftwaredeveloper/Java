/**
 * @file ZAlgorithmTest.java
 * @author Braiden Gole
 * @version 1.0
 * @date 2025-07-26
 *
 * @brief Unit tests for the ZAlgorithm class using JUnit 5.
 *
 * This test suite verifies the correctness of the Z Algorithm implementation by testing
 * various cases including single/multiple matches, no matches, edge cases, and correctness
 * of result positions.
 *
 * Dependencies:
 * - JUnit 5 (Ensure the JUnit 5 library is included in your build configuration)
 * - ZAlgorithm.java (class under test)
 */
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZAlgorithmTest {
    /**
     * Test case: Pattern occurs exactly once in the text.
     */
    @Test
    public void TestSingleMatch() {
        // Arrange.
        String text = "abxabcabcaby";
        String pattern = "abcaby";

        // Act.
        List<Integer> result = ZAlgorithm.search(pattern, text);

        // Assert.
        assertEquals(List.of(6), result, "Pattern should match at index 6");
    }

    /**
     * Test case: Pattern occurs multiple times in the text.
     */
    @Test
    public void TestMultipleMatches() {
        // Arrange
        String text = "ababababa";
        String pattern = "aba";

        // Act
        List<Integer> result = ZAlgorithm.search(pattern, text);

        // Assert
        assertEquals(List.of(0, 2, 4, 6), result, "Pattern should match at indices 0, 2, 4, 6");
    }

    /**
     * Test case: Pattern does not exist in the text.
     */
    @Test
    public void TestNoMatch() {
        // Arrange
        String text = "abcdefg";
        String pattern = "xyz";

        // Act
        List<Integer> result = ZAlgorithm.search(pattern, text);

        // Assert
        assertTrue(result.isEmpty(), "Pattern should not be found");
    }

    /**
     * Test case: Pattern is the same as the text.
     */
    @Test
    public void TestExactMatch() {
        // Arrange
        String text = "matchme";
        String pattern = "matchme";

        // Act
        List<Integer> result = ZAlgorithm.search(pattern, text);

        // Assert
        assertEquals(List.of(0), result, "Pattern should match entire text at index 0");
    }

    /**
     * Test case: Pattern is longer than the text.
     */
    @Test
    public void TestPatternLongerThanText() {
        // Arrange
        String text = "short";
        String pattern = "muchlongerpattern";

        // Act
        List<Integer> result = ZAlgorithm.search(pattern, text);

        // Assert
        assertTrue(result.isEmpty(), "Pattern longer than text should not match");
    }
}
