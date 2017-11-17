package rpgDDSL;

import oceanExplorer.CaveExplorer;
import java.util.Scanner;


public class DanielFrontend implements StevenSupport{

	private String map;
	private RPGRoom[][] room;
	private StevenBackend backend;
	private boolean won;
	
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
		backend = new StevenBackend(this, 2);
		won = false;
	}
	public void fogOfWar()
	{
		int humanX = backend.getHuman()[0];
		int humanY = backend.getHuman()[1];
		if (backend.getHuman()[0] != 0 && backend.getHuman()[0] != backend.getMap().length)
		{
			if (humanY+1 < backend.getMap().length && humanX + 1 < backend.getMap()[0].length)
			{	
				/* field of view is 
				 * 		0		1		
				 *1     view	view
				 *2 	PLAYER	view
				 *3		view	view
				 */		
				if (humanX-1 > 0)
				{
					backend.getMap()[humanX-1][humanY].setType(3); 
					backend.getMap()[humanX+1][humanY+1].setType(3);
					backend.getMap()[humanX+1][humanY].setType(3);
					backend.getMap()[humanX][humanY+1].setType(3);
					backend.getMap()[humanX-1][humanY+1].setType(3);
				}
				else
				{	
					/* Player on starting square (0,0)
					* field of view is 
					* 		0		1		
					*1      PLAYER	view
					*2 		view	view
					*3		NONE	NONE
					*/						
					if (humanX == 0)
					{
						
					}
					else
					{
						/* Player on bottom square (x,7)
						* field of view is 
						* 		0		1		
						*5      NONE	NONE
						*6 		view	view
						*7		PLAYER	view 
						*/	
						if (humanX == backend.getMap().length - 1)
						{
							backend.getMap()[humanX-1][humanY].setType(3); 
							backend.getMap()[humanX][humanY+1].setType(3);
							backend.getMap()[humanX-1][humanY+1].setType(3);
						}
					}
				}
			}
			else
			{

			}
		}

		
	}
	public void updateMap() {
		map = " ";
		fogOfWar();
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
						if (i == 2 && j == 1)
						{
							map += backend.getMap()[2][1] + " | ";
							backend.getMap()[2][1].setEast(true);
							backend.getMap()[2][2].setWest(true);							
						}
						else
						{
							if (i == 2 && j == 4)
							{
								map += backend.getMap()[2][4] + " | ";
								backend.getMap()[2][4].setEast(true);
								backend.getMap()[2][5].setWest(true);	
							}
							else
							{
								if (i == 4 && j == 1)
								{
									map += backend.getMap()[4][1] + " | ";
									backend.getMap()[4][1].setEast(true);
									backend.getMap()[4][2].setWest(true);	
								}
								else
								{
									if (i == 4 && j == 4)
									{
										map += backend.getMap()[4][4] + " | ";
										backend.getMap()[4][4].setEast(true);
										backend.getMap()[4][5].setWest(true);	
									}
									else
									{
										map += backend.getMap()[i][j].toString() + "   ";
									}
								}
								
							}
						}
					}
				}
			}
			
			map += "\n";
		}
		
		
		
		System.out.println(map);
	}
	public DanielFrontend getFront()
	{
		return this;
	}
}
