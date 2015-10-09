/**
 * DFAMain.java
 *
 * This program creates a DFA specified from a text file.
 * Then it takes strings from the language in the DFA and indicates whether it will be accepted or rejected.
 * The strings are also specified in the text file.
 *
 * Authors: Spencer McDonald and Jack Li
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class DFAMain {

    public static void main(String[] args) throws IOException{
        if (args.length == 0) { //Get file name from command line
            System.out.println("Error: Invalid File Name");
            System.exit(1);
        }
        String filename = args[0];
        readFile(filename);
    }

    /**
     * Creates a DFA from a text file and then takes strings indicated in the text file and tells you whether they are accepted or rejected in the language
     *
     * @param filename Text file that contains instructions on how to create DFA
     * @throws IOException If there contains an error with reading the file
     */
    public static void readFile (String filename) throws IOException{
        int num_states; //Stores number of states in DFA
        char[] alphabet; //Stores characters in alphabet
        int start_state; //Indicates start state

        HashMap map = new HashMap(); //Stores ArrayLists that contain all possible transitions when reading a symbol from a string

        try {
            //Initiailize file reader
            FileReader file_reader = new FileReader(filename);
            BufferedReader buffered_reader = new BufferedReader(file_reader);

            //Get number of states
            String line = buffered_reader.readLine();
            num_states = Integer.parseInt(line);

            //Get alphabet
            line = buffered_reader.readLine();
            alphabet = line.toCharArray();
            int key = -1;

            //Read in transitions and create DFANode for each state and possible transitions to be made when a symbol is read
            for (int i = 0; i < num_states; i++) {
                ArrayList<DFANode> nodeArr = new ArrayList<DFANode>(); //Arraylist to store DFANodes for each possible transition for a state
                for (int j = 0; j < alphabet.length; j++) {
                    line = buffered_reader.readLine();
                    String[] temp = line.split(" ");
                    DFANode tempNode = new DFANode();
                    key = Integer.parseInt(temp[0]);
                    tempNode.setID(key);
                    temp[1] = temp[1].replace("’", "").replace(" ", "");
                    tempNode.setAlphabet(temp[1]);
                    tempNode.setDest(Integer.parseInt(temp[2]));
                    nodeArr.add(j, tempNode); //Store DFANode in state arrayList
                }
                map.put(key, nodeArr); //Store the arraylist for the current state to hashmap map
            }

            //get start state
            line = buffered_reader.readLine();
            start_state = Integer.parseInt(line);
            ArrayList<DFANode> temp = (ArrayList<DFANode>) map.get(start_state);
            Iterator<DFANode> it = temp.iterator();
            while (it.hasNext()) {
                DFANode t = it.next();
                t.setStart(true);
            }

            //get accept states
            line = buffered_reader.readLine();
            int[] accept = new int[line.length()];
            for (int i = 0; i < line.length()-1; i++) {
                String[] l = line.split(" ");
                int acc = Integer.parseInt(l[i]);
                ArrayList<DFANode> arr = (ArrayList<DFANode>) map.get(acc);
                it = arr.iterator();
                while (it.hasNext()) {
                    it.next().setAccept(true);
                }
            }

            //Read in the strings in text file and prints whether it is accepted or rejected from the DFA
            while (null != (line = buffered_reader.readLine())) { //loop through all strings indicated in text file
                ArrayList<DFANode> state = (ArrayList<DFANode>) map.get(start_state); //Creates ArrayList that indicates current state

                for (int i = 0; i < line.length(); i++) { //Iterates through string
                    it = state.iterator();
                    boolean found = false;
                    while (!found && it.hasNext()) {
                        DFANode tempNode = it.next();
                        if (tempNode.getAlphabet().equals(line.substring(i, i + 1))) { //If current symbol in string matches transition symbol
                            state = (ArrayList<DFANode>) map.get(tempNode.getDest()); //Change to next state indicated
                            found = true;
                        }
                    }
                }
                System.out.println(state.get(0).getAccept());
            }

            buffered_reader.close(); //close buffered reader
        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.exit(1);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }
}
