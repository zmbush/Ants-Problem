import ants.*;

public class WorldMap{
  // This will contain the timestep when we saw the data in this position
  int[][] lastSeenTimeStep; 

  // These are the tiles that have been seen. 
  Tile[][] seenTiles;

  // The "center" of our coordinate scheme is the distance from the anthill.
  int centerx;
  int centery;

  WorldMap(int size){
    this.lastSeenTimeStep = new int[size][size];
    this.seenTiles = new Tile[size][size];
    this.centerx = size / 2;
    this.centery = size / 2;
  }
}
