package yu.JavaOO;

import java.util.HashMap;
import java.util.Map;

public class DocumentProcessor {

    private LineFilterer lf;
    private CaseConverter cc;
    private WordFinder wf;
    private WordCounter wc;
    private NonABCFilter naf;

    public DocumentProcessor(LineFilterer lf, CaseConverter cc, WordFinder wf, WordCounter wc, NonABCFilter naf) {
        this.lf = lf;
        this.cc = cc;
        this.wf = wf;
        this.wc = wc;
        this.naf = naf;
    }

    /*public String processGrep(String contentsOfFile) {
        if(lf != null && cc == null && wf == null && naf == null && wc == null) {
            //must be a call to grep
            return lf.process(contentsOfFile);
        }
        else {
            throw new IllegalArgumentException("Document processor is invalid for grep");
        }
    }*/

    public Map process(String contentsOfFile) {
        if(cc != null && wf != null && naf != null && wc != null && lf == null) {
            //must be a call to wc
            return doWc(contentsOfFile);
        }
        else if(lf != null && cc != null && wf != null && naf != null && wc != null) {
            //must be a call to grep | wc
            return doWc(lf.process(contentsOfFile));
        }
        else if(lf != null && cc == null && wf == null && naf == null && wc == null) {
            //must be a call to grep
            HashMap<Integer, String> result = new HashMap<>();
            result.put(1, lf.process(contentsOfFile));
            return result;
        }
        else {
            throw new IllegalArgumentException("Document processor is invalid for wc");
        }
    }

    private Map doWc(String contentsOfFile) {
        String copy = contentsOfFile;
        copy = cc.process(contentsOfFile);
        String[] listOfContents = wf.process(copy);
        listOfContents = naf.process(listOfContents);
        Map<String, Integer> result = wc.process(listOfContents);
        return result;
    }

    public static class DocumentProcessorBuilder{
        private LineFilterer lf;
        private CaseConverter cc;
        private WordFinder wf;
        private WordCounter wc;
        private NonABCFilter naf;


        public DocumentProcessorBuilder setLineFilterer(LineFilterer lf) {
            this.lf = lf;
            return this;
        }

        public DocumentProcessorBuilder setCaseConverter(CaseConverter cc) {
            this.cc = cc;
            return this;
        }

        public DocumentProcessorBuilder setWordFinder(WordFinder wf) {
            this.wf = wf;
            return this;
        }

        public DocumentProcessorBuilder setWordCounter(WordCounter wc) {
            this.wc = wc;
            return this;
        }

        public DocumentProcessorBuilder setNonABCFilter(NonABCFilter naf) {
            this.naf = naf;
            return this;
        }

        public DocumentProcessor build() {
            return new DocumentProcessor(lf, cc, wf, wc, naf);
        }

    }
}
