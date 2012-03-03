public class Position{
  private int x;
  private int y;

  Position(int x, int y){
    this.x = x;
    this.y = y;
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public String toString(){
    return "(" + x + ", " + y + ")";
  }

  public boolean equals(Object other){
    if(this == other) return true;
    if(!(other instanceof Position)) return false;
    Position pos = (Position)other;
    return (this.x == pos.x && this.y == pos.y);
  }

  public int hashCode(){
    return (x*17) ^ y;
  }
}
