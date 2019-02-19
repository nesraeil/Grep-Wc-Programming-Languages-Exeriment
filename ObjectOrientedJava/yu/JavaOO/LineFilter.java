package yu.JavaOO;

public interface LineFilter {
    /**
     * Input: String
     * Output: new String
     * Purpose: filter out lines that don’t meet grep search criteria
     */

    String process(String input);
}
