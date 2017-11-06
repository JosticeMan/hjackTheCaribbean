package oceanExplorer;

public class StevenRoom extends CaveRoom {

	public StevenRoom(String description) {
		super(description);
	}
	public String validKeys() {
		return "wdsae";
	}
	
	public void printAllowedEntry() {
		System.out.println("You can only enter 'w', 'a', 's', or 'd' to move or, you can enter 'e' to check your ship.");
	}
	
	
	public void performAction(int direction) {
		if(direction==4) {
			CaveExplorer.print(getDescription());
		}
		else {
			CaveExplorer.print("That key does nothing.");
		}
	}
	
	public String getContents() {
		if(containsNPC() && presentNPC.isActive()) {
			return "M";
		}
		else {
			return super.getContents();
		}
	}
	
	public String getDescription() {
		if(CaveExplorer.inventory.getBeginningShip()==null) {
			return "You do not have a ship. Please go and get one.";
		}
		else {
			return "Your ship's stats is:\nAttack: "+CaveExplorer.inventory.getBeginningShip().getAttack()+"\n"
		}
		
	}

}
