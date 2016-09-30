
public class Transitions {
    public char symbol;
    public int stateOne;
    public int stateTwo;
    public boolean isFinalState;

    public  Transitions(int stateOne, int stateTwo, char symbol, boolean isFinalState) {
        this.stateOne = stateOne;
        this.stateTwo = stateTwo;
        this.symbol = symbol;
        this.isFinalState = isFinalState;
    }

}
