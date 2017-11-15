package jungleTreasureHuntAZKL;

import java.util.Scanner;
public class KevinFrontend implements AndrewSupport {
	
	private KevinSupport backend;
	public static Scanner  in;

	public static final void main(String[] args) {
		in = new Scanner(System.in);
		//KevinFrontend demo = new KevinFrontend();
		
	}
	
	public void play() {
		startGameMessage();
		/*
		  while(backend.playing()) {
			getMapInfo(); //get location of stuffs such as trees
			getStepCount(); // number of steps taken before limit
			getLookCount(); // number of times to look for traps before limit
			String input = backend.processInput(); 
			respondToInput(input);
			
			updateMap(); //basically update line of vision+ old vision
		}
		printEndGame(backend.end());
		*/
	}

	private void startGameMessage() {
		String s = "Welcome to the Treasure Hunter Game!! To see the rules type 'r' and to play press 'enter'";
		 System.out.println(s);
		
	}

	private void printEndGame(Object end) {
		// TODO Auto-generated method stub
		
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

	@Override
	public String getUserInput() {
		return in.nextLine();
	}
}
