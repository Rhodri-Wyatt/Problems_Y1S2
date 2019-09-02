import java.util.ArrayList;

//Class is used to store anything relating to redaction of words
public class Redact {
    //array of words to redact from (in our case this is war and peace)
    String[] textToRedactFrom;
    //list of words to redact
    ArrayList<String> wordsToRedact;

    /**
     * Constructor
     *
     * @param textToRedactFrom : entire text document as an array
     * @param wordsToRedact : arraylist of words that must be redacted from the text document
     */
    public Redact(String[] textToRedactFrom, ArrayList<String> wordsToRedact){
        //fill our array and arraylist with data passed in to constructor
        this.textToRedactFrom = textToRedactFrom;
        this.wordsToRedact = wordsToRedact;

    }

    /**
     * if the word is in the list of words to redact, then replace the word with a star word.
     *
     * @return the array
     */
    public String[] redactProperNouns(){
        //loop through all the words in the array.
        for (int i = 0; i < textToRedactFrom.length; i++){
            //if the word from the text is in the array of lists to remove
            if (wordsToRedact.contains(textToRedactFrom[i])) {
                //replace the word with a starred out version of the word
                //the starWord method produces a starred word for the string
                textToRedactFrom[i] = starWord(textToRedactFrom[i]);
            }
        }
        //return the array
        return textToRedactFrom;
    }


    /**
     * Method produces a starred out version of the word. It only stars out the letters and not any other characters
     *
     *
     * @param word : word to be checked
     * @return the starred out word
     */
    private String starWord(String word){
        String starWord = "";
        //loop through every character in the word
        for(int i = 0; i < word.length(); i++){
            //Check that the character is valid to be removed
            if(checkValidStarCharacter(word.charAt(i))) {
                //add an asterisk to the star word
                starWord += "*";
            }
            else{
                //Add the original character if it is an invalid character to change to a star
                starWord += Character.toString(word.charAt(i));
            }
        }
        //return the starred out word
        return starWord;
    }



    /**
     * Method checks that the character is valid to be turned into a star.
     * it returns a boolean value
     *
     * @param charToCheck
     * @return the boolean value of whether it should be converted or not.
     */
    private boolean checkValidStarCharacter(char charToCheck){
        //a set of chars not to be removed
        char charsToIgnore[] = {'\n', ',', '.', ';', '\'', '\"', '!', '-', '_', ')', '('};
        boolean valid = true;

        //Check each character of the word, if it is one of the characters to ignore, return false
        for(int x = 0; x < charsToIgnore.length; x++){
            if(charsToIgnore[x] == charToCheck){
                valid = false;
                break;
            }
        }

        return valid;
    }
}
