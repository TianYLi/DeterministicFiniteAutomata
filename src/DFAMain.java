import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Created by Spencer and Jack on 10/6/2015.
 */
public class DFAMain {

    //NOTES FOR SPENCER, I left all my "work" as in printlns and such so it might help to understand
    //also made basic comments about where things are
    //pls don't forget to put my name somewhere :)
    //also naming conventions... I usually do camelCase as opposed to camel_case because its easier to type
    //either way we should probably keep it consistent, just pick one.
    //have a good night! :)

    public static void main(String[] args) throws IOException{
        if (args.length == 0) { //Get file name from command line
            System.out.println("Error: Invalid File Name");
            System.exit(1);
        }
        String filename = args[0];
        readFile(filename);
    }

    public static void readFile (String filename) throws IOException{
        int num_states;
        char[] alphabet;
        int start_state;

        ArrayList<String> test_str = new ArrayList<String>();
        HashMap map = new HashMap();

        try {
            //initiailize file reader
            FileReader file_reader = new FileReader(filename);
            BufferedReader buffered_reader = new BufferedReader(file_reader);

            //Get number of states
            String line = buffered_reader.readLine();
            num_states = Integer.parseInt(line);

            //get alphabet
            line = buffered_reader.readLine();
            alphabet = line.toCharArray();
            int key = -1;
            //get transitions
            for (int i = 0; i < num_states; i++) {
                ArrayList<DFANode> nodeArr = new ArrayList<DFANode>();
                for (int j = 0; j < alphabet.length; j++) {
                    line = buffered_reader.readLine();
                    String[] temp = line.split(" ");
                    DFANode tempNode = new DFANode();
                    key = Integer.parseInt(temp[0]);
                    tempNode.setID(key);
                    temp[1] = temp[1].replace("’", "").replace(" ", "");
                    tempNode.setAlphabet(temp[1]);
                    tempNode.setDest(Integer.parseInt(temp[2]));
                    nodeArr.add(j, tempNode);
                }
                map.put(key, nodeArr);
                //System.out.println(nodeArr+" is stored at "+key);
            }
            /*
            for(Object k : map.values()) {
                ArrayList<DFANode> a = (ArrayList<DFANode>) k;
                DFANode t = a.get(0);
                System.out.println(t.getID());
                System.out.println(t.getAlphabet());
                System.out.println(t.getDest());
                System.out.println(t.getStart());
                System.out.println(t.getAccept());
                DFANode t1 = a.get(1);
                System.out.println(t1.getID());
                System.out.println(t1.getAlphabet());
                System.out.println(t1.getDest());
                System.out.println(t1.getStart());
                System.out.println(t1.getAccept());
            }
            */
            //get start state
            line = buffered_reader.readLine();
            start_state = Integer.parseInt(line);
            //System.out.println("Start state: "+start_state);
            ArrayList<DFANode> temp = (ArrayList<DFANode>) map.get(start_state);
            Iterator<DFANode> it = temp.iterator();
            while (it.hasNext()) {
                DFANode t = it.next();
                t.setStart(true);
                //System.out.println("start ID: "+t.getID());
                //System.out.println("start dest: " + t.getDest());
            }

            //get accept states
            line = buffered_reader.readLine();
            //get strings
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

            //the actual testing part
            //System.out.println("start_state: "+start_state);
            /*
            System.out.println("Start:");
            DFANode t = state.get(0);
            System.out.println(t.getID());
            System.out.println(t.getAlphabet());
            System.out.println(t.getDest());
            System.out.println(t.getStart());
            System.out.println(t.getAccept());
            DFANode t1 = state.get(1);
            System.out.println(t1.getID());
            System.out.println(t1.getAlphabet());
            System.out.println(t1.getDest());
            System.out.println(t1.getStart());
            System.out.println(t1.getAccept());
            */
            while (null != (line = buffered_reader.readLine())) {
                ArrayList<DFANode> state = (ArrayList<DFANode>) map.get(start_state);
                //System.out.println("line: "+line);
                //int counter = 0;
                for (int i = 0; i < line.length(); i++) {
                    it = state.iterator();
                    boolean found = false;
                    while (!found && it.hasNext()) {
                        //System.out.println(found);
                        DFANode tempNode = it.next();
                        //System.out.println("Node: "+tempNode.getAlphabet());
                        //System.out.println("char: "+line.charAt(i));
                        //System.out.println("Compare: "+tempNode.getAlphabet().equals(line.substring(i, i+1)));
                        if (tempNode.getAlphabet().equals(line.substring(i, i+1))) {
                            //System.out.println("ID: "+tempNode.getID());
                            //System.out.println("dest: "+tempNode.getDest());
                            state = (ArrayList<DFANode>) map.get(tempNode.getDest());
                            found = true;
                        }
                    }
                    //System.out.println(counter);
                    //counter++;
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
