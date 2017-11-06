package oceanExplorer;

public class Inventory {


	private String map; 
	private Ship beginningShip;
	private int kItems;

	
	private int lastDirection;
	
	public Inventory() {
		updateMap();
		beginningShip=new Ship(10,100,40);
	}

	public int getLastDirection() {
		return lastDirection;
	}

	public void setLastDirection(int lastDirection) {
		this.lastDirection = lastDirection;
	}

	public void updateMap() {
		map = " ";
		for(int col = 0; col < CaveExplorer.caves[0].length - 1; col++) {
			map += "____"; // 4 underscores
		}
		map += "___ \n"; // 3 underscores
		for(CaveRoom[] row: CaveExplorer.caves) {
			//3 rows of text
			for(int i = 0; i < 3; i++) {
				String text = "";
				for(CaveRoom cr : row) {
					//If door is open, leave open
					if(cr.getDoor(CaveRoom.WEST) != null && cr.getDoor(CaveRoom.WEST).isOpen()) {
						text += " ";
					}
					else {
						text += "|";
					}
					//Contents of room depend on what row this is
					if(i == 0) {
						text += "   ";
					}
					else if(i == 1) {
						text += " " + cr.getContents() + " ";
					}
					else if(i == 2) {
						//Draw space if door to south is open 
						if(cr.getDoor(CaveRoom.SOUTH) != null && cr.getDoor(CaveRoom.SOUTH).isOpen()) {
							text += "   "; //3 spaces
						}
						else {
							text += "___";
						}
					}
				} // Last caveroom in row
				text += "|";
				map += text + "\n";
			}
		}
	}

	public String getDescription() {
 		// return "You have nothing in your inventory.";
		return map;
 	}

	public Ship getBeginningShip() {
		return beginningShip;
	}

	public void addItems(int direction) {
		kItems += direction;
	}
	public int numItems() {
		return kItems;
	}
	
}
