package jungleTreasureHuntAZKL;
/*
 * PLANNING:
 * Basic Treasure Hunting Mini-Game
 * 	Player goal is to reach the treasure as they traverse through a jungle map
 *  with a limited amount of steps and when they reach the treasure they will
 *  recieve a powerup that will assist them in the Boss Battles.
 * 
 * GAMEPLAY:
 * -Player will be able to move around with wasd <- keep track of position
 * -Player will have a limited amount of vision <- a sight radius done in frontend
 * -Player will have a limited amount of moves <- keep track of steps
 * -Player will have a limited amount of observations <- keep track of observes (can be considered taking a step)
 * -Player has to avoid monkeys and traps which will either kill, delay, or disorient the player
 * 
 * NEEDED:
 * 	2D Array to track map info
 * 	A player that moves through the map
 * 	Tiles that the map is made of
 * 	Objects that go within the Tile to create attributes of the map
 * 
 * DESIGN (ultimately up to Kevin):
 *    This is a 7x12 map
 * X X X X X X X X X X X X X X
 * 
 * X                         X
 * 
 * X                T  X     X
 *                              //only path would be through the F
 * X R  R  R  T  F  T  T  R  X  //Player wont be able to see this outside
 *   x
 * XxLx                      X
 *  x  x
 * XxL Lx                    X
 *  x    x
 * XxL L Lx                  X
 *  x      x
 * XxP L L Lx                X
 *  xxxxxxxxxxxx
 * X X X X X X X X X X X X X X
 *  //Below is the full radius of what they'll be able to see and what empty tiles will look like
 * X X X X X X X X X X X X X X   X X X X X X X X X X X X X X
 *           x                                             
 * X        x x              X   X                         X
 *         x   x                                             
 * X      x     x            X   X                         X
 *       x       x                                           
 * X    x         x          X   X                         X 
 *     x           x                                          
 * X  x      P      x        X   X                         X 
 *     x           x                                          
 * X    x         x          X   X                         X
 *       x       x                                            
 * X      x     x            X   X                         X
 *         x   x                                              
 * X        x x              X   X                         X
 *           x                                               
 * X X X X X X X X X X X X X X   X X X X X X X X X X X X X X
 * 
 * X X X X X X X X X X X X X X  
 *           x                                             
 * X        x x              X  
 *         x  x                                              
 * X      x   x              X  
 *       x     x                                             
 * X    x      Txx           X  
 *     x          xx            if an object is blocking the line of sight, then the tiles above and to the right wont be visible to the                                        
 * X  x      P L L Lx        X  player, this is why a radius of 3 was chosen, because there is only one
 *     x           x            scenario where the vision radius will appear different 
 * X    x         x          X 
 *       x       x                                            
 * X      xT    x            X
 *         x   x                                              
 * X        x x              X 
 *           x                                               
 * X X X X X X X X X X X X X X
 * 
 * ONE SPACE BETWEEN EACH COLUMN (horizontal aesthetics need modifying)
 * ONE SPACE BETWEEN EACH ROW
 * ^this is to help give a visual aid of the player's
 *  sight radius (represented by small x)
 *  
 *     LEGEND(for me to test):
 *     P = player T = tree
 *     M = monkey F = forage
 *     R = rock 
 *     X in map is treasure
 *     **Things you can't see should just be space tiles.
 *     L = filler tile to help visualize each tile slot
 *     
 *   FEATURES NEEDED:
 *      Some random generation of player spawn, tile occupants, treasure location, monkey spawn
 *      
 */
public class AndrewBackend implements KevinSupport{

	private AndrewSupport frontend;
	
	private AndrewKevinTile[][] map;
	
	//all objects in the game only need the position or 
	//everything is created through the class AndrewKevinTile
	//ROW, COL
	private int[] playerPos;
	private int stepCount;
	
	private int[][] monkeys;
	
	private int[] treasurePos;
	
	/*------CONSTANTS--------*/
	public static final int ROW = 0;
	public static final int COL = 1;
	
	public static final int NOTHING = 0;
	
	//static occupant ints
	public static final int ROCK = 1;
	public static final int TREE = 2;
	public static final int FORAGE = 3;
	public static final int TREASURE = 7;
	public static final int LEAVETILE = 6;
	//non-static occupant ints
	public static final int PLAYER = 1;
	public static final int MONKEY = 2;
	
