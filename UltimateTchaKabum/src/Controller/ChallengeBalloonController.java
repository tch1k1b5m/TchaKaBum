package Controller;

import java.util.ArrayList;

import org.andengine.entity.scene.Scene;

import Model.BallonSprite;
import Model.BalloonType;
import Model.ChallengeLvlDynamic;

public class ChallengeBalloonController {

	private ArrayList<BallonSprite> redBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> darkblueBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> yellowBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> lightBlueBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> pinkBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> blackBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> grayBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> greenBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> orangeBallons = new ArrayList<BallonSprite>();

	private ArrayList<BallonSprite> coinBalloons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> blasterBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> addlifeBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> blizzardBallons = new ArrayList<BallonSprite>();
	private ArrayList<BallonSprite> comboBallons = new ArrayList<BallonSprite>();

	private BallonSprite[] ballonsMenu = new BallonSprite[3];
	private ChallengeLvlDynamic[] levelDynamics = new ChallengeLvlDynamic[19];

	public ChallengeLvlDynamic[] getLevelDynamics() {
		return levelDynamics;
	}

	public void setLevelDynamics(ChallengeLvlDynamic[] levelDynamics) {
		this.levelDynamics = levelDynamics;
	}

	private int rightColors[] = { 0, 0, 0 };

	private static final BalloonType bType = new BalloonType();

	private int colorCont = 4;
	private int penultPos = 0;
	private int antpenultPos = 0;
	private int rightColorsCont = 1;
	private int lastColor = 0;
	private float speed = 5;
	private float baseSpeed = 5;
	private float timer = 0.3f;
	private int freezeCont = 0;

	String cor1 = "";
	String cor2 = "";
	String cor3 = "";

	private BallonSprite yellowBallonMenu;
	private BallonSprite darkblueBallonMenu;
	private BallonSprite redBallonMenu;
	private BallonSprite orangeBallonMenu;
	private BallonSprite grayBallonMenu;
	private BallonSprite lightblueBallonMenu;
	private BallonSprite pinkBallonMenu;
	private BallonSprite blackBallonMenu;
	private BallonSprite greenBallonMenu;

	public ChallengeBalloonController() {
		levelDynamics[0] = new ChallengeLvlDynamic(19, 9, 2.5f, 0.3f);
		levelDynamics[1] = new ChallengeLvlDynamic(1, 4, 4.5f, 0.3f);
		levelDynamics[2] = new ChallengeLvlDynamic(2, 4, 4.0f, 0.3f);
		levelDynamics[3] = new ChallengeLvlDynamic(3, 4, 3.5f, 0.3f);
		levelDynamics[4] = new ChallengeLvlDynamic(4, 4, 3.2f, 0.4f);
		levelDynamics[5] = new ChallengeLvlDynamic(5, 4, 3.0f, 0.3f);
		levelDynamics[6] = new ChallengeLvlDynamic(6, 4, 2.8f, 0.3f);
		levelDynamics[7] = new ChallengeLvlDynamic(7, 4, 2.5f, 0.3f);
		levelDynamics[8] = new ChallengeLvlDynamic(8, 7, 2.5f, 0.3f);
		levelDynamics[9] = new ChallengeLvlDynamic(9, 7, 3.0f, 0.4f);
		levelDynamics[10] = new ChallengeLvlDynamic(10, 7, 3.0f, 0.4f);
		levelDynamics[11] = new ChallengeLvlDynamic(11, 7, 2.5f, 0.4f);
		levelDynamics[12] = new ChallengeLvlDynamic(12, 7, 2.0f, 0.4f);
		levelDynamics[13] = new ChallengeLvlDynamic(13, 7, 2.0f, 0.4f);
		levelDynamics[14] = new ChallengeLvlDynamic(14, 7, 4.0f, 0.4f);
		levelDynamics[15] = new ChallengeLvlDynamic(15, 9, 4.0f, 0.4f);
		levelDynamics[16] = new ChallengeLvlDynamic(16, 9, 3.5f, 0.4f);
		levelDynamics[17] = new ChallengeLvlDynamic(17, 9, 3.0f, 0.4f);
		levelDynamics[18] = new ChallengeLvlDynamic(18, 9, 2.5f, 0.4f);

	}

