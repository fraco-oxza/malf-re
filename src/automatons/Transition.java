package automatons;

import java.util.Objects;

public class Transition {
  private int fromNode;
  private final char character;
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

  public void setToNode(int toNode) {
    this.toNode = toNode;
  }

  public void setFromNode(int fromNode) {
    this.fromNode = fromNode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transition that = (Transition) o;
    return fromNode == that.fromNode && character == that.character && toNode == that.toNode;
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromNode, character, toNode);
  }

  @Override
  public String toString() {
    String stateSymbol = "q";
    String separatorString = ", ";

    return "("
        + stateSymbol
        + fromNode
        + separatorString
        + character
        + separatorString
        + stateSymbol
        + toNode
        + ")";
  }
}
