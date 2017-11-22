package battleshipJYSK;

import oceanExplorer.CaveExplorer;
import oceanExplorer.Inventory;
import oceanExplorer.Ship;

public class FrontEndSunnyK implements JustinSupporter {
	
	private boolean playing;
	private SunnySupporter backend;
	
	private String commanderName;
	private int commanderLevel; //This is essentially the difficulty level of the commander
	private boolean isPlayerTurn; //This tracks whose turn it is
	private String userName; //User name of the player
	private boolean isWinner;
	private Ship[] ships;
	
	private JustinSunnyPlot[][] playerPlots;
    private JustinSunnyPlot[][] commanderPlots;
    private String[][] quotes;
    
    private String[][] quotes1;

    /*
	public static final void main(String[] args)
	{
		FrontEndSunnyK test = new FrontEndSunnyK();
		test.play(1, "Sunny", "Commander");
	}
	*/
	
	public FrontEndSunnyK(int level, String userName, String name) {
		this.commanderLevel = level;
		this.commanderName = name;
		this.userName = userName;
		backend = new BackEndJustinY(this);
		if(this.userName.equals("")) {
			this.userName = "Mysterious Person";
		}
		String[][] temp = {{"Oju! How dare you, " + userName + ", hit one of my ships!", "Some of my companions were on that boat! You need some counseling!"},
						   {"Spain will sink your ship before you sink ours! Too bad for you, " + userName + ".", "Not one of my ships! I will get you for this!"},
						   {"Oh " + userName + ", One ship hit is no big deal to the great british!", "The men aboard that ship have died gloriously."}};
		quotes = temp;
		String[][] temp1 = {{"It seems like you are struggling brother, " + userName + ". Better for me!", "I suggest that you surrender before I have to sink you, " + userName + "."},
							{"Is that, " + userName + ", struggling to hit our boats! Bueno!", "What a great day at the sea! A bad day for you, " + userName + "!"},
							{"I am confident in our ability to sink your ships. Give up, " + userName + ".", "It appears god is on our side and not yours, " + userName + "."}};
		quotes1 = temp1;
	}	
	
	public boolean play() {
		isWinner = false;
		playerPlots = backend.getPlayerPlots();
		commanderPlots = backend.getCommanderPlots();
		ships = CaveExplorer.inventory.getShip();
		
		new SunnyIntro(commanderName).play();
		gameMenu();
		
		return isWinner;
	}
	
	public void gameMenu()
	{
		System.out.println("Captain Duran: If you do not know how to play Battleship, enter 'a' \n If you already know how to play, enter 'd'");
		String input = CaveExplorer.in.nextLine();
		if(input.equalsIgnoreCase("a"))
		{
			System.out.print("In Battleship, you have the player board as well as the opponent's board."
					+ "\n Each player sets up their board by placing their available ships in different configurations." 
					+ "\n After the board is set up, each player takes turns and targets one spot on one another's board."
					+ "\n This 'fires' a shot at that target and will either hit or miss a ship, once all ships are sunk "
					+ "\n on one person's board, a winner is declared! There are also powerups to assist you. \n");
			CaveExplorer.pause(2000);
			startGame();
		}
		if(input.equalsIgnoreCase("d"))
		{
			startGame();
		}
		else
		{
			System.out.println("Captain Duran: What are you saying!? That key is not valid. Only 'd' or 'a'!");
			gameMenu();
			
		}
	}
	
	public void startGame() 
	{
		CaveExplorer.pause(500);
		displayBoard(playerPlots);
		playing = true;
		askCoordsForShips();
		backend.commanderPlaceShip(ships);
		determineFirstTurn();
		if(!playing) {
		}
		else if(isPlayerTurn) 
		{
			playerGoesFirst();
		}
		else 
		{
			commanderGoesFirst();
		}
	}
	
	public void playerGoesFirst()
	{
		updateBothMaps();
		CaveExplorer.pause(500);
		askCoordsToFire();
		if(!playing) 
		{
			return;
		}
		if(isGameOver())
		{
			playing = false;
		}
		else 
		{
												CaveExplorer.pause(500);
			commanderMakesMove();
			if(isGameOver())
			{
				playing = false;
			}
			else 
			{
												CaveExplorer.pause(500);
				updateBothMaps();
				rotateTurns();
			}
		}
	}
	
