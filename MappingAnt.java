import ants.*;
import java.lang.Math;
import java.util.ArrayDeque;
import java.util.Stack;
import java.util.Hashtable;

public class MappingAnt implements Ant{
  private boolean hasFood = false;
  private int timeStep = 0;
  private int x = 0;
  private int y = 0;
  private WorldMap map = new WorldMap(5);
  private ArrayDeque<Action> plan;
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
    if(plan == null || plan.isEmpty()){
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
      System.out.println("Plan attempted an invalid move. (" + x + ", " + y +
                         ") " + nextMove.getDirection());
      System.out.println(map);
      System.out.println();
      System.exit(0);
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
    public ArrayDeque<Action> moves;

    PartialPlan(Position p){
      this.p = p;
      moves = new ArrayDeque<Action>();
    }

    public int getX(){
      return p.getX();
    }

    public int getY(){
      return p.getY();
    }

    public PartialPlan planWithMove(Move m){
      PartialPlan retval = new PartialPlan(new Position(m.getPosition()));
      retval.moves = new ArrayDeque<Action>(this.moves);
      retval.moves.add(m.getAction());
      return retval;
    }

		public Position getPosition(){
			return p;
		}

    public String toString(){
      return "Partial Plan: " + p + " " +  moves;
    }
  }

  private ArrayDeque<Action> findFoodPlan(){
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

  private ArrayDeque<Action> deliverFoodPlan(){
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

  private ArrayDeque<Action> searchForGoal(SearchGoal g){
    // PriorityQueue<Position> fringe = new PriorityQueue<Position>(20, c);
    ArrayDeque<PartialPlan> fringe = new ArrayDeque<PartialPlan>();
    Hashtable<Position, Boolean> closedSet = new Hashtable<Position, Boolean>();
    PartialPlan start = new PartialPlan(new Position(this.x, this.y));
    boolean debug = false;
    if(debug)
      System.out.println(map);
    fringe.push(start);
    while(!fringe.isEmpty()){
      PartialPlan consider = fringe.remove();
      Move[] successors = map.getPossibleMoves(consider.getX(), 
                                               consider.getY(),
                                               hasFood);
      if(closedSet.put(consider.getPosition(), true) == null){
        if(g.isGoal(consider.getPosition())){
          return consider.moves;
        }
        for(Move successor : successors){
          PartialPlan newPlan = consider.planWithMove(successor);
          fringe.add(newPlan);
          if(debug)
            System.out.println("Pushed: " + newPlan);
        }
      }
    }
    System.out.println("No path to " + g.planName() + " found. Using fallback");
    ArrayDeque<Action> backup = new ArrayDeque<Action>();
    Move[] p = map.getPossibleMoves(x, y, hasFood);
    int index = (int)(Math.random() * p.length);
    backup.add(p[index].getAction());
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
