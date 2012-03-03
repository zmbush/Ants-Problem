import ants.*;

public class Move{
  private Action a;
  private Position p;
  Move(Action a, Position p){
    this.a = a;
    this.p = p;
  }

  public Action getAction(){
    return a;
  }

  public Position getPosition(){
    return p;
  }
}
