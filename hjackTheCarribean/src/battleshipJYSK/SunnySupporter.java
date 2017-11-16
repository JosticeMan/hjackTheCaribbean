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
}