	//basically caveroom controls
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	public AndrewBackend(AndrewSupport frontend) {
		this.frontend = frontend;
		
		//creates map and populates with a bunch of tiles
		map = new  AndrewKevinTile[12][12];
		for(int row = 0; row < map.length; row++) {
			for(int col = 0; col < map[row].length; col++) {
				map[row][col] = new AndrewKevinTile(row, col);
			}
		}
		
		//creates the boundary of rocks to keep player within map **Will leave on slot open if player just wants to leave**
		createMapRockBorder();
			
		//stepCount is based off the size of the map, for now it'll be 10
		stepCount = 10;
		
		//creates and sets player starting position
		int[] a = new int[2];
		playerPos = a;
		setPlayerPos(map.length-2, 1); //player position will be randomly generated along the border

		//creates and sets monkeys and their positions
		monkeys = new int[5][2]; //first numbers is how many monkeys, 2nd is for their coords
	}
	
	/*---- KEVINSUPPORT METHODS ----*/
	
	public int getStepCount() {
		return stepCount;
	}
	
	public AndrewKevinTile[][] getMap(){
		return this.map;
	}
	
	@Override
	public boolean playing() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object end() {
		// TODO Auto-generated method stub
		return null;
	}

	/*---- MONKEY METHODS ----*/
	
	public void setSpecificMonkeyPos(int idx, int row, int col) {
		monkeys[idx][ROW] = row;
		monkeys[idx][COL] = col;
		map[row][col].setNonStaticOccupant(2); 
	}
	
	public int[] getSpecificMonkeyPos(int idx) {
		return monkeys[idx];
	}
	/**
	 * All monkeys move in a random direction
	 */
	public void allMonkeyMove() {
		for(int i = 0; i < monkeys.length; i++) {
			monkeyMove(i);
			monkeyAttack(i);
		}
	}
	
	public void monkeyMove(int idx) {
		int randomDir = (int)(Math.random()*4);
		attemptMonkeyMove(idx, randomDir);
	}
	
	public void attemptMonkeyMove(int idx, int dir) {
		int[] attemptedTile = getDirectedCoordinates(dir);
		int into = checkTile(attemptedTile[0],attemptedTile[1]);
		if(into == ROCK) {
			
		}else
			if(into == TREE || into == NOTHING) {
				monkeys[idx][ROW] = attemptedTile[0];
				monkeys[idx][COL] = attemptedTile[1];
			}
	}
	/**
	 * Monkey attacks player if they are in the same tile
	 * @param idx
	 */
	public void monkeyAttack(int idx) {
		if(playerPos[ROW] == monkeys[idx][ROW] && playerPos[COL] == monkeys[idx][COL]) {
			stepCount--; //monkey removes the player steps
			for(int i = 0; i < 7; i++) { //monkey runs away
				monkeyMove(idx);
			}
		}
	}
	
	/*---- PLAYER CONTROL METHODS----*/
	
	public void setPlayerPos(int row, int col) {
		playerPos[ROW] = row;
		playerPos[COL] = col;
		
		map[row][col].setNonStaticOccupant(PLAYER);
	}
	/**
	 * Checks the tile the player is attempting to move into
	 * and moves player into tile if there is NOTHING/TREASURE
	 * @param direction
	 */
	public void attemptPlayerMove(int direction) {
		int[] attemptedTile = getDirectedCoordinates(direction);
		int into = checkTile(attemptedTile[0],attemptedTile[1]);
		
		//!!! NEED ONE TO CHECK FOR MONKEY AND FIGURE OUT WHAT HAPPENS THEN!!!
		
		//checks if the tile is valid for moving into
		//This is where events will trigger
		if(into == NOTHING) {
			setPlayerPos(attemptedTile[0], attemptedTile[1]);
			stepCount--;
		}else {
				allMonkeyMove();
					if(into == TREE || into == ROCK) { //cannot walk into these
						//return fail to move towards coordinates
					}else 
						if(into == FORAGE) { //walks into forage and forage gets removed
						setPlayerPos(attemptedTile[0], attemptedTile[1]);
						stepCount--;
						map[attemptedTile[0]][attemptedTile[1]].setNonStaticOccupant(NOTHING);
					}else 
						if(into == TREASURE) {
						//player wins
					}
		}
	}
	
