package jungleTreasureHuntAZKL;

public interface KevinSupport {

	boolean playing();

	Object end();

	void processInput(String input);
	
	AndrewKevinTile[][] getMap();

	int getStepCount();
	
	int[] getPlayerPos();
	
	int[][] getVisibleRadius();

	int[][] getMonkeys();
	
	int[] getTreasurePos();
}
