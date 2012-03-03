import ants.*;
import java.lang.Math;
import java.util.Stack;
import java.util.Hashtable;

public class MappingAnt implements Ant{
  private boolean hasFood = false;
  private int timeStep = 0;
  private int x = 0;
  private int y = 0;
  private WorldMap map = new WorldMap(5);
  private Stack<Action> plan = new Stack<Action>();
  private boolean planInAction = false;
  private int goalx = 0;
  private int goaly = 0;

  @Override
  public Action getAction(Surroundings surroundings){
    updateSurroundings(surroundings);
    // System.out.println(map);

    this.timeStep++;

    if(!hasFood && map.getFood(x, y) > 0 && (x != 0 || y != 0)){
      plan = deliverFoodPlan();
      return this.makeMove(Action.GATHER);
    }
    if(hasFood && x == 0 && y == 0){
      plan = findFoodPlan();
      return this.makeMove(Action.DROP_OFF);
    }
    if(plan == null || plan.empty()){
      if(!hasFood){
        plan = findFoodPlan();
      }else{
        plan = deliverFoodPlan();
      }
    }
    Action nextMove = plan.pop();
    if(map.validMove(nextMove, x, y, hasFood)){
      return this.makeMove(nextMove);
    }else{
      plan = null;
      return this.makeMove(Action.HALT);
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


  private void updateSurroundings(Surroundings s){
    map.updateMap(x, y,   s.getCurrentTile(), timeStep);
    map.updateMap(x, y-1, s.getTile(Direction.NORTH), timeStep);
    map.updateMap(x+1, y, s.getTile(Direction.EAST), timeStep);
    map.updateMap(x, y+1, s.getTile(Direction.SOUTH), timeStep);
    map.updateMap(x-1, y, s.getTile(Direction.WEST), timeStep);
  }
  
  // The below code is used to help the ant form a plan
  private interface SearchGoal{
    public boolean isGoal(Position p);
    public String planName();
  }


  private class PartialPlan{
    public Position p;
    public Stack<Action> moves;

    PartialPlan(Position p){
      this.p = p;
      moves = new Stack<Action>();
    }

    public int getX(){
      return p.getX();
    }

    public int getY(){
      return p.getY();
    }

    public PartialPlan planWithMove(Move m){
      PartialPlan retval = new PartialPlan(m.getPosition());
      Object oldMoves = this.moves.clone();
      retval.moves = (Stack<Action>)this.moves.clone();
      retval.moves.push(m.getAction());
      return retval;
    }

		public Position getPosition(){
			return p;
		}

    public String toString(){
      return p.toString();
    }
  }

  private Stack<Action> findFoodPlan(){
    return searchForGoal(new SearchGoal(){
      @Override
      public boolean isGoal(Position p){
        return (map.getFood(p.getX(), p.getY()) > 0 && 
                           (p.getX() != 0 || p.getY() != 0));
      }

      @Override
      public String planName(){
        return "Find Food";
      }
    });
  }

  private Stack<Action> deliverFoodPlan(){
    return searchForGoal(new SearchGoal(){
      @Override
      public boolean isGoal(Position p){
        return (p.getX() == 0 && p.getY() == 0);
      }

      @Override
      public String planName(){
        return "Deliver Food";
      }
    });
  }

  private Stack<Action> searchForGoal(SearchGoal g){
    // PriorityQueue<Position> fringe = new PriorityQueue<Position>(20, c);
    Stack<PartialPlan> fringe = new Stack<PartialPlan>();
    Hashtable<Position, Boolean> closedSet = new Hashtable<Position, Boolean>();
    PartialPlan start = new PartialPlan(new Position(this.x, this.y));
    fringe.push(start);
    while(!fringe.empty()){
      PartialPlan consider = fringe.pop();
      Move[] successors = map.getPossibleMoves(consider.getX(), 
                                               consider.getY(),
                                               hasFood);
      if(closedSet.put(consider.getPosition(), true) == null){
        if(g.isGoal(consider.getPosition())){
          return consider.moves;
        }
        for(Move successor : successors){
          PartialPlan newPlan = consider.planWithMove(successor);
          fringe.push(newPlan);
        }
      }
    }
    System.out.println("No path to goal found. Using fallback");
    Stack<Action> backup = new Stack<Action>();
    Move[] p = map.getPossibleMoves(x, y, hasFood);
    int index = (int)(Math.random() * p.length);
    backup.push(p[index].getAction());
    return backup;
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

}
