package Controller;

import java.util.Arrays;

import org.andengine.opengl.shader.PositionColorShaderProgram;

public class ChallengeGameController {

	private int lvlChangeScore = 100;
	private int lvl = 1;
	private int previousLvl = 0;
	private int lifes = 3;
	private int score = 0;
	private int combo = 0;
	private int burstBalloonCont = 0;
	private int coins;
	private int coinsPerMatch = 0;
	private int[] scoreArray = new int[10];
	private int previousColorBurst = 0;

	public int[] getScoreArray() {
		return scoreArray;
	}

	public void setScoreArray(int[] scoreArray) {
		this.scoreArray = scoreArray;
	}

	public void addLife() {
		lifes++;
	}

	public void increaseScore() {
		score += combo + 10;
	}
	
	public void increaseScoreWithoutCombo() {
		score += 10;
	}

	public void decreaseScore() {
		if (score >= 50)
			score -= 50;
		else
			score = 0;
	}

	public void clearBurstBalloonCont() {
		burstBalloonCont = 0;
	}

	public void verifyLevelChange() {
		if (lvl <= 3) {
			lvlChangeScore = 100;
		} else if (lvl <= 13) {
			lvlChangeScore = 200;
		} else if (lvl >= 14 && lvl <= 18) {
			lvlChangeScore = 300;
		} else {
			lvlChangeScore = 400;
		}
		previousColorBurst = 0;
		burstBalloonCont = 0;
		lvl++;

	}

	public void resetGame() {
		lvlChangeScore = 100;
		lvl = 1;
		previousLvl = 0;
		lifes = 3;
		score = 0;
		combo = 0;
		burstBalloonCont = 0;
		coinsPerMatch = 0;
	}

	public void increaseBurstBallonCont() {
		burstBalloonCont += 10;
	}

	public int getBurstBalloonCont() {
		return burstBalloonCont;
	}

	public void setBurstBalloonCont(int burstBalloonCont) {
		this.burstBalloonCont = burstBalloonCont;
	}

	public int getLvlChangeScore() {
		return lvlChangeScore;
	}

	public void increaseCombo(int ballonColor, boolean doubleCombo) {
		if(doubleCombo){
			if (lvl <= 8) {
				combo+=2;
			} else if (lvl <= 13) {
				if (ballonColor == previousColorBurst) {
					combo += 4;
				} else {
					previousColorBurst = ballonColor;
					combo+=2;
				}
			} else {
				if (ballonColor == previousColorBurst) {
					combo += 6;
				} else {
					previousColorBurst = ballonColor;
					combo+=3;
				}
			}
		}else{
			if (lvl <= 8) {
			combo++;
		} else if (lvl <= 13) {
			if (ballonColor == previousColorBurst) {
				combo += 2;
			} else {
				previousColorBurst = ballonColor;
				combo++;
			}
		} else {
			if (ballonColor == previousColorBurst) {
				combo += 3;
			} else {
				previousColorBurst = ballonColor;
				combo++;
			}
		}
	}
}

	public void clearCombo() {
		combo = 0;
	}

	public int getCombo() {
		return combo;
	}
	
	public void setLvlChangeScore(int lvlChangeScore) {
		this.lvlChangeScore = lvlChangeScore;
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

	public int getLifes() {
		return lifes;
	}

	public void setLifes(int lifes) {
		this.lifes = lifes;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getCoins() {
		String string = coins +"";
		return string;
	}
	
	public int getIntCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}
	
	public int getCoinsPerMatch(){
		return coinsPerMatch;
	}
	
	public void setCoinsPerMatch(int coins) {
		this.coinsPerMatch = coins;
	}

	public int increaseCoinsPerMatch(){
		this.coinsPerMatch +=1;
		return coinsPerMatch;
	}
	public String increaseCoins() {
		this.coins += 1;
		String coinsString = "" + this.coins;
		return coinsString;
	}

	public void updateScore() {

		if (scoreArray[0] < this.score) {
			scoreArray[0] = this.score;
			Arrays.sort(scoreArray);
		}

	}
	
	public String getRankingPosition(){
		String position = null;
		int pos = 10;
		for(int i = 0; i<scoreArray.length; i++){
			if(scoreArray[i] == score){
				position = Integer.toString(pos);	
			}
			pos--;
			
		}
		return position;
	}
	
}
