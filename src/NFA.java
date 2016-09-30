import java.util.ArrayList;

public class NFA {
    public int startState;
    public int finalState;
    public int totalStates;

    public ArrayList<Transitions> transitions;

    public NFA(int startState, int finalState) {
        this.startState = startState;
        this.finalState = finalState;
        transitions = new ArrayList();
    }

    public static NFA concatenate(NFA nfa1, NFA nfa2) {
        //Add a ε-transition from the final state of r1 to the start state of r2.e
        Transitions newT = new Transitions(nfa1.finalState, nfa2.startState, 'E',false);

        NFA conNFA = new NFA(nfa1.startState, nfa2.finalState);
        conNFA.transitions.add(newT);

        for (int i = 0; i <nfa1.transitions.size() ; i++) {
            conNFA.transitions.add(nfa1.transitions.get(i));
        }
        for (int i = 0; i <nfa2.transitions.size() ; i++) {
            conNFA.transitions.add(nfa2.transitions.get(i));
        }
        return conNFA;
    }

    public static NFA union(NFA nfa1, NFA nfa2) {

        //Add a new start state s and make a ε-transition from this state to the start states of FA1 and FA2.
        //Add a new final state f and make a ε-transition to this state from each of the final states of FA1 and FA2.

        NFA unionNFA = new NFA(nfa2.finalState+1, nfa2.finalState+2);


        NFA newStart = new NFA(nfa2.finalState+1,nfa2.finalState+1);
        NFA newFinal = new NFA(unionNFA.finalState, unionNFA.finalState);

        for (int i = 0; i < nfa1.transitions.size(); i++) {
            if (nfa1.transitions.get(i).isFinalState) {
                Transitions newTransition = new Transitions(nfa1.transitions.get(i).stateTwo, newFinal.startState, 'E', true);
                unionNFA.transitions.add(newTransition);
            }
        }
        for (int i = 0; i < nfa2.transitions.size(); i++) {
            if (nfa2.transitions.get(i).isFinalState) {
                Transitions newTransition = new Transitions(nfa2.transitions.get(i).stateTwo, newFinal.startState, 'E', true);
                unionNFA.transitions.add(newTransition);
            }
        }

        Transitions newEdge = new Transitions(newStart.startState, nfa1.startState, 'E', false);
        Transitions secondNewEdge = new Transitions(newStart.startState, nfa2.startState, 'E', false);

        newStart.transitions.add(newEdge);
        newStart.transitions.add(secondNewEdge);


        for (int i = 0; i <newStart.transitions.size() ; i++) {
            unionNFA.transitions.add(newStart.transitions.get(i));
        }

        for (int i = 0; i <nfa1.transitions.size() ; i++) {
            unionNFA.transitions.add(nfa1.transitions.get(i));
        }
        for (int i = 0; i <nfa2.transitions.size() ; i++) {
            unionNFA.transitions.add(nfa2.transitions.get(i));
        }
        unionNFA.totalStates = nfa1.totalStates+nfa2.totalStates+2;

        System.out.println("union = " + unionNFA.totalStates);

        return unionNFA;
    }

    public static NFA star(NFA nfa) {

        /*
            -Add a new start state s and make a ε-transition from this state to the start state of FA1.
            -Make a ε-transition from the final state of F1 to the new start state s.
            -The final states of FA1 are no longer final and s is the final state of FA2.
        */

        NFA newStart = new NFA(nfa.finalState+1, nfa.finalState+1);
        NFA starNFA = new NFA(nfa.startState, nfa.finalState+1);
        Transitions newEdge = new Transitions(newStart.startState, nfa.startState, 'E', true);
        Transitions secondNewEdge = new Transitions(nfa.finalState, newStart.startState, 'e', true);

        nfa.transitions.add(secondNewEdge);

        newStart.transitions.add(newEdge);

        for (int i = 0; i <nfa.transitions.size() ; i++) {
            nfa.transitions.get(i).isFinalState = false;
            starNFA.transitions.add(nfa.transitions.get(i));
        }
        for (int i = 0; i < newStart.transitions.size(); i++) {
            starNFA.transitions.add(newStart.transitions.get(i));
        }

        starNFA.totalStates = nfa.totalStates+1;

        return starNFA;
    }
}
