import ants.*;

public class MyAnt implements Ant{

	public Action getAction(Surroundings surroundings){
	    return Action.move(Direction.EAST);
	}
	
	public byte[] send(){
		return null;
	}
	
	public void receive(byte[] data){
		//Do nothing
	}

}