	public void resetBalloonController(Scene mScene) {
		rightColors[0] = 0;
		rightColors[1] = 0;
		rightColors[2] = 0;
		colorCont = 4;
		penultPos = 0;
		antpenultPos = 0;
		rightColorsCont = 1;
		lastColor = 0;
		speed = 5;
		System.gc();
	}

	// novo metodo
	
	public void setDynamicsSpeed(int lvl, boolean freeze) {
		ChallengeLvlDynamic aux = null;
		if (lvl >= 19)
			aux= levelDynamics[0];
		else
			aux = levelDynamics[lvl];
		this.speed = aux.getSpeed();
		this.timer = aux.getTimer();
		
		if (freeze == true) {
			this.speed += 1.5f;
			this.timer += 0.1f;
			System.out.println("ENTROU NO ITEM!");
		} else {

		}
		this.colorCont = aux.getColorCont();

	}

	public void clearLists() {
		this.yellowBallons.clear();
		this.darkblueBallons.clear();
		this.redBallons.clear();
		this.orangeBallons.clear();
		this.grayBallons.clear();
		this.blackBallons.clear();
		this.pinkBallons.clear();
		this.lightBlueBallons.clear();
		this.greenBallons.clear();
		this.addlifeBallons.clear();
		this.blizzardBallons.clear();
		this.comboBallons.clear();
		this.blasterBallons.clear();
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
		for (int cont = 0; cont < lightBlueBallons.size(); cont++) {
			mScene.unregisterTouchArea(lightBlueBallons.get(cont));
			lightBlueBallons.get(cont).dispose();
		}
		for (int cont = 0; cont < greenBallons.size(); cont++) {
			mScene.unregisterTouchArea(greenBallons.get(cont));
			greenBallons.get(cont).dispose();
		}
		for (int cont = 0; cont < greenBallons.size(); cont++) {
			mScene.unregisterTouchArea(greenBallons.get(cont));
			addlifeBallons.get(cont).dispose();
		}
		for (int cont = 0; cont < greenBallons.size(); cont++) {
			mScene.unregisterTouchArea(greenBallons.get(cont));
			comboBallons.get(cont).dispose();
		}
		for (int cont = 0; cont < greenBallons.size(); cont++) {
			mScene.unregisterTouchArea(greenBallons.get(cont));
			blasterBallons.get(cont).dispose();
		}
		for (int cont = 0; cont < greenBallons.size(); cont++) {
			mScene.unregisterTouchArea(greenBallons.get(cont));
			blizzardBallons.get(cont).dispose();
		}
	}

