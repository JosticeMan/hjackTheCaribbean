package rpgDDSL;

import oceanExplorer.CaveExplorer;
import oceanExplorer.Ship;

import java.util.Scanner;


public class DanielFrontend implements StevenSupport{

	private String map;
	private RPGRoom[][] room;
	private StevenBackend backend;
	private boolean won;
	private int num;
	private boolean viewed;
	private Ship a;
	
	public static final void main(String[] args)
	{
		DanielFrontend demo = new DanielFrontend();
		CaveExplorer.in=new Scanner(System.in);
		demo.intro();		
	}
	public void intro()
	{	
		if (!viewed)
		{
			System.out.println("You've encountered rogue pirates! Do your best to defeat them all.\n Enter 'i' for instructions, or get into the action by entering 'p.'");
		}
		else
		{
			System.out.println("\nEnter 'p' to play the game, or reread the instructions by entering 'i'.");
		}
		String input = CaveExplorer.in.nextLine();
	
			if (input.equals("p"))
			{
				play();
			}
			else
			{
				if (input.equals("i"))
				{
					System.out.println("Your goal is to beat up all the pirates that invaded your ship.\n"
							+ "You are represented by 'H' while enemies are represented by 'E.'\nYour field of view is locked because of all the fog around you, represented by X."
							+ "\nLocate the pirates and then engage in battle with them by inputting 'r' once you are next to them to attack them."
							+ "\nDepending on your speed, you can move a certain amount of times. Pay attention to your stats!");
					viewed = true;
					intro();
				}
				else
				{
					intro();
				}
			}
	}
	public void play() 
	{
		backend.setFrontend(this);
		String input;
		fogOfWar();
		updateMap();
			while(!won) {
				input=CaveExplorer.in.nextLine();
				if (!input.equals("cheat"))
				{
					backend.movement(input);
				}
				else
				{
					System.out.println("You won! (kinda)");
					won = true;
				}
			}
	}//
	public DanielFrontend()
	{
		num = 5;
		a = new Ship(30,10,3);
		backend = new StevenBackend(this, num);
		won = false;
	}
	public void visionIfRow2()
	{
		int humanX = backend.getHuman()[0];
		int humanY = backend.getHuman()[1];
		
		if (humanX - 1 > 0)
		{
			backend.getMap()[humanX-1][humanY].setType(3);
			isEnemy(humanX-1, humanY);
			backend.getMap()[humanX+1][humanY].setType(3);
			isEnemy(humanX+1, humanY);
			backend.getMap()[humanX][humanY+1].setType(3);
			isEnemy(humanX, humanY+1);
		}
		else
		{
			backend.getMap()[humanX+1][humanY].setType(3);
			isEnemy(humanX+1, humanY);
			backend.getMap()[humanX][humanY+1].setType(3);		
			isEnemy(humanX, humanY+1);
		}
	}
	public void visionRegular()
	{
		int humanX = backend.getHuman()[0];
		int humanY = backend.getHuman()[1];
		
		backend.getMap()[humanX-1][humanY].setType(3); 
		isEnemy(humanX-1, humanY); 
		
		backend.getMap()[humanX+1][humanY+1].setType(3);
		isEnemy(humanX+1, humanY+1);
		
		backend.getMap()[humanX+1][humanY].setType(3);
		isEnemy(humanX+1, humanY);
		
		backend.getMap()[humanX][humanY+1].setType(3);
		isEnemy(humanX, humanY+1);
		
		backend.getMap()[humanX-1][humanY+1].setType(3);
		isEnemy(humanX-1, humanY+1);
				
		backend.getMap()[humanX][humanY-1].setType(3);
		isEnemy(humanX, humanY-1);
		
		backend.getMap()[humanX+1][humanY-1].setType(3);
		isEnemy(humanX+1, humanY-1);
			
		backend.getMap()[humanX-1][humanY-1].setType(3);
		isEnemy(humanX-1, humanY-1);
	}
	
	public void visionBottom()
	{
		int humanX = backend.getHuman()[0];
		int humanY = backend.getHuman()[1];
		
		backend.getMap()[humanX][humanY+1].setType(3); 
		isEnemy(humanX, humanY+1);
		
		backend.getMap()[humanX-1][humanY+1].setType(3);
		isEnemy(humanX-1, humanY+1);
				
		backend.getMap()[humanX-1][humanY].setType(3);
		isEnemy(humanX-1, humanY);
		
	}
	
	public void visionRightWall()
	{
		int humanX = backend.getHuman()[0];
		int humanY = backend.getHuman()[1];
		
		if (humanX + 1 < backend.getMap().length)
		{
			backend.getMap()[humanX][humanY-1].setType(3); 
			isEnemy(humanX, humanY-1);
			
			backend.getMap()[humanX+1][humanY].setType(3);
			isEnemy(humanX+1, humanY);
			
			backend.getMap()[humanX+1][humanY-1].setType(3);
			isEnemy(humanX+1, humanY-1);
		}
		else
		{
			backend.getMap()[humanX][humanY-1].setType(3); 
			isEnemy(humanX, humanY-1);
			
			backend.getMap()[humanX-1][humanY].setType(3);
			isEnemy(humanX-1, humanY);
			
			backend.getMap()[humanX-1][humanY-1].setType(3);
			isEnemy(humanX-1, humanY-1);
		}
	}
	
