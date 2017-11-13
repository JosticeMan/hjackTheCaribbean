package battleshipJYSK;

import oceanExplorer.CaveExplorer;

public class FrontEndSunnyK implements JustinSupporter {
	
	private static boolean playing;
	private SunnySupporter backend;
	
	private static int commanderLevel; //This is essentially the difficulty level of the commander
	private static boolean isPlayerTurn; //This tracks whose turn it is
	private static String userName; //Username of the player
	
	public FrontEndSunnyK() {
		backend = new BackEndJustinY(this);
	}
	
	public void play() {
		new SunnyIntro().play();
		CaveExplorer.in.nextLine();
		startGame();
	}
	
	public void startGame() {
		JustinSunnyPlot[][] playerPlots = backend.getPlayerPlots();
		JustinSunnyPlot[][] commanderPlots = backend.getCommanderPlots();
		
	}
	
	//Shows both player and boss' fields
	public void displayMap()
	{
		String[][] playerMap = BackEndJustinY.getThePlayerGameBoard();
		
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
