package Model;

public class ChallengeLvlDynamic {
	
	private int lvl = 0;
	private int colorCont = 0 ;
	private float speed = 0.0f;
	private float timer = 0.0f;
	
	public ChallengeLvlDynamic(int lvl, int colorCont, float speed, float timer){
		this.lvl = lvl;
		this.colorCont = colorCont;
		this.speed = speed;
		this.timer = timer;
	}

	//--------------------
	//Getters and Setters
	//--------------------
	
	
	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public int getColorCont() {
		return colorCont;
	}

	public void setColorCont(int colorCont) {
		this.colorCont = colorCont;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getTimer() {
		return timer;
	}

	public void setTimer(float timer) {
		this.timer = timer;
	}
	

	
}
