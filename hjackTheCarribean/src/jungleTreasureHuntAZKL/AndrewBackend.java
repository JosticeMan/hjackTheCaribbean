package jungleTreasureHuntAZKL;

import java.util.Scanner;
/*
 * PLANNING:
 * Basic Treasure Hunting Mini Game
 * 
 * User will start out on an open map
 * Tiles will be the main part of the map
 * 
 * NEEDED:
 * 2D array to keep track of every tile
 */
public class AndrewBackend implements KevinSupport{

	private AndrewSupport frontend;
	
	private AndrewKevenTile[][] map;
	
	
	public AndrewBackend(AndrewSupport frontend) {
		this.frontend = frontend;
		map = new  AndrewKevinTile[10][10];
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
