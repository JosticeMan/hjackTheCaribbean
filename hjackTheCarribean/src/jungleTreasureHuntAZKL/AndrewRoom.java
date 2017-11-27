package jungleTreasureHuntAZKL;

import oceanExplorer.CaveRoom;

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
				trueDescription = "Shipmate: Arghh, the sea be opening an entrance to Davy Jones' Locker.";
				turnCount = (int)(Math.random()*6)+4;
				distanceFromCenter = turnCount-1; //user will have to input the right thing at least once to survive
				/**
				 * Whirlpool will always spin counter clock-wise (coriolis effect in S Hemisphere)
				 * So the center will always be to the left and user will be moving left
				 */
				directionFacing = (int)(Math.random()*4);
				trueDescription += "\n Shipmate:The ship be pointing to the "+translateDirection(directionFacing)+"."; //always tell the user their direction
				setDescription(trueDescription);
				setValidKeys("----wdsa");
			}else {
				setDescription("Shipmate: This used to be where we fought the sea and lived to tell the tale.");
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
			trueDescription = "Shipmate: Cap'n be bringing us closer to Jones' locker!";
		}else if(newDir == oppositeDirection(centerDirection(directionFacing))){
			//user goes the proper direction against the center
			trueDescription = "Shipmate: Yo-ho-ho, we be keep'n the locker at bay.";
		}else {
			distanceFromCenter --;
			trueDescription = "Avast, we can be doin' better Cap'n!";
		}
			turnCount --;
			
		//change the direction (basically turn left) since user is going in a circle
			directionFacing = centerDirection(directionFacing);
		
			trueDescription += "\nShipmate: The ship be pointing to the "+translateDirection(directionFacing)+".";
			setDescription(trueDescription);
		
		//user survives
		if(turnCount == 0) {
			setDescription("Shipmate: Aye, the sea be calm now.");
			setContents("x");
			//OPTIONAL: user is launched towards the last direction they were facing
			entered = true;
			respondToKey(newDir);
			setValidKeys("wdsa");
		}
		if(distanceFromCenter <= 0) {
			//user loses
			setDescription("Shipmate: CAP'N! NOT THE LOCKER! YOU LOSE.");
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

