package oceanExplorer;

import java.util.Scanner;

public class CaveExplorer {

	public static CaveRoom[][] caves; //Every one in the cave
	public static Scanner in; //For user input
	public static CaveRoom currentRoom; //Changes based on how the user navigated
	public static Inventory inventory; //Where are all objects found are kept
	public static boolean playing = true;
	public static NPC[] npcs;
	
	public static void main(String[] args) {
		in = new Scanner(System.in);
		CaveRoom.setUpCaves();
		
		inventory = new Inventory();
		startExploring();
	}
	
	public static void startExploring() {
		while(playing) {
			//npcActions();
 			print(inventory.getDescription());
 			print(currentRoom.getDescription());
 			print("What would you like to do?");
 			String input = in.nextLine();
 			currentRoom.interpretInput(input);
 		}
	}
	
	private static void npcActions() {
		for(NPC n: npcs) {
			n.act();
		}
		inventory.updateMap();
	}

	public static void print(String s) {
 		//NOTE: later, you can replace this line with the more sophistocated "multiLinePrint" from Chatbot
 		System.out.println(s);
  	}
}
