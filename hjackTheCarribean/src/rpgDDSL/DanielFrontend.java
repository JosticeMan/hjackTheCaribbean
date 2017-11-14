package rpgDDSL;

import oceanExplorer.CaveExplorer;
import java.util.Scanner;


public class DanielFrontend implements StevenSupport{

	private String map;
	private RPGRoom[][] room;
	private StevenBackend backend;
	public static Scanner in;
	private boolean won;
	
	public static final void main(String[] args)
	{
		StevenBackend b = new StevenBackend(this);
		DanielFrontend demo = new DanielFrontend(b);
		in = new Scanner(System.in);
		demo.play();
		
	}
	public void play() 
	{
		String input;
		while(!won) {
			updateMap();
			input=in.nextLine();
			backend.interpretInput(input);
		}
		
	}
	public DanielFrontend(StevenBackend x)
	{
		this.backend = x;
		won=false;
	}
	
	public void updateMap() {
		map = " ";
		
		for (int i = 0; i < backend.getMap().length; i++)
		{
			for (int j = 0; j < backend.getMap()[0].length; j++)
			{
				map += backend.getMap()[i][j].toString() + " , ";
			}
			
			map += "\n";
		}
		System.out.println(map);
	}
}
