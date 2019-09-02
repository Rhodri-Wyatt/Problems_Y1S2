import java.io.*;
import java.util.ArrayList;


//please note that if the program feels like it is taking a long time to run this is normal.
// When i tested on linux.bath it took me 2 minutes 15 seconds.
public class Q6 {
    String[] inputtedText;
    String[] wordsToRedact;

    ArrayList<String> properNounsToRedact = new ArrayList<>();


    /**
     * Constructor
     * Calls the methods and creates objects from classes when needed.
     *
     *
     * @param inputFileName : name of the input file to be accessed
     * @param redactFileName : name of the redact file to be accessed
     * @param outputFileName : name of the file that the data is to be saved to.
     */
    public Q6(String inputFileName, String redactFileName, String outputFileName){
        setUpArrays(inputFileName, redactFileName);

        //Class deals with all things relating to proper nouns
        ProperNouns properNouns = new ProperNouns(inputtedText, wordsToRedact);
        properNounsToRedact = properNouns.findProperNouns();

        //create object from redact class
        Redact redact = new Redact(inputtedText, properNounsToRedact);

        System.out.println("Redacting text");
        //set input text to the redacted text. this is done by calling the redact method from the redact class
        inputtedText = redact.redactProperNouns();

        System.out.println("Saving to file");
        //write redacted text to a file.
        saveOutputToFile(outputFileName, inputtedText);
        System.out.println("Saved to file\n");
    }


    /**
     * For each text file -
     * Method gets text from the text file and stores it in a string.
     * This string is then split by spaces and stored in an array.
     *
     * @param inputFileName : name of the input file to be accessed
     * @param redactFileName : name of the redact file to be accessed
     */
    public void setUpArrays(String inputFileName, String redactFileName) {
        //Get the Input & Redact file
        System.out.println("Getting Inputted Text from file");
        String inputString = getInputFromFile(inputFileName);
        System.out.println("Getting redacted Text from file");
        String redactString = getInputFromFile(redactFileName);

        //Stop processing if the input file or Redact file don't exist
        if (inputString == null || redactString == null) {
            return;
        }

        System.out.println("File Read");

        //split the string and store it in the array
        System.out.println("Splitting Inputted Text");
        inputtedText = splitString(inputString);

        //split the string and store it in the array
        System.out.println("Splitting Redacted Text");
        wordsToRedact = splitString(redactString);
    }


    /**
     * Method is a reimplementation of the split function.
     * Split the strings by spaces.
     *
     * @param string : a string of all the words
     * @return words : an array of the every word in the string
     */
    private String[] splitString(String string){
        //calculate the number of elements needed in the array
        int count = countNumberOfSplits(string);
        //initiate the string
        String[] words = new String[count];
        //set index to 0
        int index = 0;
        String tempWord = "";
        //loop through every character in the string
        for (int i = 0; i < string.length(); i++){
            //if the character is a space
            if (string.charAt(i) == ' '){
                //add the current word to the words array
                words[index] =  tempWord;
                //reset the temp word
                tempWord = "";
                //move to next element in the word array
                index++;
            }
            else {
                //add character to the end of the string array
                tempWord += string.charAt(i);
            }
        }
        //return the arrray
        return words;
    }


    /**
     * Method counts the number of spaces.
     * This is used to set up the array in the split method
     *
     * @param string
     * @return count: the number of spaces
     */
    private int countNumberOfSplits(String string){
        int count = 0;
        //loop through string
        for (int i = 0; i < string.length(); i++) {
            //if character is a space
            if (string.charAt(i) == ' ') {
                //increment count
                count ++;
            }
        }
        return count;
    }


    /**
     * Retrieves all the text from the file and stores it in a the string.
     *
     * @param input
     * @return
     */
    private String getInputFromFile(String input){
        try {
            //Open the file
            //this gets the directory of the pathname
            String pathname = System.getProperty("user.dir");
            //fileWithText stores the specified textFile from the directory
            File fileWithText = new File(pathname + "/" + input);
            System.out.println("Pathname of file = " + pathname + "/" +  input);

            //used to read text from the file
            BufferedReader reader = new BufferedReader(new FileReader(fileWithText));

            //string to store contents from the file.
            String dataLine = "";

            //fill dataline with the text.
            while(reader.ready()) {
                //after every line, add it to the string and add a new line character after
                dataLine = dataLine.concat(reader.readLine() + " \n ");
            }

            //Close the readers
            reader.close();
            //return the string
            return dataLine;

        }catch(FileNotFoundException e){
            System.out.println("Input File Could Not Be Found");
            return null;
        }
        catch(IOException e){
            System.out.println("IO Exception Occurred When Loading");
            return null;
        }
    }


    /**
     * Method writes data form the array to the file.
     * Every element from the array is written and separated by a space.
     *
     * @param outputFileName : filename of the output file.
     * @param inputWords : Array of file to be added
     */
    private void saveOutputToFile(String outputFileName, String[] inputWords){
        try {
            //creates a new file with the name sortedNames
            FileWriter writer = new FileWriter(outputFileName);
            //loops from every element in the list
            for (int i = 0; i < inputWords.length; i++){
                //writes every element into the file
                writer.write(inputWords[i] + " ");
            }
            //close the writer when finished
            writer.close();
        } catch (IOException e){

        }
    }

    /**
     * Gets names for the files and calls the constructor.
     *
     * @param args
     */
    public static void main(String[] args) {
        // args 0 - name of file to read in text
        String inputFileName = "";
        // args 1 - name of file to read in list of words to Redact
        String redactFileName = "";
        // args 2 - name of file to write out the redacted text
        String outputFileName = "";

        if (args.length == 2) {
            inputFileName = args[0];
            redactFileName = args[1];
            outputFileName = "output.txt";
        } else if (args.length == 3) {
            inputFileName = args[0];
            redactFileName = args[1];
            outputFileName = args[2];
        } else {
            System.out.println("Please enter valid argument. Enter input file name, redact file name and output file name.");
            System.exit(0);
        }

        System.out.println("Text file to be accessed = " + inputFileName);
        System.out.println("Redact words to be accessed = " + redactFileName);
        System.out.println("Output file to be saved to = " + outputFileName);

        //create new object
        new Q6(inputFileName, redactFileName, outputFileName);
    }
}
