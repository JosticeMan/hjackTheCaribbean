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
	
	
	BackEndJustinY numberOfShips();
	
	BackEndJustinY getBoardSize();
	
	BackEndJustinY getCoordInputs();

	boolean isThereWinner();
	
	boolean isPlayerWinner();
}
