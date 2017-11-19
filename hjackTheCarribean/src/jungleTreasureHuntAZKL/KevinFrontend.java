package jungleTreasureHuntAZKL;

import java.util.Scanner;

import oceanExplorer.CaveExplorer;
import oceanExplorer.CaveRoom;
public class KevinFrontend implements AndrewSupport {
	
	public static final int ROCK = 1;
	public static final int TREE = 2;
	public static final int FORAGE = 3;
	public static final int TREASURE = 7;

	public static final int PLAYER = 1;
	public static final int MONKEY = 2;
	
	private KevinSupport backend;
	public static Scanner inputSource;
	
	private AndrewKevinTile[][] map;

	public static final void main(String[] args) {
		inputSource = new Scanner(System.in);
		KevinFrontend demo = new KevinFrontend();
		demo.play();
		//KevinFrontend demo = new KevinFrontend();
		
	}

	public static String getInput() {
		return inputSource.nextLine();
	}
	
	public KevinFrontend() {
		backend = new AndrewBackend(this);
		this.map = backend.getMap();
	}

	public void play() {
		startGameMessage();
		  while(backend.playing()) {
			updateMap(map);
			backend.getStepCount(); // number of steps taken before limit
			backend.processInput(getInput());
		}
		printEndGame(backend.end());
	}

	private void startGameMessage() {
		String s = "Welcome to the Treasure Hunter Game!! To see the rules type 'r' and to play press 'wdsa'";
		 System.out.println(s);
		
	}

	private void printEndGame(Object end) {
		// TODO Auto-generated method stub
		
	}

	public void updateMap(AndrewKevinTile[][] tMap) {
		int i = 0;
		String numCol = "";
		for(int row = 0; row<tMap.length; row++) {
			for(int col = 0; col < tMap[row].length; col++) {				
				if(tMap[row][col].getNonStaticOccupant() == PLAYER) {
					System.out.print("P");
					
				}
				else if(tMap[row][col].getStaticOccupant() == ROCK) {
					System.out.print("R");
				}
				else if(tMap[row][col].getStaticOccupant() == TREE) {
					System.out.print("T");
				}
				else if(tMap[row][col].getStaticOccupant() == FORAGE) {
					System.out.print("F");
				}
				else if(tMap[row][col].getStaticOccupant() == TREASURE) {
					System.out.print("X");
				}
				else if(tMap[row][col].getNonStaticOccupant() == MONKEY) {
					System.out.print("M");
				}
				else{
					System.out.print(" ");
				}
			}
			System.out.println(" "+row);
		}
		while(i < tMap[0].length) {
			numCol += i;
			i++;
		}
		System.out.println(numCol);
	}
	
	private boolean respondToInput(String input) {
		if(isValidCoordinates()) {
			return true;
		}else if(isValidDirection()) {
			return true;
		}else {
			System.out.println("That is not valid");
			return false;
		}
		
	}

	@Override
	public String getUserInput() {
		return inputSource.nextLine();
	}
	@Override
	public boolean isValidDirection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValidCoordinates() {
		// TODO Auto-generated method stub
		return false;
	}
	//for(AndrewKevinTile col: map[row]) {
	//	text += col.getContent(); // Will be modified based off contents of the Tile
	//}
}
