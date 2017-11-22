package oceanExplorer;

import rpgDDSL.DanielFrontend;

public class stevenDanRoom extends CaveRoom {
	
	public int level;
	
	public stevenDanRoom(String description,int level) {
		super(description);
		this.level=level;
		
	}
	
	public void fight() {
		DanielFrontend a=new DanielFrontend(CaveExplorer.inventory.getBeginningShip(),level);
		a.play();
	}

}
