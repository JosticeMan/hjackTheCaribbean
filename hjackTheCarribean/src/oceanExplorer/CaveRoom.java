package oceanExplorer;

public class CaveRoom {

	private String description; //Tells the room looks like
	private String directions; //Tells what you can do 
	private String contents; //A symbol representing what's in the room
	private String defaultContents;
	//The rooms are organized by direction, 'null' signifies no room/doors in that direction
	private CaveRoom[] borderingRooms;
	private Door[] doors;
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public CaveRoom(String description) {
		this.description = description;
		setDefaultContents(" ");
		contents = defaultContents;
		//Difference between defaultContents and contents is "contents" becomes
		//an x when you are inside this room, when you leave it goes back to default
		//contents
		
		//Note: By default, arrays will populate with 'null', meaning there are no connections
		borderingRooms = new CaveRoom[4];
		doors = new Door[4];
		setDirections();
	}

	/**
	 * For every door in doors[], appends a String to "directions" describing the access
	 * For example: 
	 *  "There is a door to the north" 
	 *  "There is a door to the south" ... etc
	 *  
	 *  If there are no doors at all, directions should say: 
	 * 		"There are no doors, you are trapped in here." 
	 */
	public void setDirections() {
		directions = "";
		boolean doorFound = false;
		for(int i = NORTH; i < WEST + 1; i++) {
			if(doors[i] != null) {
				doorFound = true;
				directions += "\nShipmate: I see the ocean is " + doors[i].getDescription() + " to " + toDirection(i) + ". " + doors[i].getDetails();
			}
		}
		if(!doorFound) {
			directions = "There are no doors in your room. You're trapped";
		}
	}
	
	public void setDirection(String directions) {
		this.directions = directions;
	}
	
	/**
	 * Converts an int to a direction
	 *    toDirection(0) -> "the North"
	 * @param dir
	 * @return
	 */
	public static String toDirection(int dir) {
		String[] direction = {"the North", "the East", "the South", "the West"};
		return direction[dir];
	}
	
	public void enter() {
		contents = "x";
	}
	
	public void leave() {
		contents = defaultContents;
	}
	
	/**
	 * Gives this room access to anotherRoom (and vice-versa)
	 * and sets a door between them, updating the directions
	 * @param direction
	 * @param anotherRoom
	 * @param door
	 */
	public void setConnection(int direction, CaveRoom anotherRoom, Door door) {
		addRoom(direction, anotherRoom, door);
		anotherRoom.addRoom(oppositeDirection(direction), this, door);
	}
	
	public int oppositeDirection(int direction) {
		//int[] opposites = {2, 3, 0, 1};
		//return opposites[direction];
		return (direction + 2)%4;
	}

	public void addRoom(int direction, CaveRoom cave, Door door) {
		borderingRooms[direction] = cave;
		doors[direction] = door;
		setDirections();
	}
	
	public void interpretInput(String input) {
		while(!isValid(input)) {
			printAllowedEntry();
			input = CaveExplorer.in.nextLine();
		}
		int direction = determineDirection(input, validKeys());
		//Task: convert user input into a direction
		//don't use more than one
		respondToKey(manageCurrentRoomShift(direction));
	}
	
	/**
	 * Override to add more keys
	 * @return
	 */
	public String validKeys() {
		return "wdsa";
	}
	
	/**
	 * Override to change the key types that the user can enter
	 */
	public void printAllowedEntry() {
		System.out.println("You can only enter 'w', 'a', 's', or 'd'");
	}
	
	public void respondToKey(int direction) {
		//First, protect against null pointer exception
		//(user cannot go through a non existent door)
		if(direction < 4) {
			if(borderingRooms[direction] != null && 
					doors[direction] != null) {
				CaveExplorer.currentRoom.leave();
				CaveExplorer.currentRoom = borderingRooms[direction];
				CaveExplorer.currentRoom.enter();
				CaveExplorer.inventory.updateMap();
			}
			else {
				nothingCanBeDone();
			}
		} 
		else {
			performAction(direction);
		}
	} 

	
	public void nothingCanBeDone() {
		
	}
	
