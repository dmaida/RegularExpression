import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class Converter {

    Stack<NFA> stackOfNFA;
    int numbStates = 0;

    private void readFile() {

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("re.txt"));
            try {
                String x;
                int numbLines = 1;
                int currentLine = 0;
                //(x = br.readLine()) != null
                x = br.readLine();
                char[] regularExpression = new char[x.length()];
                while (currentLine != numbLines) {
                    currentLine++;
                    // printing out each line in the file
                    for (int i = 0; i < x.length(); i++) {
                        regularExpression[i] = x.charAt(i);
                        //System.out.println(x.charAt(i));
                    }
                    createAutomaton(regularExpression);
                    //System.out.println(x);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void createNFA(char symbol) {
        numbStates = numbStates + 2;
        Transitions newEdge = new Transitions(numbStates-1, numbStates, symbol, true);
        NFA newNFA = new NFA(numbStates-1, numbStates);
        newNFA.totalStates = 2;
        newNFA.transitions.add(newEdge);
        stackOfNFA.push(newNFA);
    }

    public void createAutomaton(char[] regularExpression) {
        stackOfNFA = new Stack();
        for (int i = 0; i < regularExpression.length; i++) {
            char c = regularExpression[i];

            if (c == '&') {
                /*
                Add a ε-transition from the final state of r1 to the start state of r2.
                The start state of FA3 is the start state of FA1 and the final state of FA3 is the final state of FA2.
                */
                NFA nfa2 = stackOfNFA.pop();
                NFA nfa1 = stackOfNFA.pop();

                NFA conNFA = NFA.concatenate(nfa1, nfa2);
                stackOfNFA.push(conNFA);

            }
            else if (c == '|') {
                /*
                Add a new start state s and make a ε-transition from this state to the start states of FA1 and FA2.
                Add a new final state f and make a ε-transition to this state from each of the final states of FA1 and FA2.
                */

                NFA nfa2 = stackOfNFA.pop();
                NFA nfa1 = stackOfNFA.pop();

                NFA unionNFA = NFA.union(nfa1, nfa2);
                numbStates = unionNFA.totalStates;

                stackOfNFA.push(unionNFA);
            }
            else if (c == '*') {

                /*
                -Add a new start state s and make a ε-transition from this state to the start state of FA1.
                -Make a ε-transition from the final state of F1 to the new start state s.
                -The final states of FA1 are no longer final and s is the final state of FA2.
                 */

                NFA nfa = stackOfNFA.pop();
                NFA starNFA = NFA.star(nfa);
                numbStates = starNFA.totalStates; // Should be equal
                stackOfNFA.push(starNFA);
            }
            else {
                    if (c == 'a') {
                        createNFA('a');
                    }
                    else if (c == 'b') {
                        createNFA('b');
                    }
                    else if (c == 'c') {
                        createNFA('c');
                    }
                    else if (c == 'd') {
                        createNFA('d');
                    }
                    else if (c == 'e') {
                        createNFA('e');
                    }
                    else if (c == 'E') {
                        createNFA('E');
                    }
            }
        }
    }

    public void printAutomaton() {

        for (int i = 0; i < stackOfNFA.size(); i++) {
            for (int j = 0; j < stackOfNFA.get(i).transitions.size(); j++) {
                System.out.println("(" +stackOfNFA.get(i).transitions.get(j).stateOne + " , "+ stackOfNFA.get(i).transitions.get(j).symbol + " )" + " --> " +stackOfNFA.get(i).transitions.get(j).stateTwo);
            }
        }
    }

    public void sortAutomaton() {


        ArrayList<Transitions> sortedList = new ArrayList<>();

        Comparator<Transitions> comp = new Comparator<Transitions>() {
            @Override
            public int compare(Transitions t1, Transitions t2) {

                return t1.stateOne-t2.stateOne;
            }
        };

        for (int i = 0; i < stackOfNFA.size(); i++) {
            for (int j = 0; j < stackOfNFA.get(i).transitions.size(); j++) {
                stackOfNFA.get(i).transitions.sort(comp);

            }
        }
    }

    public static void main(String args[]) {
        Converter stateMachine = new Converter();
        stateMachine.readFile();
        System.out.println("start State  = " +stateMachine.stackOfNFA.get(0).startState);
        System.out.println("final State  = " +stateMachine.stackOfNFA.get(0).finalState);
        stateMachine.sortAutomaton();
        stateMachine.printAutomaton();

    }
}
