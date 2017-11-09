package oceanExplorer;

public class DanFountainRoom extends CaveRoom {

	private int hp;
	private int max;
	private boolean isUsed;
	
	public DanFountainRoom(String description) 
	{		
		super(description);		
		max = 25;
		isUsed = false;
	}

	public String validKeys() {
		return "wdsae";
	}
	
	public void printAllowedEntry() {
		System.out.println("You can only enter 'w', 'a', 's', or 'd' to move or, you can enter 'e' to check your ship.");
	}
	
	
	public void performAction(int direction) 
	{
		if(direction == 4 && !isUsed) 
		{
			restoreHealth();
		}
		else 
		{
			CaveExplorer.print("That key does nothing.");
		}
	}
	
	
	public String restoreHealth() 
	{
		if (!isUsed)
		{
			CaveExplorer.print("Your health was "+CaveExplorer.inventory.getBeginningShip().getHp()+" points.");
			CaveExplorer.print(restoreHealth());
			CaveExplorer.print("Your health is now "+CaveExplorer.inventory.getBeginningShip().getHp()+" points.");
			CaveExplorer.inventory.getBeginningShip().setHp(max);
			isUsed = true;
			return "Your health has been restored.";
		}
		else
		{
			return "The fountain is dry...";
		}
	}
}
