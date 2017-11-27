package oceanExplorer;

import rpgDDSL.DanielFrontend;

public class StevenDanRoom extends CaveRoom {
	
	private DanielFrontend game;
	private boolean visited;
	
	public StevenDanRoom() {
		super("You are being attacked by rogue pirates! Prepare to fight or flee with your life. (Surrender with a directional key or press f to fight!)");
		visited=false;
	}

	//OVERRIDE
	
	public String validKeys() {
		if(!visited)
			return "wdsaf";
		else
			return super.validKeys();
	}
	
	public void printAllowedEntry() {
		if(!visited)
			System.out.println("Press 'f' to begin the treasure hunt or a direction key to surrender.");
		else 
			super.printAllowedEntry();
	}
	
	public void performAction(int direction) {
		if(!visited) {
			if(direction == 4) {
				visited=true;
			    game = new DanielFrontend(CaveExplorer.inventory.getBeginningShip(),CaveExplorer.getLevel());
			    game.play();
			} else if(direction<4){
				System.out.println("You have decided to surrender.");
				System.exit(0);
			}else {
				printAllowedEntry();
			}
		}
		else {
			super.performAction(direction);
		}
	}
	
	public String getContents() {
		if(!visited) {
			return "E";
		}
		else {
			return " ";
		}
	}
	
	public String getDescription() {
		if(!visited) {
			return super.getDescription();
		}else {
			return "You have defeated the priates here.";
		}
	}
	

}
