#include <stdio.h>
#include <stdlib.h>

//declare functions used
void addElement (char string[]);
void removeElement(char string[]);
int search(char string[]);
int hashFunction (char string[]);
int incrementIndex(int index);
int compareStrings(char string1[], char string2[]);
int getStringLength(char* string);

//create a big hash table
char hashTable[100000][50];
//keep track of how many elements that have been entered
int elementsUsed = 0;
	

/*
Function takes in the fileName, gets names from the file and adds it to the hash table.

@param filename: the array to be filled with contents of the file
*/
int readFile(char* fileName){
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

	//build up string and add it to the hash table
   char string[50];
   int charactersInString = 0;
   
	//loop through until the end of the file
   char character;
   while ((character = getc(fp)) != EOF) {
   	//if the character is a comma	or quotation marks
		if (character == ','){
			//end string by adding a terminating character
			string[charactersInString] = '\0';
			//set index to 0 for next name to be added
			charactersInString = 0;
			
			//add to hash table 
			//printf("%s\n", string);
			addElement(string);
			
       } else if (character != '"') {
           //Add character to name
           string[charactersInString] = character;
           //move to next character in the array
           charactersInString++;
       }
   }
   //terminate character
	string[charactersInString] = '\0';

   //for final element from the text file add to the hash table
	addElement(string);

   //close file
   fclose(fp);
}


/*
Function to add an element into the hash table.
makes sures there is still space in the hash table
find a free index for the element by the hash table.
add the element to the hash table at that index
function is called addElement instead of add to keep it consistent with remove

@param string: element to be added to hash table.
*/
void addElement (char string[]){
	//if all the space in the hash table is used then do not let the user add a new element.
	if (elementsUsed == 100000){
		printf("Unable to add element as HashTable is full./n");
		return;
	}
	
	//find the index based on the hash function
	int index = hashFunction(string);	
	
	//set that there is a free index since not 100000 elements
	int freeIndex = 0;
	
	//loop while a free index has not been found.
	while (freeIndex == 0){
		//printf("In While Loop Of Add\n");
		 //check if the current element is free.
		 //this is done by checking whether the first character is the terminating character or not.
		if(hashTable[index][0] == '\0'){
			//loop through characters in the string
			for (int i = 0; i < getStringLength(string); i++){
				// add character to the hash table at the correct index
				hashTable[index][i] = string[i];
			}
			printf("%s placed in table at index = %d\n",string, index);
			freeIndex = 1;
      }
		else{
			//try the next index to see if it is free.
        	index = incrementIndex(index); 
		}
	}
	//increment the number of elements used.
	elementsUsed++;
}


/*
Function is used to remove element from the hash table.
function is called removeElement instead of remove as c would not let me name the function
remove as it it is already a key word when using files.

@param string: element to be added to hash table.
*/
void removeElement(char string[]){
	//if the string is present in the hash table
	if (search(string) == 1){
		//find the index from the string
		int index = hashFunction(string);
		//boolean check whether element has been removed or not.
		int elementDeleted = 1;
		//while element has not been deleted
		while (elementDeleted == 1){
			//compare the string with the string in the hash table at the index
			if (compareStrings(string, hashTable[index]) == 0){
				//if the same string
				//set first element to terminating condition
				hashTable[index][0] = '\0';
				//set boolean of elementDeleted to be true
				elementDeleted = 0;
				//decrement the element
				elementsUsed--;
				printf("%s has succesfully been deleted from the hash table\n", string); 
			}
			else {
				//if string is not the same as the string at the index in the hash table then
				//move to the next string.
				index = incrementIndex(index);
			}		
		}
	}
	else {
	printf("String is not in the hash table\n");
	}
}


/*
Function searches hash table to see if string is present there

@param string: string to be searched for
@return 1 if element if found, 0 if element is not found.
*/
int search(char string[]){
	//calculate index from hash function
	int index = hashFunction(string);
	//set boolean of element found to false
	int elementFound = 0;
	//while the element has not been found
	while (elementFound == 0){
		//if the first char in element at the index is the terminating character 
		if (hashTable[index][0] == '\0'){
			//the element is not there
			return 0;
		}
		//if the element in the hashtable at the index is the same as the string being checked
		else if ( compareStrings(string, hashTable[index]) == 1) {
			//set boolean of element found to true
			elementFound = 1;
			//return 0 for true
			return 1;
		}
		//if the element is not the same then move to the next element
		else {
			index = incrementIndex(index);
		}
	}
}


/*
Function returns a unique index based on the string inputted

@param string: string for the hash function to be performed on.
@return index: the unique index for the string
*/
int hashFunction (char string[]){
	//set index to be 0
   int index = 0;
   //for every character in the string
 	for(int i = 0; i < getStringLength(string); i++){
 		//set the string to its ascii value 
 		int ascii = (int) string[i];
 		//times the ascii of the character by its index in the string
		index += (int) ascii * i;
		//perform a logical left shift by one
		index = index << 1;
	} 
	
	//if the index is too high
	if(index > 99999){
		//set it to modulo of the number of letters of in the alphabet
		index = index % 26;
	}
	return index;
}


/*
Function increments the index by one.

@param index: index to be incremented
*/
int incrementIndex(int index){
	if (index == 99999){
		index = 0;
	} else {
		index++;
	}
	return index;
}


/*
Function commpares two strings and returns 0 if not the same and 1 if the same.

@param string1: first string to be compared
@param string2: second string to be compared
@return 1 if element if different, 0 if element is not the same.
*/
int compareStrings(char string1[], char string2[]){
	//if they are the same length
	if (getStringLength(string1) == getStringLength(string1)){
		//loop through all the characters in the string
		for(int i = 0; i < getStringLength(string1); i++){
			//if the two characters in the string are different
			if(string1[i] != string2[i]){
				//return false
				return 0;
			}
		}
		//return true
		return 1;
	}
	//otherwise return false
	return 0;
}


/*
Function calculates the length of a given string
reimplementation of strlen

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


/*
Function to test that the code words correctly. test the functions on the spec
*/
void runTests(){
	printf(" * TEST * \n");
	addElement("TOMITHY");
	addElement("ZACK");
	addElement("RHODRI");
	removeElement("TOMITHY");
	printf("search result = %d \n TOMITHY in hashTable? 1 if True. 0 if False.\n", search("TOMITHY"));
	
}



int main(int argc, char *argv[]){
	//Get arguments from the array
	//the first argument is the file that is to be redacted from
	char* inputFileName = ""; 	
 	
 	//get the file name from arguments
	if(argc == 2){	
		inputFileName = argv[1];
		printf("Input string from file : %s\n", inputFileName);
	}
	else{
	    printf("Invalid Args Input \n please run with argument of file name to be accessed \n");
	    exit(0);
	}
	
	//read the file to get the names
	readFile(inputFileName);
	
	//tests check all the functions work correctly
	runTests();	
}



