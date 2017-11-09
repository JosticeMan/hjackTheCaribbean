package oceanExplorer;

import java.util.Scanner;

public class CaveExplorer {

	public static CaveRoom[][] caves; //Every one in the cave
	public static Scanner in; //For user input
	public static CaveRoom currentRoom; //Changes based on how the user navigated
	public static Inventory inventory; //Where are all objects found are kept
	public static boolean playing = true;
	public static NPC[] npcs;
	public static final String[] intro = {  "**You are a newly appointed captain following the completion of apprenticeship under Captain Duran, the legendary pirate of the era.**",
											"Captain Duran: Arghh! You've trained up to your life for this moment. A chance to conquer the seas!", 
											"Parrot: Ka-awh!", 
											"Shipmate: Duran! We're reaching the perimeter of the Carribean soon! A dangerous area indeed!",
											"Captain Duran: I've heard of the 3 nations that control this territory! The fearsome British, Spanish, and the nice French!",
											"Captain Duran: Ol buddy! It's time to engrave our names in history!", 
											"Captain Duran: Let's plunder and conquer! I've heard of great treasures in this area that will surely aid us in our ventures!", 
											"Shipmates: Ahoy!"};
	
	public static final String[] logo = {  
			" __ __  ____  ____   ____    __  __  _      ______  __ __    ___         __   ____  ____   ____  ____   ____     ___   ____  ____   ",
			"|  |  ||    ||    | /    |  /  ]|  |/ ]    |      ||  |  |  /  _]       /  ] /    ||    ) |    ||    ) |    )   /  _] /    ||    ) ",
			"|  |  | |  | |__  ||  o  | /  / |  ' /     |      ||  |  | /  [_       /  / |  o  ||  D  ) |  | |  o  )|  o  ) /  [_ |  o  ||  _  |",
			"|  _  | |  | __|  ||     |/  /  |    |     |_|  |_||  _  ||    _]     /  /  |     ||    /  |  | |     ||     ||    _]|     ||  |  |",
			"|  |  | |  |/  |  ||  _  /   |_ |     ]      |  |  |  |  ||   [_     /   ]  |  _  ||    )  |  | |  O  ||  O  ||   [_ |  _  ||  |  |",
			"|  |  | |  |(  `  ||  |  [     ||  .  |      |  |  |  |  ||     |    [     ||  |  ||  .  ) |  | |     ||     ||     ||  |  ||  |  |",
			"|__|__||____|[____||__|__|[____||__|[_|      |__|  |__|__||_____|     [____||__|__||__|[_||____||_____||_____||_____||__|__||__|__|"
			};
	public static int level;
	
	public static void main(String[] args) {
		level = 1;
		in = new Scanner(System.in);
		CaveRoom.setUpCaves(level);
		
		inventory = new Inventory();
		startExploring();
	}
	
	public static void setPlaying(boolean playing) {
		CaveExplorer.playing = playing;
	}

	public static int getLevel(int level) {
		return CaveExplorer.level;
	}
	
	public static void setLevel(int level) {
		CaveExplorer.level = level;
	}

	public static void startExploring() {
		printIntroduction(); //This will introduce the player to the game world of the Caribbean
		while(playing) {
			//npcActions();
 			print(inventory.getDescription());
 			print(currentRoom.getDescription());
 			print("Shipmate: What are your orders captain? To thou direction shall we sail?");
 			String input = in.nextLine();
 			currentRoom.interpretInput(input);
 		}
	}
	
	public static void printIntroduction() {
		for(String line: logo) {
			sprint(line);
		}
		for(String entry: intro) {
			sprint(entry);
		}
	}
	
	private static void npcActions() {
		for(NPC n: npcs) {
			n.act();
		}
		inventory.updateMap();
	}
	
	/**
	 * This method returns a random integer/set of integers from the given array
	 * @param arr - Integer Array that contains random numbers
	 * @return - A random integer/set of integers from the given array
	 */
	public static int randomInt(int[] arr) {
		return arr[(int) (Math.random() * arr.length)];
	}
	
	/**
	 * This method returns a random string/set of strings from the given array
	 * @param arr - String Array that contains random string(s)
	 * @return - A random string/set of strings from the given array
	 */
	public static String randomString(String[] arr) {
		return arr[(int) (Math.random() * arr.length)];
	}
	
	/**
	 * Chooses & prints a random phrase from a String Array
	 * @param arr - String Array that contains random phrases
	 */
	public static void randomText(String[] arr) {
		
		CaveExplorer.print(arr[((int) (Math.random() * arr.length))]);
		
	}
	
	/**
	 * This method prints whatever string is inputed using multiLinePrint() to wrap the sentences around at the given character limit per line
	 * @param s - The message that you want to print out
	 */
	public static void print(String s){
		  multiLinePrint(s);
		  System.out.println("");
	  }
	
	public static void sprint(String s){
		  multiLinePrint(s);
	  }
	
	/**
	 * This method prints whatever string is inputed to wrap the sentences around at the given character limit per line
	 * @param s - The message that you want to print out
	 */
	public static void multiLinePrint(String s){
		  String printString = "";
		  int cutoff = 500;
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
