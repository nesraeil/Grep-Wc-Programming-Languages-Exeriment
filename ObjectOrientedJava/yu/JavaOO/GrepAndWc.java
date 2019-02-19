package yu.JavaOO;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.Map;
import java.util.Scanner;

public class GrepAndWc {

   /* public static void main(String[] args) {
        GrepAndWc newApp = new GrepAndWc(args);
    }*/

    @SuppressWarnings("unchecked")
    public GrepAndWc(String[] args) {
        if(args.length == 0) throw new IllegalArgumentException("No arguments found");
        DocumentProcessor processor;
        if(args.length == 2) {
            //If input is correct, input of this length must be wc
            processor = buildWc(args);
            String file = processFile(args[1]);
            Map<String, Integer> result = processor.process(file);
            for(String s : result.keySet()) {
                System.out.println(s + " " + result.get(s));
            }
        }
        else if(args.length == 3) {
            //If input is correct, input of this length must be grep
            processor = buildGrep(args);
            String file = processFile(args[2]);
            Map<Integer, String> result = processor.process(file);
            System.out.println(result.get(1));

        }
        else if(args.length == 5) {
            //If input is correct, input of this length must be grep | wc
            processor = buildGrepWc(args);
            String file = processFile(args[2]);
            //Because processor is a grep | wc DocumentProcessor, processWc will know how to handle it
            Map<String, Integer> result = processor.process(file);
            for(String s : result.keySet()) {
                System.out.println(s + " " + result.get(s));
            }
        }
    }

    public String processFile(String filePath) {
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
        //System.out.println(result);
        return result;
    }

    public DocumentProcessor buildWc(String[] args) {
        if(!args[0].equals("wc")) {
            throw new IllegalArgumentException("Input is incorrect");
        }
        return new DocumentProcessor.DocumentProcessorBuilder()
                .setCaseConverter(new CaseConverter())
                .setWordFinder(new WordFinder())
                .setNonABCFilter(new NonABCFilterer())
                .setWordCounter(new WordCounter())
                .build();
    }

    public DocumentProcessor buildGrep(String[] args) {
        if(!args[0].equals("grep")) {
            System.out.println(args[0]);
            throw new IllegalArgumentException("Input is incorrect");
        }
        return new DocumentProcessor.DocumentProcessorBuilder()
                .setLineFilterer(new LineFilterer(args[1]))
                .build();
    }

    public DocumentProcessor buildGrepWc(String[] args) {
        if(!args[0].equals("grep") || !args[4].equals("wc") || !args[3].equals("|")) {
            throw new IllegalArgumentException("Input is incorrect");
        }
        return new DocumentProcessor.DocumentProcessorBuilder()
                .setLineFilterer(new LineFilterer(args[1]))
                .setCaseConverter(new CaseConverter())
                .setWordFinder(new WordFinder())
                .setNonABCFilter(new NonABCFilterer())
                .setWordCounter(new WordCounter())
                .build();
    }
}
