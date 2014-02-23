package Controller;

import java.util.ArrayList;

import org.andengine.entity.scene.Scene;

import Model.BallonSprite;

public class GeniusBalloonController {

	private ArrayList<BallonSprite> redBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> darkblueBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> yellowBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> lightblueBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> pinkBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> blackBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> grayBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> greenBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> orangeBallons = new ArrayList<BallonSprite>();

	private boolean hasPassedLvl = false;

	private int penultPos = 0;
	private int antpenultPos = 0;
	private int rightColorsCont = 1;
	private int lastColor = 0;
	private float speed = 5;

	private ArrayList<Integer> geniusColors = new ArrayList<Integer>();
	private int geniusColorIndex = 0;
	private int geniusColorIndexPopup = 0;
	
	public void increaseGeniusColorIndexPopup(){
		geniusColorIndexPopup++;
	}
	
	public void resetGeniusColorIndexPopup(){
		geniusColorIndexPopup = 0;
	}

	public int getGeniusColorIndexPopup() {
		return geniusColorIndexPopup;
	}

	public void setGeniusColorIndexPopup(int geniusColorIndexPopup) {
		this.geniusColorIndexPopup = geniusColorIndexPopup;
	}

	public void resetBalloonController(Scene mScene) {
		penultPos = 0;
		antpenultPos = 0;
		rightColorsCont = 1;
		lastColor = 0;
		speed = 5;
		geniusColorIndex = 0;
		geniusColors.clear();

		System.gc();
	}

	public boolean rightColorBurst(int color) {
		if (geniusColors.get(geniusColorIndex) == color) {
			System.out.println("Cor certa");
			if (geniusColors.size() != (geniusColorIndex+1))
				geniusColorIndex++;
			else {
				hasPassedLvl = true;
			}
			return true;
		} else {
			return false;
		}
	}

	public String getGeniusColorsString() {
		String colors = "";
		for (int cont = 0; cont < geniusColors.size(); cont++) {
			if (geniusColors.get(cont) == 1) {
				System.out.println("amarelo");
				colors += "amarelo ;";
			} else if (geniusColors.get(cont) == 2)
				colors += "azul ;";
			else if (geniusColors.get(cont) == 3)
				colors += "vermelho ;";
			else if (geniusColors.get(cont) == 4)
				colors += "laranja ;";
		}
		return colors;
	}

	public void lvlPassed() {
		int index = (int) (Math.random() * 4) + 1;
		geniusColors.add(index);
		geniusColorIndex = 0;
		hasPassedLvl = false;
	}

	public ArrayList<BallonSprite> getGrayBallons() {
		return grayBallons;
	}

	public void setGrayBallons(ArrayList<BallonSprite> grayBallons) {
		this.grayBallons = grayBallons;
	}

	public boolean hasPassedLvl() {
		return hasPassedLvl;
	}

	public void setHasPassedLvl(boolean hasPassedLvl) {
		this.hasPassedLvl = hasPassedLvl;
	}

	public ArrayList<Integer> getGeniusColors() {
		return geniusColors;
	}

	public void setGeniusColors(ArrayList<Integer> geniusColors) {
		this.geniusColors = geniusColors;
	}

	public int getGeniusColorIndex() {
		return geniusColorIndex;
	}

	public void setGeniusColorIndex(int geniusColorIndex) {
		this.geniusColorIndex = geniusColorIndex;
	}

	public void clearLists() {
		this.yellowBallons.clear();
		this.darkblueBallons.clear();
		this.redBallons.clear();
		this.orangeBallons.clear();
		this.grayBallons.clear();
		this.blackBallons.clear();
		this.pinkBallons.clear();
		this.lightblueBallons.clear();
		this.greenBallons.clear();
	}

	public void disposeAllBalloons(Scene mScene) {
		for (int cont = 0; cont < yellowBallons.size(); cont++) {
			mScene.unregisterTouchArea(yellowBallons.get(cont));
			yellowBallons.get(cont).dispose();
		}
		for (int cont = 0; cont < darkblueBallons.size(); cont++) {
			mScene.unregisterTouchArea(darkblueBallons.get(cont));
			darkblueBallons.get(cont).dispose();
		}
		for (int cont = 0; cont < redBallons.size(); cont++) {
			mScene.unregisterTouchArea(redBallons.get(cont));
			redBallons.get(cont).dispose();
		}

		for (int cont = 0; cont < orangeBallons.size(); cont++) {
			mScene.unregisterTouchArea(orangeBallons.get(cont));
			orangeBallons.get(cont).dispose();
		}
		for (int cont = 0; cont < grayBallons.size(); cont++) {
			mScene.unregisterTouchArea(grayBallons.get(cont));
			grayBallons.get(cont).dispose();
		}
		for (int cont = 0; cont < blackBallons.size(); cont++) {
			mScene.unregisterTouchArea(blackBallons.get(cont));
			blackBallons.get(cont).dispose();
		}
		for (int cont = 0; cont < pinkBallons.size(); cont++) {
			mScene.unregisterTouchArea(pinkBallons.get(cont));
			pinkBallons.get(cont).dispose();
		}
		for (int cont = 0; cont < lightblueBallons.size(); cont++) {
			mScene.unregisterTouchArea(lightblueBallons.get(cont));
			lightblueBallons.get(cont).dispose();
		}
		for (int cont = 0; cont < greenBallons.size(); cont++) {
			mScene.unregisterTouchArea(greenBallons.get(cont));
			greenBallons.get(cont).dispose();
		}
	}

