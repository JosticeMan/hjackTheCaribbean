package oceanExplorer;

public class AndrewRoom extends CaveRoom {

	private CaveRoom[] borderingRooms;
	private String currentDirections;

	public AndrewRoom(String description) {
		super(description);
		currentDirections = "sawdfe";
	}
	
	public void forceGoLast() {
		CaveExplorer.currentRoom.leave();
		CaveExplorer.currentRoom = borderingRooms[CaveExplorer.inventory.getLastDirection()];
		CaveExplorer.currentRoom.enter();
		CaveExplorer.inventory.updateMap();
	}
	
	public void changeValidKeys() {
		currentDirections = "wasdfe";
	}
	//OVERIDE
	
	public String validKeys() {
		return currentDirections;
	}
	
	public void printAllowedEntry() {
		System.out.println("You can only enter 'w', 'a', 's', or 'd'");
	}
	
	public void performAction(int direction) {
		if(direction == 4) {
			System.out.println("You've reversed the spell!");
			changeValidKeys();
		}else if(direction == 5) {
			forceGoLast();
		}else{
			System.out.println("Try pressing f.");
		}
	}
	
	public void enter() {
		setContents("x");
	}
	

}

