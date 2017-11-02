package oceanExplorer;

public class Inventory {

	private String map; 
	
	public Inventory() {
		updateMap();
	}

	public void updateMap() {
		map = " ";
		for(int col = 0; col < OceanExplorerMain.territories[0].length - 1; col++) {
			map += "____"; // 4 underscores
		}
		map += "___ \n"; // 3 underscores
		for(OceanTerritory[] row: OceanExplorerMain.territories) {
			//3 rows of text
			for(int i = 0; i < 3; i++) {
				String text = "";
				for(OceanTerritory cr : row) {
					//If door is open, leave open
					if(cr.getRiptide(OceanTerritory.WEST) != null && cr.getRiptide(OceanTerritory.WEST).isOpen()) {
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
						if(cr.getRiptide(OceanTerritory.SOUTH) != null && cr.getRiptide(OceanTerritory.SOUTH).isOpen()) {
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
}
