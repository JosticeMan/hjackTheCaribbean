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
 * DESIGN (ultimately up to Kevin) : This is a 7x12 map
 * X X X X X X X X X X X X X X
 * 
 * X                         X
 * 
 * X                         X
 * 
 * X                         X
 * 
 * X  █                      X
 *   
 * X x█  █                   X
 *      x
 * X  █  █  █                X
 *   
 * X  P  █  █  █             X
 * 
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
 */
public class AndrewBackend implements KevinSupport{

	private AndrewSupport frontend;
	
	private AndrewKevinTile[][] map;
	private int[][] visibleTile;
	
	private int playerRow;
	private int playerCol;
	
	
	public AndrewBackend(AndrewSupport frontend) {
		this.frontend = frontend;
		
		
		map = new  AndrewKevinTile[10][10];
		for(int row = 0; row < map.length; row++) {
			for(int col = 0; col < map[row].length; col++) {
				map[row][col] = new AndrewKevinTile(row, col);
			}
		}
		
		
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

	@Override
	public String processInput() {
		// TODO Auto-generated method stub
		return null;
	}
}
