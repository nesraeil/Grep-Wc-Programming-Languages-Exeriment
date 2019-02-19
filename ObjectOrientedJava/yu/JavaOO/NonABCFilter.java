package yu.JavaOO;

public interface NonABCFilter {
    /**
     * Input: List of words
     * Output: list of words
     * Purpose:  strip out all non-alphabetic characters. Eliminate any "words" that are just white space.
     */

    String[] process(String[] words);
}