	public int getEnabledBallons(int color) {

		int indexLista = -1;
		if (color == bType.Yellow) {
			if (yellowBallons.size() != 0) {
				for (int cont = 0; cont < yellowBallons.size(); cont++) {
					if (yellowBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == bType.DarkBlue) {
			if (darkblueBallons.size() != 0) {
				for (int cont = 0; cont < darkblueBallons.size(); cont++) {
					if (darkblueBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == bType.Red) {
			if (redBallons.size() != 0) {
				for (int cont = 0; cont < redBallons.size(); cont++) {
					if (redBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == bType.Orange) {
			if (orangeBallons.size() != 0) {
				for (int cont = 0; cont < orangeBallons.size(); cont++) {
					if (orangeBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == bType.Gray) {
			if (grayBallons.size() != 0) {
				for (int cont = 0; cont < grayBallons.size(); cont++) {
					if (grayBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == bType.Black) {
			if (blackBallons.size() != 0) {
				for (int cont = 0; cont < blackBallons.size(); cont++) {
					if (blackBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == bType.Pink) {
			if (pinkBallons.size() != 0) {
				for (int cont = 0; cont < pinkBallons.size(); cont++) {
					if (pinkBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == bType.LightBlue) {
			if (lightBlueBallons.size() != 0) {
				for (int cont = 0; cont < lightBlueBallons.size(); cont++) {
					if (lightBlueBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}
		} else if (color == bType.Green) {
			if (greenBallons.size() != 0) {
				for (int cont = 0; cont < greenBallons.size(); cont++) {
					if (greenBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}

		} else if (color == bType.Coin) {
			if (coinBalloons.size() != 0) {
				for (int cont = 0; cont < coinBalloons.size(); cont++) {
					if (coinBalloons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}

		} else if (color == bType.Combo) {
			if (comboBallons.size() != 0) {
				for (int cont = 0; cont < comboBallons.size(); cont++) {
					if (comboBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}

		} else if (color == bType.Blizzard) {
			if (blizzardBallons.size() != 0) {
				for (int cont = 0; cont < blizzardBallons.size(); cont++) {
					if (blizzardBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}

		} else if (color == bType.Cloud) {
			if (blasterBallons.size() != 0) {
				for (int cont = 0; cont < blasterBallons.size(); cont++) {
					if (blasterBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}

		} else if (color == bType.AddLife) {
			if (addlifeBallons.size() != 0) {
				for (int cont = 0; cont < addlifeBallons.size(); cont++) {
					if (addlifeBallons.get(cont).getIsInUse() == false) {
						indexLista = cont;
						break;
					}

				}
			}

		}

		return indexLista;
	}

	public void generateMenuBallonsList() {

		if (rightColorsCont >= 1) {
			if (this.rightColors[0] == bType.Yellow) {
				ballonsMenu[0] = yellowBallonMenu;
			} else if (this.rightColors[0] == bType.DarkBlue) {
				ballonsMenu[0] = darkblueBallonMenu;
			} else if (this.rightColors[0] == bType.Red) {
				ballonsMenu[0] = redBallonMenu;
			} else if (this.rightColors[0] == bType.Orange) {
				ballonsMenu[0] = orangeBallonMenu;
			} else if (this.rightColors[0] == bType.Gray) {
				ballonsMenu[0] = grayBallonMenu;
			} else if (this.rightColors[0] == bType.Black) {
				ballonsMenu[0] = blackBallonMenu;
			} else if (this.rightColors[0] == bType.Pink) {
				ballonsMenu[0] = pinkBallonMenu;
			} else if (this.rightColors[0] == bType.LightBlue) {
				ballonsMenu[0] = lightblueBallonMenu;
			} else if (this.rightColors[0] == bType.Green) {
				ballonsMenu[0] = greenBallonMenu;
			} else {
				ballonsMenu[0] = greenBallonMenu;
			}
			if (rightColorsCont >= 2) {
				if (this.rightColors[1] == bType.Yellow) {
					ballonsMenu[1] = yellowBallonMenu;
				} else if (this.rightColors[1] == bType.DarkBlue) {
					ballonsMenu[1] = darkblueBallonMenu;
				} else if (this.rightColors[1] == bType.Red) {
					ballonsMenu[1] = redBallonMenu;
				} else if (this.rightColors[1] == bType.Orange) {
					ballonsMenu[1] = orangeBallonMenu;
				} else if (this.rightColors[1] == bType.Gray) {
					ballonsMenu[1] = grayBallonMenu;
				} else if (this.rightColors[1] == bType.Black) {
					ballonsMenu[1] = blackBallonMenu;
				} else if (this.rightColors[1] == bType.Pink) {
					ballonsMenu[1] = pinkBallonMenu;
				} else if (this.rightColors[1] == bType.LightBlue) {
					ballonsMenu[1] = lightblueBallonMenu;
				} else if (this.rightColors[1] == bType.Green) {
					ballonsMenu[1] = greenBallonMenu;
				}
				if (rightColorsCont == 3) {
					if (this.rightColors[2] == bType.Yellow) {
						ballonsMenu[2] = yellowBallonMenu;
					} else if (this.rightColors[2] == bType.DarkBlue) {
						ballonsMenu[2] = darkblueBallonMenu;
					} else if (this.rightColors[2] == bType.Red) {
						ballonsMenu[2] = redBallonMenu;
					} else if (this.rightColors[2] == bType.Orange) {
						ballonsMenu[2] = orangeBallonMenu;
					} else if (this.rightColors[2] == bType.Gray) {
						ballonsMenu[2] = grayBallonMenu;
					} else if (this.rightColors[2] == bType.Black) {
						ballonsMenu[2] = blackBallonMenu;
					} else if (this.rightColors[2] == bType.Pink) {
						ballonsMenu[2] = pinkBallonMenu;
					} else if (this.rightColors[2] == bType.LightBlue) {
						ballonsMenu[2] = lightblueBallonMenu;
					} else if (this.rightColors[2] == bType.Green) {
						ballonsMenu[2] = greenBallonMenu;
					}
				}
			}
		} else {
			ballonsMenu[0] = greenBallonMenu;
		}
	}

	public void decreaseSpeed() {

		this.speed = baseSpeed + 1.5f;
		System.out.println(speed);
	}

	public void increaseSpeed() {

		this.speed -= 2;
	}


	
	// Getters and Setters (MUITOS)

	public ArrayList<BallonSprite> getCoinBalloons() {
		return coinBalloons;
	}

	public void setCoinBalloons(ArrayList<BallonSprite> coinBalloons) {
		this.coinBalloons = coinBalloons;
	}

	public ArrayList<BallonSprite> getBlasterBallons() {
		return blasterBallons;
	}

	public void setBlasterBallons(ArrayList<BallonSprite> blasterBallons) {
		this.blasterBallons = blasterBallons;
	}

	public ArrayList<BallonSprite> getAddlifeBallons() {
		return addlifeBallons;
	}

	public void setAddlifeBallons(ArrayList<BallonSprite> addlifeBallons) {
		this.addlifeBallons = addlifeBallons;
	}

	public ArrayList<BallonSprite> getBlizzardBallons() {
		return blizzardBallons;
	}

	public void setBlizzardBallons(ArrayList<BallonSprite> blizzardBallons) {
		this.blizzardBallons = blizzardBallons;
	}

	public ArrayList<BallonSprite> getComboBallons() {
		return comboBallons;
	}

	public void setComboBallons(ArrayList<BallonSprite> comboBallons) {
		this.comboBallons = comboBallons;
	}

	public BallonSprite[] getBallonsMenu() {
		return ballonsMenu;
	}

	public void setBallonsMenu(BallonSprite[] ballonsMenu) {
		this.ballonsMenu = ballonsMenu;
	}

	public int[] getRightColors() {
		return rightColors;
	}

	public void setRightColors(int[] rightColors) {
		this.rightColors = rightColors;
	}

	public BallonSprite getYellowBallonMenu() {
		return yellowBallonMenu;
	}

	public void setYellowBallonMenu(BallonSprite yellowBallonMenu) {
		this.yellowBallonMenu = yellowBallonMenu;
	}

	public BallonSprite getDarkBlueBallonMenu() {
		return darkblueBallonMenu;
	}

	public void setDarkBlueBallonMenu(BallonSprite blueBallonMenu) {
		this.darkblueBallonMenu = blueBallonMenu;
	}

	public BallonSprite getRedBallonMenu() {
		return redBallonMenu;
	}

	public void setRedBallonMenu(BallonSprite redBallonMenu) {
		this.redBallonMenu = redBallonMenu;
	}

	public BallonSprite getOrangeBallonMenu() {
		return orangeBallonMenu;
	}

	public void setOrangeBallonMenu(BallonSprite orangeBallonMenu) {
		this.orangeBallonMenu = orangeBallonMenu;
	}

	public BallonSprite getgrayBallonMenu() {
		return grayBallonMenu;
	}

	public void setgrayBallonMenu(BallonSprite grayBallonMenu) {
		this.grayBallonMenu = grayBallonMenu;
	}

	public String getCor1() {
		return cor1;
	}

	public void setCor1(String cor1) {
		this.cor1 = cor1;
	}

	public String getCor2() {
		return cor2;
	}

	public void setCor2(String cor2) {
		this.cor2 = cor2;
	}

	public String getCor3() {
		return cor3;
	}

	public void setCor3(String cor3) {
		this.cor3 = cor3;
	}

	public BallonSprite getLightBlueBallonMenu() {
		return lightblueBallonMenu;
	}

	public void setLightBlueBallonMenu(BallonSprite purpleBallonMenu) {
		this.lightblueBallonMenu = purpleBallonMenu;
	}

	public BallonSprite getPinkBallonMenu() {
		return pinkBallonMenu;
	}

	public void setPinkBallonMenu(BallonSprite pinkBallonMenu) {
		this.pinkBallonMenu = pinkBallonMenu;
	}

	public BallonSprite getBlackBallonMenu() {
		return blackBallonMenu;
	}

	public void setBlackBallonMenu(BallonSprite blackBallonMenu) {
		this.blackBallonMenu = blackBallonMenu;
	}

	public BallonSprite getGreenBallonMenu() {
		return greenBallonMenu;
	}

	public void setGreenBallonMenu(BallonSprite greenBallonMenu) {
		this.greenBallonMenu = greenBallonMenu;
	}

	public void trocarCores(int targetBlocker) {
		cor1 = "";
		cor2 = "";
		cor3 = "";

		while (true) {
			if (getLastColor() == this.rightColors[0]) {
				if (targetBlocker == 0)
					setRightColors(0,(int) (Math.random() * getColorCont() + 1));
				else 
					break;
			} else {
				break;
			}
		}
		lastColor = (this.rightColors[0]);
		if (this.rightColorsCont >= 2) {
			if (targetBlocker == 0 || rightColors[1] == 0) {
				rightColors[1] = ((int) Math.random() * getColorCont() + 1);
				while (true) {

					if (this.rightColors[0] == this.rightColors[1]) {
						setRightColors(1,
								(int) (Math.random() * getColorCont() + 1));
					} else {
						break;
					}
				}
			}
		}
		if (this.rightColorsCont == 3) {
			if (targetBlocker == 0 || rightColors[2] == 0) {
				rightColors[2] = (int) (Math.random() * getColorCont() + 1);
				while (true) {
					if (this.rightColors[0] == this.rightColors[2]
							|| this.rightColors[1] == this.rightColors[2]) {
						setRightColors(2,
								(int) (Math.random() * getColorCont() + 1));
					} else {
						break;
					}
				}
			}
		}
	}

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
		return lightBlueBallons;
	}

	public void setLightBlueBallons(ArrayList<BallonSprite> purpleBallons) {

		this.lightBlueBallons = purpleBallons;
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

	public int getRightColors(int position) {
		if (position == 0) {
			return this.rightColors[0];
		} else if (position == 1) {
			return this.rightColors[1];
		} else if (position == 2) {
			return this.rightColors[2];
		} else {
			return -1;
		}
	}

	public void setRightColors(int position, int color) {

		if (position == 0) {
			this.rightColors[0] = color;
		} else if (position == 1) {
			this.rightColors[1] = color;
		} else if (position == 2) {
			this.rightColors[2] = color;
		} else {
			this.rightColors = new int[] { 0, 0, 0 };
		}
	}

	public int getColorCont() {
		return colorCont;
	}

	public void setColorCont(int colorCont) {

		this.colorCont = colorCont;
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
		return this.speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
		this.baseSpeed = speed;
	}

	public float getTimer() {
		return timer;
	}

	public void setTimer(float timer) {
		this.timer = timer;
	}

	public int getFreezeCont() {
		return freezeCont;
	}

	public void setFreezeCont(int freezeCont) {
		this.freezeCont = freezeCont;
	}

}
