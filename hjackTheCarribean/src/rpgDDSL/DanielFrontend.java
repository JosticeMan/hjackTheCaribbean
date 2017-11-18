package rpgDDSL;

import oceanExplorer.CaveExplorer;
import java.util.Scanner;


public class DanielFrontend implements StevenSupport{

	private String map;
	private RPGRoom[][] room;
	private StevenBackend backend;
	private boolean won;
	private int[][] fogCoords;
	
	public static final void main(String[] args)
	{
		
		DanielFrontend demo = new DanielFrontend();
		CaveExplorer.in=new Scanner(System.in);
		demo.play();
		
	}
	public void play() 
	{
		backend.setFrontend(this);
		String input;
		while(!won) {
			fogOfWar();
			updateMap();
			input=CaveExplorer.in.nextLine();
			while (!backend.isValid(input) || !backend.checkWalls(input, backend.getHuman()))
			{
				if (!backend.checkWalls(input, backend.getHuman()))
				{
					System.out.println("There is a wall. Please enter a valid direction.");
				}
				else
				{
					System.out.println("Enter a valid key.");
				}
				input = CaveExplorer.in.nextLine();
			}
			backend.interpretInput(input);
		}
		
	}
	public DanielFrontend()
	{
		backend = new StevenBackend(this, 0);
		won = false;
	}
	public void visionIfRow2()
	{
		int humanX = backend.getHuman()[0];
		int humanY = backend.getHuman()[1];
		
		if (humanX - 1 > 0)
		{
			backend.getMap()[humanX-1][humanY].setType(3); 
			backend.getMap()[humanX+1][humanY].setType(3);
			backend.getMap()[humanX][humanY+1].setType(3);
		}
		else
		{
			backend.getMap()[humanX+1][humanY].setType(3);
			backend.getMap()[humanX][humanY+1].setType(3);						
		}
	}
	public void visionRegular()
	{
		int humanX = backend.getHuman()[0];
		int humanY = backend.getHuman()[1];
		
		backend.getMap()[humanX-1][humanY].setType(3); 
		backend.getMap()[humanX+1][humanY+1].setType(3);
		backend.getMap()[humanX+1][humanY].setType(3);
		backend.getMap()[humanX][humanY+1].setType(3);
		backend.getMap()[humanX-1][humanY+1].setType(3);
		
		backend.getMap()[humanX][humanY-1].setType(3);
		backend.getMap()[humanX+1][humanY-1].setType(3);
		backend.getMap()[humanX-1][humanY-1].setType(3);
	}
	
	public void visionBottom()
	{
		int humanX = backend.getHuman()[0];
		int humanY = backend.getHuman()[1];
		
		backend.getMap()[humanX][humanY+1].setType(3); 
		backend.getMap()[humanX-1][humanY+1].setType(3);
		backend.getMap()[humanX-1][humanY].setType(3);
	}
	
	public void visionRightWall()
	{
		int humanX = backend.getHuman()[0];
		int humanY = backend.getHuman()[1];
		
		if (humanX + 1 < backend.getMap().length)
		{
			backend.getMap()[humanX][humanY-1].setType(3); 
			backend.getMap()[humanX+1][humanY].setType(3);
			backend.getMap()[humanX+1][humanY-1].setType(3);
		}
		else
		{
			backend.getMap()[humanX][humanY-1].setType(3); 
			backend.getMap()[humanX-1][humanY].setType(3);
			backend.getMap()[humanX-1][humanY-1].setType(3);
		}
	}
	
	public boolean isEnemy(int x, int y)
	{
		int[][] temp;
		temp = new int[backend.getEnemyPosition().length][backend.getEnemyPosition()[0].length];
		
		for (int i = 0; i < backend.getEnemyPosition().length; i++)
		{
			for (int j = 0; j < backend.getEnemyPosition()[0].length; j++)
			{
				if (backend.getEnemyPosition()[i][j] == temp[x][y])
				{
					backend.getMap()[x][y].setType(2);
					return true;
				}
			}
		}
		
		return false;
	}
	public void fogOfWar()
	{
		fogCoords = new int[20][20]; 
		
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
			backend.getMap()[humanX+1][humanY].setType(3);
			backend.getMap()[humanX+1][humanY+1].setType(3);
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
	//	setType();
		
		//CREATES FOG + HUMAN
		fogOfWar();
		

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
	/*
	public void setType()
	{		
		backend.getMap()[backend.getHuman()[0]][backend.getHuman()[1]].setType(1);
		
		for (int[] a : backend.getEnemyPosition())
		{
			backend.getMap()[a[0]][a[1]].setType(2);
		}
	}
	*/
	public DanielFrontend getFront()
	{
		return this;
	}
}
