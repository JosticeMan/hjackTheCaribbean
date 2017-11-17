package rpgDDSL;

import oceanExplorer.CaveExplorer;

public class StevenBackend implements DanSupport {
	
	private StevenSupport frontend;
	private RPGRoom[][] map;
	private int[] human;
	private DanielFrontend front;
	private int[][] enemyPosition;
	private int num;
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public StevenBackend(DanielFrontend frontend,int num)
	{
		map = new RPGRoom[20][20];
		human=new int[2];
		enemyPosition = new int[num][2];
		this.num=num;
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
		map[0][0].setType(1);
		human[0]=0;
		human[1]=0;
		
//		map[3][3].setType(2);
		
		spawnEnemy();
		
		
	}
	
	public void spawnEnemy()
	{
		int enemyPosX = 0;
		int enemyPosY = 0;
		
		for (int i = 0; i < num; i++)
		{
			enemyPosX = (int)(Math.random()*map.length);
			enemyPosY = (int)(Math.random()*map[0].length);
			
			while ((enemyPosX == human[0] && enemyPosY == human[1])||checkEnemy(enemyPosX,enemyPosY))
			{
				System.out.println(enemyPosX);
				System.out.println(enemyPosY);
				enemyPosX = (int)(Math.random()*map.length);
				enemyPosY = (int)(Math.random()*map[0].length);
			}
			enemyPosition[i][0]=enemyPosX;
			enemyPosition[i][1]=enemyPosY;
		}
		
	}
	
	public boolean checkEnemy(int row,int col) {
		for(int[] a:enemyPosition) {
			if(a[0]==row&&a[1]==col)return true;
		}
		return false;
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

	public int[][] getEnemyPosition() {
		return enemyPosition;
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
	
	public boolean checkWalls(String input,int[] type)
	{
		
		
		
		if ((input.equalsIgnoreCase("w")&&map[type[0]][type[1]].isNorth()))
		{
			return false;
		}
		else
		{
			if ((input.equalsIgnoreCase("d")&&map[type[0]][type[1]].isEast()))
			{
				return false;
			}
			else
			{
				if ((input.equalsIgnoreCase("s")&&map[type[0]][type[1]].isSouth()))
				{
					return false;
				}
				else
				{
					if ((input.equalsIgnoreCase("a")&&map[type[0]][type[1]].isWest())) 
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
			map[human[0]][human[1]].setType(3);
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
			enemyAction(false);
		} 
		else {
			performAction(direction);
		}
	}
	
	public void enemyAction(boolean type) {
		if(type) {
			
		}else {
			int direction=(int)(Math.random()*4);
			String input="wdsa".substring(direction,direction+1);
			for(int[] a:enemyPosition) {
				while(!checkWalls(input,a)||checkHuman(direction,a)) {
					direction=(int)(Math.random()*4);
					input="wdsa".substring(direction,direction+1);
					System.out.println(input);
				}
				if(direction==0) {
					a[0]-=1;
				}
				if(direction==1) {
					a[1]+=1;
				}
				if(direction==2) {
					a[0]+=1;
				}
				if(direction==3) {
					a[1]-=1;
				}
			}
		}
		
	}

	public boolean checkEnemyPos(int direction) {
		int x;
		int y;
		for(int[] a:enemyPosition) {
			x=a[0];
			y=a[1];
			for(int[] b:enemyPosition) {
				if(direction==0&&x--==b[0]) {
					return true;
				}
				if(direction==2&&x++==b[0]) {
					return true;
				}
				if(direction==3&&y++==b[1]) {
					return true;
				}
				if(direction==1&&y--==b[1]) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkHuman(int direction, int[] a) {
		int x=a[0];
		int y=a[1];
		if(direction==0&&x--==human[0]) {
			return true;
		}
		if(direction==2&&x++==human[0]) {
			return true;
		}
		if(direction==3&&y++==human[1]) {
			return true;
		}
		if(direction==1&&y--==human[1]) {
			return true;
		}
		return false;
	}

	public void setFrontend(DanielFrontend x)
	{
		front = x;
	}
	
}
