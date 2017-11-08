package oceanExplorer;

public class KevinRoom extends CaveRoom {

	public KevinRoom(String description) {
		super(description);
	}
	public String validKeys() {
		return "wdsa1234";
	}
	
	public void printAllowedEntry() {
		System.out.println("You can only enter 'q', 'w', 'e', or num keys 1-4 to interact.");
	}
	public void performAction(int direction) {
		if(direction <4) {
			super.performAction(direction);
		}
		else {
			CaveExplorer.inventory.addItems(direction-3);
			System.out.println("You have "+CaveExplorer.inventory.numItems()+ " items."); 
		}	
	} 
	public String getContents() {
		return "K";
	}
	public String getDescription() {
		return super.getDescription() + "\n In this room, you can some number of an item based on what keys to pressed!";
		
	}
	
}