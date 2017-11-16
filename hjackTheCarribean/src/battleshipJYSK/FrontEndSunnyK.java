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

	public static final void main(String[] args)
	{
		FrontEndSunnyK test = new FrontEndSunnyK();
		test.play(1, "Sunny", "Commander");
	}
	
	public FrontEndSunnyK() {
		backend = new BackEndJustinY(this);
	}
	
	public static boolean play(int level, String userName, String name) {
		commanderLevel = level;
		isWinner = false;
		commanderName = name;
		
		new SunnyIntro().play();
		CaveExplorer.in.nextLine();
		gameMenu();
		startGame();
		
		return isWinner;
	}
	
	public static void gameMenu()
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
		//Shows empty maps
		displayBothMaps();
		//asks coordinates to place ships
		askCoordsForShips();
		playing = true;
		while(playing)
		{
				//updates map each time
			displayBothMaps();
				//asks coordinates to fire on opponent
			askCoordsToFire();
				//checks to see if the game is over, if it is then playing = false
			if(isGameOver())
			{
				playing = false;
			}
		}
		System.out.print("");
	}
	
	
	public static void displayBothMaps()
	{
		int numRows = backend.boardSize();
		System.out.print("~ ~ ~ Your Board ~ ~ ~ ~ ~ ~ ~ Opponent Board ~ ~ ~");	
		for(int i = 0; i < numRows+4; i++)
		{
			if(i == 0 || i == numRows+2)
			{
				String rowString = " ";
				for(int j = 0; j < (numRows*2)+3;j++)
				{
					rowString += "_";
				}
			}
			if(i == numRows+3)
			{
				
			}
			System.out.print("\n");
			
		}
	}
	public static void askCoordsForShips()
	{
		//uses backend.getCo;ordInput

		for(int i = 0; i < backend.numberOfShips(); i++)
		{
			System.out.print("Where would you like to place ship #"+i+"?");
			int[] coords =  backend.getCoordInput();
			
			System.out.print("Which direction would you like to place it in? Enter 'N','E','W','S'");
			
			//int direction = backend.interpretDirectionInput();
			
		}
	}
	
	//lengthOfShip(ship e) gets length of ship from backend
	
	//tryShipPlacement(int row, int col, int direction,
	//int shipLength, JustinSunnyPlot[][] playerBoard) test to see if it can fit
	
	public static void askCoordsToFire()
	{
		int[] coords = {-10, -10};
		if(backend.isSkipPlayerTurn()) {
			backend.setSkipPlayerTurn(false);
			coords = backend.randomShipHit();
			backend.hit(coords[0], coords[1], backend.getCommanderPlots());
		}
		else {
			System.out.println("Shipmate: Where would you like to fire?");
			coords = backend.getCoordInput();
			if(coords[0] < 0 && coords[1] < 0) {
				int type = coords[0] * -1;
				if(backend.hasPowerUp(type)) {
					backend.decrementPowerUp(type);
					backend.processPowerUp(type);
				}
				else {
					System.out.println("Shipmate: You do not have any more of that power up!");
					askCoordsToFire();
				}
			}
			else {
				while(!(backend.hit(coords[0], coords[1], backend.getCommanderPlots()))) {
					System.out.println("Shipmate: That spot has already been struck or is out of the battlefield!");
					System.out.println("Shipmate: Where would you like to fire?");
					coords = backend.getCoordInput();
				}	
			}	
		}
		String j = "You did not hit a ship.";
		if(backend.playerHitShip(coords[0], coords[1])) {
			j = "You hit a ship!";
		}
		System.out.println("Shipmate: Bomb ahoy! You striked " + coords[0] + "," + coords[1] + "! " + j);
	}
	
	public static boolean isGameOver()
	{
		if(backend.isThereWinner())
		{
			isWinner = backend.isPlayerWinner();
			return true;
		}
		return false;
	}
	
	
	 // This method flips a coin that determines who makes the first move 
	public static void determineFirstTurn() {
		if(Math.random() < .50) {
			isPlayerTurn = true;
		}
		isPlayerTurn = false;
	}

	public boolean isPlaying() {
		return playing;
	}
	
	public String getCommanderName() {
		return commanderName;
	}
	
	public int getCommanderLevel() {
		return commanderLevel;
	}
	
}