package oceanExplorer;

public class DanRoom extends CaveRoom {

	private int hp;
	private int max;
	
	public DanRoom(String description) 
	{
		super(description);
		max = 25;
	}

	public String validKeys() {
		return "wdsae";
	}
	
	public void printAllowedEntry() {
		System.out.println("You can only enter 'w', 'a', 's', or 'd' to move or, you can enter 'e' to check your ship.");
	}
	
	
	public void performAction(int direction) 
	{
		if(direction == 4) 
		{
			CaveExplorer.print("Your health was "+CaveExplorer.inventory.getBeginningShip().getHp()+" points.");
			CaveExplorer.print(restoreHealth());
			CaveExplorer.print("Your health is now "+CaveExplorer.inventory.getBeginningShip().getHp()+" points.");
		}
		else
		{
			CaveExplorer.print("That key does nothing.");
		}
	}
	
	
	public String restoreHealth() 
	{
		CaveExplorer.inventory.getBeginningShip().setHp(max);
		return "Your health has been restored.";
	}
}
