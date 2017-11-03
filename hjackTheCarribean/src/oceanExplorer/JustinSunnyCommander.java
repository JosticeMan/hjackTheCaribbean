package oceanExplorer;

import battleshipJYSK.*;

public class JustinSunnyCommander extends NPC {

	/*
	 Level 1: Commander will randomly attack the player's field (not in the hit places)  
	 Level 2: Commander will remember places that he attacked and not attack areas around the areas hit
	 Level 3: Commander will always hit a ship  
	*/
	private int level;
	private String name;
	
	public JustinSunnyCommander(String name, int level) {
		this.level = level;
		this.name = name;
	}
	
	public void fight() {
		BackEndJustinY.startBattle(level); //This starts the battle
	}
	
	//Override methods
	
	public void interact() {
		
	}
	
}
