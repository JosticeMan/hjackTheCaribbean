package battleshipJYSK;

import oceanExplorer.Ship;

public interface SunnySupporter {
 
	/**
	 * @return - Returns the player field 
	 */
	JustinSunnyPlot[][] getPlayerPlots();

	/**
	 * @return - Returns the commander field
	 */
	JustinSunnyPlot[][] getCommanderPlots();
	
	
	boolean tryShipPlacement(int row, int col, int direction, int shipLength, JustinSunnyPlot[][] playerBoard);
	
	int lengthOfShip(Ship e);
	
	int numberOfShips();
	
	int interpretDirectionInput();
	
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

	boolean playerHitShip(int i, int j, JustinSunnyPlot[][] playerPlots);

	void commanderPlaceShip(Ship[] ships);

	void printMap(JustinSunnyPlot[][] playerPlots);

	int[] commanderMove(int commanderLevel);

	boolean isCommanderSkip();
}
