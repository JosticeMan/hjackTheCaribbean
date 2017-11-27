package oceanExplorer;

import java.util.Scanner;

public class CaveExplorer {

	public static CaveRoom[][] caves; //Every one in the cave
	public static Scanner in; //For user input
	public static CaveRoom currentRoom; //Changes based on how the user navigated
	public static Inventory inventory; //Where are all objects found are kept
	public static boolean playing = true;
	public static NPC[] npcs;
	public static int level;
	public static final String[][] intro ={{
											 "**You are a newly appointed captain following the completion of apprenticeship under Captain Duran, the legendary pirate of the era.**",
											 "Captain Duran: Arghh! You've trained up to your life for this moment. A chance to conquer the seas!", 
											 "Parrot: Ka-awh!", 
											 "Shipmate: Duran! We're reaching the perimeter of the Carribean soon! A dangerous area indeed!",
											 "Captain Duran: I've heard of the 3 nations that control this territory! The fearsome British, Spanish, and the nice French!",
											 "Captain Duran: Ol buddy! It's time to engrave our names in history!", 
											 "Captain Duran: Let's plunder and conquer! I've heard of great treasures in this area that will surely aid us in our ventures!", 
											 "Shipmates: Ahoy!"
											},
											{
										     "Captain Duran: Good job beating the french!", 
										     "Captain Duran: The next commander, the Spanish, won't be as easy!",
										     "Captain Duran: I heard they have significantly better strategies at tackling your ships. Beware!", 
										     "Captain Duran: You are now entering the territory of the Spanish!"
											}, 
											{
											 "Captain Duran: Ahoy! You've passed the intelligent beings!", 
											 "Captain Duran: Your next challenger, the British, has never lost an naval battle! But I believe in you!", 
											 "Captain Duran: You are now entering the territory of the British!"
											}
										  };
	
	public static final String[] logo = {  
			" __ __  ____  ____   ____    __  __  _      ______  __ __    ___         __   ____  _____  ____  ____   ____     ___   ____  ____   ",
			"|  |  ||    ||    | /    |  /  ]|  |/ ]    |      ||  |  |  /  _]       /  ] /    ||     ||    ||    ) |    )   /  _] /    ||    ) ",
			"|  |  | |  | |__  ||  o  | /  / |  ' /     |      ||  |  | /  [_       /  / |  o  ||  D  | |  | |  o  )|  o  ) /  [_ |  o  ||  _  |",
			"|  _  | |  | __|  ||     |/  /  |   /      |_|  |_||  _  ||    _]     /  /  |     ||    /  |  | |     ||     ||    _]|     ||  |  |",
			"|  |  | |  | | |  ||  _  /   |_ |   |_       |  |  |  |  ||   [_     /   |_ |  _  ||    )  |  | |  O  ||  O  ||   [_ |  _  ||  |  |",
			"|  |  | |  | |  ` ||  |  [     ||  .  |      |  |  |  |  ||     |    [     ||  |  ||  .  | |  | |     ||     ||     ||  |  ||  |  |",
			"|__|__||____|[____||__|__|[____||__|[_|      |__|  |__|__||_____|     [____||__|__||__|[_||____||_____||_____||_____||__|__||__|__|"
			};
	
	
	public static final String[] victory = {

		   " _ _  _        _                  ",
		   "| | |<_> ___ _| |_ ___  _ _  _ _ ",
		   "| ' || |[ | ' | | [ . ]| '_>| | |",
		   "|__/ |_|[_|_. |_| [___]|_|  `_. |",
		   "                            <___'",
	
	};
	
	public static final String[] gameOver1 = {"Shipmate: Capt! Bad news! Something bad is happen--",
											  "**Your vision suddenly blacks out**", 
											  "**You are awaken by someone. You make out its Captain Duran**",
											  "Captain Duran: Pal... I'm sorry but your adventure has come to an end.", 
											  "Captain Duran: Your ship was sunk being drifted by a riptide and hitting a large mass. I managed to come in time to save you.", 
											  "Captain Duran: As for your shipmates, they've lost their lives. We're heading back to land."};
	public static final String[] gameO = {
			"  ____                         ___                   _ ",
			" [ ___| __ _ _ __ ___   ___   [ _ ]__   _____ _ __  | |",
			"| |  _ [ _` | '_ ` _ ] [ _ ] | | | [ ] [ ] _ ) '__| | |",
			"| |_| | (_| | | | | | |  __/ | |_| |[ V ]  __/ |    |_|",
			" [____|[__,_|_| |_| |_|[___|  [___]  [_] [___|_|    (_)"
			};
	
	public static final String[] endOfGame = {
												"Captain Duran: Congratulations on creating history! You defeated the undefeated!",
												"Captain Duran: We must head back to shore quickly! We have a new Pirate King!",
												"Shipmates: Arrr! Hail the new Pirate King! We will follow you to the end!"
	};
				
	
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

	public static int getLevel() {
		return CaveExplorer.level;
	}
	
	public static void setLevel(int level) {
		CaveExplorer.level = level;
	}

	public static void incrementLevel() {
		CaveExplorer.level++;
	}
	
	public static void startExploring() {
		//printIntroduction(level); //This will introduce the player to the game world of the Caribbean
		while(playing) {
			//npcActions();
 			print(inventory.getDescription());
 			print(currentRoom.getDescription());
 			print("Shipmate: What are your orders captain? To thou direction shall we sail?");
 			String input = in.nextLine();
 			currentRoom.interpretInput(input);
 		}
	}
	
	public static void printEnd() {
		for(String line: endOfGame) {
			sprint(line);
			pause(1000);
		}
	}
	
	public static void printVictory() {
		pause(250);
		for(String line: victory) {
			sprint(line);
		}
		pause(500);
	}
	
	public static void printGameOver() {
		pause(250);
		for(String line: gameO) {
			sprint(line);
		}
		pause(1000);
		for(String entry: gameOver1) {
			sprint(entry);
			pause(1000);
		}
	}
	
	public static void printIntroduction(int level) {
		if(level == 1)
		{
			for(String line: logo) {
				sprint(line);
			}
		}
		pause(1000);
		for(String entry: intro[level - 1]) {
			sprint(entry);
			pause(1000);
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
	 * 2nd version of randomInt for 2D arrays
	 * @param arr
	 * @return
	 */
	public static int[] randomInt(int[][] arr) {
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
	 * Variation of the above method but for 2D arrays
	 * @param arr
	 * @return
	 */
	public static String[] randomString(String[][] arr) {
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
	 * This method causes the program to pause 
	 * CREDITS TO: NOCKLES for providing the method through his example
	 * @param i - Length of the pause
	 */
	public static void pause(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
