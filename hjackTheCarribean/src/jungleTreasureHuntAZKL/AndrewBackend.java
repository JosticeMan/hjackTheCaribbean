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
	
	private boolean playing;
	
	private AndrewKevinTile[][] map;
	
	//all objects in the game only need the position or 
	//everything is created through the class AndrewKevinTile
	//ROW, COL
	private int[] playerPos;
	private int stepCount;
	
	private int[][] monkeys;
	
	private int[] treasurePos;
	
	private int[][] visibleRadius;
	
	private boolean play;
	/*------CONSTANTS--------*/
	//most of these could've been classes
	public static final int ROW = 0;
	public static final int COL = 1;
	
	public static final int NOTHING = 0;
	
	//static occupant ints
	public static final int ROCK = 1;
	public static final int TREE = 2;
	public static final int FORAGE = 3;
	public static final int[] closedTiles = {1,2,3};
	
	public static final int TRAP = 4;
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
	
	public static final int VISIBLE = 1;
	public static final int NOTVISIBLE = 0;
	public static final int OUTSIDEMAP = -1;
	
	public static final boolean OPEN = true;
	public static final boolean CLOSED = false;
	
	
	public AndrewBackend(AndrewSupport frontend) {
		this.frontend = frontend;
		
		//creates map and populates with a bunch of tiles
		map = new  AndrewKevinTile[10][10];
		for(int row = 0; row < map.length; row++) {
			for(int col = 0; col < map[row].length; col++) {
				map[row][col] = new AndrewKevinTile(row, col);
			}
		}
		//populates map
		populateTiles();
		//creates the boundary of rocks to keep player within map **Will leave on slot open if player just wants to leave**
		createMapRockBorder();
			
		//stepCount is based off the size of the map, for now it'll be 10
		stepCount = 20;
		
		//creates and sets player starting position
		playerPos = new int[2];
		
		playerSpawn();
		map[playerPos[ROW]][playerPos[COL]].setStaticOccupant(NOTHING);

		//creates and sets monkeys and their positions
		monkeys = new int[3][2]; //first numbers is how many monkeys, 2nd is for their coords
		
		monkeySpawn();
		
		treasurePos = new int[2];
		
		treasureSpawn();
		
		//checkPathToTreasure();
		
		visibleRadius = new int[7][7];
		updateVisibleRadius();
	}
	
	/*---- KEVINSUPPORT METHODS ----*/
	
	public boolean playing() {
		return play;
	}
	
	public void setPlay(boolean a) {
		play = a;
	}
	
	public int[] getPlayerPos() {
		return playerPos;
	}
	
	public int getStepCount() {
		return stepCount;
	}
	
	public AndrewKevinTile[][] getMap(){
		return this.map;
	}

	@Override
	public Object end() {
		setPlay(false);
		return null;
	}

	public int[][]getVisibleRadius(){
		return visibleRadius;
	}
	
	public int[][] getMonkeys(){
		return monkeys;
	}
	
	public int[] getTreasurePos() {
		return treasurePos;
	}
	/*---- MONKEY METHODS ----*/
	
	public void setSpecificMonkeyPos(int idx, int row, int col) {
		if(map[monkeys[idx][ROW]][monkeys[idx][COL]].getNonStaticOccupant() != PLAYER)
			map[monkeys[idx][ROW]][monkeys[idx][COL]].setNonStaticOccupant(NOTHING);
		monkeys[idx][ROW] = row;
		monkeys[idx][COL] = col;
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
			if(monkeyAndPlayerPosSame(i)) {
				monkeyAttack(i);
			}
		}
		confirmMonkeysOnMap();
	}
	
	public void monkeyMove(int idx) {
		int randomDir = (int)(Math.random()*4);
		attemptMonkeyMove(idx, randomDir);
	}
	
	public void attemptMonkeyMove(int idx, int dir) {
		int[] attemptedTile = getDirectedCoordinates(dir, getSpecificMonkeyPos(idx)[ROW],getSpecificMonkeyPos(idx)[COL]);
		int into = checkTile(attemptedTile[0],attemptedTile[1]);
		if(into == ROCK) {
			
		}else
			if(into == TREE || into == NOTHING) {
				setSpecificMonkeyPos(idx, attemptedTile[ROW], attemptedTile[COL]);
			}
	}
	/**
	 * Monkey attacks player if they are in the same tile
	 * @param idx
	 */
	public void monkeyAttack(int idx) {
			stepCount--; //monkey removes the player steps
		while(!monkeyAndPlayerPosSame(idx)) {
			for(int i = 0; i < 7; i++) { //monkey runs away
				monkeyMove(idx);
			}
		}
	}
	
	public boolean monkeyAndPlayerPosSame(int monkeyIdx) {
		return playerPos[ROW] == monkeys[monkeyIdx][ROW] && playerPos[COL] == monkeys[monkeyIdx][COL];
	}

	public void confirmMonkeysOnMap() {
		for(int i = 0; i < monkeys.length; i++) {
			map[monkeys[i][ROW]][monkeys[i][COL]].setNonStaticOccupant(MONKEY);
		}
	}
	
	public void monkeySpawn() {
		int monkeyCount = monkeys.length-1;
			for(int i = 1; i < map.length-2; i++) {
				for(int j = 1; j < map[i].length; j++) {
					if(monkeyCount == -1) {
						break;
					}
					if(Math.random() < 0.2) {
						if(checkTile(i,j) == ROCK && checkTile(i,j) == TREASURE) {
							
						}else {
							setSpecificMonkeyPos(monkeyCount, i, j);
							monkeyCount--;
						}
					}
				}
			}
		confirmMonkeysOnMap();
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
	 * @return 
	 */
	public int attemptPlayerMove(int direction) {
		int[] attemptedTile = getDirectedCoordinates(direction, playerPos[ROW], playerPos[COL]);
		int into = checkTile(attemptedTile[0],attemptedTile[1]);
		
		//checks if the tile is valid for moving into
		if(into == NOTHING) {
			map[playerPos[ROW]][playerPos[COL]].setNonStaticOccupant(NOTHING);
			setPlayerPos(attemptedTile[0], attemptedTile[1]);
			stepCount--;
			
			updateVisibleRadius();
			allMonkeyMove();
			return 1;
		}else {
				allMonkeyMove();
					if(into == TREE || into == ROCK) { //cannot walk into these
						//return fail to move towards coordinates
						return 0;
					}else 
						if(into == FORAGE) { //walks into forage and forage gets removed
						map[playerPos[ROW]][playerPos[COL]].setNonStaticOccupant(NOTHING);
						setPlayerPos(attemptedTile[0], attemptedTile[1]);
						stepCount--;
						map[attemptedTile[0]][attemptedTile[1]].setStaticOccupant(NOTHING);

						allMonkeyMove();
						updateVisibleRadius();
						return 1;
					}else 
						if(into == TREASURE) {
						end();
							return 4;
					}
		}
		return -1;
	}
	
	public int attemptObserve(int row, int col) {
		//checks if coordinates are within vision range
			//checks if within map bounds
		if(withinMap(row, col)) {
			if(withinVisibleRange(row, col)) {
				//give hint
				//System.out.println("You did good coordinates");
				return 3;
			}else {
				//System.out.println("You can't see there");
				return 2;
			}
		}else {
			//System.out.println("Outside map");
			return 5;
		}
	}
	/**
	 * Gets coordinates in a direction from player
	 * Processes the 
	 */
	public int[] getDirectedCoordinates(int direction, int row, int col) {
		int[] returnInt = new int[2];
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
	public void checkLoss() {
		if(stepCount <= 0) {
			setPlay(false);
		}
	}
	/*---- INPUT PROCESSING METHODS ----*/
	
	/**
	 * Takes input from KevinFrontend and processes it
	 * first checks if the user wants to move in a direction
	 * then checks if user wants to look at a coordinate
	 * otherwise nothing will happen within the backend
	 */
	public int processInput(String input) {
		if(isValidDirection(input)) {
			String dirKeys = "wdsa";
			return attemptPlayerMove(dirKeys.indexOf(input));
		}else if(isValidCoordinates(input)){
			int row = Integer.parseInt(input.substring(0,1));
			int col = Integer.parseInt(input.substring(2,3));
			return attemptObserve(row, col);
		}else if(input.equalsIgnoreCase("get me out")) {
			return 8;
		}
		checkLoss();
		return -1;
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
	 * Generation of a random map will require cells of 2x2 tiles (designed if different/specific tile placement is wanted it can be created)
	 * These cells will have variations of OPEN(FORAGE & NOTHING):CLOSED(ROCK & TREE)
	 * Ratios of above variations 4:0, 3:1, 2:2, 1:3, 0:4
	 * Will generate from 1,1(top left within rock borders) to map.length-2,map[row].length-2(bottom right within rock borders)
	 * 
	 * if conditions are to control chances of specific tile types
	 * **THIS WILL PROBABLY CONTROL GAME DIFFICULTY THE MOST**
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
				applyTiles(2, condenseRow, condenseCol, theTwoByTwo);
				}
			}
		}
	/**
	 * Method to replace apply2x2Tiles in order to allow for greater variations in tile generation
	 * Can be used for larger tile dimensions
	 * @param dimensions
	 */
	public void applyTiles(int dimensions, int topLeftRow, int topLeftCol, int[][] tiles){
		for(int i = 0; i < dimensions; i++) {
			for(int j = 0; j < dimensions ; j++) {
				map[topLeftRow + i][topLeftCol + j].setStaticOccupant(tiles[i][j]);
			}
		}
	}
	//---Random tile type generation
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
	 * For now it'll just be along the left and top sides of the map
	 */
	public void treasureSpawn() {
		int randomRow = (int)(Math.random()*map.length-2)+2;
		int randomCol = (int)(Math.random()*map[0].length-2)+1;
		if(randomRow != 1) { //
			randomCol = map[randomRow].length-2;
		}
		treasurePos[ROW] = randomRow;
		treasurePos[COL] = randomCol;
		map[randomRow][randomCol].setStaticOccupant(TREASURE);
		
		checkPathToTreasure();
	}
	/**
	 * Checks to see if the player has a path of OPEN tiles to get to the treasure
	 * And if not, make one
	 * 
	 * 1.start from treasurePos
	 * 2.check for adjacent open type tiles
	 * 3.if none it will create one in a random dir TOWARDS playerSpawn
	 * 4.continue until the row and col matches player spawn
	 * 
	 * Method will have to try to work its way to the bottom left of the map (to match player spawn) <-- can be modified for a unique player spawn
	 * 	In order for the path to be random the method will choose between
	 * 		Either making the currentRow of the path go to playerPos[ROW]
	 * 			Or the currentCol of the path go to playerPos[COL]
	 * 
	 * getDirectedCoordinates()
	 * checkTile()
	 * randomTile(boolean open)
	 */
	public void checkPathToTreasure() {
		int currentRow = treasurePos[ROW];
		int currentCol = treasurePos[COL];
		
		int[][] beenTo = new int[(map.length-2)*(map.length-2)][2];
		for(int i = 0; i < beenTo.length; i++) {
			beenTo[i] = null;
		}
		int cordCount = 0;
		
		boolean openPath = false;
		
		while(!openPath) {
			//System.out.println(currentRow + ", " + currentCol);
			if(currentRow == playerPos[ROW] && currentCol == playerPos[COL])
				openPath = true;
			
			boolean adjacentOpen = false;
			
			for(int dir = 3; dir > -1; dir--) {
				
				int[] goInto = getDirectedCoordinates(dir, currentRow, currentCol);
				int tileType = checkTile(goInto[ROW],goInto[COL]);
				
				if((tileType == ROCK || tileType == TREE) && inArray(goInto, beenTo)) {	
				}else {
					beenTo[cordCount] = goInto;
					cordCount++;
					currentRow = goInto[ROW];
					currentCol = goInto[COL];
					adjacentOpen = true;
					break;
				}
			}
			if(!adjacentOpen) {
				if(currentRow == playerPos[ROW] || currentCol == playerPos[COL]) {
					if(currentRow == playerPos[ROW]) {
						currentCol --;

						map[currentRow][currentCol].setStaticOccupant(randomTile(CLOSED));
						
						beenTo[cordCount][ROW] = currentRow;
						beenTo[cordCount][COL] = currentCol;
						cordCount++;
					}else {
						currentRow --;

						map[currentRow][currentCol].setStaticOccupant(randomTile(CLOSED));
						
						beenTo[cordCount][ROW] = currentRow;
						beenTo[cordCount][COL] = currentCol;
						cordCount++;
					}
				}else {
					if(Math.random() < 0.5) {
						currentCol --;

						map[currentRow][currentCol].setStaticOccupant(randomTile(CLOSED));
						
						beenTo[cordCount][ROW] = currentRow;
						beenTo[cordCount][COL] = currentCol;
						cordCount++;
					}else {
						currentRow --;

						map[currentRow][currentCol].setStaticOccupant(randomTile(CLOSED));
						
						beenTo[cordCount][ROW] = currentRow;
						beenTo[cordCount][COL] = currentCol;
						cordCount++;
					}
				}
			}
		}
	}
	public boolean inArray(int[] arr, int[][] bigArr) {
		for(int i = 0; i < bigArr.length; i++) {
			if(bigArr[i] == null)
				return false;
			if(bigArr[i][ROW] == arr[ROW] && bigArr[i][COL] == arr[COL]) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Updates the tiles that are visible in the sight radius
	 * P - player position
	 * T - true/ visible to player
	 * F = false/ not visible to player\
	 *      -3 -2 -1  0  1  2  3   
	 *       0  1  2  3  4  5  6
	 *-3 0 [[F][F][F][T][F][F][F]]
	 *-2 1 [[F][F][T][T][T][F][F]]
	 *-1 2 [[F][T][T][T][T][T][F]]
	 * 0 3 [[T][T][T][P][T][T][T]]
	 * 1 4 [[F][T][T][T][T][T][F]]
	 * 2 5 [[F][F][T][T][T][F][F]]
	 * 3 6 [[F][F][F][T][F][F][F]] 
	 * //What they see with absolutely no obstructions and all within map bounds
	 * 
	 * 3,3 is playerPos[ROW],playerPos[COL] relative to the map
	 * 
	 * checkTile()
	 * withinMap()
	 * 
	 *1.find out which tiles will be outside the map
	 *2.fill out the normal view (see diagram)
	 *3.figure out the special cases of when an object is obstructing
	 * 
	 * The outside numbers are positions relative to the player
	 * ****This is important for implementing the visible radius
	 * 		With the map because it needs player position
	 */
	public void updateVisibleRadius() {
		int originX = playerPos[COL];
		int originY = playerPos[ROW];
		
		//populate with OUTSIDEMAP where out of bounds and NOTVISIBLES elsewhere
		for(int fromOriginY = -3; fromOriginY < 4; fromOriginY++) {
			for(int fromOriginX = -3; fromOriginX < 4; fromOriginX++) {
				if(withinMap(originY + fromOriginY, originX + fromOriginX)) {
					visibleRadius[fromOriginY + 3][fromOriginX + 3] = NOTVISIBLE;
				}else {
					visibleRadius[fromOriginY + 3][fromOriginX + 3] = OUTSIDEMAP;
				}
			}
		}
		//populate the appropriate visible radius where its not OUTSIDEMAP
		for(int i = 0; i < visibleRadius.length; i++) {
			if(i == 0 || i == 6) {
				if(visibleRadius[i][3] != OUTSIDEMAP)
					visibleRadius[i][3] = VISIBLE;
			}
			if(i == 1 || i == 5) {
				for(int j = 2; j < 5; j++) {
					if(visibleRadius[i][j] != OUTSIDEMAP)
						visibleRadius[i][j] = VISIBLE;
				}
			}
			if(i == 2 || i == 4) {
				for(int j = 1; j < 6; j++) {
					if(visibleRadius[i][j] != OUTSIDEMAP)
						visibleRadius[i][j] = VISIBLE;
				}
			}
			if(i == 3){
				for(int j = 0; j < 7; j++) {
					if(visibleRadius[i][j] != OUTSIDEMAP)
						visibleRadius[i][j] = VISIBLE;
				}
			}
		}
		
		//Special case of where vision is blocked, all can probably be optimized due to how similar all the conditions are
		//FOR RADIUS of 2 from PLAYER
			//vertical line of sight
		if(withinMap(originY + -2, originX))
			if(isClosed(checkTile(originY + -2, originX))) {
				visibleRadius[0][3] = NOTVISIBLE;
			}
		if(withinMap(originY + 2, originX)) {
			if(isClosed(checkTile(originY + 2, originX))) {
				visibleRadius[6][3] = NOTVISIBLE;
			}
		}
			//horizontal line of sight
		if(withinMap(originY, originX + -2)) {
			if(isClosed(checkTile(originY, originX + -2))) {
				visibleRadius[3][0] = NOTVISIBLE;
			}
		}
		if(withinMap(originY, originX + 2)) {
			if(isClosed(checkTile(originY, originX + 2))) {
				visibleRadius[3][6] = NOTVISIBLE;
			}
		}
		//FOR RADIUS of 1 from PLAYER
			//corner
				//top left
		if(withinMap(originY + -1, originX + -1)) {
				if(isClosed(checkTile(originY + -1, originX + -1))) {
					visibleRadius[1][2] = NOTVISIBLE;
					visibleRadius[2][1] = NOTVISIBLE;
				}
		}
				//top right
		if(withinMap(originY + -1, originX + 1)) {
				if(isClosed(checkTile(originY + -1, originX + 1))) {
					visibleRadius[1][4] = NOTVISIBLE;
					visibleRadius[2][5] = NOTVISIBLE;
				}
		}
				//bottom left
		if(withinMap(originY + 1, originX + -1)) {
				if(isClosed(checkTile(originY + 1, originX + -1))) {
					visibleRadius[4][1] = NOTVISIBLE;
					visibleRadius[5][2] = NOTVISIBLE;
				}
		}
				//bottom right
		if(withinMap(originY + 1, originX + 1)) {
				if(isClosed(checkTile(originY + 1, originX + 1))) {
					visibleRadius[5][4] = NOTVISIBLE;
					visibleRadius[4][5] = NOTVISIBLE;
				}
		}
			//cross
				//vertical line of sight
				//top
		if(withinMap(originY + -1, originX)) {
				if(isClosed(checkTile(originY + -1, originX))) {
					visibleRadius[1][2] = NOTVISIBLE;
					visibleRadius[1][3] = NOTVISIBLE;
					visibleRadius[1][4] = NOTVISIBLE;
					visibleRadius[0][3] = NOTVISIBLE; //from RADIUS 2
				}
		}
				//bottom
		if(withinMap(originY + 1, originX)) {
				if(isClosed(checkTile(originY + 1, originX))) {
					visibleRadius[5][2] = NOTVISIBLE;
					visibleRadius[5][3] = NOTVISIBLE;
					visibleRadius[5][4] = NOTVISIBLE;
					visibleRadius[6][3] = NOTVISIBLE; //from RADIUS 2
				}
		}
				//horizontal line of sight
				//left
		if(withinMap(originY, originX + -1)) {
				if(isClosed(checkTile(originY, originX + -1))) {
					visibleRadius[2][1] = NOTVISIBLE;
					visibleRadius[3][1] = NOTVISIBLE;
					visibleRadius[4][1] = NOTVISIBLE;
					visibleRadius[3][0] = NOTVISIBLE; //from RADIUS 2
				}
		}
				//right
		if(withinMap(originY, originX + 1)) {
				if(isClosed(checkTile(originY, originX + 1))) {
					visibleRadius[2][5] = NOTVISIBLE;
					visibleRadius[3][5] = NOTVISIBLE;
					visibleRadius[4][5] = NOTVISIBLE;
					visibleRadius[3][6] = NOTVISIBLE; //from RADIUS 2
				}
		}
		//re-populates where there is out of bounds because above rules don't take in account out of bounds
		for(int fromOriginY = -3; fromOriginY < 4; fromOriginY++) {
			for(int fromOriginX = -3; fromOriginX < 4; fromOriginX++) {
				if(withinMap(originY + fromOriginY, originX + fromOriginX)) {
				}else {
					visibleRadius[fromOriginY + 3][fromOriginX + 3] = OUTSIDEMAP;
				}
			}
		}
		//printVision();
	}
	
	public boolean isClosed(int type) {
		for(int i = 0; i < closedTiles.length; i++) {
			if(closedTiles[i] == type)
				return true;
		}
		return false;
	}
	//testing code
	public void printVision() {
		String printTxt = "";
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				int type = visibleRadius[i][j];
						if(type == VISIBLE) {
							printTxt += "O ";
						}else if(type == NOTVISIBLE) {
							printTxt += "X ";
						}else if(type == OUTSIDEMAP) {
							printTxt += "- ";
						}
			}
			printTxt += "\n";
		}
		System.out.println(printTxt);
	}
	/**
	 * Will check if the coords player wants to observe are within the visibleRadius of the player
	 * based off the map
	 * 
	 * Player position is needed, will be similar to the beginning of updateVisibleRange()
	 * **Can potentially be used for frontend to check if the frontend will print out the appropriate symbol
	 * ex.
	 * 	for(cycle through rows of FULL map){
	 * 		for(cycle through col of FULL map){
	 * 			if(withinVisibleRange(rowCords,colCords)
	 * 				**add the occupant of said coords to the printout of the map
	 * 			else
	 * 				**add whatever represents no vision
	 * @param rowCords
	 * @param colCords
	 * @return
	 */
	public boolean withinVisibleRange(int rowCords, int colCords) {
		int originX = playerPos[COL];
		int originY = playerPos[ROW];
		/*Need to translate the coords in reference to the visibleRadius\
		 * 	Then vice versa for the frontend
		 *      -3 -2 -1  0  1  2  3   
		 *       0  1  2  3  4  5  6
		 *-3 0 [[F][F][F][T][F][F][F]]
		 *-2 1 [[F][F][T][T][T][F][F]]
		 *-1 2 [[F][T][T][T][T][T][F]]
		 * 0 3 [[T][T][T][P][T][T][T]]
		 * 1 4 [[F][T][T][T][T][T][F]]
		 * 2 5 [[F][F][T][T][T][F][F]]
		 * 3 6 [[F][F][F][T][F][F][F]]*/
		//will already be checked for out of map
		int fromOriginY = rowCords - originY;
		int fromOriginX = colCords - originX;
		//check if within a radius of 3 from player so
		//there won't be an outOfBounds when using visibleRange
		if(fromOriginY > -4 && fromOriginY < 4) {
			if(fromOriginX > -4 && fromOriginX < 4) {
				if(visibleRadius[fromOriginY+3][fromOriginX+3] == VISIBLE)
					return true;
			}
		}
		return false;
	}
}
