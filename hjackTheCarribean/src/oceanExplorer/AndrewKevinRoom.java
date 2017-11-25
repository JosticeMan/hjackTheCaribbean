package oceanExplorer;

import jungleTreasureHuntAZKL.KevinFrontend;

public class AndrewKevinRoom extends CaveRoom {
	
	private KevinFrontend game;
	
	public AndrewKevinRoom() {
		super("Shipmate: Land Ho Cap'n! There be treasure in this jungle, I smell it!");
	}

	//OVERRIDE
	
	public String validKeys() {
		return "wdsaf";
	}
	
	public void printAllowedEntry() {
		System.out.println("Press 'f' to begin the treasure hunt or continue your journey by leaving the room.");
	}
	
	public void performAction(int direction) {
		if(direction == 5) {
		    game = new KevinFrontend();
		    game.play();
		} else {
			System.out.println("Shipmate: ...");
		}
	}
	
	public String getContents() {
		return "L";
	}
}
