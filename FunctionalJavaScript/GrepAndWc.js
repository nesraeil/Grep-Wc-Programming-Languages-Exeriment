class GrepAndWc {
    constructor(args) {

        if(args.length === 0) throw new Error("No arguments were given");

        if(args.length === 2) {
            let result = this.processFile(args[1]).split("\n")
                .map(s => s.toLowerCase())
                .map(s => s + " ")
                .reduce(function(previous, current){return previous + current}, '').trim().split(/\s+/)
                .map(s => s.split('').filter(c => !c.match(/[\W_]+/))
                    .reduce(function(previous, current){return previous + current}, ''))
                .filter(s => s !== "")
                .reduce(function(map, toCount) {map[toCount] = ++map[toCount] || 1;return map}, {});
            console.log(result);
        } else if(args.length === 3) {
            let filtered = this.processFile(args[2]).split("\n")
                .filter(s => s.includes(args[1]))
                .map(s => s.concat("\n"))
                .reduce(function(previous, current){return previous + current}, '');
            console.log(filtered);
        }
        else if(args.length == 5) {
            let result = this.processFile(args[2]).split("\n")
                .filter(s => s.includes(args[1]))
                .map(s => s.concat("\n"))
                .reduce(function(previous, current){return previous + current}, '').split("\n")
                .map(s => s.toLowerCase())
                .map(s => s + " ")
                .reduce(function(previous, current){return previous + current}, '').trim().split(/\s+/)
                .map(s => s.split('')
                    .filter(c => !c
                        .match(/[\W_]+/))
                    .reduce(function(previous, current){return previous + current}, ''))
                .filter(s => s !== "")
                .reduce(function(map, toCount) {map[toCount] = ++map[toCount] || 1;return map}, {});
            console.log(result);
        }
    }



    processFile(filePath) {
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