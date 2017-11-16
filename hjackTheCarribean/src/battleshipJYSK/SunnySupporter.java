package battleshipJYSK;

public interface SunnySupporter {

	/**
	 * @return - Returns the player field 
	 */
	JustinSunnyPlot[][] getPlayerPlots();

	/**
	 * @return - Returns the commander field
	 */
	JustinSunnyPlot[][] getCommanderPlots();
	
	
	int numberOfShips();
	
	int boardSize();
	
	int[] getCoordInput();

	boolean isThereWinner();
	
	boolean isPlayerWinner();

	boolean hit(int i, int j, JustinSunnyPlot[][] commanderPlots);

	boolean isSkipPlayerTurn();

	void setSkipPlayerTurn(boolean b);

	int[] randomShipHit();

	boolean hasPowerUp(int i);

	void decrementPowerUp(int type);

	void processPowerUp(int type);

	boolean playerHitShip(int i, int j);
}
