package rpgDDSL;

import oceanExplorer.CaveExplorer;
import oceanExplorer.Ship;

public class StevenBackend implements DanSupport {
	
	private StevenSupport frontend;
	private RPGRoom[][] map;
	private int[] human;
	private DanielFrontend front;
	private int[][] enemyPosition;
	private int num;
	private Ship ship;
	private int[][] enemyValue;
	private int[][] vision;
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public StevenBackend(DanielFrontend frontend,int num,int level)
	{
		map = new RPGRoom[5*level][5*level];
		human=new int[2];
		enemyPosition = new int[num][2];
		enemyValue = new int[num][2];
		vision = new int[400][2];
		ship=frontend.getShip();
		this.num=num;
		setValues();
		makeMap();
	}
	
	public void setValues() {
		for(int i=0;i<enemyValue.length;i++) {
			enemyValue[i][0]=(int)(ship.getHp()/2);
			enemyValue[i][1]=(int)(ship.getAttack()/2);
		}
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
		human[0]=(int)(Math.random()*map.length);
		human[1]=(int)(Math.random()*map[0].length);
		spawnEnemy();
		makeWalls();
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
		for(RPGRoom[] a:map) {
			for(RPGRoom b:a) {
				b.setEast(false);
				b.setNorth(false);
				b.setSouth(false);
				b.setWest(false);
			}
		}
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
		for(int i=0;i<enemyPosition.length;i++) {
			if(enemyValue[i][0]>0) {
				map[enemyPosition[i][0]][enemyPosition[i][1]].setEast(true);
				map[enemyPosition[i][0]][enemyPosition[i][1]].setWest(true);
				map[enemyPosition[i][0]][enemyPosition[i][1]].setNorth(true);
				map[enemyPosition[i][0]][enemyPosition[i][1]].setSouth(true);
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
		int x=type[0];
		int y=type[1];
		x--;
		if (input.equalsIgnoreCase("w")&&(map[type[0]][type[1]].isNorth()||(x>-1&&map[x][type[1]].isSouth())))
		{
			return false;
		}
		else
		{
			y++;
			if (input.equalsIgnoreCase("d")&&(map[type[0]][type[1]].isEast()||(y<map[0].length&&map[type[0]][y].isWest())))
			{
				return false;
			}
			else
			{
				x=type[0];
				x++;
				if (input.equalsIgnoreCase("s")&&(map[type[0]][type[1]].isSouth()||(x<map.length&&map[x][type[1]].isNorth())))
				{
					return false;
				}
				else
				{
					y=type[1];
					y--;
					if (input.equalsIgnoreCase("a")&&(map[type[0]][type[1]].isWest()||(y>-1&&map[type[0]][y].isEast()))) 
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
			if(direction==0) {
				human[0]-=1;
			}
			if(direction==1) {
				human[1]+=1;
			}
			if(direction==2) {
				human[0]+=1;
			}
			if(direction==3) {
				human[1]-=1;
			}
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
			for(int i=0;i<enemyPosition.length;i++) {
				if(enemyValue[i][0]>0) {
					direction=(int)(Math.random()*4);
					input="wdsa".substring(direction,direction+1);
					map[enemyPosition[i][0]][enemyPosition[i][1]].setEast(false);
					map[enemyPosition[i][0]][enemyPosition[i][1]].setNorth(false);
					map[enemyPosition[i][0]][enemyPosition[i][1]].setSouth(false);
					map[enemyPosition[i][0]][enemyPosition[i][1]].setWest(false);
					for(int j=0;j<enemyPosition.length;j++) {
						if(j!=i) {
							map[enemyPosition[j][0]][enemyPosition[j][1]].setEast(true);
							map[enemyPosition[j][0]][enemyPosition[j][1]].setNorth(true);
							map[enemyPosition[j][0]][enemyPosition[j][1]].setSouth(true);
							map[enemyPosition[j][0]][enemyPosition[j][1]].setWest(true);
						}
					}
					for (int k = 0; k < map.length; k++)
					{
						for (int j = 0; j < map[0].length; j++) {
							if(k==0) {
								map[k][j].setNorth(true);
							}
							if(k==map.length-1) {
								map[k][j].setSouth(true);
							}
							if(j==0) {
								map[k][j].setWest(true);
							}
							if(j==map[0].length-1) {
								map[k][j].setEast(true);
							}
						}
					}
					map[human[0]][human[1]].setEast(true);
					map[human[0]][human[1]].setNorth(true);
					map[human[0]][human[1]].setSouth(true);
					map[human[0]][human[1]].setWest(true);
					if(checkWalls(input,enemyPosition[i])){
						if(direction==0) {
							enemyPosition[i][0]-=1;
						}
						if(direction==1) {
							enemyPosition[i][1]+=1;
						}
						if(direction==2) {
							enemyPosition[i][0]+=1;
						}
						if(direction==3) {
							enemyPosition[i][1]-=1;
						}
					}
				}
				makeWalls();
				enemyAttack(i);
			}
		}
	}
	public void enemyAttack(int i) {
		int x;
		int y;
			x=enemyPosition[i][0];
			y=enemyPosition[i][1];
			if(enemyValue[i][0]>0) {
				x--;
				if(x>0) {
					if(human[0]==x&&human[1]==y) {
						ship.setHp(ship.getHp()-enemyValue[i][1]);
						System.out.println("You have taken "+enemyValue[i][1]+" damage from enemy "+(i+1)+" who is located at ("+enemyPosition[i][0]+","+enemyPosition[i][1]+").");
					}
				}
				y--;
				if(y>0&&x>0) {
					if(human[0]==x&&human[1]==y) {
						ship.setHp(ship.getHp()-enemyValue[i][1]);
						System.out.println("You have taken "+enemyValue[i][1]+" damage from enemy "+(i+1)+" who is located at ("+enemyPosition[i][0]+","+enemyPosition[i][1]+").");
					}
				}
				y+=2;
				if(y<map[0].length&&x>0) {
					if(human[0]==x&&human[1]==y) {
						ship.setHp(ship.getHp()-enemyValue[i][1]);
						System.out.println("You have taken "+enemyValue[i][1]+" damage from enemy "+(i+1)+" who is located at ("+enemyPosition[i][0]+","+enemyPosition[i][1]+").");
					}
				}
				x++;
				if(y<map[0].length) {
					if(human[0]==x&&human[1]==y) {
						ship.setHp(ship.getHp()-enemyValue[i][1]);
						System.out.println("You have taken "+enemyValue[i][1]+" damage from enemy "+(i+1)+" who is located at ("+enemyPosition[i][0]+","+enemyPosition[i][1]+").");
					}
				}
				x++;
				if(y<map[0].length&&x<map.length) {
					if(human[0]==x&&human[1]==y) {
						ship.setHp(ship.getHp()-enemyValue[i][1]);
						System.out.println("You have taken "+enemyValue[i][1]+" damage from enemy "+(i+1)+" who is located at ("+enemyPosition[i][0]+","+enemyPosition[i][1]+").");
					}
				}
				y--;
				if(x<map.length) {
					if(human[0]==x&&human[1]==y) {
						ship.setHp(ship.getHp()-enemyValue[i][1]);
						System.out.println("You have taken "+enemyValue[i][1]+" damage from enemy "+(i+1)+" who is located at ("+enemyPosition[i][0]+","+enemyPosition[i][1]+").");
					}
				}
				y--;
				if(y>0&&x<map.length) {
					if(human[0]==x&&human[1]==y) {
						ship.setHp(ship.getHp()-enemyValue[i][1]);
						System.out.println("You have taken "+enemyValue[i][1]+" damage from enemy "+(i+1)+" who is located at ("+enemyPosition[i][0]+","+enemyPosition[i][1]+").");
					}
				}
				x--;
				if(y>0) {
					if(human[0]==x&&human[1]==y) {
						ship.setHp(ship.getHp()-enemyValue[i][1]);
						System.out.println("You have taken "+enemyValue[i][1]+" damage from enemy "+(i+1)+" who is located at ("+enemyPosition[i][0]+","+enemyPosition[i][1]+").");
					}
				}
			
			}
		
	}

	public Ship getShip() {
		return ship;
	}
	public int[][] getEnemyValue() {
		return enemyValue;
	}
	public void movement() {
		int times=ship.getSpeed();
		String input;
		input = CaveExplorer.in.nextLine();
		if(input.equals("cheat")) {
			front.setWon(true);
		}else {
			while(times>0) {
				while (!isValid(input) || !checkWalls(input, getHuman()))
				{
					if (!checkWalls(input, getHuman()))
					{
						System.out.println("There is a wall. Please enter a valid direction.");
					}
					else
					{
						System.out.println("Enter a valid key.");
					}
					input = CaveExplorer.in.nextLine();
				}
				interpretInput(input);
				times--;
				front.displayHumanStats();
				front.displayEnemyStats();
				if(times>0) {
					input = CaveExplorer.in.nextLine();
					System.out.println("You can move "+times+" more times.");
				}else {
					int count=0;
					for(int i=0;i<front.getCoords().length;i++) {
						for(int j = 0; j < enemyPosition.length; j++) {
							if(enemyPosition[j][0]==front.getCoords()[i][0]&&enemyPosition[j][1]==front.getCoords()[i][1]) {
								if(enemyValue[j][0]>0) {
									count++;
								}
							}
						}
					}
					front.updateMap();
					if(count>0) {
						System.out.println("You may now attack");
						
						humanAttack();
					}
					else {
						System.out.println("There is no one to attack. It is now the enemies turn");
					}
				}
			}
			enemyAction(false);
			front.updateMap();
		}
	}
	public void humanAttack() {
		int[][] coord=front.getCoords();
		String input;
		String output="You may attack the follow enemies:\n";
		for(int[] a:coord) {
			for(int i=0;i<enemyPosition.length;i++) {
				if(enemyPosition[i][0]==a[0]&&enemyPosition[i][1]==a[1]&&enemyValue[i][0]>0) {
					output+="Enemy "+(i+1)+" at coord "+"("+a[0]+","+a[1]+")\n";
				}
			}
		}
		System.out.println(output);
		input = CaveExplorer.in.nextLine();
		while(!checkInput(input)&&!input.equals("0")) {
			System.out.println("Enter a valid key. Please select the enemy you want to attack.");
			input = CaveExplorer.in.nextLine();
		}
		enemyValue[Integer.parseInt(input)-1][0]-=ship.getAttack();
		System.out.println("You have attacked enemy "+input+".");
	}

	public boolean checkInput(String input) {
		int[][] coord=front.getCoords();
		int[] list = new int[enemyPosition.length];
		for(int[] a:coord) {
			for(int i=0;i<enemyPosition.length;i++) {
				if(enemyPosition[i][0]==a[0]&&enemyPosition[i][1]==a[1]&&enemyValue[i][0]>0) {
					for(int j=0;j<list.length;j++) {
						if(list[j]==0) {
							list[j]=i+1;
							break;
						}
					}
				}
			}
		}
		for(int j=0;j<list.length;j++) {
			try {
				if(list[j]==Integer.parseInt(input)) {
					return true;
				}
			}catch(NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	public boolean checkEnemyPos(int direction,int idx) {
		int x;
		int y;
		for(int i=0;i<enemyPosition.length;i++) {
			x=enemyPosition[i][0];
			y=enemyPosition[i][1];
			int x1=x--;
			int x2=x++;
			int y1=y--;
			int y2=y++;
			if(i!=idx) {
				if(direction==0&&x1==enemyPosition[i][0]) {
					return true;
				}
				if(direction==2&&x2==enemyPosition[i][0]) {
					return true;
				}
				if(direction==3&&y2==enemyPosition[i][1]) {
					return true;
				}
				if(direction==1&&y1==enemyPosition[i][1]) {
					return true;
				}
			}
		}
		
		return false;
	}

	public boolean checkHuman(int direction, int[] a) {
		int x=a[0];
		int y=a[1];
		int x1=x--;
		int x2=x++;
		int y1=y--;
		int y2=y++;
		if(direction==0&&x1==human[0]) {
			return true;
		}
		if(direction==2&&x2==human[0]) {
			return true;
		}
		if(direction==3&&y2==human[1]) {
			return true;
		}
		if(direction==1&&y1==human[1]) {
			return true;
		}
		return false;
	}
	
	public void attack(int type,int row,int col, int x, int y) {
		if(type==1) {
			for(int i=0;i<enemyPosition.length;i++) {
				
			}
		}
	}

	public void setFrontend(DanielFrontend x)
	{
		front = x;
	}
	
	public void setTypeEnemy() {
		for(int i=0;i<enemyValue.length;i++) {
			if(enemyValue[i][0]<1) {
				map[enemyPosition[i][0]][enemyPosition[i][1]].setType(3);
			}else {
				map[enemyPosition[i][0]][enemyPosition[i][1]].setType(2);
			}
				
		}
	}
	//
	/*public void makeFog() {
		int[] coord=new int[2];
		coord[0]=human[0]--;
		coord[1]=human[1]--;
		if(coord[0]>-1&&coord[1]>-1) {
			addVision(coord);
		}
		coord[0]=human[0];
		if(coord[0]>-1&&coord[1]>-1) {
			addVision(coord);
		}
		coord[1]=human[0];
		if(coord[0]>-1&&coord[1]>-1) {
			addVision(coord);
		}
		coord[1]=human[0]++;
		if(coord[0]>-1&&coord[1]<map[0].length-1) {
			addVision(coord);
		}
		
		
	}
	public void addVision(int[] coord) {
		if(!checkDupe(vision,coord)) {
			for(int i=0;i<vision.length;i++) {
				if(vision[i][0]==0&&vision[i][1]==0) {
					vision[i]=coord;
				}
			}
		}
	}
	public boolean checkDupe(int[][] a,int[] t) {
		if(t.length>2) {
			return false;
		}
		for(int[] b:a) {
			if(b[0]==t[0]&&b[1]==t[1]) {
				return true;
			}
		}
		return false;
	}*/
	
}
