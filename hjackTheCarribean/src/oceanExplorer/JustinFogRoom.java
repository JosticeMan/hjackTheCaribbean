package oceanExplorer;

public class JustinFogRoom extends CaveRoom {
	
	//The objective of this room is to recall from memory which path to choose and then get out of the fog.
	//If the player makes the wrong move, they will crash and the game ends.
	
	private int visited;
	private static boolean inRoom; //This allows the inventory class to know if we're currently in the room or not 
	private static boolean isClear;
	private int turns; //This will allow us to know how many entries the user has to make until the fog disappears
	
	private String visitedDescription;

	public JustinFogRoom(String description, int turns) {
		super(description + "\n");
		
		this.inRoom = false;
		this.isClear = false;
		this.turns = turns;
		this.visited = -1;
		this.visitedDescription = "You've wandered into area where you were previously surrounded by fog!";
	}

	public void crashedShip() {
		CaveExplorer.setPlaying(false);
		CaveExplorer.printGameOver();
	}

	public static boolean isOccupied() {
		return inRoom;
	}
	
	public static boolean isClear() {
		return isClear;
	}
	//-------------------------
	//Override Methods below
	//-------------------------
	
	public void nothingCanBeDone() {
		crashedShip();
	}
	
	public void setDirections() {
		super.setDirection("Shipmate: The pathways are barely visible. Capt, we don't know if any passage is safe.");
	}
	
	public void leave() {
		super.leave();
		this.inRoom = false;
	}
	
	public void enter() {
		super.enter();
		this.visited++; //This allows the program to track whether or not the player has visited the room
		this.inRoom = true;
	}
	
	public String getDescription() {
		if(this.visited > this.turns) {
			return visitedDescription;
		}
		if(this.visited == this.turns) {
			this.isClear = true;
			return visitedDescription + "\n It appears that the fog has cleared up!";
		}
		if(this.visited > 0) {
			return visitedDescription + "\n It appears that the fog still hasn't cleared up!";
		}
		return super.getDescription();
	}
	
	public String getContents() {
		if(this.visited >= 0) {
			return "F";
		}
		return " ";
	}
}
