package battleshipJYSK;

import oceanExplorer.CaveExplorer;

public class SunnyIntro {

	private String commanderName;

	public SunnyIntro(String cName) {
		this.commanderName = cName;
	}

	public void play() {
		showIntroMessage();
	}
	
	public void showIntroMessage()
	{
		System.out.println("Captain Duran: The commander, " + commanderName + ", is setting up his battleships! Prepare to battle!");
		CaveExplorer.pause(100);
		System.out.println("(Prepare for a game of Battleship)");
		CaveExplorer.pause(500);
		System.out.println("\n~ ~ Press enter to begin! ~ ~");
	}
}
