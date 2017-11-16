package jungleTreasureHuntAZKL;

public class AndrewKevinTile {
	
	private int row;
	private int col;
	
	private int staticOccupant;

	private int nonStaticOccupant;
	
	public AndrewKevinTile(int row, int col) {
		this.row = row;
		this.col = col;
		this.staticOccupant = 0;
		this.nonStaticOccupant = 0;
	}
	
	public AndrewKevinTile(int row, int col, int sO) {
		this.row = row;
		this.col = col;
		this.staticOccupant = sO;
		this.nonStaticOccupant = 0;
	}
	
	public int getStaticOccupant() {
		return staticOccupant;
	}

	public void setStaticOccupant(int staticOccupant) {
		this.staticOccupant = staticOccupant;
	}

	public int getNonStaticOccupant() {
		return nonStaticOccupant;
	}

	public void setNonStaticOccupant(int nonStaticOccupant) {
		this.nonStaticOccupant = nonStaticOccupant;
	}
}
