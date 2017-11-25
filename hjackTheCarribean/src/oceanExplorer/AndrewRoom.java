package oceanExplorer;


/**
 * Whirlpool room
 * User will be unable to move and will turn the user a certain amount of time
 * Then it will send the user into a room in the direction they will be facing last.
 * 
 * 
 * @author BT_1N3_06
 *
 */
public class AndrewRoom extends CaveRoom {
	
	private String currentValidKeys;
	
	private int turnCount; //amount of turns until the whirlpool disappears
	private int distanceFromCenter; //distance the user is from the center of the whirlpool(dying)
	
	private int directionFacing; //direction the user's ship is facing
	
	private String trueDescription; //what will really be printed
	
	private boolean entered = false; //keep track if whirlpool was already entered

	public AndrewRoom() {
		super("");
			if(!entered) {
				trueDescription = "The ship is in a whirlpool";
				turnCount = (int)(Math.random()*6)+4;
				distanceFromCenter = turnCount-1; //user will have to input the right thing at least once to survive
				/**
				 * Whirlpool will always spin counter clock-wise (coriolis effect in S Hemisphere)
				 * So the center will always be to the left and user will be moving left
				 */
				directionFacing = (int)(Math.random()*4);
				trueDescription += "\nThe ship is pointed to the "+translateDirection(directionFacing)+"."; //always tell the user their direction
				setDescription(trueDescription);
				setValidKeys("----wdsa");
			}else {
				setDescription("This used to be a whirlpool.");
				setValidKeys("wdsa");
			}
	}
	
	public int centerDirection(int direction){
		if(direction == 0){
		   return 3;
		}
		return direction - 1;
	}
	
	public static String translateDirection(int direction) {
		String[] dir = {"North", "East", "South", "West"};
		return dir[direction];
	}
	
	public void setValidKeys(String a) {
		currentValidKeys = a;
	}
	
	//OVERIDE
	
	public void performAction(int direction) {
		int newDir = direction - 4;
		if(newDir == centerDirection(directionFacing)) {
			//user heads deeper into the center and faster
			distanceFromCenter -= 2;
			trueDescription = "The ship heads deeper to the center.";
		}else if(newDir == oppositeDirection(centerDirection(directionFacing))){
			//user goes the proper direction against the center
			trueDescription = "The ship maintains its distance from the center.";
		}else {
			distanceFromCenter --;
			trueDescription = "The whirlpool pulls the ship in closer.";
		}
			turnCount --;
			
		//change the direction (basically turn left) since user is going in a circle
			directionFacing = centerDirection(directionFacing);
		
			trueDescription += "\nThe ship is pointed to the "+translateDirection(directionFacing)+".";
			setDescription(trueDescription);
		
		//user survives
		if(turnCount == 0) {
			setDescription("The whirlpool disappeared.");
			setContents("x");
			//OPTIONAL: user is launched towards the last direction they were facing
			entered = true;
			respondToKey(newDir);
			setValidKeys("wdsa");
		}
		if(distanceFromCenter <= 0) {
			//user loses
			setDescription("The whirlpool consumes your ship. YOU LOSE.");
		}
	}
	
	public void enter() {
		if(entered){
			setContents("x");
		}else {
			setContents("W");
		}
	}
	
	public String validKeys() {
		return currentValidKeys;
	}
	
	//causes glitches if respondToKey is overridden
}

