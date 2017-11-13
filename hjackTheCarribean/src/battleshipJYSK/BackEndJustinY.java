package battleshipJYSK;

import oceanExplorer.*;

public class BackEndJustinY implements SunnySupporter {

	//THIS PART OF THE CODE IS TO BE PRIMARILY MANAGED BY JUSTIN YAU 
	//PERIOD 4 & 5 

	/* 
	 PLANNING:
	 
	 GENERAL: 
	 Commanders will be fought at the end of 3 levels. Beating a commander will reward a random ship to the player.
	 There will be 3 levels of Commanders that the player will face:
	 Level 1: Commander will randomly attack the player's field (not in the hit places)  
	 Level 2: Commander will remember places that he attacked and not attack areas around the areas hit
	 Level 3: Commander will always hit a ship 
	 ------------------------------------------
	 
	 FOR THE GAMEBOARD:
	 Use 2D arrays to keep track of the ship placements and areas hit. 
	 ------------------------------------------
	 KEY: 
	 X - Areas hit
	 (A-Z) - A ship with special properties
	 ------------------------------------------
	 [X], [ ], [ ], [ ], [ ],
	 [A], [A], [ ], [B], [ ],
	 [ ], [ ], [ ], [B], [ ],
	 
	 ------------------------------------------
	 FOR THE PLAYER TO SELECT THEIR SHIP POSITION:
	 Have the player enter the coordinates of one end of the ship and ask whether they want it to face NORTH, EAST, SOUTH, OR WEST
	 Do not allow the player to place ships on top of each other.
	 
	 Referee: Where would you like to place one end of your ship, (replace with ship name here), with size 2 (replace size here)?
	 User inputs [1,2]
	 Would you like to make it face NORTH, EAST, SOUTH, OR WEST?
	 User inputs NORTH
	 
	 Referee: Here's your ship placement. Are you happy with your choice?
	 [ ], [ ], [X], [ ], [ ],
	 [ ], [ ], [X], [ ], [ ],
	 [ ], [ ], [ ], [ ], [ ],
	 
	 SPECIAL CASE:
	 Referee: Where would you like to place one end of your ship, (replace with ship name here), with size (replace size here)?
	 User inputs [0,0]
	 Would you like to make it face SOUTH or EAST?
	 ------------------------------------------
	 TO SELECT AN AREA TO BE HIT:
	 Have the player input the coordinates of the place they would like to hit
	 Do not allow the player to hit the same place again 
	 Display H in every spot of where a ship used to be if the player did manage to sink a ship
	 ------------------------------------------
	 KEY:
	 X - Place to nuke
	 H - Marks where the player already striked
	 S - Areas that the player hit a ship
	 ------------------------------------------
	 
	 Referee: Where would you like to missile?
	 [H], [ ], [H], [S], [ ],
	 [ ], [ ], [ ], [ ], [ ],
	 [ ], [ ], [ ], [ ], [ ],	 
	 
	 ------------------------------------------
	 POWERUPS:
	 Track powerups in another 2D Array (Should be retrieved from another class) and when the user triggers the powerup during the turn, activate effect
	 ------------------------------------------
	 PLANNED POWERUPS:
	 BoinkRadar - Gives the player a general idea of where one of the opponent's ship is. 
	 CriticalMissile - Sets a missile off that will guarantee a hit on a boat in a turn but the user cannot do anything in that time. 
     Stormcaller - The opponent's battleships are surrounded by bad weather and unable to make a player for one turn. 
	 ------------------------------------------
	 IF TIME ALLOWS IT: 
	 Give commanders a special fig (icon) so that the dialogues are more appealing, cosmetic wise. 
	 ------------------------------------------
	*/
	
	private JustinSupporter frontend;

	private static JustinSunnyPlot[][] thePlayerGameBoard; //This will monitor the game board of the player
	private static JustinSunnyPlot[][] theOpponentGameBoard; //This will monitor the game board of the AI
	
	public BackEndJustinY(JustinSupporter frontend) {
		this.frontend = frontend;
	}
	
	/**
	 * This generates a new 2D array of the appropriate board size; 
	 */
	public void generateMap() {
		int dimension = 5+(frontend.getCommanderLevel() - 1);
		thePlayerGameBoard = new JustinSunnyPlot[dimension][dimension];
		theOpponentGameBoard = new JustinSunnyPlot[dimension][dimension];
	}
	
	public JustinSunnyPlot[][] getPlayerPlots() {
		return thePlayerGameBoard;
	}
	
	public JustinSunnyPlot[][] getCommanderPlots() {
		return theOpponentGameBoard;
	}
	
	/**
	 * Returns whether or not the player has at least one of the appropriate powerup
	 * @param type
	 * @return
	 */
	public static boolean handlePowerUp(int type) {
		return oceanExplorer.Inventory.getBossPowerUps()[type] > 0;
	}
	
	public static void decrementPowerUp(int type) {
		int[] newPowerUpCount = oceanExplorer.Inventory.getBossPowerUps();
		newPowerUpCount[type]--;
		oceanExplorer.Inventory.setBossPowerUps(newPowerUpCount);
	}
	
}