	public void commanderGoesFirst()
	{
		CaveExplorer.pause(500);
		System.out.println("Captain Duran: " + commanderName + " is making the first move!");
		CaveExplorer.pause(500);
		commanderMakesMove();
		if(isGameOver())
		{
			playing = false;
		}
		else 
		{
			CaveExplorer.pause(500);
			updateBothMaps();
			CaveExplorer.pause(500);
			askCoordsToFire();
			if(!playing) 
			{
				return;
			}
			if(isGameOver())
			{
				playing = false;
			}
			else 
			{
													CaveExplorer.pause(500);
				commanderMakesMove();
				if(isGameOver())
				{
					playing = false;
				}
				else 
				{
													CaveExplorer.pause(500);
					updateBothMaps();
					rotateTurns();
				}
			}
		}
	}
	
	public void rotateTurns()
	{
		while(playing)
		{
										CaveExplorer.pause(500);
			askCoordsToFire();
			if(!playing) 
			{
				return;
			}
										CaveExplorer.pause(500);
			updateBothMaps();
			if(isGameOver())
			{
				playing = false;
			}
			else 
			{
										CaveExplorer.pause(500);
				commanderMakesMove();
				if(isGameOver())
				{
					playing = false;
				}
										CaveExplorer.pause(500);
				updateBothMaps();
			}
		}
	}
	
	public void commanderMakesMove() {
		int[] coords = backend.commanderMove(commanderLevel);
		if(coords[0] != -1 && coords[1] != -1) 
		{
			backend.hit(coords[0], coords[1], playerPlots);
			String dia = commanderName + " did not manage to hit your ship! Argh!";
			if(backend.playerHitShip(coords[0], coords[1], playerPlots)) 
			{
				dia = commanderName + " hit a part of one of your ships!";
			}
			System.out.println("Captain Duran: " + commanderName + " has decided to hit " + coords[0] + " , " + coords[1] + "! " + dia);
			CaveExplorer.pause(1500);
		}
	}

	public void updateBothMaps()
	{
		displayBoard(playerPlots);
		displayBoard(commanderPlots); 
	}
	
	public void displayBoard(JustinSunnyPlot[][] plots)
	{
		int numRows = backend.boardSize();
		if(plots == playerPlots)
			System.out.print("~ ~ Your Board ~ ~\n");
		if(plots == commanderPlots)
			System.out.print("~ Commander Board ~\n");
		for(int row = 0; row < numRows; row++)
		{
			System.out.print(row + " ");
			for(int col = 0; col < numRows; col++)
			{
				if(plots == commanderPlots) {
					System.out.print(plots[row][col].toCommanderString());
				}
				else {
					System.out.print(plots[row][col]);
				}
			}
			System.out.print("\n");
		}
		System.out.print(" ");
		for(int numCol = 0; numCol < numRows; numCol++)
		{
			System.out.print("  " + numCol);
		}
		System.out.print("\n");
	}
	
	public String addHitMessage()
	{
		return commanderName + ": " + CaveExplorer.randomString(quotes[commanderLevel - 1]);
	}
	
	public String addHitNoMessage()
	{
		return commanderName + ": " + CaveExplorer.randomString(quotes1[commanderLevel - 1]);
	}
	
	
	public void askCoordsForShips()
	{	
		for(int i = 0; i < backend.numberOfShips(); i++)
		{
			int lengthOfCurrentShip = backend.lengthOfShip(ships[i]);
			
			System.out.println("Shipmate: Where would you like to position ship #"+(i+1)+" of length " + lengthOfCurrentShip +"?");
			int[] coords =  backend.getCoordInput();
			if(coords.length == 0) {
				return;
			}
			
			System.out.println("Shipmate: To thou direction would you like to place it in? Enter 'N','E','S','W'");
			int direction = backend.interpretDirectionInput();
			
			while(!backend.tryShipPlacement(coords[0], coords[1], direction, lengthOfCurrentShip, playerPlots))
			{
				System.out.println("Shipmate: Your input was either already taken by another ship or is out of the battlefield! Where would you like to place ship #"+(i+1)+"?");
				coords =  backend.getCoordInput();
				if(coords.length == 0) {
					return;
				}
				
				System.out.println("Shipmate: To which direction would you like to place it in? Enter 'N','E','S','W'");
				direction = backend.interpretDirectionInput();
			}
			System.out.println("Shipmate: Ho-ah! The ship has been succesfully position captain!");
			updateMaps();
			displayBoard(playerPlots);
		}
	}
	
