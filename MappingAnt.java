import ants.*;

public class MappingAnt implements Ant{
  private class WorldMap{
    int[][] lastSeenTimeStep;
    Tile[][] seenTiles;
    int centerx;
    int centery;
    WorldMap(){
    }
  }
  private boolean hasFood = false;

  @Override
  public Action getAction(Surroundings surroundings){
    return Action.move(Direction.NORTH);
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
