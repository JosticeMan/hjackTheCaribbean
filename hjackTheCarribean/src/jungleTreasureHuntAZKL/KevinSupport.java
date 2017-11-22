package jungleTreasureHuntAZKL;

public interface KevinSupport {

	boolean playing();
	void setPlay(boolean a);

	Object end();

	int processInput(String input);
	
	AndrewKevinTile[][] getMap();

	int getStepCount();
	
	int[] getPlayerPos();
	
	int[][] getVisibleRadius();

	int[][] getMonkeys();
	
	int[] getTreasurePos();
	
	boolean withinVisibleRange(int rowCords, int colCords);
	
	boolean isValidDirection(String input);
	
	boolean isValidCoordinates(String input);
}
