package battleshipJYSK;

import oceanExplorer.CaveExplorer;

public class FrontEndSunnyK implements JustinSupporter {
	
	private static boolean playing;
	private static SunnySupporter backend;
	
	private static int commanderLevel; //This is essentially the difficulty level of the commander
	private static boolean isPlayerTurn; //This tracks whose turn it is
	private static String userName; //Username of the player
	private static boolean isWinner;
	
	public FrontEndSunnyK() {
		backend = new BackEndJustinY(this);
	}
	
	public static boolean play(int level, String userName) {
		commanderLevel = level;
		userName = userName;
		isWinner = false;
		
		new SunnyIntro().play();
		CaveExplorer.in.nextLine();
		startGame();
		
		return isWinner;
	}
	
	public static void startGame() {
		JustinSunnyPlot[][] playerPlots = backend.getPlayerPlots();
		JustinSunnyPlot[][] commanderPlots = backend.getCommanderPlots();
		
	}
	
	/**
	 * This method flips a coin that determines who makes the first move
	 */
	public static void determineFirstTurn() {
		if(Math.random() < .50) {
			isPlayerTurn = true;
		}
		isPlayerTurn = false;
	}
	
}
