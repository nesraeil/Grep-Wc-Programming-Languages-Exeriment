package yu.JavaOO;

import java.util.HashMap;
import java.util.Map;

public class WordCounter implements WordCount {
    @Override
    public Map<String, Integer> process(String[] allWords) {
        Map<String, Integer> result = new HashMap<String,Integer>();
        for(String s:allWords) {
            if(result.get(s) != null) {
                Integer i = result.get(s);
                i++;
                result.put(s, i);
            }
            else {
                result.put(s, 1);
            }
        }
        return result;
    }
}
