package oceanExplorer;

import battleshipJYSK.*;

public class SunnyNPC extends NPC {

	
	private String[] powerUps;
	private String[] responses;
	
	public SunnyNPC() {
		// TODO Auto-generated constructor stub
		String[] powerUpList = {};
		powerUps = powerUpList;
		
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
		while(!response.equalsIgnoreCase("yes")) {
			 
			response = CaveExplorer.in.nextLine();
		}
		CaveExplorer.print("Well, that was fun. Later!");
	}

}
