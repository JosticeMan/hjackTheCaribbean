package battleshipJYSK;

import oceanExplorer.*;

public class BackEndJustinY {

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
	
	private static boolean playing;

	private static int commanderLevel; //This is essentially the difficulty level of the commander
	private static boolean isPlayerTurn; //This tracks whose turn it is
	private static String userName; //Username of the player
	
	private static int[][] playerShips; //This contains the coordinates of all the player's ships
	private static int[][] commanderShips; //This contains the coordinates of all the AI's ships
	
	//THIS IS NOT THE BEST WAY TO IMPLEMENT THIS
	//Could implement Lists/Sets in this scenario to make the Algorithm more efficient
	private static int[][] playerHitMarks; //This will track all the places that the commander hit 
	private static int[][] commanderHitMarks; //This will track all the places that the player hit
	
	private static String[][] thePlayerGameBoard; //This will monitor the game board of the player
	private static String[][] theOpponentGameBoard; //This will monitor the game board of the AI
	
	public BackEndJustinY() {
		playing = false;
	}
	
	/**
	 * Main method that will handle the main components of the game between the commander and player
	 * @param gameLevel - Difficulty of Commander
	 * @param user - Name of the player
	 * @return
	 */
	public static boolean startBattle(int gameLevel, String user) {
		commanderLevel = gameLevel;
		playing = true;
		userName = user;
		generateMap(); //Generate the opponent and player game board for the game to begin
		determineFirstTurn(); //Essentially a coin flip that determines who makes the first move
		while(playing) {

		}
		commanderLevel++;
		return false;
	}
	
	/**
	 * This method flips a coin that determines who makes the first move
	 */
	public static void determineFirstTurn() {
		if(Math.random() < .50) {
			isPlayerTurn = true;
		}
		isPlayerTurn = false;
	}
	
	/**
	 * This generates a new 2D array of the appropriate board size; 
	 */
	public static void generateMap() {
		int dimension = 5+(commanderLevel - 1);
		thePlayerGameBoard = new String[dimension][dimension];
		theOpponentGameBoard = new String[dimension][dimension];
	}
	
	public static String[][] getThePlayerGameBoard() {
		return thePlayerGameBoard;
	}
	
	public static String[][] getTheOpponentGameBoard() {
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
