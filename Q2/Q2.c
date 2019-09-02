#include <stdio.h>
#include <stdlib.h>
#include <string.h>


//NOTE: I got inspiration for the quicksort elements of this code from https://www.geeksforgeeks.org/quick-sort/


/*
Function reads in names from the file and stores it in the array.

@param namesArray: the array to store all the names
@param inputFileName: the file name of the file to be accessed
@return elements: the number of elements in the array.
*/
int readFromFile(char namesArray[50000][20], char* inputFileName){
	//create a pointer to a file
	FILE *fp;
	//fill file with data from the file with name stored in inputFileName
	fp = fopen(inputFileName, "r");

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
		if (character == ','){
			//move to next element
			elements++;
			//end string by adding a terminating character
			namesArray[elements][indexOfElement] = '\0';
			//set index to 0 for next name to be added
			indexOfElement = 0;
       } else {
           //Add character to name
           namesArray[elements][indexOfElement] = character;
           //move to next character in the array
           indexOfElement++;
       }
   }
   //put terminating character at end of the array
   if (indexOfElement > 0) {
     elements++;
     namesArray[elements][indexOfElement] = '\0';
   }
   printf("%d\n", elements);
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
    while(character != '\0'){
        index++;
        //load next character from string
        character = string[index];
    }
    return index;
}



/*
Function compares two strings and returns 0 if the first string is before (or equals) the 
second string, and returns 1 is second string comes before first string.
Essentially it is the same a <= but for strings

This is written instead of using a string function from a library.

@param string1: first string to be compared
@param string2: second string, to be compared with string 1.
*/
int compareStrings(char* string1, char* string2){
	//get the length of the largest strings
	int length = 0;
	if(getStringLength(string1) < getStringLength(string2)){
		length = getStringLength(string1);
	}
	else{
		length = getStringLength(string2);
	}
	
	int index;
	//loop through every char in the string
	for (index = 0; index<length; index++){
		//if character is in string 1 is greater than the character at the same position 
		//in string 2 then return 1 meaning string 2 is alphabetically before string 1.
		if (string1[index] > string2[index]){
			return 1;
		} 
		//if character is in string 1 is less than the character at the same position 
		//in string 2 then return 0 meaning string 1 is alphabetically before string 2.
		else if (string1[index] < string2[index]){
			return 0;
		}	
	}
	
	//return 0 if the same
	return 0;

}


/*
Function swaps two elements in the array

@param namesArray[][20]: stores the array of names
@param string1[20]: stores one of the names to be swapped
@param string2[20]: stores the other name to be swapped
*/
void swap(char namesArray[][20], char string1[20], char string2[20]){
    char tempString[20];
    strcpy(tempString, string1);
    strcpy(string1, string2);
    strcpy(string2, tempString);
}

/**
function reorders the array by moving elements alphabetically before the pivot to before the pivot
and elements alphabetically after the pivot to after the pivot.

@param namesArray[][20]: stores the array of names
@param lowIndex: stores the lower index of the data to be reordered
@param highIndex: stores the upper index of the data to be reordered
*/
int reorderArray (char namesArray[][20], int lowIndex, int highIndex) { 
	//set the pivot to the final element in the array
	char* pivot = namesArray[highIndex];
	//set the partition to be the lowest element
	//partition will be the index of the smaller element - 1
	int partition = (lowIndex - 1); 
  
	//loop from the smallest element (tempLowIndex) to the highest element -1 (as pivot is the highest element)
	for (int tempLowIndex = lowIndex; tempLowIndex < highIndex; tempLowIndex++) { 
		// If current element is smaller than or equal to pivot 
		if (compareStrings (namesArray[tempLowIndex], pivot) == 0) { 
			//increment partition
			partition++;    
			//swap elements
			swap(namesArray, namesArray[partition], namesArray[tempLowIndex]); 
		} 
	} 
	//swap the element after the partition with the pivot
	//this means that pivot is in the correct position in regards to the other elements
	swap(namesArray, namesArray[partition + 1], namesArray[highIndex]); 
	//return partition + 1 to give the index of the pivot. 
	return (partition + 1); 
} 


/*
Function calls reorder to reorder the data within the indexes.
the function calls itself once it has reordered the array.

@param namesArray[][20]: stores the array of names
@param lowIndex: stores the lower index for quicksort to be performed upon
@param highIndex: stores the upper index for quicksort to be performed upon
*/
void quicksort(char namesArray[][20], int indexLow, int indexHigh){
	if (indexLow < indexHigh) { 
		//Reorder the array so that the pivot will be in the correct position.
		//this will return the index of the pivot
		//all elements befor the pivots will be less than the pivot
		//all elements after the pivot will be greater than
		int pivotIndex = reorderArray(namesArray, indexLow, indexHigh); 
  
  		//call quicksort on elements before the partition
		quicksort(namesArray, indexLow, pivotIndex - 1); 
		//call quicksort on elements after the partition
		quicksort(namesArray, pivotIndex + 1, indexHigh); 
	} 
}



/*
Function prints out all the values in the array

@param namesArray[][20]: stores the array of names
@param size: the number of elements in the array
*/
void printArray(char namesArray[50000][20], int size) {
	//loop through every element in the array and print it out
	for (int i=0; i < size; i++){
		printf("%s\n", namesArray[i]);
	}
}



/**
 *Write the names to a file
 *
 * @param fileName: The name of the output file
 * @param namesArray: The array of all names
 * @param size: The size of the array
 */
void writeNamesToFile(char* outputFileName, char namesArray[50000][20], int size){
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
		if(counter != size-1){
			//print the string from the array separated by a comma.
			fprintf(myFile, "%s,", namesArray[counter]);
		}
		//Don't include the comma on the last name
		else{
			fprintf(myFile, "%s", namesArray[counter]);
		}
	}
	//close the file
	fclose(myFile);
}

int main(int argc, char *argv[]){
//Get arguments from the array
//the first argument is the file that is to be redacted from
//the second argument is the output file
	char* inputFileName = "";
 	char* outputFileName = "";
 	
	if(argc == 2){	
		inputFileName = argv[1];
		printf("Input names from file : %s\n", inputFileName);
		outputFileName = "output.txt";

	}
	else if(argc == 3){
		inputFileName = argv[1];
		outputFileName = argv[2];
		
		printf("Input names from file : %s\n", inputFileName);
		printf("Output sorted names to file : %s\n", outputFileName);

		
	}
	else{
	    printf("Invalid Args Input\ninputFileName redactFileName outputFileName\n");
	    exit(0);
	}

	//Read From the file
	//create up the array
	char namesArray[50000][20];

	int elements = readFromFile(namesArray, inputFileName);
	//call quicksort on the array
	quicksort(namesArray, 0, elements -1);
	//print out the sorted array to terminal
	printf("Final Array = \n");
	printArray(namesArray, elements);
	//write names to the file.
	writeNamesToFile(outputFileName, namesArray, elements);
	printf("Written to file");

	return 0;
}