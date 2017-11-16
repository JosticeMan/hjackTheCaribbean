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
		updateMap();
		
		
		  while(backend.playing()) {
			getMapInfo(); //get location of stuffs such as trees
			getStepCount(); // number of steps taken before limit
			backend.processInput(getInput());
			updateMap(); //basically update line of vision+ old vision
		}
		printEndGame(backend.end());
	}

	private void startGameMessage() {
		String s = "Welcome to the Treasure Hunter Game!! To see the rules type 'r' and to play press 'enter'";
		 System.out.println(s);
		
	}

	private void printEndGame(Object end) {
		// TODO Auto-generated method stub
		
	}

	public void updateMap() {
		String displayMap = "";
		for(int col = 0; col < map[0].length; col++) {
			displayMap+="X";
		}
		displayMap += "XX\n";
		
		for(int row = 0; row < map.length; row++) {
			String text = "X";
			for(AndrewKevinTile col: map[row]) {
				text += col.getContent(); // Will be modified based off contents of the Tile
			}
			text += "X";
			displayMap += text + "\n";
			
		}
		
		String text = "";
		for(int col = 0;col < map[map.length-1].length; col++) {
			text += "X";
		}
		text += "XX";
		displayMap += text + "\n";
		
		System.out.println(displayMap);
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
	public AndrewKevinTile[][] getMapInfo() {
		return backend.getMap();
		
	}

	@Override
	public void getStepCount() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void revealTreasure() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getUserInput() {
		return in.nextLine();
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
}
