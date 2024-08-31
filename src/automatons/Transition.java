package automatons;

public class Transition {
    private int fromNode;
    private char character;
    private int toNode;

    public Transition(int fromNode, char character, int toNode) {
        this.fromNode = fromNode;
        this.character = character;
        this.toNode = toNode;
    }

    public int getFromNode() {
        return fromNode;
    }

    public char getCharacter() {
        return character;
    }

    public int getToNode() {
        return toNode;
    }
}

