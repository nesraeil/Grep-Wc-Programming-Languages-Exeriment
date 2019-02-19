import sys
import re
import pprint

class LineFilter:
    def __init__(self, filterby):
        self.filterby = filterby

    def process(self, input):

        split = input.split("\n")
        result = ""
        for x in split:
            if self.filterby in x:
                result = result + x + "\n"
        return result


class CaseConvert:
  def process(self, input):
        result = input.lower()
        return result


class WordFind:
    def process(self, input):
        result = input.replace('\n', " ")
        result = result.strip().split()

        return result


class NonAbcFilter:
    def process(self, input):
        copy = list(input)
        remove = re.compile('[\W_]+')
        result = [remove.sub('', x) for x in copy]
        result = (filter(lambda x: x != "",result))
        return result


class WordCount:
    def process(self, input):
        map = dict()
        for x in input:
            if x in map:
                map[x] = map.get(x) + 1

            else:
                map[x] = 1
        return map;


class DocumentProcessor:
    def __init__(self, build):
        self.lf = build.lf;
        self.cc = build.cc;
        self.wf = build.wf;
        self.wc = build.wc;
        self.naf = build.naf;

    def process(self, file):
        if self.cc != None and self.wf != None and self.naf != None and self.wc != None and self.lf == None:
            copy = self.cc().process(file)
            listOfContents = self.wf().process(copy)
            new = self.naf().process(listOfContents)
            result = self.wc().process(new)
            return result
        elif self.cc != None and self.wf != None and self.naf != None and self.wc != None and self.lf != None:
            filtered = self.lf.process(file)
            copy = self.cc().process(filtered)
            listOfContents = self.wf().process(copy)
            new = self.naf().process(listOfContents)
            result = self.wc().process(new)
            return result
        elif self.cc == None and self.wf == None and self.naf == None and self.wc == None and self.lf != None:
            return self.lf.process(file)

    class DocumentProcessorBuilder:
        lf = None
        cc = None
        wf = None
        wc = None
        naf = None

        def setLineFilterer(self, lf):
            self.lf = lf;
            return self;

        def setCaseConverter(self, cc):
            self.cc = cc;
            return self;

        def setWordFinder(self, wf):
            self.wf = wf;
            return self;

        def setWordCounter(self, wc):
            self.wc = wc;
            return self;

        def setNonABCFilter(self, naf):
            self.naf = naf;
            return self;

        def build(self):
            return DocumentProcessor(self)


def processFile(input):
    with open(input, "r") as contents:
        data = contents.read()
        return data;


def run():
  argsLength = len(sys.argv) - 1
  if argsLength == 2 and sys.argv[1] == "wc":

      processor = DocumentProcessor\
          .DocumentProcessorBuilder()\
          .setCaseConverter(CaseConvert)\
          .setWordFinder(WordFind)\
          .setNonABCFilter(NonAbcFilter)\
          .setWordCounter(WordCount)\
          .build()
      file = processFile(sys.argv[2])
      result = processor.process(file)
      pprint.pprint(result)
  elif argsLength == 3 and sys.argv[1] == "grep":
      processor = DocumentProcessor.DocumentProcessorBuilder().setLineFilterer(LineFilter(sys.argv[2])).build()
      file = processFile(sys.argv[3])
      result = processor.process(file)
      print(result)
  elif argsLength == 5 and sys.argv[1] == "grep" and  sys.argv[5] == "wc" and sys.argv[4] == "|":
      processor = DocumentProcessor \
          .DocumentProcessorBuilder()\
          .setLineFilterer(LineFilter(sys.argv[2])) \
          .setCaseConverter(CaseConvert) \
          .setWordFinder(WordFind) \
          .setNonABCFilter(NonAbcFilter) \
          .setWordCounter(WordCount) \
          .build()
      file = processFile(sys.argv[3])
      result = processor.process(file)
      pprint.pprint(result)

run()