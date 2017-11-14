package rpgDDSL;

public class RPGRoom {

	private int type;
	private boolean north;
	private boolean south;
	private boolean east;
	private boolean west;
	
	
	public RPGRoom(int type) {
		this.type = type;
		north = false;
		south = false;
		west = false;
		east = false;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isNorth() {
		return north;
	}

	public void setNorth(boolean north) {
		this.north = north;
	}

	public boolean isSouth() {
		return south;
	}

	public void setSouth(boolean south) {
		this.south = south;
	}

	public boolean isEast() {
		return east;
	}

	public void setEast(boolean east) {
		this.east = east;
	}

	public boolean isWest() {
		return west;
	}

	public void setWest(boolean west) {
		this.west = west;
	}

	public String toString()
	{
		if (type == 0)
		{
			return "_";
		}
		else
		{
			if (type == 1)
			{
				return "H";
			}
			else
			{
				return "E";
			}
		}
	}
}