	public int manageCurrentRoomShift(int direction) {
		return direction;
	}
	

	/**
	 * Override to give response to keys other than wasd
	 * @param direction
	 */
	public void performAction(int direction) {
		System.out.println("That key does nothing.");
	}

	/*
		LEVEL 3: 
		
		 ___________________ 
		|                   |
		| K   x             |
		|            ___    |
		|   |       |   |   |
		|   |       |   |   |
		|   |___    |___|___|
		|   |   |           |
		|   |   |         B |
		|   |___|___     ___|
		|       |   |   |   |
		|       |   |   |   |
		|___ ___|___|   |___|
		|   |   |   |       |
		|   |   |   |     P |
		|___|___|___|___ ___|
	*/
	
	/*
	  	LEVEL 1:
		 _______________________ 
		|       |   |   |       |
		|     x |   |   |       |
		|    ___|___|___|       |
		|   |   |               |
		| M |   |               |
		|   |___|    ___ ___    |
		|           |   |   |   |
		| E   F   E |   |   |   |
		|    ___    |___|___|   |
		|   |   |   |           |
		| F |   |   |           |
		|   |___|   |___ ___ ___|
		|   |                   |
		|   |     E   E       B |
		|   |___     ___     ___|
		|   |   |   |   |       |
		|   |   |   |   |       |
		|___|___|___|___|___ ___|
	 */
	public static void setUpLevel1(int level) {
		CaveRoom[][] c = CaveExplorer.caves;
		
		CaveRoom jRoom = new JustinBossRoom("Captain Duran: You've entered the territory of a commander! The commander is coming soon. Prepare to play a game of battleship or run!", level);
		CaveRoom StevenRoom = new StevenRoom("There is nothing there.");
		c[1][0]= StevenRoom;
		closeAllDoorsAtCoordinate(1,0,c);
		c[1][0].setConnection(NORTH, c[0][0], new Door());
		c[1][0].setConnection(SOUTH, c[2][0], new Door());
		CaveRoom StevenDanRoom =new StevenDanRoom("You have encountered enemies. Prepare to fight!");
		
		c[4][5] = jRoom;
		c[4][5].setConnection(NORTH, c[3][5], null);
		c[4][5].setConnection(WEST, c[4][4], new Door());
		
		CaveRoom fRoom = new JustinFogRoom("This area is populated by dense fog. You can barely see.", 1);
		c[3][0] = fRoom;
		closeAllDoorsAtCoordinate(3,0,c);
		c[3][0].setConnection(NORTH, c[2][0], new Door());
		c[3][0].setConnection(SOUTH, c[4][0], new Door());
		
		CaveRoom fRoom1 = new JustinFogRoom("This area is populated by dense fog. You can barely see.", 1);
		c[2][1] = fRoom1;
		closeAllDoorsAtCoordinate(2,1,c);
		c[2][1].setConnection(WEST, c[2][0], new Door());
		c[2][1].setConnection(EAST, c[2][2], new Door());
		
		CaveExplorer.currentRoom = CaveExplorer.caves[0][1];
		
		CaveExplorer.currentRoom.enter();
		c[4][4].setConnection(WEST, c[4][3], new Door());
		c[4][4].setConnection(NORTH, c[3][4], null);
		closeAllDoorsAtCoordinate(1,1,c);
		closeAllDoorsAtCoordinate(3,1,c);
		c[4][1].setConnection(WEST, c[4][0], null);
		c[3][3].setConnection(WEST, c[3][2], null);
		c[3][3].setConnection(SOUTH, c[4][3], null);
		closeAllDoorsAtCoordinate(0,2,c);
		closeAllDoorsAtCoordinate(0,3,c);
		closeAllDoorsAtCoordinate(2,4,c);
		closeAllDoorsAtCoordinate(5,1,c);
		closeAllDoorsAtCoordinate(2,3,c);
		closeAllDoorsAtCoordinate(5,3,c);
	}
	
