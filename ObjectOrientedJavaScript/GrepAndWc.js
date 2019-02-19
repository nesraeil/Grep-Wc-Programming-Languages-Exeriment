class lineFilter {
    constructor(filterBy) {
        this.filterBy = filterBy;
    }
    process(input) {
        let split = input.split("\n");
        let result = "";
        for (let i=0; i<split.length; i++) {
            if(split[i].includes(this.filterBy)) {
                result = result + split[i] + "\n";
            }
        }
        return result;
    }
}

class wordFind {
    process(input) {
        var copy = input.replace(/\n/g," ");
        copy = copy.trim();
        var result = copy.split(/\s+/);
        return result;
    }
}
/*let test = new wordFind().process("wiweuiuwfe eifnowefn \n sdfsdfsdf hello \n why dont you say hello\n");
console.log(test);*/

class nonAbcFilter {
    process(input) {
        var copy = []
        for (let i=0; i <input.length; i++) {
            copy[i] = input[i].replace(/[\W_]+/g,"");
        }
        copy = copy.filter(x => x != "");
        //let filtered = input.replace(/[\W_]+/g,"");
        return copy;
    }
}
/*var array = ['Apple', 'Banana', 'jnsojns.', 'wefwf{s,.']
let test = new nonAbcFilter().process(array);
console.log(test);*/

class caseConvert {
    process(input) {
        var copy = ""
        copy = input.toLowerCase();
        return copy;
    }
}
/*var array = "ooOOIOppOIOI";
let test = new caseConvert().process(array);
console.log(test);*/

class wordCount {
    process(input) {
        let myMap = new Map();
        for (let i=0; i <input.length; i++) {
            if(myMap.get(input[i]) === undefined) {
                myMap.set(input[i], 1);
            }
            else {
                myMap.set(input[i], myMap.get(input[i]) + 1);
            }
        }
        return myMap;
    }
}

/*var array = ['Apple', 'Banana', 'too', 'too', 'jnsJNIjns', 'wefwH', 'nate', 'nate'];
let test = new wordCount().process(array);
for (const [k, v] of test) {
    console.log(k, v)
}*/



class DocumentProcessor {
    constructor(build) {
        this.lf = build.lf;
        this.cc = build.cc;
        this.wf = build.wf;
        this.wc = build.wc;
        this.naf = build.naf;
    }

    process(contents) {

        if(typeof this.cc != "undefined" && typeof this.wf != "undefined" && typeof this.naf != "undefined" && typeof this.wc != "undefined" && typeof this.lf == "undefined") {
            //must be a call to wc
            //console.log("yay");
            return this.doWc(contents);
        }
        else if(typeof this.lf != "undefined" && typeof this.cc != "undefined" && typeof this.wf != "undefined" && typeof this.naf != "undefined" && typeof this.wc != "undefined") {
            //must be a call to grep | wc
            return this.doWc(this.lf.process(contents));
        }
        else if(typeof this.lf != "undefined" && typeof this.cc == "undefined" &&  typeof this.wf == "undefined" && typeof this.naf == "undefined" && typeof this.wc == "undefined") {
            //must be a call to grep
            return  this.lf.process(contents);
        }
        else throw new Error("Invalid input to document processor");
    }

    doWc(contentsOfFile) {
        let copy = contentsOfFile;
        copy = this.cc.process(contentsOfFile);
        let listOfContents = this.wf.process(copy);
        listOfContents = this.naf.process(listOfContents);
        let result = this.wc.process(listOfContents);
        return result;
    }

    static get DocumentProcessorBuilder() {
        class Builder {
            constructor() {

            }
            setLineFilterer(lf) {
                this.lf = lf;
                return this;
            }
            setCaseConverter(cc) {
                this.cc = cc;
                return this;
            }
            setWordFinder(wf) {
                this.wf = wf;
                return this;
            }
            setWordCounter(wc) {
                this.wc = wc;
                return this;
            }
            setNonABCFilter(naf) {
                this.naf = naf;
                return this;
            }
            build() {
                return new DocumentProcessor(this);
            }
        }
        return Builder;
    }
}
class GrepAndWc {
    constructor(args) {
        if(args.length === 0) throw new Error("No arguments were given");

        if(args.length === 2) {
            //If input is correct, input of this length must be wc
            let processor = new DocumentProcessor.DocumentProcessorBuilder()
                .setCaseConverter(new caseConvert())
                .setWordFinder(new wordFind())
                .setNonABCFilter(new nonAbcFilter())
                .setWordCounter(new wordCount())
                .build();
            let file = GrepAndWc.processFile(args[1]);
            let result = processor.process(file);
            for (const [k, v] of result) {
                console.log(k, v)
            }
        } else if(args.length === 3) {
            //If input is correct, input of this length must be grep
            let processor = new DocumentProcessor.DocumentProcessorBuilder()
                .setLineFilterer(new lineFilter(args[1]))
                .build();
            let file = GrepAndWc.processFile(args[2]);
            let result = processor.process(file);
            console.log(result);
        }
        else if(args.length == 5) {
            let processor = new DocumentProcessor.DocumentProcessorBuilder()
                .setLineFilterer(new lineFilter(args[1]))
                .setCaseConverter(new caseConvert())
                .setWordFinder(new wordFind())
                .setNonABCFilter(new nonAbcFilter())
                .setWordCounter(new wordCount())
                .build();
            let file = GrepAndWc.processFile(args[2]);
            let result = processor.process(file);
            for (const [k, v] of result) {
                console.log(k, v)
            }
        }
    }

    static processFile(filePath) {
        var fs = require('fs');
        try {
            var data = fs.readFileSync(filePath, 'utf8');
            return data;
        } catch(e) {
            throw new Error("File Input is invalid");
        }
    }
}

let run = new GrepAndWc(process.argv.slice(2));

