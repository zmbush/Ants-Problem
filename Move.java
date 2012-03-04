import ants.*;

/**
 * This class stores a move. Which consists of an action and the new position
 * after the action.
 */
public class Move{
  /**
   * The action we are taking.
   */
  private Action a;

  /**
   * The position we will be in after the move.
   */
  private Position p;

  /**
   * Primary constructor.
   * @param a The action we are taking
   * @param p The new Position
   */
  Move(Action a, Position p){
    this.a = a;
    this.p = p;
  }

  /**
   * Action getter.
   * @return The action
   */
  public Action getAction(){
    return a;
  }

  /**
   * Position getter.
   * @return the new position.
   */
  public Position getPosition(){
    return p;
  }

  /**
   * Converts to a string.
   */
  public String toString(){
    return a.toString() + " -> " + p;
  }
}