	/*
	LEVEL 2:
		
		 _______________________ 
		|   |   |               |
		|   |   |               |
		|   |___|    ___     ___|
		|           |   |   |   |
		|           |   |   |   |
		|    ___    |___|   |___|
		|   |   |               |
		|   |   |             B |
		|   |___|___     ___ ___|
		|               |   |   |
		| x       F     |   |   |
		|___     ___    |___|   |
		|   |   |   |           |
		|   |   |   |           |
		|___|   |___|    ___    |
		|               |   |   |
		|               |   |   |
		|___ ___ ___ ___|___|___|
	*/
	public static void setUpLevel2(int level) {
		CaveRoom[][] c = CaveExplorer.caves;
		
		CaveRoom jRoom = new JustinBossRoom("Captain Duran: You've entered the territory of a commander! The commander is coming soon. Prepare to play a game of battleship or run!", level);
		c[2][5] = jRoom;
		c[2][5].setConnection(WEST, c[2][4], new Door());
		
		CaveRoom fRoom = new JustinFogRoom("This area is populated by dense fog. You can barely see.", 1);
		c[3][2] = fRoom;
		closeAllDoorsAtCoordinate(3,2,c);
		c[3][2].setConnection(WEST, c[3][1], new Door());
		c[3][2].setConnection(EAST, c[3][3], new Door());
		//4. Set your starting room:
		CaveExplorer.currentRoom = CaveExplorer.caves[3][0];
		CaveExplorer.currentRoom.enter();
		//5. Set up doors
		closeAllDoorsAtCoordinate(0,1,c);
		closeAllDoorsAtCoordinate(2,1,c);
		closeAllDoorsAtCoordinate(1,3,c);
		c[2][2].setConnection(SOUTH, c[3][2], null);
		closeAllDoorsAtCoordinate(3,4,c);
		closeAllDoorsAtCoordinate(4,0,c);
		closeAllDoorsAtCoordinate(4,2,c);
		closeAllDoorsAtCoordinate(1,5,c);
		closeAllDoorsAtCoordinate(5,4,c);
	}

	/*
  	LEVEL 3:
	 _______________________ 
	|       |   |           |
	|       |   |           |
	|___    |___|    ___ ___|
	|   |           |   |   |
	|   |     F     |   |   |
	|___|    ___    |___|___|
	|       |   |           |
	| x     |   |           |
	|       |___|    ___ ___|
	|   |   |   |   |   |   |
	| F |   |   |   |   |   |
	|___|   |___|   |___|___|
	|       |   |           |
	|       |   |           |
	|___    |___|    ___    |
	|   |           |   |   |
	|   |     F     |   | B |
	|___|___ ___ ___|___|___|
	 */
	public static void setUpLevel3(int level) {
		CaveRoom[][] c = CaveExplorer.caves;

		CaveRoom jRoom = new JustinBossRoom("Captain Duran: You've entered the territory of a commander! The commander is coming soon. Prepare to play a game of battleship or run!", level);
		c[5][5] = jRoom;
		closeAllDoorsAtCoordinate(5,5,c);
		c[5][5].setConnection(NORTH, c[4][5], new Door());
		
		CaveRoom fRoom = new JustinFogRoom("This area is populated by dense fog. You can barely see.", 1);
		c[3][0] = fRoom;
		closeAllDoorsAtCoordinate(3,0,c);
		c[3][0].setConnection(NORTH, c[2][0], new Door());
		
		CaveRoom fRoom1 = new JustinFogRoom("This area is populated by dense fog. You can barely see.", 1);
		c[1][2] = fRoom1;
		closeAllDoorsAtCoordinate(1,2,c);
		c[1][2].setConnection(WEST, c[1][1], new Door());
		c[1][2].setConnection(EAST, c[1][3], new Door());
		
		CaveRoom fRoom2 = new JustinFogRoom("This area is populated by dense fog. You can barely see.", 1);
		c[5][2] = fRoom2;
		closeAllDoorsAtCoordinate(5,2,c);
		c[5][2].setConnection(WEST, c[5][1], new Door());
		c[5][2].setConnection(EAST, c[5][3], new Door());
		
		//4. Set your starting room:
		CaveExplorer.currentRoom = CaveExplorer.caves[2][0];
		CaveExplorer.currentRoom.enter();
		//5. Set up doors
		closeAllDoorsAtCoordinate(5,4,c);
		closeAllDoorsAtCoordinate(5,0,c);
		closeAllDoorsAtCoordinate(4,2,c);
		closeAllDoorsAtCoordinate(3,2,c);
		closeAllDoorsAtCoordinate(3,4,c);
		closeAllDoorsAtCoordinate(3,5,c);
		closeAllDoorsAtCoordinate(2,2,c);
		closeAllDoorsAtCoordinate(1,0,c);
		closeAllDoorsAtCoordinate(0,2,c);
		closeAllDoorsAtCoordinate(1,4,c);
		closeAllDoorsAtCoordinate(1,5,c);
	}
	
