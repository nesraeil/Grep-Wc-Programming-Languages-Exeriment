package yu.JavaOO;


public class NonABCFilterer implements NonABCFilter {
    @Override
    public String[] process(String[] words) {
        String[] copy = new String[words.length];
        int count = 0;
        for(int i = 0; i < words.length; i++) {
            String s = words[i].replaceAll("[^A-Za-z0-9]", "");
            if(!s.trim().equals("")) {
                copy[count] = s;
                count++;
            }
        }
        String[] result = new String[count];//Second array happens in case any null values in the copy array
        for(int i = 0; i < result.length; i++){
            result[i] = copy[i];
        }
        return result;
    }
}
