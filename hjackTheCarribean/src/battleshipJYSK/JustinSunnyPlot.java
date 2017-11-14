package battleshipJYSK;

public class JustinSunnyPlot {

	private boolean shipOccupied;
	private boolean hasBeenHit;
	private int row;
	private int col;
	
	public JustinSunnyPlot(int row, int col) {
		shipOccupied = false;
		hasBeenHit = false;
		this.row = row;
		this.col = col;
	}

	public String toString() {
		if(shipOccupied) {
			return "[X]";
		}
		return "[ ]";
	}
	
	public boolean isShipOccupied() {
		return shipOccupied;
	}

	public void setShipOccupied(boolean shipOccupied) {
		this.shipOccupied = shipOccupied;
	}

	public boolean isHasBeenHit() {
		return hasBeenHit;
	}

	public void setHasBeenHit(boolean hasBeenHit) {
		this.hasBeenHit = hasBeenHit;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

}