	public void isEnemy(int x, int y)
	{
		for (int i = 0; i < num; i++)
		{
			if (x == backend.getEnemyPosition()[i][0] && y == backend.getEnemyPosition()[i][1])
			{
				backend.getMap()[x][y].setType(2);
			}
		}
	}
	public void fogOfWar()
	{ 
		
		int humanX = backend.getHuman()[0];
		int humanY = backend.getHuman()[1];
		
		//SETS FOG
		for (int i = 0; i < backend.getMap().length; i++)
		{
			for (int j = 0; j < backend.getMap()[0].length; j++)
			{
				backend.getMap()[i][j].setType(0);
			}
		}
		
		//SETS HUMAN
		backend.getMap()[humanX][humanY].setType(1);
		
		//SETS FLASHLIGHT VISION FOR PLAYER 
		
		
		//STARTING POSITION VISION
		if (humanX + 1 < backend.getMap().length && humanY + 1 < backend.getMap()[0].length)
		{
			backend.getMap()[humanX][humanY+1].setType(3); 
			isEnemy(humanX, humanY+1);
			
			backend.getMap()[humanX+1][humanY].setType(3);
			isEnemy(humanX+1, humanY);
			
			backend.getMap()[humanX+1][humanY+1].setType(3);
			isEnemy(humanX+1, humanY+1);
			
		}
		
		if (humanX != 0 && humanY != backend.getMap().length)
		{
		
			if (humanY+1 < backend.getMap().length && humanX + 1 < backend.getMap()[0].length)
			{	
				/* field of view is 
				 * 		0		1		2		
				 *1     view	view	view
				 *2 	view	PLAYER	view
				 *3		view	view	view
				 */		
				if (humanX-1 > 0 && humanX+1 < backend.getMap().length && humanY - 1 > 0 && humanY+1 < backend.getMap().length - 1)
				{
					visionRegular();
				}
				else
				{		
					visionIfRow2();
				}
			}
		}
		if(humanX == backend.getMap().length - 1 && humanY + 1 < backend.getMap().length)
		{
			visionBottom();
		}
		if (humanY == backend.getMap()[0].length - 1)
		{
			visionRightWall();
		}

		 
		
	}
	public void updateMap() {
		map = " ";
		
		//CLEAR 2D ARRAY
		for (RPGRoom[] a: backend.getMap())
		{
			for (RPGRoom b: a)
			{
				b.setType(3);
			}
		}
		
		//SETS TYPES 
		setType();
		
		//CREATES FOG + HUMAN
		//fogOfWar();
		

		//MAP CREATION
		
		
		for (int i = 0; i < backend.getMap().length; i++)
		{
			if (i != 0 || i == backend.getMap().length - 1)
			{
				map += "|";
			}
			
			
			for (int j = 0; j < backend.getMap()[0].length; j++)
			{
				
				if (j == backend.getMap()[0].length - 1 && i != 0)
				{
					map += backend.getMap()[i][j].toString() + "|";
				}
				else
				{
					
					if (i == 0 || i == backend.getMap().length - 1)
					{
						if (i == backend.getMap().length - 1 && j != backend.getMap()[0].length - 1)
						{
							map += backend.getMap()[i][j].toString() + "___";
						}
						if (i == 0 && j != backend.getMap()[0].length - 1)
						{
							map += backend.getMap()[i][j].toString() + "¯ ¯";
						}
						if (j == backend.getMap()[0].length - 1)
						{
							map += backend.getMap()[i][j].toString() + "¯  ";
						}
					}
					else
					{
						map += backend.getMap()[i][j].toString() + "   ";
					}
				}
			}
			map += "\n";
		}
		
		
			
		/*
		for (int row = 0; row < backend.getMap().length; row++)
		{
			for (int col = 0; col < backend.getMap()[0].length; col++)
			{
				if (fogCoords[row][col] == 0)
				{
					backend.getMap()[fogCoords[0][col]][col].setType(3);
				}
			}
		}
		*/
		
		System.out.println(map);
	}
	
	public void setType()
	{		
		backend.getMap()[backend.getHuman()[0]][backend.getHuman()[1]].setType(1);
		
		for (int[] a : backend.getEnemyPosition())
		{
			backend.getMap()[a[0]][a[1]].setType(2);
		}
	}
	
	public DanielFrontend getFront()
	{
		return this;
	}
	@Override
	public Ship getShip() 
	{
		
		return a;
	}
	
	public int[][] getCoords()
	{
		int[][] enemyCoords;
		enemyCoords = new int[num][num];
		
		int humanX = backend.getHuman()[0];
		int humanY = backend.getHuman()[1];
		
		for (int i = 0; i < num; i++)
		{
			if (humanX + 1 == backend.getEnemyPosition()[i][0] || humanX - 1 == backend.getEnemyPosition()[i][0] || humanY + 1 == backend.getEnemyPosition()[i][1] || humanY - 1 == backend.getEnemyPosition()[i][1])
			{
				enemyCoords[i][0] = backend.getEnemyPosition()[i][0];
				enemyCoords[i][1] = backend.getEnemyPosition()[i][1];
				
			}
		}
		return enemyCoords;		
	}
}
