package oceanExplorer;

public class DanTrapRoom extends CaveRoom {

	private int ctr;
	public DanTrapRoom(String description) 
	{
		super(description);
		

	}

	public String getDescription() 
	{
		if(CaveExplorer.inventory.getBeginningShip() != null) 
		{
			if (ctr < 2)
			{
				CaveExplorer.inventory.getBeginningShip().setHp(CaveExplorer.inventory.getBeginningShip().getHp()-10);
				ctr++;
				return "You've been bitten by a sea rat! -10 HP.";
			}
			else
			{
				return "There is nothing here.";
			}
		}
		
		return "";
	}

}
