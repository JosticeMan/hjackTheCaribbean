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
	
	private boolean playing;
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
		map = new  AndrewKevinTile[10][10];
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
		playerPos = new int[2];
		
		playerSpawn(); //player position will be randomly generated along the border

		//creates and sets monkeys and their positions
		monkeys = new int[5][2]; //first numbers is how many monkeys, 2nd is for their coords
		
		monkeySpawn();
		
		treasurePos = new int[2];
		
		treasureSpawn();
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

	public void monkeySpawn() {
		int monkeyCount = monkeys.length-1;
		while(monkeyCount > -1) {
			for(int i = 0; i < map.length-2; i++) {
				for(int j = 0; j < map[i].length; j++) {
					if(Math.random() < 0.5) {
						if(checkTile(i,j) == ROCK && checkTile(i,j) == TREASURE) {
							
						}else {
							if(monkeyCount == -1) monkeyCount = 0;
							setSpecificMonkeyPos(monkeyCount, i, j);
							monkeyCount--;
						}
					}
				}
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
		
		//!!! Needs one to check if monkey attacked and send information about the attack to front end
		
		//checks if the tile is valid for moving into
		//This is where events will trigger
		if(into == NOTHING) {
			map[playerPos[ROW]][playerPos[COL]].setNonStaticOccupant(NOTHING);
			setPlayerPos(attemptedTile[0], attemptedTile[1]);
			stepCount--;
		}else {
				allMonkeyMove();
					if(into == TREE || into == ROCK) { //cannot walk into these
						//return fail to move towards coordinates
					}else 
						if(into == FORAGE) { //walks into forage and forage gets removed
						map[playerPos[ROW]][playerPos[COL]].setNonStaticOccupant(NOTHING);
						setPlayerPos(attemptedTile[0], attemptedTile[1]);
						stepCount--;
						map[attemptedTile[0]][attemptedTile[1]].setStaticOccupant(NOTHING);
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
		}else if(input.length()==1) {
			if(input.substring(0,1).equalsIgnoreCase("r")) {
				
			}
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
		
		populateTiles();
	}
	
	/**
	 * Generation of a random map will require cells of 2x2 tiles
	 * These cells will have variations of OPEN(FORAGE & NOTHING):CLOSED(ROCK & TREE)
	 * 4:0, 3:1, 2:2, 1:3, 0:4
	 * Will generate from 1,1(top left within rock borders) to map.length-2,map[row].length-2(bottom right within rock borders)
	 */
	public void populateTiles() {
		for(int condenseRow = 1; condenseRow < map.length-2/2; condenseRow += 2) { //goes through every 2x2 tile on map, will be mostly 4:0 open tiles
			for(int condenseCol = 1; condenseCol < map[0].length-2/2; condenseCol += 2) {
				int[][] theTwoByTwo = new int[2][2];
				if(Math.random() < 0.20) { // 20% chance of a four to zero formation
					if(Math.random()<0.80) { // 80% chance of it to be all open
						theTwoByTwo = ratioFourToZero(true);
					}else { // 20% chance of it to be all closed
						theTwoByTwo = ratioFourToZero(false);
					}
				}else if(Math.random() < 0.5) { // 30% chance of a three to one formation
					if(Math.random()<0.66) { // 66% chance of it to be mostly open
						theTwoByTwo = ratioThreeToOne(true);
					}else { // 66% chance of it to be mostly closed
						theTwoByTwo = ratioThreeToOne(true);
					}
				}else if(Math.random() < 1) { // 50% chance of a two to two formation
						theTwoByTwo = ratioTwoToTwo();
				}
				applyTwoByTwoTiles(condenseRow, condenseCol, theTwoByTwo);
				}
			}
		}
	/**
	 * Method to replace apply2x2Tiles in order to allow for greater variations in tile generation
	 * Able to add specific formations such as a path that can only be moved in from
	 * @param dimensions
	 */
	public void applyTiles(int dimensions, int topLeftRow, int topLeftCol, int[][] tiles){
		for(int i = 0; i < dimensions; i++) {
			for(int j = 0; j < dimensions ; j++) {
				map[topLeftRow + i][topLeftCol + j].setStaticOccupant(tiles[i][j]);
			}
		}
	}
	
	public void applyTwoByTwoTiles(int topLeftCoordsRow, int topLeftCoordsCol, int[][] twoByTwo) {
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2 ; j++) {
				map[topLeftCoordsRow + i][topLeftCoordsCol + j].setStaticOccupant(twoByTwo[i][j]);
			}
		}
	}
	
	public int[][] ratioFourToZero(boolean open) {
		int[][] returnIntArr = new int[2][2];
		for(int row = 0; row < 2; row++) {
			for(int col = 0; col < 2; col++) {
				returnIntArr[row][col] = randomTile(open);
			}
		}
		return returnIntArr;
	}
	
	public int[][] ratioThreeToOne(boolean open) {
		int[][] returnIntArr = new int[2][2];
		int openCount = 1;
		if(open) {
			openCount = 3;
		}
		int closedCount = 4 - openCount;
		for(int row = 0; row < 2; row++) {
			for(int col = 0; col < 2; col++) {
				boolean foundTile = false;
				while(!foundTile) {
					if(Math.random() > 0.5) {
						if(openCount > 0) {
							returnIntArr[row][col] = randomTile(true);
							openCount --;
							foundTile = true;
						}
					}else {
						if(closedCount > 0) {
							returnIntArr[row][col] = randomTile(false);
							closedCount --;
							foundTile = true;
						}
					}
				}
			}
		}
		return returnIntArr;
	}
	
	public int[][] ratioTwoToTwo() {
		int[][] returnIntArr = new int[2][2];
		int openCount = 2;
		int closedCount = 2;
		for(int row = 0; row < 2; row++) {
			for(int col = 0; col < 2; col++) {
				boolean foundTile = false;
				while(!foundTile) {
					if(Math.random() > 0.5) {
						if(openCount > 0) {
							returnIntArr[row][col] = randomTile(true);
							openCount --;
							foundTile = true;
						}
					}else {
						if(closedCount > 0) {
							returnIntArr[row][col] = randomTile(false);
							closedCount --;
							foundTile = true;
						}	
					}
				}
			}
		}
		return returnIntArr;
	}
	/**
	 * Gives a random tile type based off open/closed
	 * Closed types: ROCK, TREE
	 * Open types: FORAGE, NOTHING
	 * @param open
	 * @return
	 */
	public int randomTile(boolean open) {
		if(open) {
			if(Math.random() > 0.25){
				return NOTHING;
			}else {
				return FORAGE;
			}
		}else {
			if(Math.random() > 0.4){
				return TREE;
			}else {
				return ROCK;//
			}
		}
	}
	
	public void playerSpawn() {
		map[map.length-2][1].setStaticOccupant(NOTHING);
		setPlayerPos(map.length-2, 1);
	}
	/**
	 * For now it'll just be along the left and top sides
	 */
	public void treasureSpawn() {
		int randomRow = (int)(Math.random()*map.length-2)+1;
		int randomCol = (int)(Math.random()*map[0].length-2)+1;
		if(randomRow != 1) { //
			randomCol = map[randomRow].length-2;
		}
		treasurePos[ROW] = randomRow;
		treasurePos[COL] = randomCol;
		map[randomRow][randomCol].setStaticOccupant(TREASURE);
	}
}