	public static void openAllDoors() {
		CaveRoom[][] c = CaveExplorer.caves;
		for(int i = 0; i < c.length; i++) {
			for(int j = 0; j < c[i].length - 1; j++) {
				c[i][j].setConnection(EAST, c[i][j + 1], new Door());
			}
		}
		for(int i = 0; i < c.length - 1; i++) {
			for(int j = 0; j < c[i].length; j++) {
				c[i][j].setConnection(SOUTH, c[i + 1][j], new Door());
			}
		}
	}
	
	public static void closeAllDoorsAtCoordinate(int row, int col, CaveRoom[][] c) {
		int[][] nearby = {{row - 1, col}, {row, col +1}, {row + 1, col}, {row, col - 1}};
		for(int direction = NORTH; direction <= WEST; direction++) {
			if(nearby[direction][0] >= 0 && nearby[direction][0] < c.length && nearby[direction][1] >= 0 && nearby[direction][1] < c.length) {
				c[row][col].setConnection(direction, c[nearby[direction][0]][nearby[direction][1]], null);
			}
		}
	}
	
	/**
	 * This will be where your group sets up all the caves
	 * and all the connections
	 */
	public static void setUpCaves(int level) {
		//ALL OF THIS CODE CAN BE CHANGED
		//1. Decide how big your caves should be
		CaveExplorer.caves = new CaveRoom[6][6];
		//2. Populate with caves and a default description: hint: when starting, use coordinates (helps debugging)
		for(int row = 0; row < CaveExplorer.caves.length; row++) {
			//PLEASE PAY ATTENTION TO THE DIFFERENCE:
			for(int col = 0; col < CaveExplorer.caves[row].length; col++) {
				//create a "default" cave
				CaveExplorer.caves[row][col] = 
						new CaveRoom("Your compass tells you that you are located in coords ("+row+","+col+")");
			}
		}
		/*
		//Steven room goes here
		//CaveExplorer.caves[1][2]=new StevenRoom("There is nothing here.");
		CaveExplorer.caves[0][1].setConnection(EAST,CaveExplorer.caves[0][2],new Door());
		CaveExplorer.caves[0][2].setConnection(SOUTH,CaveExplorer.caves[1][2],new Door());
		//end
		//Dan room
		
		//Fountain
		CaveExplorer.caves[0][3]=new DanFountainRoom("This is fountain. Interact with 'e' so you can restore your health.");
		CaveExplorer.caves[0][2].setConnection(EAST,CaveExplorer.caves[0][3],new Door());
		
		//Trap
		CaveExplorer.caves[1][1]=new DanTrapRoom("You've been bitten by a sea rat! -5 HP.");
//		CaveExplorer.caves[1][2].setConnection(EAST,CaveExplorer.caves[1][3],new Door());
		
		
		//end
		//Andrew room
		CaveExplorer.caves[0][4]=new AndrewRoom("You feel dizzy.");
		CaveExplorer.caves[0][4].setConnection(SOUTH,CaveExplorer.caves[1][4],new Door());
		CaveExplorer.caves[0][4].setConnection(WEST,CaveExplorer.caves[0][3],new Door());
		CaveExplorer.caves[0][3].setConnection(WEST,CaveExplorer.caves[0][2],new Door());
		//end
		//Kevin room
		CaveExplorer.caves[1][1] = new KevinRoom("-This is Kevin Room- OH NO! A tsunami wave suddenly appeared! It is gonna blow you away!");
		CaveExplorer.caves[1][1].setConnection(NORTH, CaveExplorer.caves[0][1], new Door());
		CaveExplorer.caves[1][1].setConnection(SOUTH, CaveExplorer.caves[2][1], new Door());
		CaveExplorer.caves[1][1].setConnection(EAST, CaveExplorer.caves[1][0], new Door());
		CaveExplorer.caves[1][1].setConnection(WEST, CaveExplorer.caves[1][2], new Door());
			
		//end
		//Sunny's Room
		CaveRoom sRoom = new SunnyRoom("You have reached the corner edge of the ocean.");
		CaveExplorer.caves[4][4] = sRoom;
		CaveExplorer.caves[4][4].setConnection(WEST, CaveExplorer.caves[4][3], new Door());
		CaveExplorer.caves[4][3].setConnection(NORTH, CaveExplorer.caves[3][3], new Door());
		CaveExplorer.caves[3][3].setConnection(NORTH, CaveExplorer.caves[2][3], new Door());
		//end
		//Justin's Room (This will be the room for boss fights)
		CaveRoom jRoom = new JustinBossRoom("Captain Duran: You've entered the territory of a commander! The commander is coming soon. Prepare to play a game of battleship or run!", level);
		CaveExplorer.caves[2][4] = jRoom;



		CaveExplorer.caves[1][1].setConnection(EAST,CaveExplorer.caves[1][2],new Door());
		CaveExplorer.caves[1][2].setConnection(SOUTH,CaveExplorer.caves[2][2],new Door());
		CaveExplorer.caves[2][2].setConnection(EAST,CaveExplorer.caves[2][3],new Door());
		CaveExplorer.caves[2][3].setConnection(EAST,CaveExplorer.caves[2][4],new Door());

		//end
		//Justin's 2nd Room 
		CaveRoom j2Room = new JustinFogRoom("This area is populated by dense fog. You can barely see.", 1);
		CaveExplorer.caves[2][0] = j2Room;
		
		CaveExplorer.caves[1][0].setConnection(SOUTH, CaveExplorer.caves[2][0], new Door());
		
		//4. Set your starting room:
		CaveExplorer.currentRoom = CaveExplorer.caves[0][1];
		CaveExplorer.currentRoom.enter();
		//5. Set up doors
		CaveRoom[][] c = CaveExplorer.caves;
		c[0][1].setConnection(SOUTH, c[1][1], new Door());
		*/
		openAllDoors();
		if(level == 1) {
			setUpLevel1(level);
		}
		if(level == 2) {
			setUpLevel2(level);
		}
		if(level == 3) {
			setUpLevel3(level);
		}
		/**
		 * Special requests:
		 * moving objects in caves
		 * what happens when you lose?
		 * can another object move toward you?
		 */
	}
	
	public int determineDirection(String input, String key) {
		return key.indexOf(input);
	}
	
	public boolean isValid(String input) {
		String validEntries = validKeys();
		return validEntries.indexOf(input) > - 1 && input.length() == 1;
	}

	public String getDescription() {
		return description + "\n" + directions;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setDefaultContents(String defaultContents) {
		this.defaultContents = defaultContents;
	}

	public Door getDoor(int direction) {
		if(direction >= 0 && direction < doors.length) {
			return doors[direction];
		}
		else {
			return null;
		}
	}

}
//