package oceanExplorer;

public class AndrewRoom extends CaveRoom {

	public AndrewRoom(String description) {
		super(description);
		// TODO Auto-generated constructor stub
	}
	
	public void goBack() {
		
	}
	
	//
	//OVERIDE
	//
	public String validKeys() {
		return "wdsaf";
	}
	
	public void printAllowedEntry() {
		System.out.println("You can only enter 'f' and some other keys");
	}
	
	public void performAction(int direction) {
		if(direction == 4) {
			goBack();
		}else {
			System.out.println("Try pressing f.");
		}
	}

}

