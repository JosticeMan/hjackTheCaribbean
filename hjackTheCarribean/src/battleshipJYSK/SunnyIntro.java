package battleshipJYSK;

import oceanExplorer.CaveExplorer;

public class SunnyIntro {

	private String commanderName;

	public SunnyIntro() {
	}

	public void play() {
		showIntroMessage();
	}
	
	public void showIntroMessage()
	{
		System.out.print("The commander, " + commanderName + ", is setting up his battleships! Prepare to battle!");
		CaveExplorer.pause(100);
		System.out.print("(Prepare for a game of Battleship)");
		CaveExplorer.pause(500);
		System.out.print("\n~ ~ Press enter to begin! ~ ~");
	}
}
