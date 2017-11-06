package oceanExplorer;

public class DanRoom extends CaveRoom {

	private int hp;
	private int max;
	
	public DanRoom(String description) 
	{
		super(description);
		hp = 25;
	}

	public String validKeys() {
		return "wdsae";
	}
	
	public void printAllowedEntry() {
		System.out.println("You can only enter 'w', 'a', 's', or 'd' to move or, you can enter 'e' to check your ship.");
	}
	
	
	public void performAction(int direction) {
		if(direction==4) 
		{
		 
			CaveExplorer.print(restoreHealth());
		}

		else
		{
			CaveExplorer.print("That key does nothing.");
		}
	}
	
	
	public String restoreHealth() 
	{
		hp = CaveExplorer.inventory.getBeginningShip().getHp();;
		return "Your health has been restored.";
	}
}
