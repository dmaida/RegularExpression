
public class Transitions {
    char symbol;
    String stateOne;
    String stateTwo;

    public Transitions createTransitions(String stateOne, String stateTwo, char symbol) {
        Transitions edge = new Transitions();
        edge.stateOne = stateOne;
        edge.stateTwo = stateTwo;
        edge.symbol = symbol;
        return edge;
    }
}
