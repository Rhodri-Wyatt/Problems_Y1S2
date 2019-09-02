#include <stdio.h>
#include <stdlib.h>

//COME BACK AND FIX Manchester United
//split in redacted file by '\n' not spaces



/*
Function takes in the array, fileName and whether the array is for the input of text or not.
Fills the array with the words from the file.

@param inputArray: the array to be filled with contents of the file
@param fileName: file name of the file to be opened
@param isInput: whether the function is being called for the inputted text array or the redacted words array
*/
int readFile(char inputArray[50000][20], char* fileName, int isInput){
	//create a new pointer to a file
	FILE *fp;
	//fill file with data from the file with name stored in fileName
	fp = fopen(fileName, "r");

	//if the file is null
	if (fp == NULL){
	//exit
		perror("File does not exist in directory. Make sure file is in same directory as code\n");
		exit(0);
	}
	printf("File is not null.\n");

	//populate array with contents of the file
   int elements = 0;
   int indexOfElement = 0;
   
	//loop through until the end of the file
   char character;
   while ((character = getc(fp)) != EOF) {
   	//if the character is a comma	
		if (character == ' '){
			//end string by adding a terminating character
			inputArray[elements][indexOfElement] = '\0';
			//move to next element
			elements++;
			//set index to 0 for next name to be added
			indexOfElement = 0;
       }
      //if the character is '\n' and new not new input
      else if (character == '\n' && (isInput == 0)){
     		
			//end string by adding a terminating character
			inputArray[elements][indexOfElement] = '\0';
			
			 //move to next element
			elements++;
			
			//set index to 0 for next name to be added
			indexOfElement = 0;
			//add new character to array
			inputArray[elements][indexOfElement] = '\n';
			//move to next element
			elements++;
		} else {
           //Add character to name
           inputArray[elements][indexOfElement] = character;
           //move to next character in the array
           indexOfElement++;
       }
   }

	//put terminating character at end of the array
   if (indexOfElement > 0) {
     elements++;
     inputArray[elements][indexOfElement] = '\0';
   }
   //close file
   fclose(fp);

	return elements;

}

/*
Function calculates the length of a given string

@param string: The string that's length is to be found
@return index: The length of the string.
*/
int getStringLength(char* string){
	int index = 0;
	char character = string[index];
	//Loop through the array until the terminating character is found
	while(character != 0){
		index++;
		character = string[index];
	}
	return index;
}

/**
 * Converts a character to lower case if it is upper case
 *
 * @param: inputChar: The character to convert to lower case
 * @return : The lowercase character
 */
char convertToLowerCase(char inputChar){
    //If the character is uppercase, make it lower
    if(65 <= inputChar && inputChar <= 90){
        return inputChar + 32;
    }
    //Otherwise, return the original character
    else{
        return inputChar;
    }
}


/*
Function checks if two strings are equal or not.
This is a reimplementation of the strcmp for my program.

@param string: The string that's length is to be found
@return counter: 0 if the same, -1 if not the same, counter if the same but with punctuation at the end.
*/
int checkIfEqual(char string1[20], char string2[20]){
	int counter=0;
	//while they are the same character and not equal to max limit of string
	while((convertToLowerCase(string1[counter])==convertToLowerCase(string2[counter])) && (counter < 20)){
		//if string one is terminating character then break out of loop
		if(string1[counter]=='\0'){
			//end of both strings
			break;
		}  
		//increment counter
		counter++;
	}	
	
	//if string they are the same
	if(string1[counter]=='\0' && string2[counter]=='\0' && string1[counter]!= '\n'){
		//the same string
		return 0;
	}
	//if the end of string 2 and the last char of string 1 is a punctuation
	else if((string1[counter] == '\0') && (string2[counter] == '.' || string2[counter] == '!' || string2[counter] == '?' || string2[counter] == ',')){
		//return counter if the same but punctuation
		//printf("Punctuation");
		return counter;
	}
	else{
		//else return not the same
		return -1;
	}

}



/*
Function loops through all the words in the redact word list, it loops through every element in the inputted text array
and replaces it with * if they match.
*/

