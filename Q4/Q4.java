import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Q4 {

    //Array list to store all the nodes. This stop them being taken by garbage collection
    ArrayList<Node> memory = new ArrayList<>();
    //right of the linked list
    Node head = null;
    //left of the linked list
    Node tail = null;

    public Q4(){
        readInNames();
        System.out.println("tail = " + tail + ". head = " + head);

        printOutNodes();
        System.out.println("\n");

        System.out.println("  * Test for Insert Before *");
        insertBefore("\"PATRICIA\"", "\"RHODRI\"");
        printOutNodes();
        System.out.println("\n");

        System.out.println("  * Test for Insert After *");
        insertAfter("\"PATRICIA\"", "\"HENRY\"");
        printOutNodes();
        System.out.println("\n");

        System.out.println("  * Test for Remove Before *");
        removeBefore("\"PATRICIA\"");
        printOutNodes();
        System.out.println("\n");

        System.out.println("  * Test for Remove After *");
        removeAfter("\"PATRICIA\"");
        printOutNodes();
        System.out.println("\n");
    }


    /**
     * My own implementation of an XOR function. Produces the XOR of two integers.
     * This is an improvement of a normal XOR method as takes into account null addresses.
     *
     * @param address1
     * @param address2
     * @return the pointer
     */
    private Integer XOR(Integer address1, Integer address2){
        Integer nextPtr = null;
        if (address1 == null && address2 == null){
            //check what to return
            return null;
        } else if (address1 == null){
            return address2;
        } else if (address2 == null){
            return address1;
        } else {
            nextPtr = address1 ^ address2;
        }
        return nextPtr;
    }


    /**
     * Returns the index of where the null is stored in the array.
     * If the node passed in is null then the address will be null.
     *
     * @param node
     * @return address of the node
     */
    private Integer getAddress(Node node){
        if ( node == null){
            return null;
        }
        //get the index from the array list
        Integer address = memory.indexOf(node);
        return address;
    }


    /**
     * Add a node to a linked list.
     *
     * @param data : data to be added to the node
     */
    private void addNode(String data){
        //create node
        Node newNode = new Node(data);
        //add node to memory
        memory.add(newNode);

        //for first element.
        if (head == null && tail == null){
            head = newNode;
            tail = newNode;
        }

        //already element in linked list
        else{
            //heads current pointer only has the previous address.
            // I am putting in the next address.
            //head is the current node.
            head.pointer = XOR(getAddress(newNode),head.pointer );

            //new nodes pointer needs to be the address of head
            newNode.pointer = getAddress(head);

            //set head to the new element
            head = newNode;
        }
    }


    /**
     * Get the names from the text file. Split them by ',' and call the addNode method.
     *
     */
    public void readInNames(){
        ArrayList<String> names = new ArrayList<>();
        //this gets the directory of the pathname
        String pathname = System.getProperty("user.dir");
        //fileWithNames stores the specified textFile from the directory
        File fileWithNames = new File(pathname + "/names.txt");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileWithNames));
            //stores the contents of the text file.
            String line = reader.readLine();
            //stores contents of line into an arraylist.
            names = new ArrayList<String>(Arrays.asList(line.split(",")));
            System.out.println("File loaded into system.");
            //System.out.println(names);
        } catch (IOException e){

        }
        //for every name from the txt file
        for (int i = 0; i < names.size(); i++){
            //call the addNode method with the name from the list
            addNode( names.get(i));
        }
    }


    /**
     * Method prints out all the nodes in order from the linked list.
     * starts from the tail (first element added) and goes through to the head.
     *
     */
    public void printOutNodes(){
        //currentPointer is the address of tail (start of list)
        Integer currentPointer = getAddress(tail);
        //previousPointer is the pointer of the last node visited
        Integer previousPointer = null;
        //while the currentPointer pointer points to something
        while (true ){
            //set the temp to what current pointer is pointing to. it will store data when traversing
            Node temp = memory.get(currentPointer);
            //print out node
            System.out.println(temp);

            //whatever current pointer points to.
            //If it is the previous pointer then there is nothing ahead so break.
            if (temp.pointer.equals( previousPointer)){
                break;
            }

            //Used for traversing
            //set next pointer to the xor of the temp pointer and the previousPointer pointer
            Integer nextPointer = XOR(previousPointer, temp.pointer);
            //move to next element by changing previous and current pointers.
            previousPointer = currentPointer.intValue();
            currentPointer = nextPointer;
        }
    }


    /**
     * Method inserts a new node before a specified node.
     *
     * @param before
     * @param newValue
     */
    private void insertBefore(String before, String newValue){
        //if temp value is what youre looking for
        //currentPointer is the address of head
        Integer currentPointer = getAddress(head);
        //previousPointer is the pointer of the last node visited
        Integer previousPointer = null;

        //traverse through the linked list
        while (true){
            //set the temp to the node that current pointer is pointing to
            Node temp = memory.get(currentPointer);

            //if the node accessed is the same as the specific node being looked for
            if (temp.data.equals( before)){
                //make new node
                Node newNode = new Node(newValue);
                //add node to memory
                memory.add(newNode);
                //get the address of the new node that has been added to the array list
                int newNodeAddress = getAddress(newNode);
                //set the next pointer
                Integer nextPointer = XOR(previousPointer, temp.pointer);

                //remove the current next pointer
                temp.pointer = XOR(temp.pointer, nextPointer);
                //put in the new next pointer
                temp.pointer = XOR(temp.pointer, newNodeAddress);

                //change the xor for the node before
                Node beforeNode = memory.get(nextPointer);
                //remove the current next pointer for the previous node
                beforeNode.pointer = XOR(beforeNode.pointer, currentPointer);
                //put in the new next pointer
                beforeNode.pointer = XOR(beforeNode.pointer, newNodeAddress);

                //set the new nodes pointer
                newNode.pointer = XOR(nextPointer, currentPointer);
            }

            //whatever current pointer points to. If it is only the previous pointer
            //then there is nothing ahead
            if (temp.pointer.equals(previousPointer)){
                break;
            }

            //set next pointer to the xor of the temp pointer and the previousPointer pointer
            Integer nextPointer = XOR(previousPointer, temp.pointer);
            //move to next element
            previousPointer = currentPointer.intValue();
            currentPointer = nextPointer;
        }
    }

    /**
     * Method inserts after a specified node.
     *
     * @param after
     * @param newValue
     */
    private void insertAfter(String after, String newValue){
        //currentPointer is the address of tail (start of list)
        Integer currentPointer = getAddress(tail);
        //previousPointer is the pointer of the last node visited
        Integer previousPointer = null;
        //traverse through the linked list
        while (true ){
            //set the temp to what current pointer is pointing to.
            //Used for traversing through the linked list
            Node temp = memory.get(currentPointer);

            //compare temp value with the node being looked for
            if (temp.data.equals(after)){
                //make new node
                Node newNode = new Node(newValue);
                //add node to memory
                memory.add(newNode);
                //get the address of the new node in the array
                int newNodeAddress = getAddress(newNode);

                //Use XOR to set the next pointer.
                //Next pointer is the pointer of the node after the new node
                Integer nextPointer = XOR(previousPointer, temp.pointer);

                //remove the current next pointer
                temp.pointer = XOR(temp.pointer, nextPointer);
                //put in the new next pointer
                temp.pointer = XOR(temp.pointer, newNodeAddress);

                //change the xor for the node before
                Node beforeNode = memory.get(nextPointer);
                //remove the current next pointer for the previous node
                beforeNode.pointer = XOR(beforeNode.pointer, currentPointer);
                //put in the new next pointer
                beforeNode.pointer = XOR(beforeNode.pointer, newNodeAddress);


                //set the new nodes pointer
                newNode.pointer = XOR(nextPointer, currentPointer);

            }

            //whatever current pointer points to. If it is only the previous pointer
            //then there is nothing ahead
            if (temp.pointer.equals(previousPointer)){
                break;
            }

            //set next pointer to the xor of the temp pointer and the previousPointer pointer
            Integer nextPointer = XOR(previousPointer, temp.pointer);
            //move to next element
            previousPointer = currentPointer.intValue();
            currentPointer = nextPointer;
        }
    }


    /**
     * Method removes the node before a specified node.
     * Technically speaking it doesnt remove the node but changes the pointers to no longer show the node.
     * identical to remove before except starting from the tail rather than the head
     *
     * @param before
     */
    private void removeBefore(String before){
        //currentPointer is the address of tail (start of list)
        Integer currentPointer = getAddress(head);
        //previousPointer is the pointer of the last node visited
        Integer previousPointer = null;
        //while the currentPointer pointer points to something
        while (true ){
            //set the temp to what current pointer is pointing to. Used when traversing data.
            Node temp = memory.get(currentPointer);

            //compare temp value
            if (temp.data.equals( before)){
                //next pointer points to the next node after the node to be removed
                Integer nextPointer = XOR(previousPointer, temp.pointer);
                //next node is the node after the node being removed
                Node nextNode = memory.get(nextPointer);
                //nextNextPointer is a pointer to the node after the node after the pointer.
                Integer nextNextPointer = XOR(currentPointer, nextNode.pointer);

                //remove the current next pointer from the temporary node.
                temp.pointer = XOR(temp.pointer, nextPointer);
                //put in the new next pointer
                temp.pointer = XOR(temp.pointer, nextNextPointer);

                //get the nextNextNode from  memory
                Node nextNextNode = memory.get(nextNextPointer);
                //remove the current next pointer for the next next node
                nextNextNode.pointer = XOR(nextNextNode.pointer, nextPointer);
                //put in the new next pointer
                nextNextNode.pointer = XOR(nextNextNode.pointer, currentPointer);
                break;
            }

            //whatever current pointer points to. If it is only the previous pointer
            //then there is nothing ahead
            if (temp.pointer.equals(previousPointer)){
                break;
            }

            //set next pointer to the xor of the temp pointer and the previousPointer pointer
            Integer nextPointer = XOR(previousPointer, temp.pointer);
            //move to next element
            previousPointer = currentPointer.intValue();
            currentPointer = nextPointer;
        }
    }


    /**
     * Method removes a node after a specified node
     * Technically speaking it doesnt remove the node but changes the pointers to no longer show the node.
     * identical to remove before except starting from the tail rather than the head
     *
     * @param after
     */
    private void removeAfter(String after){
            //currentPointer is the address of tail (start of list)
            Integer currentPointer = getAddress(tail);
            //previousPointer is the pointer of the last node visited
            Integer previousPointer = null;
            //while the currentPointer pointer points to something

            //Teraverse through the list.
            //This code is very similar to that of other mehods
            while (true ){
                //set the temp to what current pointer is pointing to. Used when traversing data.
                Node temp = memory.get(currentPointer);

                //compare temp value to see if it is the needed value
                if (temp.data.equals(after)){
                    //next pointer points to the next node after the node to be removed
                    Integer nextPointer = XOR(previousPointer, temp.pointer);
                    //next node is the node after the node being removed
                    Node nextNode = memory.get(nextPointer);
                    //nextNextPointer is a pointer to the node after the node after the pointer.
                    Integer nextNextPointer = XOR(currentPointer, nextNode.pointer);

                    //remove the current next pointer
                    temp.pointer = XOR(temp.pointer, nextPointer);
                    //put in the new next pointer
                    temp.pointer = XOR(temp.pointer, nextNextPointer);

                    //change the xor for the node before
                    Node nextNextNode = memory.get(nextNextPointer);
                    //remove the current next pointer for the previous node
                    nextNextNode.pointer = XOR(nextNextNode.pointer, nextPointer);
                    //put in the new next pointer
                    nextNextNode.pointer = XOR(nextNextNode.pointer, currentPointer);

                    break;
                }


                //whatever current pointer points to. If it is only the previous pointer
                //then there is nothing ahead
                if (temp.pointer.equals(previousPointer)){
                    break;
                }

                //set next pointer to the xor of the temp pointer and the previousPointer pointer
                Integer nextPointer = XOR(previousPointer, temp.pointer);
                //move to next element
                previousPointer = currentPointer.intValue();
                currentPointer = nextPointer;
            }
    }

    public static void main(String[] args){

        //start program
        new Q4();
    }
}
