package oceanExplorer;

public class JustinBossRoom extends CaveRoom {

	private JustinSunnyCommander currentCommander;
	
	public JustinBossRoom(String description, int level) {
		super(description); //This manages the description
		currentCommander = new JustinSunnyCommander("Johnny", level);
	}
	
	//-------------------------
	//Override Methods below
	//-------------------------
	
	public String validKeys() {
		return "wsae";
	}
	
	public void printAllowedEntry() {
		System.out.println("You can only enter 'w', 'a', or 's' to run or, you can enter 'e' to start the game!");
	}
	
	public int numberOfDirections() {
		return 3;
	}
	
	public int manageCurrentRoomShift(int direction) {
		if(direction > 0) {
			return direction + 1;
		}
		return direction;
	}
	
	public void performAction(int direction) {
		if(direction == 3) {
		    currentCommander.interact();
		} else {
			System.out.println("Random Sailor: Are you really that afraid!? What are you pressing?");
		}
	}
	
	public String getContents() {
		return "B";
	}
	
	/*
	 public String getDescription() {
		if(containsNPC() && !presentNPC.isActive()) {
			return super.getDescription() + "\n" + presentNPC.getInactiveDescription();
		} else {
			String npcDesc = "";
			if(presentNPC != null) {
				npcDesc = presentNPC.getActiveDescription();
			}
			return super.getDescription() + "\n" + npcDesc;
		}
	}
	*/
}
