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
	private int repeatCount;
	
	private String[] commanderGreetings;
	private String[] commanderAngerName;
	
	public JustinSunnyCommander(String name, int level) {
		this.level = level;
		this.name = name;
		this.repeatCount = 0;
		
		String[] tempCG = {this.name + ": How dare you step in " + this.name + "'s territory! Prepare yourself!", this.name + ": Who dares challenges me? The Great " + this.name + "!?", this.name + ": You want this land? Come and get some!"};
		commanderGreetings = tempCG;
		String[] tempCAN = {this.name + ": Psh! You think you can fight me with no name!? State your name mysterious one.", this.name + ": Come on! Don't be like that!", this.name + ": You're really making me angry!"};
		commanderAngerName = tempCAN;
	}
	
	/**
	 * This method tells the class to run our pair package's main class which will start the battleship game between the two players
	 * @param user - UserName of the player that the AI is going to face
	 */
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
		while(response.isEmpty() && repeatCount < 3) {
			CaveExplorer.print(commanderAngerName[repeatCount]);
			response = CaveExplorer.in.nextLine();
			repeatCount++;
		}
		fight(response); //MAKE SURE TO INCORPORATE UNIQUE BEHAVIOR FOR EMPTY RESPONSES
		//Insert code about handling the winner of the game and level switch here!
	}
	
}
