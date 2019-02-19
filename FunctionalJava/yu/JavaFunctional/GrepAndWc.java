package yu.JavaFunctional;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.Arrays.stream;

public class GrepAndWc {
    /**
     * wc C:\Users\PC\Documents\YU_Git\NathanielEsraeilian\PL\FinalProject\test.txt
     * grep this C:\Users\PC\Documents\YU_Git\NathanielEsraeilian\PL\FinalProject\test.txt
     * grep this C:\Users\PC\Documents\YU_Git\NathanielEsraeilian\PL\FinalProject\test.txt | wc
     */

    public static void main(String[] args) {

        if(getCmd(args).equals("grep")) {
            String filtered = stream(processFile(args[2]).split("\n"))
                    .filter(s -> s.contains(args[1]))
                    .map(s -> s.concat("\n"))
                    .reduce("", (a,b) -> a + b);
            System.out.println(filtered);

        } else if(getCmd(args).equals("wc")) {
            Map<String, Integer> result = stream(stream(processFile(args[1]).split("\n"))
                    .map(s -> s.toLowerCase())
                    .map(s -> s + " ")
                    .reduce("", (a, b) -> a + b).split("\\s+"))
                    .map(s -> removeNonAbc(s))
                    .filter(x -> !x.equals(""))
                    .collect(Collectors.toMap(Function.identity() , s -> 1, (s1, s2) ->  s1 + 1));
            for(String s : result.keySet()) { System.out.println(s + " " + result.get(s)); }


        } else if(getCmd(args).equals("grepwc")) {
            Map<String, Integer> result = stream(stream(processFile(args[2]).split("\n"))
                    .filter(s -> s.contains(args[1]))
                    .map(s -> s.concat("\n"))
                    .map(s -> s.toLowerCase())
                    .map(s -> s + " ")
                    .reduce("", (a, b) -> a + b).split("\\s+"))
                    .map(s -> removeNonAbc(s))
                    .filter(x -> !x.equals(""))
                    .collect(Collectors.toMap(Function.identity() , s -> 1, (s1, s2) ->  s1 + 1));
            for(String s : result.keySet()) System.out.println(s + " " + result.get(s));
        }
    }

    public static String processFile(String filePath) {
        String result = "";
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                result += scanner.nextLine();
                result += "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getCmd(String[] args) {
        if(args.length == 5 && args[0].equals("grep") && args[3].equals("|") && args[4].equals("wc")) {
            return "grepwc";
        }
        else if(args.length == 3 && args[0].equals("grep")) {
            return "grep";
        }
        else if(args.length == 2 && args[0].equals("wc")) {
            return "wc";
        }
        else{
            throw new IllegalArgumentException("Input command is incorrect");
        }
    }

    public static String removeNonAbc(String s) {
        String converted = s.chars().mapToObj(c -> (char) c).filter(c -> Character.isLetterOrDigit(c)).map(c -> c.toString()).reduce("", (a,b) -> a + b);
        return converted;
    }

    /*public static Map<String, Integer> count(List<String> list) {
        //Map<String, Integer> result = new HashMap<String, Integer>();
        //List test = list.stream().map(s -> result.put(s, numberInMap(result, s))).collect(toList());
        Map<String, Integer> result = list.stream().collect(Collectors.toMap(Function.identity() , s -> 1, (s1, s2) ->  s1 + 1));
        return result;
    }*/

    /*public static List<String> abcFilter(List<String> list) {
        List<String> filtered = list.stream().map(s -> removeNonAbc(s)).collect(toList());
        return filtered;
    }*/

    /*public static List<String> findWords(String input) {
        List<String> found = Arrays.asList(stream(input.split("\n")).map(s -> s + " ").reduce("", (a, b) -> a + b).split(" "));
        //for(String s: found) System.out.println(s);
        return found;
    }*/


    /*public static String convertCase(String input) {
        String converted = stream(input.split("")).map(s -> s.toLowerCase()).reduce("", (a,b) -> a + b);
        //System.out.println(converted);
        return converted;
    }*/
    /*public static String lineFilter(String input, String filterBy) {
        String[] line = input.split("\n");
        String filtered = stream(line).filter(s -> s.contains(filterBy)).map(s -> s.concat("\n")).reduce("", (a,b) -> a + b);
        //System.out.println(filtered);
        return filtered;
    }*/
}


