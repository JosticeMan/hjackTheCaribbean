package oceanExplorer;

public class KevinRoom extends CaveRoom {

	public KevinRoom(String description) {
		super(description);
	}
	public String validKeys() {
		return "f";
	}
	
	public void printAllowedEntry() {
		System.out.println("You can only enter f to interact!");
	}
	public void performAction(int direction) {
		if(direction ==1) {
			if((int)(Math.random()*2)+1 ==1 ){
				CaveExplorer.currentRoom = CaveExplorer.caves[0][1];
				CaveExplorer.currentRoom.enter();
			}
			else if((int)(Math.random()+1)*4 ==2 ){
				CaveExplorer.currentRoom = CaveExplorer.caves[2][1];
				CaveExplorer.currentRoom.enter();
			}
		}

	} 
	public String getContents() {
		return "K";
	}
	public String getDescription() {
		return super.getDescription() + "\n In this room, You can only pressed f!";
		
	}
	public int genRanNum() {
		return (int)(Math.random()*6);
	}
}