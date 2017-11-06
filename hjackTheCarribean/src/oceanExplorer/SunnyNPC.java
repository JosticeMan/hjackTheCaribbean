package oceanExplorer;

import battleshipJYSK.*;

public class SunnyNPC extends NPC {

	
	/*
	 They're as follow: (Their code will be handled by my backend class in the battelship package
	 BoinkRader - Gives the player a general idea of where one of the opponent's ship is. 
	 CriticalMissile - Sets a missile off that will guarantee a hit on a boat in a turn but the user cannot do anything in that time. 
    Stormcaller - The opponent's battleships are surrounded by bad weather and unable to make a player for one turn. 
	*/
	
	private String[] powerUps;
	private String[] responses;
	
	public SunnyNPC() {
		// TODO Auto-generated constructor stub
		String[] powerUpListDesc = {"The BoinkRadar is a power up that shows the general location of an enemy's ship! Arrr!",
									"CriticalMissile it a power up that gives the player a guarenteed hit on one of the enemy's ships!",
									"Stormcaller creates a massive storm around the enemy, making them unable to do anything for one turn!"};
		powerUps = powerUpListDesc;
		
		String[] positiveResp = {"yes", "sure", "okay"};
		String[] negativeResp = {"no", "nope", "nah"};
		
		
	}
	
	public void sayRandomPower()
	{
		CaveExplorer.randomText(powerUps);
	}
	
	public void interact() {
		CaveExplorer.print("Argghh! Did you know there are multiple power ups? Would you like to hear some?");
		String response = CaveExplorer.in.nextLine();
		while(!response.equalsIgnoreCase("bye")) {
			CaveExplorer.print(powerUps[(int) (Math.random()*powerUps.length)]);
			response = CaveExplorer.in.nextLine();
		}
		CaveExplorer.print("Well, that was fun. Later!");
	}

}
