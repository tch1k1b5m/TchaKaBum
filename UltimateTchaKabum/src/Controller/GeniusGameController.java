package Controller;

import java.util.Arrays;

public class GeniusGameController {

	private int lvl = 1;
	private int previousLvl = 0;
	private int coins;
		

	public void increasePrevLvl(){
		previousLvl++;
	}
	public void increaseLvl(){
		lvl++;
	}

	public void resetGame() {
		lvl = 1;
		previousLvl = 0;
	}


	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public int getPreviousLvl() {
		return previousLvl;
	}

	public void setPreviousLvl(int previousLvl) {
		this.previousLvl = previousLvl;
	}

	
	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public String increaseCoins() {
		this.coins++;
		String coinsString = "" + this.coins;
		return coinsString;
	}

	
}
