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
	private boolean winner;
	
	private String[] commanderGreetings;
	
	public JustinSunnyCommander(String name, int level) {
		this.level = level;
		this.name = name;
		
		String[] tempCG = {this.name + ": How dare you step in " + this.name + "'s territory! Prepare yourself!", this.name + ": Who dares challenges me? The Great " + this.name + "!?", this.name + ": You want this land? Come and get some!"};
		commanderGreetings = tempCG;
	}
	
	public void fight(String user) {
		this.winner = BackEndJustinY.startBattle(level, user); //This starts the battle
	}
	
	//-------------------------
	//Override Methods below
	//-------------------------
	
	public void interact() {
		CaveExplorer.randomText(commanderGreetings);
		CaveExplorer.print(this.name + ": Who is it that challenges me?");
		String response = CaveExplorer.in.nextLine();
		fight(response);
		//Insert code about handling the winner of the game and level switch here!
	}
	
}
