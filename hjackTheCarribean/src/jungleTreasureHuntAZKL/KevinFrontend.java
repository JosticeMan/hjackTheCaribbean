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
	
	public static final int ROW = 0;
	public static final int COL = 1;
	
	private KevinSupport backend;
	public static Scanner inputSource;
	
	public int pRow;
	public int pCol;
	public int tRow;
	public int tCol;
	public boolean starting;
	public boolean treasureFound;
	
	public boolean mNear;
	public boolean mClose;
	public boolean tClose;
	public boolean tRightInFront;
	
	private AndrewKevinTile[][] map;

	public static void main(String[] args) {
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
		tRow = backend.getTreasurePos()[ROW];
		tCol = backend.getTreasurePos()[COL];
		getNonStaticPosition();
		mNear = false; //farther than close
		mClose = false;
		tClose = false;
		tRightInFront = false;
		starting = false; //loop if they dont press p
		treasureFound  = false;
	}

	public void play() {
		startGameMessage();
		while(starting == false) {
			startInput(getInput());
		}
		  	while(backend.playing()) {
			updateMap(map);
			displayTreasureHint();
			displayMonkeyHints();
			displayNumSteps();
			respondToInput(backend.processInput(getInput()));
		}
		printEndGame();
	}

	private void respondToInput(int i) {
		if(i == 0) {
			System.out.println("There is something there, you can't move there.");
		}else if(i == 2) {
			System.out.println("That is beyond your line of sight, You can look there.");
		}else if(i == 5) {
			System.out.println("That is outside the map. Don't be dum");
		}else if(i == 4 || i ==8) {
			treasureFound = true;
			backend.setPlay(false);
		}else if(i == -1) {
			System.out.println("That is not a valid key. Please type 'wdsa'.");
		}
	}
	private void displayVictory() {
		String v = "Dun-Da-Da-Dun! Congratulation, You have found the treasure!";
		System.out.println(v);
	}

	private int amountVicinityRow(int row) {
		return Math.abs(row - pRow);
	}
	private int amountVicinityCol(int col) {
		return Math.abs(col - pCol);
	}
	private void howtCloseTreasure() {
		if(amountVicinityRow(tRow) < 2 && amountVicinityCol(tRow)<2) {
			tClose = false;
			tRightInFront = true;

		}
		else if(amountVicinityRow(tRow) < 3 && amountVicinityCol(tRow)<3) {
			tClose = true;
		}
	}
	private void displayTreasureHint() {
		howtCloseTreasure();
		if(tRightInFront == true) {
			System.out.println("You notice something at ground reallll close to you!!");
		}
		else if(tClose == true) {
			System.out.println("You notice something strange in the ground near you!");
		}
	}
	private void howCloseAreMonkeys() {
		for(int i = 0; i < backend.getMonkeys().length; i++) {
			if(amountVicinityRow(backend.getMonkeys()[i][ROW]) < 2 && amountVicinityCol(backend.getMonkeys()[i][COL]) < 2) {
				mClose = true;
				mNear = false;
				break;
			}else if(amountVicinityRow(backend.getMonkeys()[i][ROW]) < 5 && amountVicinityCol(backend.getMonkeys()[i][COL]) < 5) {
				mNear = true;
				break;
			}
		}
	}
	private void displayMonkeyHints() {
		howCloseAreMonkeys();
		if(mClose == true) {
			System.out.println("The rustling noises are getting closer...");
		}else if(mNear == true) {
			System.out.println("You started to hear rustling noises.");
		}
	}
	private void displayNumSteps() {
		System.out.println("You have "+backend.getStepCount()+" steps left.");
	}
	private void getNonStaticPosition() {
		pRow = backend.getPlayerPos()[ROW];
		pCol = backend.getPlayerPos()[COL];
	}
	private void startGameMessage() {
		String s = "Welcome to the Treasure Hunter Game!! In this game, you need to found the treasure, to move type 'wdsa'." +"\n"+ "There will be hints when you are close to the treasure beware of the wild monkeys." +"\n"+ "If caught, game over! So listen closely for the rustling sounds! To get started type 'p'!";
		System.out.println(s);
		
	}
	private void printEndGame() {
		if(backend.getStepCount() > 0 && treasureFound == false) {
			System.out.println("Awwwww, you ran out of steps out to found the treasure! That is unfortunate.");
		}else {
			displayVictory();
		}
	}

	public void updateMap(AndrewKevinTile[][] tMap) {
		int i = 0;
		String numCol = "";
		getNonStaticPosition();
		for(int row = 0; row<tMap.length; row++) {
			for(int col = 0; col < tMap[row].length; col++) {				
				if(backend.withinVisibleRange(row, col)){
					showContent(tMap, row, col);
				}else {
					System.out.print("X ");
				}
			}
			System.out.println(" "+row);
		}
		while(i < tMap[0].length) {
			numCol += i + " ";
			i++;
		}
		System.out.println(numCol);
	}
	
	private void showContent(AndrewKevinTile[][] tMap,int row, int col) {
		if(tMap[row][col].getStaticOccupant() == ROCK) {
			System.out.print("R ");
		}
		else if(tMap[row][col].getStaticOccupant() == TREE) {
			System.out.print("T ");
		}
		else if(tMap[row][col].getStaticOccupant() == FORAGE) {
			System.out.print("F ");
		}
		else if(tMap[row][col].getStaticOccupant() == TREASURE) {
			System.out.print("X ");
		}
		else if(tMap[row][col].getNonStaticOccupant() == MONKEY) {
			System.out.print("M ");
		}else if(tMap[row][col].getNonStaticOccupant() == PLAYER) {
			System.out.print("P ");
		}else {
			System.out.print("  ");
		}
	}
	private void startInput(String input) {
		if(input.equalsIgnoreCase("p")) {
			backend.setPlay(true);
			starting = true;
			System.out.println("Let the hunt begin.");
		}else {
			System.out.println("Please type in 'p' if u want to play");
		}
		
	}


		//for(AndrewKevinTile col: map[row]) {
	//	text += col.getContent(); // Will be modified based off contents of the Tile
	//}
}
