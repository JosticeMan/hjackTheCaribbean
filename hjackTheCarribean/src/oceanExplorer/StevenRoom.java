package oceanExplorer;

public class StevenRoom extends CaveRoom {
	
	private boolean make;
	private int stat=5;
	private int atk=5;
	private int hp=10;
	private int spd=1;
	private boolean visited;

	public StevenRoom(String description) {
		super(description);
		make=false;
		visited=false;
	}
	public void interpretInput(String input) {
		if(!visited) {
			if(!make&&CaveExplorer.inventory.getBeginningShip()==null&&!input.equalsIgnoreCase("yes")) {
				CaveExplorer.currentRoom.leave();
				CaveExplorer.currentRoom = CaveExplorer.caves[0][1];
				CaveExplorer.currentRoom.enter();
				CaveExplorer.inventory.updateMap();
			}
			if(!make&&CaveExplorer.inventory.getBeginningShip()==null&&input.equalsIgnoreCase("yes")) {
				make=true;
			}
			while(make) {
				CaveExplorer.print("You have "+stat+" points remaining. What stat would you like to put your stat point into(hp,atk,spd)?");
				input=CaveExplorer.in.nextLine();
				if(input.equalsIgnoreCase("hp")||input.equalsIgnoreCase("atk")||input.equalsIgnoreCase("spd")) {
					if(input.equalsIgnoreCase("hp")) {
						stat--;
						hp+=10;
					}
					if(input.equalsIgnoreCase("atk")) {
						stat--;
						atk+=5;
					}
					if(input.equalsIgnoreCase("spd")) {
						stat--;
						spd+=1;
					}
				}
				else {
					CaveExplorer.print("Please select a stat");
				}
				if(stat==0) {
					make=false;
					CaveExplorer.inventory.setBeginningShip(new Ship(hp,atk,spd));
					visited=true;
					CaveExplorer.currentRoom.leave();
					CaveExplorer.currentRoom = CaveExplorer.caves[0][1];
					CaveExplorer.currentRoom.enter();
					CaveExplorer.inventory.updateMap();
				}
			}
		}else {
			super.interpretInput(input);
		}
	}
//
	
	public String getDescription() {
		if(CaveExplorer.inventory.getBeginningShip()==null) {
			return "You have found materials to build your ship. How would you like to customize it?";
		}
		return super.getDescription();

	}
	public String getContents() {
		if(!visited) {
			return "M";
		}
		return super.getContents();
		
	}

}
