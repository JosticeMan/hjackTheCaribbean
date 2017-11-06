package oceanExplorer;

public class Ship {
	
	private int hp;
	private int attack;
	private int speed;
	
	public Ship(int hp,int attack,int speed) {
		this.hp=hp;
		this.attack=attack;
		this.speed=speed;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	

}
