package jungleTreasureHuntAZKL;

public class AndrewKevinTile {

	public static final int PLAYER = 1;
	public static final int MONKEY = 2;
	
	public static final int ROCK = 1;
	public static final int TREE = 2;
	public static final int FORAGE = 3;
	public static final int TREASURE = 7;
	
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

	public String getContent() {
		if(staticOccupant == ROCK) {
			return "R";
		}else if(nonStaticOccupant == MONKEY) {
			return "M";
		}else if(nonStaticOccupant == ROCK) {
			return "P";
		}
		return " ";
	}
}