	public void win() {
		isWinner = true; 
		playing = false;
	}
	
	//lengthOfShip(ship e) gets length of ship from backend
	
	//tryShipPlacement(int row, int col, int direction,
	//int shipLength, JustinSunnyPlot[][] playerBoard) test to see if it can fit
	
	public void updateMaps() {
		playerPlots = backend.getPlayerPlots();
		commanderPlots = backend.getCommanderPlots();
	}

	public void askCoordsToFire()
	{
		boolean usedPowerup = false;
		int[] coords = {-10, -10};
		if(backend.isSkipPlayerTurn()) 
		{
			backend.setSkipPlayerTurn(false);
			coords = backend.randomShipHit();
			backend.hit(coords[0], coords[1], backend.getCommanderPlots());
		}
		else 
		{
			System.out.println("Shipmate: Where would you like to fire?");
			coords = backend.getCoordInput();
			if(coords.length == 0) 
			{
				return;
			}
			if(coords[0] < 0 && coords[1] < 0) 
			{
				int type = coords[0] * -1;
				usedPowerup = checkAndUsePowerup(type, usedPowerup);
				return;
			}
			else 
			{
				while(!(backend.hit(coords[0], coords[1], backend.getCommanderPlots()))) 
				{
					System.out.println("Shipmate: That spot has already been struck or is out of the battlefield!");
					System.out.println("Shipmate: Where would you like to fire?");
					coords = backend.getCoordInput();
				}	
			}	
		}
		if(!(usedPowerup)) 
		{
			String j = "You did not hit a ship. \n";
			j += addHitNoMessage();
			updateMaps();
			if(backend.playerHitShip(coords[0], coords[1], commanderPlots)) 
			{
				j = "You hit a ship! \n";
				j += addHitMessage();
			}
			System.out.println("Shipmate: Bomb ahoy! You striked " + coords[0] + "," + coords[1] + "! " + j);
			CaveExplorer.pause(1500);
		}
	}
	
	public boolean checkAndUsePowerup(int type, boolean usedPowerup)
	{
		if(backend.hasPowerUp(type)) 
		{
			backend.decrementPowerUp(type);
			backend.processPowerUp(type);
			checkStormcaller();
			return true;
		}
		else 
		{
			System.out.println("Shipmate: You do not have any more of that power up!");
			askCoordsToFire();
			return false;
		}
	}
	
	public void checkStormcaller()
	{
		if(backend.isCommanderSkip())
			askCoordsToFire();
	}
	
	public void torpedo()
	{
		int[] coords = backend.getCoordInput();
		int direction = backend.interpretDirectionInput();
		
		while(tryTorpedoPlacement())
		{
			
		}
		
		
	}
	public boolean tryTorpedoPlacement(int coord1, int coord2, int direction)
	{
		
		//NESW 0123
		if(direction ==)
		if(playerPlots[coord1][coord2])
		{
			
		}
		
		return true;
	}
	
	public boolean isGameOver()
	{
		if(backend.isThereWinner())
		{
			isWinner = backend.isPlayerWinner();
			return true;
		}
		return false;
	}
	
	
	 // This method flips a coin that determines who makes the first move 
	public void determineFirstTurn() {
		if(Math.random() < .50) {
			isPlayerTurn = true;
		}
		isPlayerTurn = false;
	}

	public boolean isPlaying() {
		return playing;
	}
	
	public String getCommanderName() {
		return this.commanderName;
	}
	
	public int getCommanderLevel() {
		return this.commanderLevel;
	}
	
}