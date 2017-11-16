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
 *              //only path would be through the F
 * X R  R  R  T  F  T  T  R  X
 *   x
 * XxLx                      X
 *  x  xx
 * XxL  Lx                   X
 *  x     xx
 * XxL  L  Lx                X
 *  x        xx
 * XxP  L  L  Lx             X
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
 * X X X X X X X X X X X X X X   X X X X X X X X X X X X X X
 *           x                                             
 * X        x x              X   X                         X
 *         x  x                                              
 * X      x   x              X   X                         X
 *       x     x                                             
 * X    x      Txx           X   X                         X 
 *     x          xx //if an object is blocking the line of sight, then the tiles above and to the right wont be visible to the                                        
 * X  x      P L L Lx        X   X                         X player, this is why a radius of 3 was chosen, because there are only two
 *     x           x                                         scenarios with specific vision blocking and both are displayed   
 * X    x         x          X   X                         X
 *       x       x                                            
 * X      xT    x            X   X                         X
 *         x   x                                              
 * X        x x              X   X                         X
 *           x                                               
 * X X X X X X X X X X X X X X   X X X X X X X X X X X X X X
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
	
	//private int[][] map;
	
	//all objects in the game only need the position or 
	//everything is created through the class AndrewKevinTile
	//ROW, COL
	private int[] playerPos;
	private int stepCount;
	
	/*private int[][] trees;
	private int[][] rocks;
	private int[][] forage;*/
	
	/*------CONSTANTS--------*/
	public static final int ROW = 0;
	public static final int COL = 1;
	
	public static final int NOTHING = 0;
	
	//static occupant ints
	public static final int ROCK = 1;
	public static final int TREE = 2;
	public static final int FORAGE = 3;
	public static final int TREASURE = 7;
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
		
		map = new  AndrewKevinTile[10][10];
		for(int row = 0; row < map.length; row++) {
			for(int col = 0; col < map[row].length; col++) {
				map[row][col] = new AndrewKevinTile(row, col);
			}
		}
		//stepCount is based off the size of the map, for now it'll be 10
		stepCount = 10;
		setPlayerPos(map.length, 0); //player position will be randomly generated along the border
		
		//map = new int[7][12];
	}

	public void setPlayerPos(int row, int col) {
		playerPos[ROW] = row;
		playerPos[COL] = col;
		
		map[row][col].setNonStaticOccupant(1);
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	public AndrewKevinTile[][] getMap(){
		return this.map;
	}
	
	@Override
	public boolean playing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object end() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Checks the tile the player is attempting to move into
	 * and moves player into tile if there is NOTHING/TREASURE
	 * @param direction
	 */
	public void attemptPlayerMove(int direction) {
		int row = playerPos[ROW];
		int col = playerPos[COL];
		if(direction == UP) {
			row--;
		}else if(direction == RIGHT) {
			col++;
		}else if(direction == DOWN) {
			row++;
		}else if(direction == LEFT) {
			col--;
		}
		int into = checkTile(row,col);
		
		if(into == NOTHING) {
			setPlayerPos(row, col);
		}else if(into == TREE || into == ROCK) {
			//return fail to move towards coordinates
		}else if(into == FORAGE) {
			setPlayerPos(row, col);
			map[row][col].setStaticOccupant(0);
		}else if(into == TREASURE) {
			//player wins
		}
	}
	
	/**
	 * Returns what the tile is (its staticOccupant)
	 * @param row
	 * @param col
	 * @return
	 */
	public int checkTile(int row, int col) {
		return map[row][col].getStaticOccupant();
	}
	
	public void attemptObserve(int row, int col) {
		
	}
	/*----INPUT PROCESSING METHODS----*/
	
	public void processInput(String input) {
		if(isValidDirection(input)) {
			String dirKeys = "wdsa";
			attemptPlayerMove(dirKeys.indexOf(input));
		}else if(isValidCoordinates(input)){
			int row = Integer.parseInt(input.substring(0,1));
			int col = Integer.parseInt(input.substring(2,3));
			attemptObserve(row, col);
		}
	}
	
	public boolean isValidDirection(String input) {
		String dirKeys = "wdsa";
		if(dirKeys.indexOf(input) > -1) {
			return true;
		}
		return false;
	}
	
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
}
