package oceanExplorer;

import battleshipJYSK.*;

public class JustinCommander extends NPC {

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
	private String[] commanderAskName;
	
	public JustinCommander(String name, int level) {
		this.level = level;
		this.name = name;
		this.repeatCount = 0;
		this.winner = false;
		
		String[] tempCG = {this.name + ": How dare you step in " + this.name + "'s territory! Prepare yourself!", this.name + ": Who dares challenges me? The Great " + this.name + "!?", this.name + ": You want this land? Come and get some!"};
		commanderGreetings = tempCG;
		String[] tempCAN = {this.name + ": Psh! You think you can fight me with no name!? State your name mysterious one.", this.name + ": Come on! Don't be like that!", this.name + ": You're really making me angry!"};
		commanderAngerName = tempCAN;
		String[] tempCASK = {this.name + ": I believe you haven't stated your name yet. May you tell me?", this.name + ": Avast from afar! Who is it that challenges me?", this.name + ": Glory to Britain! Down to... What's your name again?"};
		commanderAskName = tempCASK;
	}
	
	/**
	 * This method tells the class to run our pair package's main class which will start the battleship game between the two players
	 * @param user - UserName of the player that the AI is going to face
	 */
	public void fight(String user) {
		this.winner = FrontEndSunnyK.play(level, user, name); //This starts the battle
	}
	
	/**
	 * This method will handle the operations involving the boss battle reward/loss message and transition to the next level or restart. 
	 */
	public void processEndBattle() {
		if(winner) {
			if(CaveExplorer.getLevel() == 3) {
				CaveExplorer.setPlaying(false);
				CaveExplorer.incrementLevel();
				CaveExplorer.printVictory();
				CaveExplorer.printEnd();
			}
			else {
				CaveExplorer.setPlaying(false);
				CaveExplorer.incrementLevel();
				CaveExplorer.printVictory();
				System.out.println(CaveExplorer.getLevel());
				CaveRoom.setUpCaves(CaveExplorer.getLevel());
				CaveExplorer.setPlaying(true);
				CaveExplorer.inventory.updateMap();
				CaveExplorer.startExploring();
			}
		}
		else {
			CaveExplorer.setPlaying(false);
			CaveExplorer.printGameOver();
		}
	}
	
	//-------------------------
	//Override Methods below
	//-------------------------
	
	public void interact() {
		CaveExplorer.randomText(commanderGreetings);
		CaveExplorer.print(commanderAskName[level - 1]);
		String response = CaveExplorer.in.nextLine();
		while(response.isEmpty() && repeatCount < 3) {
			CaveExplorer.print(commanderAngerName[repeatCount]);
			response = CaveExplorer.in.nextLine();
			repeatCount++;
		}
		fight(response); //MAKE SURE TO INCORPORATE UNIQUE BEHAVIOR FOR EMPTY RESPONSES
		processEndBattle();//Insert code about handling the winner of the game and level switch here!
	}
	
}
