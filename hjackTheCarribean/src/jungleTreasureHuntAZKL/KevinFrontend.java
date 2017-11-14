package jungleTreasureHuntAZKL;

public class KevinFrontend implements AndrewSupport {
	
	private KevinSupport backend;

	public static final void main(String[] args) {
		KevinFrontend demo = new KevinFrontend();
		
	}
	
	public void play() {
		while(backend.playing()) {
			getMapInfo(); //get location of stuffs such as trees
			getStepCount(); // number of steps taken before limit
			getLookCount(); // number of times to look for traps before limit
			String input = backend.getInput(); 
			respondToInput(input);
			
			updateMap(); //basically update line of vision+ old vision
		}
		printEndGame(backend.end());
	}

	private void updateMap() {
		// TODO Auto-generated method stub
		
	}

	private void respondToInput(String input) {
		// TODO Auto-generated method stub
		
	}

	public KevinFrontend() {
		backend = new AndrewBackend(this);
	}

	@Override
	public void getMapInfo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getStepCount() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void revealTreasure() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getLookCount() {
		// TODO Auto-generated method stub
		
	}
}
