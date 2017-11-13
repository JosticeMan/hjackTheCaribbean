package rpgDDSL;

import oceanExplorer.CaveExplorer;
import oceanExplorer.CaveRoom;

public class DanielFrontend implements StevenSupport{

	private String map;
	private RPGRoom[][] room;
	private StevenBackend backend;
	
	public static final void main(String[] args)
	{
		StevenBackend b = new StevenBackend(null);
		DanielFrontend demo = new DanielFrontend(b);
		
		demo.play();
		
	}
	public void play() 
	{
		updateMap();
		System.out.println(map);
	}
	public DanielFrontend(StevenBackend x)
	{
		this.backend = x;
	}
	
	public void updateMap() {
		map = " ";
		
		for (int i = 0; i < backend.getMap().length; i++)
		{
			for (int j = 0; j < backend.getMap()[0].length; i++)
			{
				map += backend.getMap()[i][j] + ",";
			}
			
			map += "\n";
		}
		
	}
}
