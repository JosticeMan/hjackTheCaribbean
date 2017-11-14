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
	 Would you like to make it face NORTH (N) , EAST (E) , SOUTH (S), OR WEST (W)?
	 User inputs N
	 
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
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public BackEndJustinY(JustinSupporter frontend) {
		this.frontend = frontend;
		generateMap();
	}
	
	/**
	 * Return the number of ships that the user has
	 * @return
	 */
	public int numberOfShips() {
		return 1; //Should be something like CaveExplorer.inventory.getShips();
	}
	
	/**
	 * Return the length of one given ship that the user has 
	 * HELPER METHOD to assist when plotting the ships
	 * @param e - The ship that the user wants to place
	 * @return
	 */
	public int lengthofShip(Ship e) {
		return ((e.getHp() - (e.getHp() % 10)) / 10);
	}
	
	public void placeShip(int row, int col, int direction, Ship e) {
		
	}
	
	public boolean isValidShipPlacement(int row, int col, int direction, Ship e) {
		if(direction == WEST || direction == EAST) {
			
		}
		else {
			//NORTH AND SOUTH HERE
		}
		return true;
	}
	
	/**
	 * Interprets the user input and returns the corresponding direction to which the user will want to place his ship at a certain coordinate
	 * @return
	 */
	public int interpretDirectionInput() {
		String input = CaveExplorer.in.nextLine();
		if(!isValid(input)) {
			printValidEntries();
			input = CaveExplorer.in.nextLine();
		}
		return determineDirection(input, validEntries());
	}
	
	/**
	 * Similar method to the one in Cave Room, this method tells the user the keys and the directions they represent. 
	 */
	public void printValidEntries() {
		CaveExplorer.print("Captain Duran: You are only allowed to type 'n' for NORTH, 'e' for EAST, 's' for SOUTH, and 'w' for WEST!");
	}
	
	/**
	 * String of all the valid inputs for the direction input
	 * @return
	 */
	public String validEntries() {
		return "neswNESW";
	}
	
	public boolean isValid(String input) {
		String validInput = validEntries();
		return validInput.indexOf(input) > - 1 && input.length() == 1;
	}

	public int determineDirection(String input, String key) {
		return key.indexOf(input) % 4;
	}
	
	/**
	 * This generates a new 2D array of the appropriate board size; 
	 */
	public void generateMap() {
		int dimension = 5+(frontend.getCommanderLevel() - 1);
		thePlayerGameBoard = new JustinSunnyPlot[dimension][dimension];
		theOpponentGameBoard = new JustinSunnyPlot[dimension][dimension];
	}
	
	/**
	 * This method will return the coordinates that the user inputs
	 * @return
	 */
	public int[] getCoordInput() {
		String input = CaveExplorer.in.nextLine();
		int[] coords = toCoords(input);
		while(coords == null){
			CaveExplorer.print("Captain Duran: You must enter cordinates of the form:\n          <row>,<col>"
					+ "\n<row> and <col> should be integers.");
			input = CaveExplorer.in.nextLine();
			coords = toCoords(input);
		}
		return coords;
	}
	
	/**
	 * This method will convert the user input into an integer array for processing by the program
	 * @param input
	 * @return
	 */
	private int[] toCoords(String input) {
		try{
			int a = Integer.parseInt(input.substring(0,1));
			int b = Integer.parseInt(input.substring(2,3));
			if(input.substring(1,2).equals(",") && input.length() ==3){
				int[] coords = {a,b};
				return coords;
			}
			else
			{
				return null;
			}
		}catch(Exception e){
			return null;
		}
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
	
	/**
	 * Decrements the count of a particular power up
	 * @param type
	 */
	public static void decrementPowerUp(int type) {
		int[] newPowerUpCount = oceanExplorer.Inventory.getBossPowerUps();
		newPowerUpCount[type]--;
		oceanExplorer.Inventory.setBossPowerUps(newPowerUpCount);
	}
	
}
