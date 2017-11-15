package rpgDDSL;

import oceanExplorer.CaveExplorer;

public class StevenBackend implements DanSupport {
	
	private StevenSupport frontend;
	private RPGRoom[][] map;
	private int[] human;
	private DanielFrontend front;
	private int[][] enemyPosition;
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public StevenBackend(DanielFrontend frontend)
	{
		map = new RPGRoom[5][5];
		human=new int[2];
		makeMap();
	}
	
	public void makeMap()
	{
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map[0].length; j++)
			{
				map[i][j] = new RPGRoom(0);
					
			}
		}
		makeWalls();
		map[3][3] = new RPGRoom(2);
		map[1][1] = new RPGRoom(1);
		human[0]=1;
		human[1]=1;
		
		
	}
	
	public void spawnEnemy(int num)
	{
		enemyPosition = new int[num][2];
		int enemyPosX = 0;
		int enemyPosY = 0;
		
		for (int i = 0; i < num; i++)
		{
			enemyPosX = (int)Math.random()*map.length;
			enemyPosY = (int)Math.random()*map[0].length;
			
			while (enemyPosX != human[0] && enemyPosY != human[1])
			{
				
			}
		}
		
	}
	public void makeWalls() {
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map[0].length; j++) {
				if(i==0) {
					map[i][j].setNorth(true);
				}
				if(i==map.length-1) {
					map[i][j].setSouth(true);
				}
				if(j==0) {
					map[i][j].setWest(true);
				}
				if(j==map[0].length-1) {
					map[i][j].setEast(true);
				}
			}
		}
	}

	public int[] getHuman() {
		return human;
	}

	public void setHuman(int[] human) {
		this.human = human;
	}

	public RPGRoom[][] getMap() {
		return map;
	}
	
	public void interpretInput(String input) {

		int direction = determineDirection(input, validKeys());
		respondToKey(direction);
	}
	
	public String validKeys() {
		return "wdsa";
	}
	
	public void printAllowedEntry() {
		System.out.println("You can only enter 'w', 'a', 's', or 'd'");
	}
	
	public void performAction(int direction) {
		System.out.println("That key does nothing.");
	}
	
	public int determineDirection(String input, String key) {
		return key.indexOf(input);
	}
	
	public boolean isValid(String input) {
		String validEntries = validKeys();
		return validEntries.indexOf(input) > - 1 && input.length() == 1;
	}
	
	public boolean checkWalls(String input)
	{
	/*	return (((input.equalsIgnoreCase("w")&&!map[human[0]][human[1]].isNorth())&&
				(input.equalsIgnoreCase("d")&&!map[human[0]][human[1]].isEast())
				||(input.equalsIgnoreCase("s")&&!map[human[0]][human[1]].isSouth())
				||(input.equalsIgnoreCase("a")&&!map[human[0]][human[1]].isWest())));
	*/	
		
		
		if ((input.equalsIgnoreCase("w")&&map[human[0]][human[1]].isNorth()))
		{
			return false;
		}
		else
		{
			if ((input.equalsIgnoreCase("d")&&map[human[0]][human[1]].isEast()))
			{
				return false;
			}
			else
			{
				if ((input.equalsIgnoreCase("s")&&map[human[0]][human[1]].isSouth()))
				{
					return false;
				}
				else
				{
					if ((input.equalsIgnoreCase("a")&&map[human[0]][human[1]].isWest())) 
					{
						return false;
					}
				}
			}
		}

		return true;
	}
	public void respondToKey(int direction) {
		//First, protect against null pointer exception
		//(user cannot go through a non existent door)
		if(direction < 4) {
			map[human[0]][human[1]].setType(0);
			if(direction==0) {
				map[human[0]-1][human[1]].setType(1);
				human[0]-=1;
			}
			if(direction==1) {
				map[human[0]][human[1]+1].setType(1);
				human[1]+=1;
			}
			if(direction==2) {
				map[human[0]+1][human[1]].setType(1);
				human[0]+=1;
			}
			if(direction==3) {
				map[human[0]][human[1]-1].setType(1);
				human[1]-=1;
			}
		} 
		else {
			performAction(direction);
		}
	}
	
	public void setFrontend(DanielFrontend x)
	{
		front = x;
	}
	
}
