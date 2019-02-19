import sys
import re
import pprint
from functools import reduce


def processFile(input):
    with open(input, "r") as contents:
        data = contents.read()

        return data;

def mapper(map, key):
    x = map.copy()
    if x.get(key) is None:
        x[key] = 1
    else:
        x[key] = x.get(key) + 1
    return x


def run():
  argsLength = len(sys.argv) - 1
  if argsLength == 2 and sys.argv[1] == "wc":
      result = reduce(lambda x, y: mapper(x,y),list(filter(lambda x: x != "", map(lambda s: re.compile('[\W_]+').sub('', s), " ".join(map(lambda x: x.lower(), processFile(sys.argv[2]).split("\n"))).strip().split()))), {})
      pprint.pprint(result)

  elif argsLength == 3 and sys.argv[1] == "grep":
      result = "\n".join(filter(lambda s: sys.argv[2] in s, processFile(sys.argv[3]).split("\n")))
      print(result)

  elif argsLength == 5 and sys.argv[1] == "grep" and  sys.argv[5] == "wc" and sys.argv[4] == "|":
      result = reduce(lambda x, y: mapper(x,y),list(filter(lambda x: x != "", map(lambda s: re.compile('[\W_]+').sub('', s), " ".join(map(lambda x: x.lower(), "\n".join(filter(lambda s: sys.argv[2] in s, processFile(sys.argv[3]).split("\n"))).split("\n"))).strip().split()))), {})
      pprint.pprint(result)

run()