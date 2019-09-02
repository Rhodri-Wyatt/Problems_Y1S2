import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;

//This is a bubbleSort
public class Q1 {

    public ArrayList<String> names;

    /**
     * Constructor, calls methods to fillNames from the array, Sort the array and print it out again.
     *
     * @param inputFile
     * @param outputFile
     */
    public Q1(String inputFile, String outputFile ){
        fillNames(inputFile);
        bubbleSort();
        writeNamesToFile(outputFile);
    }

    /**
     * Method gets the contents of the text file and stores it in a string called line.
     * Line is split by ',' and the names are stored in the arrayList called names.
     *
     * @param inputFile
     */
    private void fillNames(String inputFile){
        try{
            //this gets the directory of the pathname
            String pathname = System.getProperty("user.dir");
            //fileWithNames stores the specified textFile from the directory
            File fileWithNames = new File(pathname + "/" + inputFile);
            //buffered reader is used to read lines from the text file
            BufferedReader reader = new BufferedReader(new FileReader(fileWithNames));
            //stores the contents of the text file.
            String line = reader.readLine();
            //stores contents of line into an arraylist.
            names = new ArrayList<String>(Arrays.asList(line.split(",")));
            System.out.println("File loaded into system.");
        }
        catch(FileNotFoundException e){
            System.out.println("Input File Could Not Be Found");
        }
        catch(IOException e){
            System.out.println("IO Exception Occurred When Loading");
        }
    }


    /**
     * This is where the main logic of the bubbleSort is written.
     *
     */
    private void bubbleSort(){
        boolean swapped;
        do{
            //set initial value of swapped to be false. every time it loops round reset to false.
            swapped = false;
            //loop through every element in the arraylist
            for (int count = 0; count < names.size() - 1; count ++){
                //swap adjacent elements in the arraylist if out of position
                if (compare(names.get(count), names.get(count + 1)) == false)  {
                    swapNames(count);
                    //set swapped to false to show that a swap has happened.
                    swapped = true;
                }
            }
            //keep looping round while the array while swaps keep taking place.
        } while(swapped == true);
        System.out.println("Contents file sorted alphabetically by bubble sort.");
    }

    /**
     * Compare if string1 comes first or not
     * return true if the first string is alphabetically before the second string
     * if they are identical return true (text1 first)
     *
     * @param text1 : string to be compared
     * @param text2 : string to be compared
     * @return
     */
    private boolean compare(String text1, String text2) {
        int index = 0;
        //runs loop on the shorter string to avoid index out of range.
        while (index < shorterString(text1, text2).length()) {
            //check if the characters are the same.
            if (text1.charAt(index) == text2.charAt(index)) {
                //if they are the same move to the next letter
                index++;
            } else if (text1.charAt(index) > text2.charAt(index)) {
                //if text1 comes after then return false
                return false;
            } else if (text1.charAt(index) < text2.charAt(index)){
                //if text2 comes after then return true
                return true;
            }
        }

        //the shorter text should be before the larger one. Example, dog is alphabetically before doggo.
        if(shorterString(text1,text2).equals(text1)){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Check which string out of the two is smaller and return it.
     *
     * @param text1
     * @param text2
     * @return the shorter string
     */
    private String shorterString(String text1, String text2) {
        //if text 1 length is less than text2
        if(text1.length() < text2.length()){
            return text1;
        } else {
            return text2;
        }
    }


    /**
     * Swaps element in arraylist with the following element;
     *
     * @param index
     */
    private void swapNames(int index){
        String temp = "";
        temp = names.get(index);
        names.set(index, names.get(index + 1));
        names.set(index + 1, temp);
    }


    /**
     * Store the contents of the sorted arraylist in the file.
     *
     * @param outputFile
     */
    private void writeNamesToFile(String outputFile){
        try {
            //creates a new file with the name sortedNames
            FileWriter writer = new FileWriter(outputFile);
            //loops from every element in the list
            for (int i = 0; i < names.size(); i++){
                //writes every element into the file
                writer.write(names.get(i));
                //if the element isn't the last element then add a comma
                if (i != names.size() - 1){
                    writer.write(",");
                }
            }
            //close the writer
            writer.close();
            System.out.println("Sorted version of file now stored in file sortedNames.txt");

        } catch (IOException e){

        }
    }


    /**
     * Gets the names for the input file and output file from the arguments passed and call the bubblesort object.
     *
     * @param args
     */
    public static void main (String[] args){
        //arg[0] will contain the name of the file to be entered
        String inputFile = "";
        //arg[1] will contain the name of the file to be written to
        String outputFile = "";

        //set the input file and output file strings
        if (args.length == 1) {
            inputFile = args[0];
            outputFile = "sortedText.txt";
        } else if (args.length == 2){
            inputFile = args[0];
            outputFile = args[1];
        } else {
            System.out.println("Please enter valid argument. Enter input file name, redact file name and output file name.");
            System.exit(0);
        }

        //creates new bubbleSort instance.
        new Q1(inputFile, outputFile);

    }
}

