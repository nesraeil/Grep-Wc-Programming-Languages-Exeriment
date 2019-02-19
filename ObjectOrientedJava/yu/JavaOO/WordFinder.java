package yu.JavaOO;

public class WordFinder implements WordFind {
    @Override
    public String[] process(String allWords) {
        String copy = allWords.replace("\n", " ");
        String[] words = copy.split("\\s+");
        /*for(String s:line) {
            System.out.println(s);
        }*/
        return words;
    }
}