	public void attemptObserve(int row, int col) {
		//checks if coordinates are within vision range
			//checks if within map bounds
		if(withinMap(row, col)) {

		}
	}
	/**
	 * Gets coordinates in a direction from player
	 * Processes the 
	 */
	public int[] getDirectedCoordinates(int direction) {
		int[] returnInt = new int[2];
		int row = playerPos[ROW];
		int col = playerPos[COL];
		if(direction == UP && row > 0) {
			row--;
		}else if(direction == RIGHT && col < map[row].length-1) {
			col++;
		}else if(direction == DOWN && row < map.length-1) {
			row+=1;
		}else if(direction == LEFT && col > 0) {
			col-=1;
		}
		if(withinMap(row,col)) {
			returnInt[0] = row;
			returnInt[1] = col;
		}
		return returnInt;
	}
	/**
	 * Checks if coords are within map bounds
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean withinMap(int row, int col) {
		return (row < map.length && row > -1 && col < map[row].length && col > -1);
	}
	/**
	 * Returns what the tile is (for its staticOccupant)
	 * @param row
	 * @param col
	 * @return
	 */
	public int checkTile(int row, int col) {
		return map[row][col].getStaticOccupant();
	}
	
	/*---- INPUT PROCESSING METHODS ----*/
	
	/**
	 * Takes input from KevinFrontend and processes it
	 * first checks if the user wants to move in a direction
	 * then checks if user wants to look at a coordinate
	 * otherwise nothing will happen within the backend
	 */
	public void processInput(String input) {
		if(isValidDirection(input)) {
			String dirKeys = "wdsa";
			attemptPlayerMove(dirKeys.indexOf(input));
			allMonkeyMove();
		}else if(isValidCoordinates(input)){
			int row = Integer.parseInt(input.substring(0,1));
			int col = Integer.parseInt(input.substring(2,3));
			attemptObserve(row, col);
		}
	}
	/**
	 * Checks if user inputs a valid direction (similar to isValid)
	 * @param input
	 * @return
	 */
	public boolean isValidDirection(String input) {
		String dirKeys = "wdsa";
		if(dirKeys.indexOf(input) > -1) {
			return true;
		}
		return false;
	}
	/**
	 * Checks if user inputs a valid coordinate (formatted like row,col)
	 * @param input
	 * @return
	 */
	public boolean isValidCoordinates(String input) {
		if(input.length() == 3) {
			if(Character.isDigit(input.charAt(0)) && Character.isDigit(input.charAt(2)) && input.substring(1,2) == ",") {
				int row = Integer.parseInt(input.substring(0,1));
				int col = Integer.parseInt(input.substring(2,3));
				if(row < map.length && row > -1 && col < map[row].length && col > -1) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}
		return false;
	}
	
	/*---- EVENT METHODS ----*/
	//monkey takes player steps
	//player gets to treasure
	//player gets into a new tile with nothing happening
	
	/*---- GENERATE MAP METHODS ----*/
	
	public void createMapRockBorder() {
		for(int col = 0; col < map[0].length; col++) {
			map[0][col].setStaticOccupant(ROCK);
			map[map.length-1][col].setStaticOccupant(ROCK);
		}
		for(int row = 0; row < map.length; row++) {
			map[row][0].setStaticOccupant(ROCK);
			map[row][map[row].length-1].setStaticOccupant(ROCK);
		}
	}
	
	/**
	 * Generation of a random map will require cells of 2x2 tiles
	 * These cells will have variations of OPEN(FORAGE & NOTHING):CLOSED(ROCK & TREE)
	 * 4:0, 3:1, 2:2, 1:3, 0:4
	 * Will generate from 1,1(top left within rock borders) to map.length-2,map[row].length-2(bottom right within rock borders)
	 */
	public void populateMap() {
		
	}
	
	public void ratioFourToZero(boolean open) {
		
	}
	
	public void ratioThreeToOne(boolean open) {
		
	}
	
	public void ratioTwoToTwo() {
		
	}
	
}