void redactWords(char inputtedText[50000][20], int sizeOfInputtedText, char redactedWords[50000][20], int sizeOfRedactedWords){
	int redactWordCount;
	//loop through all elements in the redacted words array
	for (redactWordCount = 0; redactWordCount < sizeOfRedactedWords; redactWordCount++){
		//printf("Word currently being redacted = %s\n", redactedWords[redactWordCount]);
	
		//loop through all the elements in the inputted text
		int inputtedTextCount;
		for (inputtedTextCount = 0; inputtedTextCount < sizeOfInputtedText; inputtedTextCount++){
			//check if equal
			int isEquals = checkIfEqual(redactedWords[redactWordCount], inputtedText[inputtedTextCount]);
		
			//if equal
			if (isEquals == 0){
				int counter = 0;
				//change the word so each character is replaced with *
				while(inputtedText[inputtedTextCount][counter] != '\0'){
					//check to see if a space.
					inputtedText[inputtedTextCount][counter] = '*';
					counter++;
				}
			}
			
			//if equals but with punctuation
			else if (isEquals > 0){
				int counter = 0;
				//loop up to punctuation and replace every character with an * 
				while(counter < isEquals){
					inputtedText[inputtedTextCount][counter] = '*';
					counter++;
				}
			}
		}
	}
}


/**
 *Write the names to a file
 *
 * @param fileName: The name of the output file
 * @param namesArray: The array of all names
 * @param size: The size of the array
 */
void writeToFile(char array[50000][20], char* outputFileName, int size){
	//The file to be written to
	FILE * myFile = fopen(outputFileName, "w");

	//If the file cannot be opened
	if(myFile == NULL){
		printf("Output File Not Found\n");
		exit(1);
	}

	int counter;
	//loop through every element in the array
	for(counter = 0; counter < size; counter++){
		//if not the last element
		if(array[counter][0] == '\n' || array[counter][0] == '\r'){
			//print the string from the array separated by a comma.
			fprintf(myFile, "%c", '\n');
		}
		//Don't include the comma on the last name
		else{
			fprintf(myFile, "%s ", array[counter]);
		}
	}
	//close the file
	fclose(myFile);
}


/*
Function prints out all the values in the array

@param namesArray[][20]: stores the array of names
@param size: the number of elements in the array
*/
void printArray(char array[50000][20], int size) {
	//loop through every element in the array and print it out
	for (int i=0; i < size; i++){
		if (array[i][0] == '\n' || array[i][0] == '\r'){
			printf("\n");
		} else {
		printf("%s ", array[i]);
		}
	}
}
	


int main(int argc, char *argv[]){
//Get arguments from the array
//the first argument is the file that is to be redacted from
//the second argument is the output file
	char* inputFileName = "";
	char* redactFileName = "";
 	char* outputFileName = "";
 	
	if(argc == 3){	
		inputFileName = argv[1];
		redactFileName = argv[2];

		printf("Input from file : %s\n", inputFileName);
		printf("Redact words from file : %s\n", redactFileName);

		outputFileName = "output.txt";

	}
	else if(argc == 4){
		inputFileName = argv[1];
		redactFileName = argv[2];
		outputFileName = argv[3];
		
		printf("Input from file : %s\n", inputFileName);
		printf("Redact words from file : %s\n", redactFileName);
		printf("Output redacted text to file : %s\n", outputFileName);
	}
	else{
	    printf("Invalid Args Input\ninputFileName redactFileName outputFileName\n");
	    exit(0);
	}

	//create up the array
	char inputtedText[50000][20];
	int sizeOfInputtedText;
	sizeOfInputtedText = readFile(inputtedText, inputFileName, 1);
	printf("Inputted text loaded into array\n");
	
	//create redacted words file
	char redactedWords[50000][20];
	int sizeOfRedactedWords;
	sizeOfRedactedWords = readFile(redactedWords, redactFileName, 0);
	printf("Redacted words loaded into array\n");

	
	//These prints were for testing
	//printf("Inputted Text: \n");
	//printArray(inputtedText, sizeOfInputtedText);
	//printf("\nRedacted Words: \n");
	//printArray(redactedWords, sizeOfRedactedWords);
	
	//call the redact words function
	redactWords(inputtedText, sizeOfInputtedText, redactedWords, sizeOfRedactedWords);
	
	//call the write to file function. We use the inputted text array as this is what has been changed.
	writeToFile(inputtedText, outputFileName , sizeOfInputtedText);
	
	printf("Text sent to file: \n");
	printArray(inputtedText, sizeOfInputtedText);

	return 0;
}
