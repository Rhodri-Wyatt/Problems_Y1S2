import java.util.ArrayList;

//Class is used to store any code relating to proper nouns
public class ProperNouns {

    ArrayList<String> listOfProperNouns = new ArrayList<>();
    String[] inputtedText;
    String[] wordsToRedact;

    //set our arrays to contain the arrays passed into the constructor
    public ProperNouns( String[] inputtedText,  String[] wordsToRedact){
        this.inputtedText = inputtedText;
        this.wordsToRedact = wordsToRedact;
    }


    /**
     * method to call other method in order to find all the proper nouns in the code
     *
     * @return listOfProperNouns
     */
    public ArrayList<String> findProperNouns(){
        addProperNounsFromRedact();
        loopToFindProperNouns();
        loopToRemoveNonProperNouns();
        //return the list of poper nouns
        return listOfProperNouns;
    }


    /**
     * Method adds all the words from the redact words to the proper nouns array.
     *
     */
    private void addProperNounsFromRedact(){
        //loops through all the words in the wordsToRedact array
        for (int i = 0; i < wordsToRedact.length; i++){
            //adds the word to the proper nouns list
            listOfProperNouns.add(wordsToRedact[i]);
        }
    }

    /**
     * Method loops through the array containing all the words from the text.
     * If the word is a proper noun then it is added to the list of proper nouns.
     *
     * I assume a word is a propper noun if the following conditions are true:
     * if the word has more than one letter,
     * if the first letter is a capital letter
     * if the second letter is not a capital letter
     */
    private void loopToFindProperNouns(){
        //loops through all the words in the inputtedText array
        for (int wordIndex = 0; wordIndex < inputtedText.length; wordIndex++) {
            //if the word legnth is greater than 1, if a first letter is a capital letter, if the second letter is not a capital letter.
            if( inputtedText[wordIndex].length() > 1 && charIsUpper(inputtedText[wordIndex].charAt(0)) && charIsUpper(inputtedText[wordIndex].charAt(1)) == false) {
                //if not already added to the array list
                if (listOfProperNouns.contains(inputtedText[wordIndex]) == false) {
                    //add to the list
                    listOfProperNouns.add(inputtedText[wordIndex]);
                }
            }
        }
    }


    /**
     * Method checks if character is uppercase or not
     *
     * @param character
     * @return boolean value
     */
    private boolean charIsUpper(char character){
        //if between bounds of an uppercase letter
        if((int)character >= (int)'A' && (int)character <= (int)'Z'){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Method loops through the array of text another time
     * if a word is found that is in the list of pronouns but in the text the first letter being lowercase
     * then remove it from the list of pronouns.
     */
    private void loopToRemoveNonProperNouns() {
        //temporary word will be the lowercase word but with the first letter being upercase
        String tempWord = "";

        for (int wordIndex = 0; wordIndex < inputtedText.length; wordIndex++) {
            //if word is greater than one and the first letter is lowercase
            if (inputtedText[wordIndex].length() > 1 && charIsUpper(inputtedText[wordIndex].charAt(0)) == false){
                //build up word with lower case letter
                //loop through all the letters in the word
                for (int i = 0; i < inputtedText[wordIndex].length(); i++) {
                    //add the change the first letter to uppercase
                    if (i ==0){
                        tempWord+=Character.toUpperCase(inputtedText[wordIndex].charAt(0));
                    }
                    //add the rest of the word to the temporary word
                    else {
                        tempWord+=inputtedText[wordIndex].charAt(i);
                    }
                }

                //check if the temporary word is in the list of pronouns
                if (listOfProperNouns.contains(tempWord)){
                    //remove word from the list
                    listOfProperNouns.remove(tempWord);
                }

                //clear tempword
                tempWord = "";
            }
        }
    }
}
