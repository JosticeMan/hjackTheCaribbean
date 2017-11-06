package oceanExplorer;

public class StevenRoom extends CaveRoom {

	public StevenRoom(String description) {
		super(description);
	}
	public String validKeys() {
		return "wdsaef";
	}
	
	public void printAllowedEntry() {
		System.out.println("You can only enter 'w', 'a', 's', or 'd' to move or, you can enter 'e' to check your ship.");
	}
	
	
	public void performAction(int direction) {
		if(direction==4) {
			CaveExplorer.print(getDescription());
			CaveExplorer.print("You may press f to upgrade your ship");
		}
		else if(direction==5&&CaveExplorer.inventory.getBeginningShip()!=null){
			CaveExplorer.inventory.getBeginningShip().setAttack(CaveExplorer.inventory.getBeginningShip().getAttack()+1);
			CaveExplorer.inventory.getBeginningShip().setHp(CaveExplorer.inventory.getBeginningShip().getHp()+1);
			CaveExplorer.inventory.getBeginningShip().setSpeed(CaveExplorer.inventory.getBeginningShip().getSpeed()+1);
			CaveExplorer.print(getDescription());
		}
		else {
			CaveExplorer.print("That key does nothing.");
		}
	}
	public String getContents() {
		return "S";
	}
	
	public String getDescription() {
		if(CaveExplorer.inventory.getBeginningShip()==null) {
			return "You do not have a ship. Please go and get one.";
		}
		else {
			return "Your ship's stats is:\nAttack: "+CaveExplorer.inventory.getBeginningShip().getAttack()+"\nHealth: "+CaveExplorer.inventory.getBeginningShip().getHp()+"\nSpeed: "+CaveExplorer.inventory.getBeginningShip().getSpeed();
		}
	}
	
	

}
