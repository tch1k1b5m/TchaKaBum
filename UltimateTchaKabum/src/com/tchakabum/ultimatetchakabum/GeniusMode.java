package com.tchakabum.ultimatetchakabum;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;

import Controller.GeniusBalloonController;
import Controller.GeniusGameController;
import Extension.PausableTimerHandler;
import Model.BallonSprite;
import Model.BalloonType;
import SharedPreferences.SharedPreferencesController;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.widget.Toast;

public class GeniusMode extends SimpleBaseGameActivity implements
		IOnSceneTouchListener, IOnAreaTouchListener, ITimerCallback,
		IOnMenuItemClickListener {

	private Camera camera;
	private int CAMERA_WIDTH = 480;
	private int CAMERA_HEIGHT = 800;
	protected static final int MENU_RESUME = 0;
	protected static final int MENU_QUIT = MENU_RESUME + 1;
	protected static final int MENU_RESTART = 3;
	private BitmapTextureAtlas balloonsTextureAtlas;

	private BalloonType bType = new BalloonType();

	private TiledTextureRegion darkblueBallonTextureRegion;
	private TiledTextureRegion yellowBallonTextureRegion;
	private TiledTextureRegion lightblueBallonTextureRegion;
	private TiledTextureRegion orangeBallonTextureRegion;
	private TiledTextureRegion blackBallonTextureRegion;
	private TiledTextureRegion pinkBallonTextureRegion;
	private TiledTextureRegion greenBallonTextureRegion;
	private TiledTextureRegion grayBallonTextureRegion;
	private TiledTextureRegion redBallonTextureRegion;

	private boolean needToPause = false;

	private ITextureRegion barraTextureRegion;// alterar
	private BuildableBitmapTextureAtlas barraTextureAtlas;// alterar
	private boolean enablePause = true;

	private Text popupEstoureText;// alterar
	private Scene mScene;
	private int mCorBalao;// alterar
	private TextureRegion mTextureRegion;

	private int[] xPositions = new int[7];
	private int x;
	private int lastxIndex = 0;

	private HUD gameHUD;

	private Font font;// alterar
	private Font fontSize64;// alterar

	private ITextureRegion pauseTextureRegion;
	private BuildableBitmapTextureAtlas pauseTextureAtlas;
	protected MenuScene mMenuScene;
	private BuildableBitmapTextureAtlas mMenuTextureAtlas;
	private ITextureRegion mMenuResumeTextureRegion;
	private ITextureRegion mMenuQuitTextureRegion;

	private int timeToLock = 1000;

	private float indResolution = 1.0f;

	private BuildableBitmapTextureAtlas backgroundTextureAtlas;
	private ITextureRegion backgroundTextureRegion;
	private Sprite bottomBar;

	private ButtonSprite sRestartGame;
	private BuildableBitmapTextureAtlas restartGameTextureAtlas;
	private ITextureRegion restartGameTextureRegion;
	private Text gameOverText;
	private Text highLevel;
	private boolean hasLostTheGame = false;

	private float timer = 0.8f;
	final private PausableTimerHandler timerHandler = new PausableTimerHandler(
			timer, true, this);
	private GeniusBalloonController balloonController = new GeniusBalloonController();
	private GeniusGameController gmController = new GeniusGameController();

	private boolean isInPopup = false;
	private boolean lastPopupWasColor = false;

	private SharedPreferencesController sPreferences = new SharedPreferencesController();
	SharedPreferences sharedPreferences;

	@Override
	public EngineOptions onCreateEngineOptions() {
		System.gc();
		this.sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		this.indResolution = loadResolution();
		this.xPositions[0] = Math.round(45 * indResolution);
		this.xPositions[1] = Math.round(113 * indResolution);
		this.xPositions[2] = Math.round(181 * indResolution);
		this.xPositions[3] = Math.round(249 * indResolution);
		this.xPositions[4] = Math.round(317 * indResolution);
		this.xPositions[5] = Math.round(385 * indResolution);
		this.xPositions[6] = Math.round(453 * indResolution);

		mScene = null;
		this.CAMERA_WIDTH *= indResolution;
		this.CAMERA_HEIGHT *= indResolution;
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineoptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineoptions.getTouchOptions().setNeedsMultiTouch(true);
		return engineoptions;
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.barraTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(512 * indResolution),
				Math.round(1024 * indResolution), TextureOptions.DEFAULT);
		this.barraTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(barraTextureAtlas, this, "Segunda Camada.png");

		// Balloons
		// ----------------------------------------
		this.balloonsTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 1093, 1350, TextureOptions.BILINEAR);
		this.darkblueBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Azul-Escuro.png", 0, 0, 10, 1);
		this.yellowBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Amarelo.png", 0, Math.round(93*indResolution), 10, 1);
		this.orangeBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Laranja.png", 0, Math.round(186*indResolution), 10, 1);
		this.grayBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Cinza.png", 0, Math.round(279*indResolution), 10, 1);
		this.pinkBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Rosa.png", 0, Math.round(372*indResolution), 10, 1);
		this.greenBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Verde.png", 0, Math.round(465*indResolution), 10, 1);
		this.lightblueBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Azul-Claro.png", 0, Math.round(558*indResolution), 10, 1);
		this.blackBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Preto.png", 0, Math.round(651*indResolution), 10, 1);
		this.redBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Vermelho.png", 0, Math.round(744*indResolution), 10, 1);

		// End Balloons
		// ----------------------------------------

		this.pauseTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(63 * indResolution),
				Math.round(63 * indResolution), TextureOptions.DEFAULT);
		this.pauseTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(pauseTextureAtlas, this, "Pause.png");
		this.balloonsTextureAtlas.load();

		this.mMenuTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(1024 * indResolution),
				Math.round(1024 * indResolution), TextureOptions.DEFAULT);
		this.backgroundTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(512 * indResolution),
				Math.round(1024 * indResolution), TextureOptions.DEFAULT);
		this.mMenuResumeTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mMenuTextureAtlas, this, "btnResume.png");
		this.mMenuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mMenuTextureAtlas, this, "btnQuit.png");

		this.restartGameTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 301, 170, TextureOptions.DEFAULT);
		this.restartGameTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(restartGameTextureAtlas, this,
						"btnPlayAgain.png");
		this.backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(backgroundTextureAtlas, this,
						"Primeira Camada.png");
		try {
			this.pauseTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.barraTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.barraTextureAtlas.load();
			this.pauseTextureAtlas.load();

			this.mMenuTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.mMenuTextureAtlas.load();

			this.restartGameTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.restartGameTextureAtlas.load();

			this.backgroundTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.backgroundTextureAtlas.load();

		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		this.balloonsTextureAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {
		this.mScene = new Scene();
		this.sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				backgroundTextureRegion, this.getVertexBufferObjectManager())));
		this.mScene.setOnSceneTouchListener(this);
		if (sPreferences.loadCoinsPreferences(sharedPreferences) == "") {
			gmController.setCoins(0);
			sPreferences.saveCoinsPreferences("coins", 0, sharedPreferences);
		} else {
			gmController.setCoins(Integer.parseInt(sPreferences
					.loadCoinsPreferences(sharedPreferences)));
		}
		if (sPreferences.loadHighLevelPreferences(sharedPreferences) == 0) {
			sPreferences.saveHighLevelPreferences(0, sharedPreferences);
		}
		createHUD();
		createMenuScene();
		mScene.registerUpdateHandler(timerHandler);
		this.mScene.setOnAreaTouchListener(this);
		return this.mScene;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}

	// -----------------------------------------------
	// Event Handler
	// -----------------------------------------------

	@Override
	public void onTimePassed(TimerHandler pTimerHandler) {
		if (needToPause) {
			blockGame(timeToLock);

			removePopupScene();
		}
		if (gmController.getLvl() > gmController.getPreviousLvl()) {
			gmController.increasePrevLvl();
			balloonController.lvlPassed();
			clearAllBallons();
			this.needToPause = true;
			popupScene();
		}
		if (!needToPause) {
			setGameDynamics();
		}

	}

	// -----------------------------------------------
	// Events Touch
	// -----------------------------------------------

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown()) {
			this.removeBalloonSprite((BallonSprite) pTouchArea, true);
			return true;
		}

		return false;
	}

	// -----------------------------------------------
	// Ballons
	// -----------------------------------------------

	private void clearAllBallons() {
		System.gc();
		for (int cont = 0; cont < balloonController.getYellowBallons().size(); cont++) {
			balloonController.getYellowBallons().get(cont).setIsInUse(false);
			balloonController.getYellowBallons().get(cont)
					.clearEntityModifiers();
			balloonController.getYellowBallons().get(cont)
					.clearUpdateHandlers();
			mScene.unregisterTouchArea(balloonController.getYellowBallons()
					.get(cont));
			balloonController.getYellowBallons().get(cont).detachSelf();
		}
		for (int cont = 0; cont < balloonController.getDarkBlueBallons().size(); cont++) {
			balloonController.getDarkBlueBallons().get(cont).setIsInUse(false);
			balloonController.getDarkBlueBallons().get(cont)
					.clearEntityModifiers();
			balloonController.getDarkBlueBallons().get(cont)
					.clearUpdateHandlers();
			mScene.unregisterTouchArea(balloonController.getDarkBlueBallons()
					.get(cont));
			balloonController.getDarkBlueBallons().get(cont).detachSelf();
		}
		for (int cont = 0; cont < balloonController.getRedBallons().size(); cont++) {
			balloonController.getRedBallons().get(cont).setIsInUse(false);
			balloonController.getRedBallons().get(cont).clearEntityModifiers();
			balloonController.getRedBallons().get(cont).clearUpdateHandlers();
			mScene.unregisterTouchArea(balloonController.getRedBallons().get(
					cont));
			balloonController.getRedBallons().get(cont).detachSelf();
		}

		for (int cont = 0; cont < balloonController.getOrangeBallons().size(); cont++) {
			balloonController.getOrangeBallons().get(cont).setIsInUse(false);
			balloonController.getOrangeBallons().get(cont)
					.clearEntityModifiers();
			balloonController.getOrangeBallons().get(cont)
					.clearUpdateHandlers();
			mScene.unregisterTouchArea(balloonController.getOrangeBallons()
					.get(cont));
			balloonController.getOrangeBallons().get(cont).detachSelf();

		}
		for (int cont = 0; cont < balloonController.getgrayBallons().size(); cont++) {
			balloonController.getgrayBallons().get(cont).setIsInUse(false);
			balloonController.getgrayBallons().get(cont).clearEntityModifiers();
			balloonController.getgrayBallons().get(cont).clearUpdateHandlers();
			mScene.unregisterTouchArea(balloonController.getgrayBallons().get(
					cont));
			balloonController.getGrayBallons().get(cont).detachSelf();

		}
		for (int cont = 0; cont < balloonController.getBlackBallons().size(); cont++) {
			balloonController.getBlackBallons().get(cont).setIsInUse(false);
			balloonController.getBlackBallons().get(cont)
					.clearEntityModifiers();
			balloonController.getBlackBallons().get(cont).clearUpdateHandlers();
			mScene.unregisterTouchArea(balloonController.getBlackBallons().get(
					cont));
			balloonController.getBlackBallons().get(cont).detachSelf();

		}
		for (int cont = 0; cont < balloonController.getPinkBallons().size(); cont++) {
			balloonController.getPinkBallons().get(cont).setIsInUse(false);
			balloonController.getPinkBallons().get(cont).clearEntityModifiers();
			balloonController.getPinkBallons().get(cont).clearUpdateHandlers();
			mScene.unregisterTouchArea(balloonController.getPinkBallons().get(
					cont));
			balloonController.getPinkBallons().get(cont).detachSelf();

		}
		for (int cont = 0; cont < balloonController.getLightBlueBallons()
				.size(); cont++) {
			balloonController.getLightBlueBallons().get(cont).setIsInUse(false);
			balloonController.getLightBlueBallons().get(cont)
					.clearEntityModifiers();
			balloonController.getLightBlueBallons().get(cont)
					.clearUpdateHandlers();
			mScene.unregisterTouchArea(balloonController.getLightBlueBallons()
					.get(cont));
			balloonController.getLightBlueBallons().get(cont).detachSelf();

		}
		for (int cont = 0; cont < balloonController.getGreenBallons().size(); cont++) {
			balloonController.getGreenBallons().get(cont).setIsInUse(false);
			balloonController.getGreenBallons().get(cont)
					.clearEntityModifiers();
			balloonController.getGreenBallons().get(cont).clearUpdateHandlers();
			mScene.unregisterTouchArea(balloonController.getGreenBallons().get(
					cont));
			balloonController.getGreenBallons().get(cont).detachSelf();

		}
	}

	final private void detachBalloon(BallonSprite sBalao) {
		if (sBalao.GetBalloonColor() == bType.Yellow) {
			for (int cont = 0; cont < balloonController.getYellowBallons()
					.size(); cont++) {
				if (balloonController.getYellowBallons().get(cont) == sBalao) {
					balloonController.getYellowBallons().get(cont)
							.setIsInUse(false);
					balloonController.getYellowBallons().get(cont)
							.clearEntityModifiers();
					balloonController.getYellowBallons().get(cont)
							.clearUpdateHandlers();
					mScene.unregisterTouchArea(balloonController
							.getYellowBallons().get(cont));
					mScene.detachChild(balloonController.getYellowBallons()
							.get(cont));
					break;
				}
			}
		} else if (sBalao.GetBalloonColor() == bType.Red) {
			for (int cont = 0; cont < balloonController.getRedBallons().size(); cont++) {
				if (balloonController.getRedBallons().get(cont) == sBalao) {
					balloonController.getRedBallons().get(cont)
							.setIsInUse(false);
					balloonController.getRedBallons().get(cont)
							.clearEntityModifiers();
					balloonController.getRedBallons().get(cont)
							.clearUpdateHandlers();
					mScene.unregisterTouchArea(balloonController
							.getRedBallons().get(cont));
					mScene.detachChild(balloonController.getRedBallons().get(
							cont));
					break;
				}
			}
		} else if (sBalao.GetBalloonColor() == bType.DarkBlue) {
			for (int cont = 0; cont < balloonController.getDarkBlueBallons()
					.size(); cont++) {
				if (balloonController.getDarkBlueBallons().get(cont) == sBalao) {
					balloonController.getDarkBlueBallons().get(cont)
							.setIsInUse(false);
					balloonController.getDarkBlueBallons().get(cont)
							.clearEntityModifiers();
					balloonController.getDarkBlueBallons().get(cont)
							.clearUpdateHandlers();
					mScene.unregisterTouchArea(balloonController
							.getDarkBlueBallons().get(cont));
					mScene.detachChild(balloonController.getDarkBlueBallons()
							.get(cont));
					break;
				}
			}
		} else if (sBalao.GetBalloonColor() == bType.Orange) {
			for (int cont = 0; cont < balloonController.getOrangeBallons()
					.size(); cont++) {
				if (balloonController.getOrangeBallons().get(cont) == sBalao) {
					balloonController.getOrangeBallons().get(cont)
							.setIsInUse(false);
					balloonController.getOrangeBallons().get(cont)
							.clearEntityModifiers();
					balloonController.getOrangeBallons().get(cont)
							.clearUpdateHandlers();
					mScene.unregisterTouchArea(balloonController
							.getOrangeBallons().get(cont));
					mScene.detachChild(balloonController.getOrangeBallons()
							.get(cont));
					break;
				}
			}
		} else if (sBalao.GetBalloonColor() == bType.Gray) {
			for (int cont = 0; cont < balloonController.getgrayBallons().size(); cont++) {
				if (balloonController.getgrayBallons().get(cont) == sBalao) {
					balloonController.getgrayBallons().get(cont)
							.setIsInUse(false);
					balloonController.getgrayBallons().get(cont)
							.clearEntityModifiers();
					balloonController.getgrayBallons().get(cont)
							.clearUpdateHandlers();
					mScene.unregisterTouchArea(balloonController
							.getgrayBallons().get(cont));
					mScene.detachChild(balloonController.getgrayBallons().get(
							cont));
					break;
				}
			}
		} else if (sBalao.GetBalloonColor() == bType.Black) {
			for (int cont = 0; cont < balloonController.getBlackBallons()
					.size(); cont++) {
				if (balloonController.getBlackBallons().get(cont) == sBalao) {
					balloonController.getBlackBallons().get(cont)
							.setIsInUse(false);
					balloonController.getBlackBallons().get(cont)
							.clearEntityModifiers();
					balloonController.getBlackBallons().get(cont)
							.clearUpdateHandlers();
					mScene.unregisterTouchArea(balloonController
							.getBlackBallons().get(cont));
					mScene.detachChild(balloonController.getBlackBallons().get(
							cont));
					break;
				}
			}
		} else if (sBalao.GetBalloonColor() == bType.Pink) {
			for (int cont = 0; cont < balloonController.getPinkBallons().size(); cont++) {
				if (balloonController.getPinkBallons().get(cont) == sBalao) {
					balloonController.getPinkBallons().get(cont)
							.setIsInUse(false);
					balloonController.getPinkBallons().get(cont)
							.clearEntityModifiers();
					balloonController.getPinkBallons().get(cont)
							.clearUpdateHandlers();
					mScene.unregisterTouchArea(balloonController
							.getPinkBallons().get(cont));
					mScene.detachChild(balloonController.getPinkBallons().get(
							cont));
					break;
				}
			}
		} else if (sBalao.GetBalloonColor() == bType.LightBlue) {
			for (int cont = 0; cont < balloonController.getLightBlueBallons()
					.size(); cont++) {
				if (balloonController.getLightBlueBallons().get(cont) == sBalao) {
					balloonController.getLightBlueBallons().get(cont)
							.setIsInUse(false);
					balloonController.getLightBlueBallons().get(cont)
							.clearEntityModifiers();
					balloonController.getLightBlueBallons().get(cont)
							.clearUpdateHandlers();
					mScene.unregisterTouchArea(balloonController
							.getLightBlueBallons().get(cont));
					mScene.detachChild(balloonController.getLightBlueBallons()
							.get(cont));
					break;
				}
			}
		} else if (sBalao.GetBalloonColor() == bType.Green) {
			for (int cont = 0; cont < balloonController.getGreenBallons()
					.size(); cont++) {
				if (balloonController.getGreenBallons().get(cont) == sBalao) {
					balloonController.getGreenBallons().get(cont)
							.setIsInUse(false);
					balloonController.getGreenBallons().get(cont)
							.clearEntityModifiers();
					balloonController.getGreenBallons().get(cont)
							.clearUpdateHandlers();
					mScene.unregisterTouchArea(balloonController
							.getGreenBallons().get(cont));
					mScene.detachChild(balloonController.getGreenBallons().get(
							cont));
					break;
				}
			}
		}

	}

	private void removeBalloonSprite(final BallonSprite balloonSprite,
			boolean isScoring) {
		if (isScoring) {
			if (!hasLostTheGame) {
				if (balloonController.rightColorBurst(balloonSprite
						.GetBalloonColor())) {
					if (balloonController.hasPassedLvl()) {
						gmController.increaseLvl();
					}
				} else {
					hasLostTheGame = true;
					createGameOverScene();
					timerHandler.pause();
					clearAllBallons();
				}
			}
		}
		detachBalloon(balloonSprite);
		
	}

	private BallonSprite getBallonSprite(int color) {
		int pX = 0;
		int pY = 0;

		int indexBalaoDisponivel = balloonController
				.getEnabledBallons(mCorBalao);
		BallonSprite balaoAux;
		if (indexBalaoDisponivel == -1) {
			final BallonSprite sBalloon;
			if (color == bType.Yellow) {
				sBalloon = new BallonSprite(pX, pY,
						this.yellowBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Yellow);
			} else if (color == bType.DarkBlue) {
				sBalloon = new BallonSprite(pX, pY,
						this.darkblueBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.DarkBlue);
			} else if (color == bType.Red) {
				sBalloon = new BallonSprite(pX, pY,
						this.redBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Red);
			} else if (color == bType.Orange) {
				sBalloon = new BallonSprite(pX, pY,
						this.orangeBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Orange);
			} else if (color == bType.Gray) {
				sBalloon = new BallonSprite(pX, pY,
						this.grayBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Gray);
			} else if (color == bType.Black) {
				sBalloon = new BallonSprite(pX, pY,
						this.blackBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Black);
			} else if (color == bType.Pink) {
				sBalloon = new BallonSprite(pX, pY,
						this.pinkBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Pink);
			} else if (color == bType.LightBlue) {
				sBalloon = new BallonSprite(pX, pY,
						this.lightblueBallonTextureRegion,
						this.getVertexBufferObjectManager(), 8);
			} else {
				sBalloon = new BallonSprite(pX, pY,
						this.greenBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Green);
			}

			balaoAux = sBalloon;

			if (color == bType.Yellow) {
				balloonController.getYellowBallons().add(sBalloon);
			} else if (color == bType.DarkBlue) {
				balloonController.getDarkBlueBallons().add(sBalloon);
			} else if (color == bType.Red) {
				balloonController.getRedBallons().add(sBalloon);
			} else if (color == bType.Orange) {
				balloonController.getOrangeBallons().add(sBalloon);
			} else if (color == bType.Gray) {
				balloonController.getgrayBallons().add(sBalloon);
			} else if (color == bType.Black) {
				balloonController.getBlackBallons().add(sBalloon);
			} else if (color == bType.Pink) {
				balloonController.getPinkBallons().add(sBalloon);
			} else if (color == bType.LightBlue) {
				balloonController.getLightBlueBallons().add(sBalloon);
			} else if (color == bType.Green) {
				balloonController.getGreenBallons().add(sBalloon);
			}

		} else {

			if (color == bType.Yellow) {
				balaoAux = balloonController.getYellowBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.DarkBlue) {
				balaoAux = balloonController.getDarkBlueBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Red) {
				balaoAux = balloonController.getRedBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Orange) {
				balaoAux = balloonController.getOrangeBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Gray) {
				balaoAux = balloonController.getgrayBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Black) {
				balaoAux = balloonController.getBlackBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Pink) {
				balaoAux = balloonController.getPinkBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.LightBlue) {
				balaoAux = balloonController.getLightBlueBallons().get(
						indexBalaoDisponivel);
			} else {
				balaoAux = balloonController.getGreenBallons().get(
						indexBalaoDisponivel);
			}
		}
		return balaoAux;
	}

	private void addBalloonSprite(final float pX, final float pY) {
		this.mCorBalao = (int) (Math.random() * 6 + 1);

		final BallonSprite sBalloon = getBallonSprite(mCorBalao);

		sBalloon.setIsInUse(true);
		sBalloon.animate(200, true);
		sBalloon.registerEntityModifier(new MoveModifier(balloonController
				.getSpeed(), (pX - 64), (pX - 64), CAMERA_HEIGHT, -128,
				EaseLinear.getInstance()));

		sBalloon.registerEntityModifier(new DelayModifier(balloonController
				.getSpeed(), new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier,
					IEntity pItem) {
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {

				runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						detachBalloon(sBalloon);

					}
				});

			}
		}));

		this.mScene.registerTouchArea(sBalloon);
		this.mScene.attachChild(sBalloon);

	}

	// -----------------------------------------------
	// Scenes
	// -----------------------------------------------

	private void createGameOverScene() {
		hasLostTheGame = true;

		gameOverText = new Text(85, 250, fontSize64, "Game Over",
				new TextOptions(HorizontalAlign.LEFT),
				this.getVertexBufferObjectManager());
		gameOverText.setText("Game Over");
		int maxLevel = sPreferences.loadHighLevelPreferences(sharedPreferences);
		if (gmController.getPreviousLvl() > maxLevel) {
			sPreferences.saveHighLevelPreferences(
					gmController.getPreviousLvl(), sharedPreferences);
			highLevel = new Text((CAMERA_WIDTH / 2 - 110), 390, font,
					"New High Level", new TextOptions(HorizontalAlign.LEFT),
					this.getVertexBufferObjectManager());
		} else {
			highLevel = new Text((CAMERA_WIDTH / 2 - 110), 390, font,
					"High Level: " + maxLevel, new TextOptions(
							HorizontalAlign.LEFT),
					this.getVertexBufferObjectManager());
		}
		gameOverText.setText("Game Over");
		gameHUD.attachChild(highLevel);
		gameHUD.attachChild(gameOverText);
		sPreferences.saveCoinsPreferences("coins", gmController.getCoins(),
				sharedPreferences);
		sRestartGame = new ButtonSprite(420, 0, restartGameTextureRegion,
				this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent touchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (touchEvent.isActionDown()) {
					removeGameOverScene();
					if (gameOverText.hasParent())
						gameOverText.detachSelf();
					if (sRestartGame.hasParent())
						sRestartGame.detachSelf();
					if (highLevel.hasParent()) {
						highLevel.detachSelf();
					}
					hasLostTheGame = false;
					clearAllBallons();
					mScene.unregisterTouchArea(sRestartGame);
				}
				return true;
			}

		};
		mScene.registerTouchArea(sRestartGame);
		sRestartGame.setPosition(
				(CAMERA_WIDTH / 2 - sRestartGame.getWidth() / 2), 500);
		gameHUD.attachChild(sRestartGame);

	}

	private void removeGameOverScene() {
		balloonController.resetBalloonController(this.mScene);
		System.gc();
		System.out.println("reset");
		gmController.resetGame();

		timerHandler.resume();
		gameOverText.detachSelf();
		sRestartGame.detachSelf();
	}

	private void popupScene() {
		enablePause = false;
		if (isInPopup == false) {
			popupEstoureText = new Text(120, 250, fontSize64, "Estoure:",
					new TextOptions(HorizontalAlign.LEFT),
					this.getVertexBufferObjectManager());
			popupEstoureText.setText("Level " + gmController.getLvl() + ":");
			popupEstoureText.setPosition(
					CAMERA_WIDTH / 2 - (popupEstoureText.getWidth() / 2),
					(CAMERA_HEIGHT / 2 - (popupEstoureText.getHeight() / 2) - 150));
			gameHUD.attachChild(popupEstoureText);
		}
		int indexPopupBalloon = balloonController.getGeniusColorIndexPopup();

		// Se Já tiverem passado todas as cores do geniu
		if ((indexPopupBalloon) >= balloonController.getGeniusColors().size()) {
			isInPopup = false;
			balloonController.resetGeniusColorIndexPopup();
		} else {
			isInPopup = true;
			clearAllBallons();
			if (lastPopupWasColor) {
				timeToLock = 500;
				lastPopupWasColor = false;
			} else {
				BallonSprite sballoon = getBallonSprite(balloonController
						.getGeniusColors().get(indexPopupBalloon));
				sballoon.setPosition((xPositions[3] - sballoon.getWidth()/2),
						CAMERA_HEIGHT / 2);
				sballoon.setIsInUse(true);
				gameHUD.attachChild(sballoon);
				lastPopupWasColor = true;
				timeToLock = 800;
				balloonController.increaseGeniusColorIndexPopup();
			}
		}
	}

	private void removePopupScene() {
		if (!isInPopup) {
			this.gameHUD.detachChild(popupEstoureText);
			clearAllBallons();
			lastPopupWasColor = false;
			needToPause = false;
			balloonController.setGeniusColorIndexPopup(0);
			enablePause = true;
		} else {
			popupScene();
		}

	}

	// -----------------------------------------------
	// Game Control
	// -----------------------------------------------

	private void setGameDynamics() {
		timer = 0.8f;
		balloonController.setSpeed(3.0f);
		x = xPositions[(int) (Math.random() * 7)];
		while (true) {
			if (x == lastxIndex) {
				x = xPositions[(int) (Math.random() * 7)];
			} else {
				break;
			}
		}
		balloonController.setPenultPos(lastxIndex);
		lastxIndex = x;
		addBalloonSprite(x, 0);
		x = xPositions[(int) (Math.random() * 7)];
		while (true) {
			if (x == lastxIndex || x == balloonController.getPenultPos()) {
				x = xPositions[(int) (Math.random() * 7)];
			} else {
				break;
			}
		}
		balloonController.setPenultPos(lastxIndex);
		lastxIndex = x;
		addBalloonSprite(x, 0);

	}

	// -----------------------------------------------
	// Game Engine
	// -----------------------------------------------

	private void blockGame(int time) {
		try {
			mEngine.stop();
			Thread.sleep(time);
			mEngine.start();
		} catch (Exception e) {
		}
	}

	@Override
	public void onBackPressed() {
		if (hasLostTheGame) {
			startActivity(new Intent(GeniusMode.this, MainActivity.class));
			GeniusMode.this.finish();
		} else if (!mScene.hasChildScene() && !isInPopup && enablePause) {
			MenuScene menuScene = createMenuScene();
			mScene.setChildScene(menuScene, false, true, true);
			menuScene.buildAnimations();
			menuScene.setBackgroundEnabled(false);
		}

	}

	// -----------------------------------------------
	// Menu Pause
	// -----------------------------------------------

	private void addPause() {

		final ButtonSprite btPause = new ButtonSprite(
				Math.round(410 * indResolution),
				(Math.round(730 * indResolution)), pauseTextureRegion,
				this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pTouchEvent.isActionUp()) {
						MenuScene menuScene = createMenuScene();
						mScene.setChildScene(menuScene, false, true, true);
						menuScene.buildAnimations();
						menuScene.setBackgroundEnabled(false);
						gameHUD.setIgnoreUpdate(true);
					

				}
				return true;
			}

		};

		gameHUD.registerTouchArea(btPause);
		gameHUD.attachChild(btPause);

	}

	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_RESUME:
			this.mScene.clearChildScene();
			gameHUD.setIgnoreUpdate(false);
			return true;
		case MENU_QUIT:
			startActivity(new Intent(GeniusMode.this, MainActivity.class));
			GeniusMode.this.finish();
			return true;
		case MENU_RESTART:
			balloonController.resetBalloonController(this.mScene);
			System.gc();
			System.out.println("reset");
			gmController.resetGame();

			timerHandler.resume();
			clearAllBallons();
			return true;
		default:
			return false;
		}

	}

	private MenuScene createMenuScene() {
		if (!isInPopup) {
			this.mMenuScene = new MenuScene(camera);
			this.mMenuScene.setBackground(new Background(0, 420, 255));

			final SpriteMenuItem resetMenuItem = new SpriteMenuItem(
					MENU_RESUME, this.mMenuResumeTextureRegion,
					this.getVertexBufferObjectManager());
			resetMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
					GLES20.GL_ONE_MINUS_SRC_ALPHA);
			this.mMenuScene.addMenuItem(resetMenuItem);

			final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT,
					this.mMenuQuitTextureRegion,
					this.getVertexBufferObjectManager());
			quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
					GLES20.GL_ONE_MINUS_SRC_ALPHA);
			this.mMenuScene.addMenuItem(quitMenuItem);

			final SpriteMenuItem restartMenuItem = new SpriteMenuItem(
					MENU_RESTART, this.restartGameTextureRegion,
					this.getVertexBufferObjectManager());
			quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
					GLES20.GL_ONE_MINUS_SRC_ALPHA);

			this.mMenuScene.addMenuItem(restartMenuItem);
			this.mMenuScene.buildAnimations();

			this.mMenuScene.setBackgroundEnabled(false);

			this.mMenuScene.setOnMenuItemClickListener(this);
		}
		return mMenuScene;

	}

	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if (pKeyCode == KeyEvent.KEYCODE_MENU
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if (this.mScene.hasChildScene()) {
				/* Remove the menu and reset it. */
				this.mMenuScene.back();
			} else {
				/* Attach the menu. */
				this.mScene.setChildScene(this.mMenuScene, false, true, true);
			}
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}

	// -----------------------------------------------
	// HUD
	// -----------------------------------------------

	private void createHUD() {
		gameHUD = new HUD();
		this.font = FontFactory.create(this.getFontManager(),
				this.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.font.load();
		this.fontSize64 = FontFactory.create(this.getFontManager(),
				this.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 64);
		this.fontSize64.load();
		// CREATE SCORE TEXT

		bottomBar = new Sprite(480, 90, barraTextureRegion,
				this.getVertexBufferObjectManager());

		bottomBar.setPosition(0, 0);

		gameHUD.attachChild(bottomBar);
		addPause();
		camera.setHUD(gameHUD);
		
	}

	// Resolution
	private float loadResolution() {
		float value = 1.6f;
		return value;
	}

	private float loadFontSize() {
		float value = 72.0f;
		return value;
	}

}