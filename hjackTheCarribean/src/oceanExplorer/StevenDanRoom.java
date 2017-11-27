package oceanExplorer;

import rpgDDSL.DanielFrontend;

public class StevenDanRoom extends CaveRoom {
	
	public boolean visited;
	
	public StevenDanRoom(String description) {
		super(description);
		visited=false;
	}
	public void interpretInput(String input) {
		fight();
		input=CaveExplorer.in.nextLine();
		super.interpretInput(input);
		
	}
	public void fight() {
		DanielFrontend a=new DanielFrontend(CaveExplorer.inventory.getBeginningShip(),CaveExplorer.level);
		a.play();
		if(a.isWon()) {
			visited=true;
		}
	}
	public String getContents() {
		if(!visited) {
			return "E";
		}
		return super.getContents();
		
	}

}
