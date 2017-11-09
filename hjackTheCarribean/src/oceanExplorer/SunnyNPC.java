package oceanExplorer;

public class SunnyNPC extends NPC {

	
	/*
	 They're as follow: (Their code will be handled by my backend class in the battelship package
	 BoinkRader - Gives the player a general idea of where one of the opponent's ship is. 
	 CriticalMissile - Sets a missile off that will guarantee a hit on a boat in a turn but the user cannot do anything in that time. 
    Stormcaller - The opponent's battleships are surrounded by bad weather and unable to make a player for one turn. 
	*/
	
	private boolean active = true;
	private String[] powerUps;
	private String activeDescription;
	private String inactiveDescription;
	
	
	public SunnyNPC() {
		String[] powerUpListDesc = {"The BoinkRadar is a power up that shows the general location of an enemy's ship! Arrr!",
									"CriticalMissile it a power up that gives the player a guarenteed hit on one of the enemy's ships!",
									"Stormcaller creates a massive storm around the enemy, making them unable to do anything for one turn!"};
		powerUps = powerUpListDesc;
		this.activeDescription = "   There is a suspicious old man fishing on a raft here.";
		this.inactiveDescription = "   The very talkative man you spoke to earlier is still fishing here.";
		
	}
	
	public boolean isActive()
	{
		return active;
	}
	 
	public void sayRandomPower()
	{
		CaveExplorer.randomText(powerUps);
	}
	
	public void interact() {
		CaveExplorer.print("Argghh! Did you know there are multiple power ups? Would you like to hear some?");
		String response = CaveExplorer.in.nextLine();
		if(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("sure") || response.equalsIgnoreCase("okay"))
		{
			while(!response.equalsIgnoreCase("bye")) 
			{
				if(!response.equals(""))
				{
					sayRandomPower();
					response = CaveExplorer.in.nextLine();
				}
				else
				{
					CaveExplorer.print("So, that doesn't interest you harghhh? How about...");
					sayRandomPower();
					response = CaveExplorer.in.nextLine();
				}
			}
			//active = false;
		}
		else
		{
			CaveExplorer.print("Arrr, fine! I didn't want to tell you about power ups anyway.");
		}
		active = false;
	}
	public String getInactiveDescription() {
		return inactiveDescription;
	}

	public String getActiveDescription() {
		return activeDescription;
	}

}
