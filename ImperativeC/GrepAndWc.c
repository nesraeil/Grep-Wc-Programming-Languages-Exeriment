#include <stdio.h>

#include<string.h>
#include <malloc.h>
#include <ctype.h>

typedef struct KeyValue{
    char *word;
    int freq;
}KeyValue;

//char processedFile;
int inputSize = 0;
int numOfWords = 0;
int uniqueWords = 0;

char* processFile(char *file)
{
    FILE *fptr;
    int count = 0;
    int ch;
    fptr=fopen(file,"r");
    while((ch = fgetc(fptr))!=EOF) {
        count++;
        //printf("%c%d ",ch, count);
    }
    count++;
    fclose(fptr);
    char *result = (char*)malloc(sizeof(char) * count);
    fptr=fopen(file,"r");
    int current = 0;
    while((ch = fgetc(fptr))!=EOF) {
        result[current++] = ch;
        //printf("%d",current);
    }
    result[current++] = '\0';
    fclose(fptr);
    inputSize = current;
    return result;
}

char* lineFilterer(char *input, char* filterBy) {
    char *temp = strtok(input, "\n");
    char *result = (char*)malloc(sizeof(char) * inputSize);
    int resultPtr = 0;
    while (temp != NULL)
    {
        if(strstr(temp, filterBy) != NULL) {
            for(int i = 0; i < strlen(temp); i++) {
                result[resultPtr++] = temp[i];
            }
            result[resultPtr++] = '\n';
        }
        temp = strtok(NULL, "\n");
    }
    result[resultPtr++] = '\0';
    free(input);
    return result;
}

void toLowercase(char *input) {
    strlwr(input);
}

char** nonAlphaFilter(char** wordList) {
    char **arr = (char**)malloc(sizeof(char*)*numOfWords);
    //wL iterates through the word list, that increments every loop no matter what.
    //If the filtered word turns out to be an empty string, i and numOfWords are both decremented
    //so that
    int wL = 0;
    for (int i = 0; i < numOfWords; i++)
    {
        char *temp = (char*)malloc(sizeof(char)*(1+ strlen(wordList[wL])));
        int index = 0;
        for (int j = 0; wordList[wL][j]!='\0'; j++)
        {
            char c = wordList[wL][j];
            if(isalnum(c)) {
                temp[index] = wordList[wL][j];
                index++;
            }
        }
        temp[index] = '\0';
        if(index == 0) {
            i--;
            numOfWords--;
            free(temp);
        }
        else{
            arr[i] = temp;
        }
        wL++;
    }
    free(wordList);
    return arr;
}



char** splitBySpaces(char* result) {
    char **arr = (char**)malloc(sizeof(char*));
    char *ptr = strtok(result, " ");
    int index = 0;
    while(ptr != NULL)
    {
        arr = (char**)realloc(arr, sizeof(char*)*(index+1));
        arr[index] = ptr;
        ptr = strtok(NULL, " ");
        index++;
    }
    numOfWords = index;
    return arr;
}


char** splitWords(char *input) {
    char *temp = strtok(input, "\n");
    char *newLineLess = (char*)malloc(sizeof(char) * (inputSize + 1));
    int resultPtr = 0;

    //To remove the \n.
    while (temp != NULL)
    {
        for(int i = 0; i < strlen(temp); i++) {
            newLineLess[resultPtr++] = temp[i]; //Copying char to new array
        }
        newLineLess[resultPtr++] = ' ';
        temp = strtok(NULL, "\n");
    }
    newLineLess[resultPtr++] = '\0';
    free(input);
    char** toReturn = splitBySpaces(newLineLess);
    //free(newLineLess);
    return toReturn;

}

KeyValue* wordCount(char** list) {
    struct KeyValue* arr = (struct KeyValue *)malloc(sizeof(struct KeyValue));
    int countInArray = 0;
    arr[0].word = NULL;
    arr[0].freq = 0;
    for (int i = 0; i < numOfWords; i++)
    {
        for(int j = 0; j < countInArray + 1; j++) {
            if(arr[j].word == NULL) {
                arr[j].word = list[i];
                arr[j].freq = 1;
                countInArray++;
                arr = (struct KeyValue *)realloc(arr, sizeof(struct KeyValue) * (countInArray + 1));
                arr[j+1].word = NULL;
                arr[j+1].freq = 0;
                break;
            }
            else if (strcmp(arr[j].word ,list[i]) == 0) {
                arr[j].freq += 1;
                break;
            }
        }
    }
    free(list);
    uniqueWords = countInArray;
    return arr;
}

int main(int argc, char *argv[]) {
    if(argc == 3 && strcmp(argv[1],"wc") == 0) {
        char *input = processFile(argv[2]);
        toLowercase(input);
        char** wordList = splitWords(input);
        char** nonAbc = nonAlphaFilter(wordList);
        struct KeyValue* result = wordCount(nonAbc);
        for(int i = 0; i < uniqueWords; i++) {
            printf("%s %d\n", result[i].word, result[i].freq);
        }
        free(result);
    }
    else if(argc == 4 && strcmp(argv[1],"grep") == 0) {
        char *input = processFile(argv[3]);
        char *result = lineFilterer(input, argv[2]);
        printf("%s", result);
        free(result);

    }
    else if((argc == 6) && (strcmp(argv[1],"grep") == 0) && (strcmp(argv[5],"wc") == 0) && (strcmp(argv[4],"|") == 0)) {
        char *input = processFile(argv[3]);
        char *filtered = lineFilterer(input, argv[2]);
        //printf("%s", filtered);
        toLowercase(filtered);
        char** wordList = splitWords(filtered);
        char** nonAbc = nonAlphaFilter(wordList);
        struct KeyValue* result = wordCount(nonAbc);
        for(int i = 0; i < uniqueWords; i++) {
            printf("%s %d\n", result[i].word, result[i].freq);
        }
        free(result);
    }
    return 0;
}