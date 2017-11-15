package jungleTreasureHuntAZKL;
/*
 * PLANNING:
 * Basic Treasure Hunting Mini Game
 * 
 * User will start out on an open map
 * Tiles will be the main part of the map
 * 
 * NEEDED:
 * 	2D Array to track map info
 * 	A user object that moves through the map
 * 	Tiles that the map is made of
 * 	Objects that go within the Tile to create attributes of the map
 * 	Will need a sight radius
 */
public class AndrewBackend implements KevinSupport{

	private AndrewSupport frontend;
	
	private AndrewKevinTile[][] map;
	private int[][] visibleTile;
	
	private int[] player;
	
	
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
