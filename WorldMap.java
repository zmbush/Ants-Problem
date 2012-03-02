import ants.*;

public class WorldMap{

  /// This will contain the timestep when we saw the data in this position
  int[][] lastSeenTimeStep; 

  /// These are the walls that have been encountered
  boolean[][] walls;

  /// This contains the amount of food in each square the last time it was seen
  int[][] foodAmounts;

  /// This contains the number of ants in the square the last time it was seen
  int[][] antAmounts;

  /**
   * The "center" of our coordinate scheme is the anthill.
   */
  int centerx;
  int centery;


  public static void main(String[] argv){
    WorldMap testWorld = new WorldMap(20);
    testWorld.updateMap(0, 0, true, 0, 0, 0);
    testWorld.updateMap(0, 1, false, 1, 0, 0);
    testWorld.updateMap(1, 1, false, 0, 0, 0);
    testWorld.updateMap(1, 0, false, 0, 0, 0);
    testWorld.updateMap(1, -1, false, 0, 0, 0);
    testWorld.updateMap(0, -1, false, 0, 0, 0);
    testWorld.updateMap(-1, -1, false, 0, 0, 0);
    testWorld.updateMap(-1, 0, false, 0, 0, 0);
    testWorld.updateMap(-1, 1, false, 0, 0, 0);
    System.out.println(testWorld);
  }

  WorldMap(int size){
    this.lastSeenTimeStep = new int[size][size];
    for(int x = 0; x < size; x++){
      for(int y = 0; y < size; y++){
        this.lastSeenTimeStep[x][y] = -1;
      }
    }
    this.walls = new boolean[size][size];
    this.foodAmounts = new int[size][size];
    this.antAmounts = new int[size][size];
    this.centerx = size / 2;
    this.centery = size / 2;
  }

  public void updateMap(int x, int y, Tile currentTile, int timestep){
    this.updateMap(x, y, !currentTile.isTravelable(),
                          currentTile.getAmountOfFood(),
                          currentTile.getNumAnts(), timestep);
  }

  public void updateMap(int x, int y, boolean wall, int food, int ants, int timestep){
    int xcoord = x + centerx;
    int ycoord = y + centery;

    this.walls[xcoord][ycoord] = wall;
    this.foodAmounts[xcoord][ycoord] = food;
    this.antAmounts[xcoord][ycoord] = ants;
    this.lastSeenTimeStep[xcoord][ycoord] = timestep;
  }

  public String toString(){
    String retval = "";
    for(int x = 0; x < this.walls.length; x++){
      int[] seenRow = this.lastSeenTimeStep[x];
      boolean[] wallRow = this.walls[x];
      int[] foodRow = this.foodAmounts[x];
      int[] antRow = this.antAmounts[x];
      for(int y = 0; y < wallRow.length; y++){
        int seen = seenRow[y];
        boolean wall = wallRow[y];
        int food = foodRow[y];
        int ant = antRow[y];
        if(wall){
          retval += "#";
        }else if(food > 0){
          retval += Integer.toString(food);
        }else if(seen < 0){
          retval += '?';
        }else{
          retval += ' ';
        }
      }
      retval += '\n';
    }
    return retval;
  }
}
