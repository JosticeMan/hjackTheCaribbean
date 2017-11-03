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
			npcActions();
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
	
	/**
	 * Chooses & prints a random phrase from a String Array
	 * @param arr - String Array that contains random phrases
	 */
	public static void randomText(String[] arr) {
		
		CaveExplorer.print(arr[((int) (Math.random() * arr.length))]);
		
	}
	
	public static void print(String s){
		  multiLinePrint(s);
		  System.out.println("");
	  }
	  
	public static void multiLinePrint(String s){
		  String printString = "";
		  int cutoff = 55;
		  //this while loop last as long as there are words left in the original String
		  while(s.length() > 0){

			  String currentCut = "";
			  String nextWord = "";

			  //while the current cut is still less than the line length 
			  //AND there are still words left to add
			  while(currentCut.length()+nextWord.length() < cutoff && s.length() > 0){
		
				//add the next word
			    currentCut += nextWord;
				 
				//remove the word that was added from the original String
			    s = s.substring(nextWord.length());
				 
				//identify the following word, exclude the space
				int endOfWord = s.indexOf(" ");
			
				 //if there are no more spaces, this is the last word, so add the whole thing
				 if(endOfWord == -1) {
					 endOfWord = s.length()-1;//subtract 1 because index of last letter is one les than length
				 }
			
				 //the next word should include the space
				 nextWord = s.substring(0,endOfWord+1);
			}

		  	  printString +=currentCut+"\n";

		  }
	
		  System.out.print(printString);
	  }
}
