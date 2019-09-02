public class Node {
    //data stored in node
    String data;
    //pointer to object of type int node
    Integer pointer;

    public Node(String data){
        this.data = data;
        pointer = null;
    }


    //if the node is asked to be printed out, toString is called
    public String toString(){
        return data;
    }

}
