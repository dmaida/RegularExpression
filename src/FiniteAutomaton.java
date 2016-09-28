import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FiniteAutomaton {

    Transitions[] transitions;
    int numbTransition = 0;

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

    public void createStates(char symbol) {
        Transitions currentState = new Transitions();
        Transitions currentEdge = currentState.createTransitions("q"+numbTransition, "q"+(numbTransition+1), symbol);
        addTransitions(currentEdge);
    }

    public void addTransitions(Transitions edge) {
        transitions[numbTransition] = edge;
        numbTransition++;
    }

    public void createAutomaton(char[] regularExpression) {
        transitions = new Transitions[100];
        for (int i = 0; i < regularExpression.length ; i++) {
            //System.out.print(regularExpression[i]);
        }
        for (int i = 0; i < regularExpression.length; i++) {
            char c = regularExpression[i];

            if(c == 'a' | c == 'b' | c == 'c' | c == 'd' | c == 'e') {
                if (c == 'a') {
                    createStates('a');
                }
                else if (c == 'b') {
                    createStates('b');
                }
                else if (c == 'c') {
                    createStates('c');
                }
                else if (c == 'd') {
                    createStates('d');
                }
                else if (c == 'e') {
                    createStates('e');
                }
            }
            System.out.println(c);

            if (c == '&') {

            }
            else if (c == '|') {

            }
        }
    }

    public void printAutomaton() {
        System.out.println("(" +transitions[0].stateOne + " , "+ transitions[0].symbol + " )" + " --> " +transitions[0].stateTwo);
        System.out.println("(" +transitions[1].stateOne + " , "+ transitions[1].symbol + " )" + " --> " +transitions[1].stateTwo);
    }


    public static void main(String args[]) {
        FiniteAutomaton stateMachine = new FiniteAutomaton();
        stateMachine.readFile();
        stateMachine.printAutomaton();

    }
}
