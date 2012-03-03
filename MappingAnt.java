import ants.*;
import java.lang.Math;

public class MappingAnt implements Ant{
  private boolean hasFood = false;
  private int timeStep = 0;
  private int x = 0;
  private int y = 0;
  private WorldMap map = new WorldMap(5);

  @Override
  public Action getAction(Surroundings surroundings){
    map.updateMap(x, y, surroundings.getCurrentTile(), timeStep);
    map.updateMap(x, y-1, surroundings.getTile(Direction.NORTH), timeStep);
    map.updateMap(x+1, y, surroundings.getTile(Direction.EAST), timeStep);
    map.updateMap(x, y+1, surroundings.getTile(Direction.SOUTH), timeStep);
    map.updateMap(x-1, y, surroundings.getTile(Direction.WEST), timeStep);
    System.out.println(map);
    this.timeStep++;
    Action[] possible = map.getPossibleMoves(x, y);
    return this.makeMove(possible[0]);
  }

  private Action makeMove(Action a){
    if(a == Action.HALT){
      return a;
    }else if(a == Action.GATHER){
      this.hasFood = true;
      return a;
    }else if(a == Action.DROP_OFF){
      this.hasFood = false;
      return a;
    }else{
      switch(a.getDirection()){
        case NORTH:
          y -= 1;
          break;
        case EAST:
          x += 1;
          break;
        case SOUTH:
          y += 1;
          break;
        case WEST:
          x -= 1;
      }
      return a;
    }
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
