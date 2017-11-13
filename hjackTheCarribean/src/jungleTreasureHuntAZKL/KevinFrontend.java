package jungleTreasureHuntAZKL;

public class KevinFrontend implements AndrewSupport {
	
	private KevinSupport backend;

	public static final void main(String[] args) {
		KevinFrontend demo = new KevinFrontend();
		
	}
	
	public KevinFrontend() {
		backend = new AndrewBackend(this);
	}
}
