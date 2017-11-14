package battleshipJYSK;

import oceanExplorer.CaveExplorer;

public class FrontEndSunnyK implements JustinSupporter {
	
	private static boolean playing;
	private static SunnySupporter backend;
	
	private static String commanderName;
	private static int commanderLevel; //This is essentially the difficulty level of the commander
	private static boolean isPlayerTurn; //This tracks whose turn it is
	private static String userName; //User name of the player
	private static boolean isWinner;
	
	public FrontEndSunnyK() {
		backend = new BackEndJustinY(this);
	}
	
	public static boolean play(int level, String userName, String name) {
		commanderLevel = level;
		userName = userName;
		isWinner = false;
		commanderName = name;
		
		new SunnyIntro().play();
		CaveExplorer.in.nextLine();
		gameMenu();
		
		return isWinner;
	}
	
	public void gameMenu()
	{
		JustinSunnyPlot[][] playerPlots = backend.getPlayerPlots();
		JustinSunnyPlot[][] commanderPlots = backend.getCommanderPlots();
		System.out.print("If you do not know how to play Battleship, enter 'a' \n If you already know how to play, enter 'd'");
		String input = CaveExplorer.in.nextLine();
		if(input.equals("a"))
		{
			System.out.print("In Battleship, you have the player board as well as the opponent's board."
					+ "\n Each player sets up their board by placing their available ships in different configurations." 
					+ "\n After the board is set up, each player takes turns and targets one spot on one another's board."
					+ "\n This 'fires' a shot at that target and will either hit or miss a ship, once all ships are sunk "
					+ "\n on one person's board, a winner is declared! There are also powerups to assist you.");
		}
		if(input.equals("d"))
		{
			startGame();
		}
		else
		{
			System.out.print("Sorry, that key is not valid. Please try again.");
			gameMenu();
			
		}
	}
	
	public static void startGame() {
		
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

	public static String getCommanderName() {
		return commanderName;
	}
	
	public int getCommanderLevel() {
		return commanderLevel;
	}
	
}
