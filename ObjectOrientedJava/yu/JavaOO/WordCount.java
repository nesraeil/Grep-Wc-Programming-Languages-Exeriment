package yu.JavaOO;

import java.util.Map;

public interface WordCount {
    /**
     * Input: list of all words
     * Output: set of word-count pairs
     * Purpose: produce a set of unique words and the number of times they each appear
     */

    Map process(String[] allWords);
}
