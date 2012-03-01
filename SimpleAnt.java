import ants.*;

public class SimpleAnt implements Ant{
  private boolean hasFood = false;

  @Override
  public Action getAction(Surroundings surroundings){
    return Action.HALT;
  }

  @Override
  public byte[] send(){
    return null;
  }

  @Override
  public void receive(byte[] data){
    //
  }
}
