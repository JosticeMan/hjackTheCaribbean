package battleshipJYSK;

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
	 Have the player navigate through their side of the arena and highlight the ship position with X
	 Do not allow the player to place ships on top of each other.
	 
	 [X], [ ], [ ], [ ], [ ],
	 [X], [ ], [ ], [ ], [ ],
	 [ ], [ ], [ ], [ ], [ ],
	 
	 ------------------------------------------
	 TO SELECT AN AREA TO BE HIT:
	 Have the player navigate through the enemy side of the arena and highlight the area with X 
	 Do not allow the player to hit the same place again 
	 Display H in every spot of where a ship used to be if the player did manage to sink a ship
	 ------------------------------------------
	 KEY:
	 X - Place to nuke
	 H - Marks where the player already striked
	 ------------------------------------------
	 	 
	 [H], [ ], [H], [ ], [ ],
	 [X], [ ], [ ], [ ], [ ],
	 [ ], [ ], [ ], [ ], [ ],	 
	 
	 ------------------------------------------
	 POWERUPS:
	 Track powerups in another 2D Array (Should be retrieved from another class) and when the user triggers the powerup during the turn, activate effect
	 ------------------------------------------
	 PLANNED POWERUPS:
	 BoinkRader - Gives the player a general idea of where one of the opponent's ship is. 
	 CriticalMissile - Sets a missile off that will guarantee a hit on a boat in a turn but the user cannot do anything in that time. 
     Stormcaller - The opponent's battleships are surrounded by bad weather and unable to make a player for one turn. 
	 ------------------------------------------
	 IF TIME ALLOWS IT: 
	 Give commanders a special fig (icon) so that the dialogues are more appealing, cosmetic wise. 
	 ------------------------------------------\
	 TEST
	*/
	
	private static boolean playing;

	private static int commanderLevel;
	private static boolean isPlayerTurn;
	
	private static String[][] thePlayerGameBoard;
	private static String[][] theOpponentGameBoard;
	
	public BackEndJustinY() {
		playing = false;
	}
	
	public static void startBattle(int gameLevel) {
		commanderLevel = gameLevel;
		playing = true;
		generateMap(); //Generate the opponent and player game board for the game to begin
		determineFirstTurn(); //Essentially a coin flip that determines who makes the first move
		while(playing) {

		}
		commanderLevel++;
	}
	
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
	
	public String[][] getThePlayerGameBoard() {
		return thePlayerGameBoard;
	}
	
	public String[][] getTheOpponentGameBoard() {
		return theOpponentGameBoard;
	}
	
}
