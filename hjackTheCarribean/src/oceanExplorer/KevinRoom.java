package oceanExplorer;

public class KevinRoom extends CaveRoom {
	int nTimes;
	int cTimes;
	public KevinRoom(String description) {
		super(description);
		nTimes = genRanNum();
		cTimes = 0;
	}
	

	public void interpretInput(String input) {
		if(cTimes < nTimes) {
		cTimes++;

		}else {
			super.interpretInput(input);
		}
		
		System.out.println(cTimes);
		System.out.println(nTimes);
	} 
	public String getContents() {
		return "K";
	}
	public String getDescription() {
		return "Shipmate: Oh no! There is no wind Captain, We need to wait for the wind to come! Continue to press the desire direction to move.";
		
	}
	public int genRanNum() {
		return (int)((Math.random()+1)*3);
	}
}