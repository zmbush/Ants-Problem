import ants.*;

public class WorldMap{

  /**
   * This will contain the timestep when this tile was last updated.
   */
  int[][] lastSeenTimeStep; 

  /**
   * These are the walls that have been seen. We will never degrade the value of
   * these, as walls are not apt to move. 
   */
  boolean[][] walls;

  /**
   * This contains how much food was in the tiles when they were last seen. This
   * will degrade slowly as the timesteps increase.
   */
  int[][] foodAmounts;

  /**
   * This contains how many ants were in the tiles when they were last seen.
   * This will degrade rapidly, since ants are moving quite frequently.
   */
  int[][] antAmounts;

  /**
   * The center in the x direction. Our coordinate system will take an input of
   * (0, 0) to be the center of the grid.
   */
  int centerx;
 
  /**
   * The center in the y direction. Our coordinate system will take an input of
   * (0, 0) to be the center of the grid.
   */
  int centery;

  /**
   * Test Procedure. 
   * @param argv Command line arguments
   */
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

  /**
   * Primary constructor. Initializes all necessary variables to their
   * appropriate sizes. 
   * @param size The size of the new world. (Will dynamically resize)
   */
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

  /**
   * Updates a position on the map with the specified tile. Calls the generic
   * version of updateMap after reading necessary information from the tile.
   * @param x The x distance from the anthill
   * @param y The y distance from the anthill
   * @param currentTile The tile that we are adding to the world
   * @param timestep The current timestep of the simulation. Used when we
   * degrade the value of the information gathered. 
   */
  public void updateMap(int x, int y, Tile currentTile, int timestep){
    this.updateMap(x, y, !currentTile.isTravelable(),
                          currentTile.getAmountOfFood(),
                          currentTile.getNumAnts(), 
                          timestep);
  }

  /**
   * Actually updates the values of the many arrays that hold the information
   * about the world. 
   * @param x The x distance from the anthill
   * @param y The y distance from the anthill
   * @param wall True if this tile is a wall
   * @param food The amount of food in this tile
   * @param ants The number of ants in this tile
   * @param timestep The current timestep of the simulation.
   */
  public void updateMap(int x, int y, boolean wall, int food, int ants, 
                        int timestep){
    // Convert the coordinates to our internal representation. 
    int xcoord = x + centerx;
    int ycoord = y + centery;

    // Set the values of the world.
    this.walls[xcoord][ycoord] = wall;
    this.foodAmounts[xcoord][ycoord] = food;
    this.antAmounts[xcoord][ycoord] = ants;
    this.lastSeenTimeStep[xcoord][ycoord] = timestep;
  }

  /**
   * Converts the current information about the world into a string to be
   * displayed. Useful for debugging, or just seeing what's going on inside of
   * an ant's brain
   */
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
