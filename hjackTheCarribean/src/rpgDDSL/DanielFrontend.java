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
			updateMap();
			input=CaveExplorer.in.nextLine();
			while (!backend.isValid(input) || !backend.checkWalls(input))
			{
				if (!backend.checkWalls(input))
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
		backend= new StevenBackend(this);
		won=false;
	}
	
	public void updateMap() {
		map = " ";
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
							map += backend.getMap()[i][j].toString() + "¯¯¯";
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
		
		backend.getMap()[2][2].setEast(true);
		System.out.println(map);
	}
	
	public DanielFrontend getFront()
	{
		return this;
	}
}
