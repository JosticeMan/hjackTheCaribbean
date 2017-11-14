package jungleTreasureHuntAZKL;

import java.util.Scanner;

public class AndrewBackend implements KevinSupport{

	private AndrewSupport frontend;
	
	
	public AndrewBackend(AndrewSupport frontend) {
		this.frontend = frontend;
		
	}

	@Override
	public boolean playing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object end() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String processInput() {
		// TODO Auto-generated method stub
		return null;
	}
}
