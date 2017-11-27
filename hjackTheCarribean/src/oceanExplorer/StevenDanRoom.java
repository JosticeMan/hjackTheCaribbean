package oceanExplorer;

import rpgDDSL.DanielFrontend;

public class StevenDanRoom extends CaveRoom {
	
	
	public StevenDanRoom(String description) {
		super(description);
	}
	public void performAction(int direction) {
		fight();
		
	}
	public void fight() {
		DanielFrontend a=new DanielFrontend(CaveExplorer.inventory.getBeginningShip(),CaveExplorer.level);
		a.play();
	}

}