	public int getEnabledBallons(int color) {

		int indexLista = -1;
		if (color == 1) {
			if (yellowBallons.size() != 0) {
				for (int cont = 0; cont < yellowBallons.size(); cont++) {
					if (yellowBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == 2) {
			if (darkblueBallons.size() != 0) {
				for (int cont = 0; cont < darkblueBallons.size(); cont++) {
					if (darkblueBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == 3) {
			if (redBallons.size() != 0) {
				for (int cont = 0; cont < redBallons.size(); cont++) {
					if (redBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == 4) {
			if (orangeBallons.size() != 0) {
				for (int cont = 0; cont < orangeBallons.size(); cont++) {
					if (orangeBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == 5) {
			if (grayBallons.size() != 0) {
				for (int cont = 0; cont < grayBallons.size(); cont++) {
					if (grayBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == 6) {
			if (blackBallons.size() != 0) {
				for (int cont = 0; cont < blackBallons.size(); cont++) {
					if (blackBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == 7) {
			if (pinkBallons.size() != 0) {
				for (int cont = 0; cont < pinkBallons.size(); cont++) {
					if (pinkBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == 8) {
			if (lightblueBallons.size() != 0) {
				for (int cont = 0; cont < lightblueBallons.size(); cont++) {
					if (lightblueBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == 9) {
			if (greenBallons.size() != 0) {
				for (int cont = 0; cont < greenBallons.size(); cont++) {
					if (greenBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		}

		return indexLista;
	}

	// Getters and Setters

	public ArrayList<BallonSprite> getRedBallons() {
		return redBallons;
	}

	public void setRedBallons(ArrayList<BallonSprite> redBallons) {

		this.redBallons = redBallons;
	}

	public ArrayList<BallonSprite> getDarkBlueBallons() {
		return darkblueBallons;
	}

	public void setDarkBlueBallons(ArrayList<BallonSprite> blueBallons) {
		this.darkblueBallons = blueBallons;
	}

	public ArrayList<BallonSprite> getYellowBallons() {

		return yellowBallons;
	}

	public void setYellowBallons(ArrayList<BallonSprite> yellowBallons) {

		this.yellowBallons = yellowBallons;
	}

	public ArrayList<BallonSprite> getLightBlueBallons() {
		return lightblueBallons;
	}

	public void setLightBlueBallons(ArrayList<BallonSprite> purpleBallons) {

		this.lightblueBallons = purpleBallons;
	}

	public ArrayList<BallonSprite> getPinkBallons() {
		return pinkBallons;
	}

	public void setPinkBallons(ArrayList<BallonSprite> pinkBallons) {

		this.pinkBallons = pinkBallons;
	}

	public ArrayList<BallonSprite> getBlackBallons() {
		return blackBallons;
	}

	public void setBlackBallons(ArrayList<BallonSprite> blackBallons) {

		this.blackBallons = blackBallons;
	}

	public ArrayList<BallonSprite> getgrayBallons() {
		return grayBallons;
	}

	public void setgrayBallons(ArrayList<BallonSprite> grayBallons) {

		this.grayBallons = grayBallons;
	}

	public ArrayList<BallonSprite> getGreenBallons() {
		return greenBallons;
	}

	public void setGreenBallons(ArrayList<BallonSprite> greenBallons) {

		this.greenBallons = greenBallons;
	}

	public ArrayList<BallonSprite> getOrangeBallons() {
		return orangeBallons;
	}

	public void setOrangeBallons(ArrayList<BallonSprite> orangeBallons) {

		this.orangeBallons = orangeBallons;
	}

	public int getPenultPos() {
		return penultPos;
	}

	public void setPenultPos(int penultPos) {

		this.penultPos = penultPos;
	}

	public int getAntpenultPos() {
		return antpenultPos;
	}

	public void setAntpenultPos(int antpenultPos) {

		this.antpenultPos = antpenultPos;
	}

	public int getRightColorsCont() {
		return rightColorsCont;
	}

	public void setRightColorsCont(int rightColorsCont) {

		this.rightColorsCont = rightColorsCont;
	}

	public int getLastColor() {
		return lastColor;
	}

	public void setLastColor(int lastColor) {

		this.lastColor = lastColor;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
