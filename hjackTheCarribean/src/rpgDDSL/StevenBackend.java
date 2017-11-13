package rpgDDSL;

public class StevenBackend implements DanSupport {
	
	private StevenSupport frontend;
	private RPGRoom[][] map;
	
	public StevenBackend(StevenSupport frontend)
	{
		map = new RPGRoom[5][5];
	}
	
	public void makeMap()
	{
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map[0].length; i++)
			{
				map[i][j] = new RPGRoom(0);
					
			}
		}
		
		map[3][3] = new RPGRoom(2);
		map[1][1] = new RPGRoom(1);
		
		
	}

	public RPGRoom[][] getMap() {
		return map;
	}
}
