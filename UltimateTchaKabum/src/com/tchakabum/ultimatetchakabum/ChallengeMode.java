package com.tchakabum.ultimatetchakabum;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ScaleModifier;
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
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;

import Controller.ChallengeBalloonController;
import Controller.ChallengeGameController;
import Extension.PausableTimerHandler;
import Model.*;
import SAX.SAXParser;
import SharedPreferences.SharedPreferencesController;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.animation.ScaleAnimation;

public class ChallengeMode extends SimpleBaseGameActivity implements
		IOnSceneTouchListener, IOnAreaTouchListener, ITimerCallback,
		IOnMenuItemClickListener {

	// -----------------------------------------------
	// Variables
	// -----------------------------------------------

	// Screen
	// -----------------------------------------------

	private Camera camera;
	private int CAMERA_WIDTH = 480;
	private int CAMERA_HEIGHT = 800;

	private Scene mScene;

	// Balloons
	// -----------------------------------------------

	private BitmapTextureAtlas balloonsTextureAtlas;
	private BitmapTextureAtlas specialBalloonsTextureAtlas;

	private TiledTextureRegion darkblueBallonTextureRegion;
	private TiledTextureRegion yellowBallonTextureRegion;
	private TiledTextureRegion lightblueBallonTextureRegion;
	private TiledTextureRegion orangeBallonTextureRegion;
	private TiledTextureRegion blackBallonTextureRegion;
	private TiledTextureRegion pinkBallonTextureRegion;
	private TiledTextureRegion greenBallonTextureRegion;
	private TiledTextureRegion grayBallonTextureRegion;
	private TiledTextureRegion redBallonTextureRegion;

	private TiledTextureRegion comboBallonTextureRegion;
	private TiledTextureRegion addlifeBallonTextureRegion;
	private TiledTextureRegion cloudBalloonTextureRegion;
	private TiledTextureRegion blizzardBallonTextureRegion;
	private TiledTextureRegion coinBallonTextureRegion;

	private int mCorBalao;
	private int[] xPositions = new int[7];
	private int balloonPosition;
	private int lastxIndex = 0;
	private BalloonType bType = new BalloonType();
	private long[] animationDuration = { 200, 200, 200, 200, 200, 200, 200, 200 };
	private long[] popDuration = { 50, 50 };

	// Itens
	// -----------------------------------------------
	
	private boolean gameIsPaused = false;
	private boolean needToPause = false;
	private boolean doubleCombo = false;
	private boolean dComboIsOver = false;
	private boolean lifePowerIsOver = false;
	private boolean blasterOver = false;
	private boolean freezeOn = false;
	private boolean cloudblockRunning = false;
	private boolean colorreminderUsed = false;
	private int targetBlocker = 0;
	private boolean wrongDoesntMatter = false;

	private int xItemOne = 90;
	private int xItemTwo = 160;
	private int xItemThree = 230;
	private int yItem = 740;

	private float indResolution = 1.0f;

	private ButtonSprite btDoubleCombo;
	private ButtonSprite btBlaster;
	private ButtonSprite btLifePower;
	private ButtonSprite btFreeze;

	private BitmapTextureAtlas leftCloudBlockTextureAtlas;
	private ITextureRegion leftCloudBlockTexture;
	private BitmapTextureAtlas rightCloudBlockTextureAtlas;
	private ITextureRegion rightCloudBlockTexture;

	private Sprite leftCloud;
	private Sprite rightCloud;

	private ArrayList<Integer> purchasedItens;
	private int[] itemSet = { 0, 0, 0 };

	private int unlockerCont = 0;
	private int itemUnlockerCont = 0;
	private int itemRatio = 5;

	// Cloud animation
	MoveModifier leftCloudIn;
	MoveModifier leftCloudOut;
	MoveModifier rightCloudIn;
	MoveModifier rightCloudOut;
	DelayModifier dModFinish;
	DelayModifier dModStart;

	// HUD
	// -----------------------------------------------

	private ITextureRegion coinTextureRegion;
	private BuildableBitmapTextureAtlas coinTextureAtlas;
	private ITextureRegion DoubleComboTextureRegion;
	private BuildableBitmapTextureAtlas DoubleComboTextureAtlas;
	private ITextureRegion burstRightTextureRegion;
	private BuildableBitmapTextureAtlas burstRightTextureAtlas;
	private ITextureRegion freezeButtonTextureRegion;
	private BuildableBitmapTextureAtlas freezeTextureAtlas;
	private ITextureRegion barraTextureRegion;
	private BuildableBitmapTextureAtlas barraTextureAtlas;
	private Text coinsText;
	private Text comboText;
	private Text coinpopupText;
	private Text levelText;
	private HUD gameHUD;
	private Text scoreText;
	private Font font;
	private Font fontSize64;
	private ITextureRegion lifesTextureRegion;
	private BuildableBitmapTextureAtlas lifesTextureAtlas;
	private Sprite coin;
	private Sprite bottomBar;
	private Sprite clearBottomBar;
	private Sprite life1;
	private Sprite life2;
	private Sprite life3;
	private Font newFont;
	private Font newFont72;
	private ITexture fontTexture;

	// Menu
	// -----------------------------------------------

	private ITextureRegion pauseTextureRegion;
	private BuildableBitmapTextureAtlas pauseTextureAtlas;
	protected MenuScene mMenuScene;
	private BuildableBitmapTextureAtlas mMenuTextureAtlas;
	private ITextureRegion mMenuResumeTextureRegion;
	private ITextureRegion mMenuQuitTextureRegion;
	private BuildableBitmapTextureAtlas restartGameTextureAtlas;
	private ITextureRegion restartGameTextureRegion;
	protected static final int MENU_RESUME = 0;
	protected static final int MENU_QUIT = MENU_RESUME + 1;
	protected static final int MENU_RESTART = 3;

	// Game Over/Menu Scene
	// -----------------------------------------------
	
	private ITextureRegion missionBalloonTextureRegion;
	private BuildableBitmapTextureAtlas missionBalloonTextureAtlas;
	private ITextureRegion homeTextureRegion;
	private BuildableBitmapTextureAtlas homeTextureAtlas;
	private ITextureRegion backTextureRegion;
	private BuildableBitmapTextureAtlas backTextureAtlas;
	private ITextureRegion checkTextureRegion;
	private BuildableBitmapTextureAtlas checkTextureAtlas;
	private ITextureRegion moneyTextureRegion;
	private BuildableBitmapTextureAtlas moneyTextureAtlas;
	private ITextureRegion missionTextureRegion;
	private BuildableBitmapTextureAtlas missionTextureAtlas;
	private BuildableBitmapTextureAtlas resumeTextureAtlas;
	private ITextureRegion resumeTextureRegion;
	private BuildableBitmapTextureAtlas gameOverTextureAtlas;
	private ITextureRegion gameOverTextureRegion;
	private ButtonSprite btRestart;
	private ButtonSprite btResume;
	private ButtonSprite btHome;
	private ButtonSprite btPause;
	private Sprite moneySprite;
	private Sprite missionsSprite;
	private Sprite gameOverBackground;
	private Text coinsPerMatch;
	private BuildableBitmapTextureAtlas recordBalloonTextureAtlas;
	private ITextureRegion recordBalloonITextureRegion;
	private ButtonSprite recordBalloon;
	private Text recordText;
	private Text finalScoreText;
	
	// Shared Preferences
	// -----------------------------------------------

	private SharedPreferencesController sPreferences = new SharedPreferencesController();
	private SharedPreferences sharedPreferences;

	// Others
	// -----------------------------------------------

	private ChallengeBalloonController ballonController = new ChallengeBalloonController();
	private ChallengeGameController gmController = new ChallengeGameController();
	final private PausableTimerHandler timerHandler = new PausableTimerHandler(
			ballonController.getTimer(), true, this);
	private boolean comboActivated = true;
	private boolean gameisOver = false;
	private BuildableBitmapTextureAtlas backgroundTextureAtlas;
	private ITextureRegion backgroundTextureRegion;
	private SAXParser saxParser = new SAXParser();

	// Sound
	// -----------------------------------------------
	
	private Sound myPopSound;

	// Missions
	// -----------------------------------------------
	private ArrayList<Integer> aquiredMKeys = new ArrayList<Integer>();
	private MissionKeys MKeys = new MissionKeys();
	private Mission[] missions;
	private int missionPack;
	private String[] missionsStatus;
	private Text m1Text;
	private Text m2Text;
	private Text m3Text;
	private Text m1StatusText;
	private Text m2StatusText;
	private Text m3StatusText;
	private Text packText;
	private Font packTextFont;
	private Font missionStatusFont;
	
	
	// -----------------------------------------------
	// Base Game Methods
	// -----------------------------------------------

	@Override
	public EngineOptions onCreateEngineOptions() {
		System.gc();
		// igualar a cena a nulo, parece ter corrigido o bug de repeticao do
		// popup/

		this.sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		this.indResolution = loadResolutionX();
		this.xPositions[0] = Math.round(65 * indResolution);
		this.xPositions[1] = Math.round(133 * indResolution);
		this.xPositions[2] = Math.round(201 * indResolution);
		this.xPositions[3] = Math.round(269 * indResolution);
		this.xPositions[4] = Math.round(337 * indResolution);
		this.xPositions[5] = Math.round(405 * indResolution);
		this.xPositions[6] = Math.round(473 * indResolution);

		final Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		// dm.
		mScene = null;
		this.CAMERA_WIDTH = (int) (CAMERA_WIDTH * indResolution);
		this.CAMERA_HEIGHT = (int) (CAMERA_HEIGHT * indResolution);
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineoptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineoptions.getTouchOptions().setNeedsMultiTouch(true);
		engineoptions.getAudioOptions().setNeedsSound(true);
		return engineoptions;
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		SoundFactory.setAssetBasePath("sfx/");

		this.coinTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(42 * indResolution),
				Math.round(42 * indResolution), TextureOptions.DEFAULT);
		this.coinTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(coinTextureAtlas, this, "dollar_coin.png");
		// Itens
		// ----------------------------------------

		this.freezeTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(60 * indResolution),
				Math.round(60 * indResolution), TextureOptions.DEFAULT); // freeze
		this.freezeButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(freezeTextureAtlas, this, "freezeicon.png");
		this.DoubleComboTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 60, 60, TextureOptions.DEFAULT);
		this.DoubleComboTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(DoubleComboTextureAtlas, this, "combox2.png");
		this.burstRightTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 60, 60, TextureOptions.DEFAULT);
		this.burstRightTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(burstRightTextureAtlas, this, "Blaster.png");
		this.leftCloudBlockTextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 64, 128);
		this.rightCloudBlockTextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 64, 128);
		this.leftCloudBlockTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(leftCloudBlockTextureAtlas, this,
						"leftcloudblock.png", 0, 0);
		this.rightCloudBlockTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(rightCloudBlockTextureAtlas, this,
						"rightcloudblock.png", 0, 0);
		// End Itens
		// ----------------------------------------

		this.barraTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(512 * indResolution),
				Math.round(1024 * indResolution), TextureOptions.DEFAULT);
		this.barraTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(barraTextureAtlas, this, "Segunda Camada.png");

		// Balloons
		// ----------------------------------------
		this.balloonsTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 1095, 1350, TextureOptions.BILINEAR);
		this.specialBalloonsTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 1095, 1350, TextureOptions.BILINEAR);
		this.darkblueBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Azul-Escuro.png", 0, 0, 10, 1);
		this.yellowBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Amarelo.png", 0, Math.round(93 * indResolution), 10, 1);
		this.orangeBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Laranja.png", 0, Math.round(186 * indResolution), 10,
						1);
		this.grayBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Cinza.png", 0, Math.round(279 * indResolution), 10, 1);
		this.pinkBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Rosa.png", 0, Math.round(372 * indResolution), 10, 1);
		this.greenBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Verde.png", 0, Math.round(465 * indResolution), 10, 1);
		this.lightblueBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Azul-Claro.png", 0, Math.round(558 * indResolution),
						10, 1);
		this.blackBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Preto.png", 0, Math.round(651 * indResolution), 10, 1);
		this.redBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.balloonsTextureAtlas, this,
						"Vermelho.png", 0, Math.round(744 * indResolution), 10,
						1);

		this.coinBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.specialBalloonsTextureAtlas, this,
						"Coin.png", 0, 0, 10, 1);
		this.addlifeBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.specialBalloonsTextureAtlas, this,
						"AddLife.png", 0, 150, 10, 1);
		this.comboBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.specialBalloonsTextureAtlas, this,
						"DoubleCombo.png", 0, 300, 10, 1);
		this.blizzardBallonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.specialBalloonsTextureAtlas, this,
						"Blizzard.png", 0, 450, 10, 1);
		this.cloudBalloonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.specialBalloonsTextureAtlas, this,
						"CloudBlock.png", 0, 600, 10, 1);

		// End Balloons
		// ----------------------------------------

		this.pauseTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(63 * indResolution),
				Math.round(63 * indResolution), TextureOptions.DEFAULT);
		this.pauseTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(pauseTextureAtlas, this, "Pause.png");
		this.balloonsTextureAtlas.load();
		this.specialBalloonsTextureAtlas.load();
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

		this.lifesTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 64, 64, TextureOptions.DEFAULT);
		this.lifesTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(lifesTextureAtlas, this, "Life.png");
		this.restartGameTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 301, 170, TextureOptions.DEFAULT);
		this.restartGameTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(restartGameTextureAtlas, this,
						"btnPlayAgain.png");
		this.backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(backgroundTextureAtlas, this,
						"Primeira Camada.png");

		// Game Over/Pause Scene
		// ----------------------------------------

		this.homeTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(63 * indResolution),
				Math.round(63 * indResolution), TextureOptions.DEFAULT);
		this.homeTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(homeTextureAtlas, this, "HOME.png");
		this.backTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(63 * indResolution),
				Math.round(63 * indResolution), TextureOptions.DEFAULT);
		this.backTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(backTextureAtlas, this, "RESTART.png");
		this.missionBalloonTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(53 * indResolution),
				Math.round(77 * indResolution), TextureOptions.DEFAULT);
		this.missionBalloonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(missionBalloonTextureAtlas, this,
						"BALAOMISSIONS.png");
		this.checkTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(53 * indResolution),
				Math.round(51 * indResolution), TextureOptions.DEFAULT);
		this.checkTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(checkTextureAtlas, this, "CHECK.png");
		this.moneyTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(196 * indResolution),
				Math.round(63 * indResolution), TextureOptions.DEFAULT);
		this.moneyTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(moneyTextureAtlas, this, "COINBAR.png");
		this.missionTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(307 * indResolution),
				Math.round(383 * indResolution), TextureOptions.DEFAULT);
		this.missionTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(missionTextureAtlas, this, "MISSIONS.png");
		this.resumeTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(64 * indResolution),
				Math.round(64 * indResolution), TextureOptions.DEFAULT);
		this.resumeTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(resumeTextureAtlas, this, "RESUME.png");
		this.recordBalloonTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(98 * indResolution),
				Math.round(141 * indResolution), TextureOptions.DEFAULT);
		this.recordBalloonITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(recordBalloonTextureAtlas, this, "Record.png");

		this.gameOverTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), Math.round(512 * indResolution),
				Math.round(1024 * indResolution), TextureOptions.DEFAULT);
		this.gameOverTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(gameOverTextureAtlas, this, "FundoGameOver.png");

		
		// Font
		// ----------------------------------------

		FontFactory.setAssetBasePath("font/");
		this.fontTexture = new BitmapTextureAtlas(this.getTextureManager(),
				1024, 1024, TextureOptions.BILINEAR);

		try {
			
			this.resumeTextureAtlas
			.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 0));
			this.resumeTextureAtlas.load();
			
			this.gameOverTextureAtlas
			.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 0));
			this.gameOverTextureAtlas.load();
			
			this.missionTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.missionTextureAtlas.load();

			this.pauseTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.pauseTextureAtlas.load();

			this.homeTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.homeTextureAtlas.load();

			this.backTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.backTextureAtlas.load();

			this.moneyTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.moneyTextureAtlas.load();

			this.freezeTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.freezeTextureAtlas.load();

			this.DoubleComboTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.DoubleComboTextureAtlas.load();

			this.burstRightTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.burstRightTextureAtlas.load();

			this.coinTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.coinTextureAtlas.load();

			this.barraTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.barraTextureAtlas.load();

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

			this.recordBalloonTextureAtlas
			.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 0));
			this.recordBalloonTextureAtlas.load();
			
			this.lifesTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.lifesTextureAtlas.load();
			this.leftCloudBlockTextureAtlas.load();
			this.rightCloudBlockTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}

		try {
			this.myPopSound = SoundFactory.createSoundFromAsset(
					this.mEngine.getSoundManager(), this, "popBalloon.mp3");
			this.myPopSound.setLooping(false);
		} catch (final IOException e) {
			Debug.e(e);
		}

		this.balloonsTextureAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {
		this.mScene = new Scene();
		//init menu/game over
		initializeMenuAndGameOverComponents();
		purchasedItens = sPreferences
				.loadSpecialBalloonsPreferences(sharedPreferences);

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
		// / SHARED PREFERENCES PACKAGE
		sPreferences.savePackagePreferences(1, sharedPreferences);
		// / SHARED PREFERENCES PACKAGE

		createHUD();
		createMenuScene();
		initMenuBallons();
		// createSet();


		if (sPreferences.loadScorePreferences(sharedPreferences).length == 0
				|| sPreferences.loadScorePreferences(sharedPreferences) == null) {
			int i = 0;
			while (i < 9) {
				gmController.getScoreArray()[i] = 0;
				i++;
			}
			sPreferences.saveScorePreferences(gmController.getScoreArray(),
					sharedPreferences);
		} else {
			gmController.setScoreArray(sPreferences
					.loadScorePreferences(sharedPreferences));
		}
		if (sPreferences.loadComboPreferences(sharedPreferences) == 0) {
			sPreferences.saveComboPreferences("combo", 0, sharedPreferences);
		}
		mScene.registerUpdateHandler(timerHandler);
		this.mScene.setOnAreaTouchListener(this);
		
		// Init missions
		initMissions();
		initMissionText();
		
		return this.mScene;
	}

	// -----------------------------------------------
	// Event Handler
	// -----------------------------------------------

	@Override
	public void onTimePassed(TimerHandler pTimerHandler) {

		if (freezeOn) {
			gameHUD.detachChild(btFreeze);
		}
		if (blasterOver) {
			gameHUD.detachChild(btBlaster);
		}
		if (lifePowerIsOver) {
			gameHUD.detachChild(btLifePower);
		}

		if (needToPause) {
			blockGame(2000);
			removePopupScene();
			comboActivated = true;
			this.gameHUD.detachChild(ballonController.getBlackBallonMenu());
			needToPause = false;
		}
		if (colorreminderUsed) {
			blockGame(2000);
			removePopupScene();
			colorreminderUsed = false;
		}

		if (gmController.getLvl() > gmController.getPreviousLvl()) {
			// Mission Key
			if (gmController.getLifes() == 3) {

			}
			gmController.setPreviousLvl(gmController.getLvl());
			if (gmController.getLvl() == 9) {
				ballonController.setRightColorsCont(2);
				if (gmController.getLifes() != 3) {

					gmController.addLife();

					if (gmController.getLifes() == 2) {

						gameHUD.attachChild(life2);

					} else if (gmController.getLifes() == 3) {

						gameHUD.attachChild(life1);
					}
				}

			}
			if (gmController.getLvl() == 14) {
				ballonController.setRightColorsCont(3);
				if (gmController.getLifes() != 3) {

					gmController.addLife();

					if (gmController.getLifes() == 2) {

						gameHUD.attachChild(life2);

					} else if (gmController.getLifes() == 3) {

						gameHUD.attachChild(life1);
					}
				}

			}
			clearAllBallons();
			this.needToPause = true;
			ballonController.trocarCores(targetBlocker);
			if (targetBlocker > 0) {
				targetBlocker--;
			}

			popupScene();
			// coinsText.setText("" + coins++);
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
			this.removeBalloonSprite((BallonSprite) pTouchArea, true,
					doubleCombo);
			return true;
		}

		return false;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

		if (pSceneTouchEvent.isActionDown()) {
			if (comboActivated) {
				if (sPreferences.loadComboPreferences(sharedPreferences) < gmController
						.getCombo()) {
					sPreferences.saveComboPreferences("combo",
							gmController.getCombo(), sharedPreferences);
				}
				gmController.clearCombo();
				this.comboText.setText("");
			}
			return true;
		}

		return false;
	}

	// -----------------------------------------------
	// Ballons
	// -----------------------------------------------

	private void initMenuBallons() {
		BallonSprite yellowBallonAux = new BallonSprite(200, 200,
				this.yellowBallonTextureRegion,
				this.getVertexBufferObjectManager(), 1);
		BallonSprite blueBallonAux = new BallonSprite(200, 200,
				this.darkblueBallonTextureRegion,
				this.getVertexBufferObjectManager(), 1);
		BallonSprite redBallonAux = new BallonSprite(200, 200,
				this.redBallonTextureRegion,
				this.getVertexBufferObjectManager(), 1);
		BallonSprite orangeBallonAux = new BallonSprite(200, 200,
				this.orangeBallonTextureRegion,
				this.getVertexBufferObjectManager(), 1);
		BallonSprite grayBallonAux = new BallonSprite(200, 200,
				this.grayBallonTextureRegion,
				this.getVertexBufferObjectManager(), 1);
		BallonSprite purpleBallonAux = new BallonSprite(200, 200,
				this.lightblueBallonTextureRegion,
				this.getVertexBufferObjectManager(), 1);
		BallonSprite pinkBallonAux = new BallonSprite(200, 200,
				this.pinkBallonTextureRegion,
				this.getVertexBufferObjectManager(), 1);
		BallonSprite blackBallonAux = new BallonSprite(200, 200,
				this.blackBallonTextureRegion,
				this.getVertexBufferObjectManager(), 1);
		BallonSprite greenBallonAux = new BallonSprite(200, 200,
				this.greenBallonTextureRegion,
				this.getVertexBufferObjectManager(), 1);

		ballonController.setYellowBallonMenu(yellowBallonAux);
		ballonController.setDarkBlueBallonMenu(blueBallonAux);
		ballonController.setRedBallonMenu(redBallonAux);
		ballonController.setOrangeBallonMenu(orangeBallonAux);
		ballonController.setgrayBallonMenu(grayBallonAux);
		ballonController.setLightBlueBallonMenu(purpleBallonAux);
		ballonController.setPinkBallonMenu(pinkBallonAux);
		ballonController.setBlackBallonMenu(blackBallonAux);
		ballonController.setGreenBallonMenu(greenBallonAux);

	}

	private void clearAllBallons() {
		System.gc();
		resetAllBallonsFromList(ballonController.getYellowBallons());
		resetAllBallonsFromList(ballonController.getDarkBlueBallons());
		resetAllBallonsFromList(ballonController.getRedBallons());
		resetAllBallonsFromList(ballonController.getOrangeBallons());
		resetAllBallonsFromList(ballonController.getBlackBallons());
		resetAllBallonsFromList(ballonController.getgrayBallons());
		resetAllBallonsFromList(ballonController.getGreenBallons());
		resetAllBallonsFromList(ballonController.getLightBlueBallons());
		resetAllBallonsFromList(ballonController.getPinkBallons());
		resetAllBallonsFromList(ballonController.getCoinBalloons());
		resetAllBallonsFromList(ballonController.getComboBallons());
		resetAllBallonsFromList(ballonController.getBlasterBallons());
		resetAllBallonsFromList(ballonController.getBlizzardBallons());
		resetAllBallonsFromList(ballonController.getAddlifeBallons());
	}

	private void resetAllBallonsFromList(ArrayList<BallonSprite> balloonList) {
		for (int cont = 0; cont < balloonList.size(); cont++) {
			balloonList.get(cont).setIsInUse(false);
			balloonList.get(cont).clearEntityModifiers();
			balloonList.get(cont).clearUpdateHandlers();
			mScene.unregisterTouchArea(balloonList.get(cont));
			mScene.detachChild(balloonList.get(cont));
		}
	}

	private void resetSpecificBalloonFromList(
			ArrayList<BallonSprite> balloonList, BallonSprite sBaloon) {
		for (int cont = 0; cont < balloonList.size(); cont++) {
			if (sBaloon == balloonList.get(cont)) {
				balloonList.get(cont).setIsInUse(false);
				balloonList.get(cont).clearEntityModifiers();
				balloonList.get(cont).clearUpdateHandlers();
				mScene.unregisterTouchArea(balloonList.get(cont));
				mScene.detachChild(balloonList.get(cont));
				break;
			}
		}
	}

	private void clearRightBalloons() {
		//System.gc();
		for (int i = 0; i < 3; i++) {
			if (ballonController.getRightColors(i) == bType.Yellow) {
				resetAllBallonsFromList(ballonController.getYellowBallons());
				gmController.increaseScoreWithoutCombo();
				this.scoreText
						.setText(Integer.toString(gmController.getScore()));
				gmController.increaseBurstBallonCont();
			} else if (ballonController.getRightColors(i) == bType.DarkBlue) {
				resetAllBallonsFromList(ballonController.getDarkBlueBallons());
				gmController.increaseScoreWithoutCombo();
				this.scoreText
						.setText(Integer.toString(gmController.getScore()));
				gmController.increaseBurstBallonCont();
			} else if (ballonController.getRightColors(i) == bType.Red) {
				resetAllBallonsFromList(ballonController.getRedBallons());
				gmController.increaseScoreWithoutCombo();
				this.scoreText
						.setText(Integer.toString(gmController.getScore()));
				gmController.increaseBurstBallonCont();
			} else if (ballonController.getRightColors(i) == bType.Orange) {
				resetAllBallonsFromList(ballonController.getOrangeBallons());
				gmController.increaseScoreWithoutCombo();
				this.scoreText
						.setText(Integer.toString(gmController.getScore()));
				gmController.increaseBurstBallonCont();
			} else if (ballonController.getRightColors(i) == bType.Gray) {
				resetAllBallonsFromList(ballonController.getgrayBallons());
				gmController.increaseScoreWithoutCombo();
				this.scoreText
						.setText(Integer.toString(gmController.getScore()));
				gmController.increaseBurstBallonCont();
			} else if (ballonController.getRightColors(i) == bType.Black) {
				resetAllBallonsFromList(ballonController.getBlackBallons());
				gmController.increaseScoreWithoutCombo();
				this.scoreText
						.setText(Integer.toString(gmController.getScore()));
				gmController.increaseBurstBallonCont();
			} else if (ballonController.getRightColors(i) == bType.Pink) {
				resetAllBallonsFromList(ballonController.getPinkBallons());
				gmController.increaseScoreWithoutCombo();
				this.scoreText
						.setText(Integer.toString(gmController.getScore()));
				gmController.increaseBurstBallonCont();
			} else if (ballonController.getRightColors(i) == bType.LightBlue) {
				resetAllBallonsFromList(ballonController.getLightBlueBallons());
				gmController.increaseScoreWithoutCombo();
				this.scoreText
						.setText(Integer.toString(gmController.getScore()));
				gmController.increaseBurstBallonCont();
			} else if (ballonController.getRightColors(i) == bType.Green) {
				resetAllBallonsFromList(ballonController.getGreenBallons());
				gmController.increaseScoreWithoutCombo();
				this.scoreText
						.setText(Integer.toString(gmController.getScore()));
				gmController.increaseBurstBallonCont();
			}
		}
	}

	final private void detachBalloon(BallonSprite sBalao) {
		if (sBalao.GetBalloonColor() == bType.Yellow) {
			resetSpecificBalloonFromList(ballonController.getYellowBallons(),
					sBalao);
		} else if (sBalao.GetBalloonColor() == bType.Red) {
			resetSpecificBalloonFromList(ballonController.getRedBallons(),
					sBalao);
		} else if (sBalao.GetBalloonColor() == bType.DarkBlue) {
			resetSpecificBalloonFromList(ballonController.getDarkBlueBallons(),
					sBalao);
		} else if (sBalao.GetBalloonColor() == bType.Orange) {
			resetSpecificBalloonFromList(ballonController.getOrangeBallons(),
					sBalao);
		} else if (sBalao.GetBalloonColor() == bType.Gray) {
			resetSpecificBalloonFromList(ballonController.getgrayBallons(),
					sBalao);
		} else if (sBalao.GetBalloonColor() == bType.Black) {
			resetSpecificBalloonFromList(ballonController.getBlackBallons(),
					sBalao);
		} else if (sBalao.GetBalloonColor() == bType.Pink) {
			resetSpecificBalloonFromList(ballonController.getPinkBallons(),
					sBalao);
		} else if (sBalao.GetBalloonColor() == bType.LightBlue) {
			resetSpecificBalloonFromList(
					ballonController.getLightBlueBallons(), sBalao);
		} else if (sBalao.GetBalloonColor() == bType.Green) {
			resetSpecificBalloonFromList(ballonController.getGreenBallons(),
					sBalao);
		} else if (sBalao.GetBalloonColor() == bType.Coin) {
			resetSpecificBalloonFromList(ballonController.getCoinBalloons(),
					sBalao);

		} else if (sBalao.GetBalloonColor() == bType.Combo) {
			resetSpecificBalloonFromList(ballonController.getComboBallons(),
					sBalao);
		} else if (sBalao.GetBalloonColor() == bType.Cloud) {
			resetSpecificBalloonFromList(ballonController.getBlasterBallons(),
					sBalao);
		} else if (sBalao.GetBalloonColor() == bType.Blizzard) {
			resetSpecificBalloonFromList(ballonController.getBlizzardBallons(),
					sBalao);
		} else if (sBalao.GetBalloonColor() == bType.AddLife) {
			resetSpecificBalloonFromList(ballonController.getAddlifeBallons(),
					sBalao);
		}

			VerificarVidas();
	}

	private void removeBalloonSprite(final BallonSprite balloonSprite,
			boolean isScoring, boolean doubleCombo) {
		if (balloonSprite.GetBalloonColor() <= 9) {

			if (isScoring && comboActivated == true) {
				if (ballonController.getRightColors(2) != 0) {
					if (balloonSprite.GetBalloonColor() == ballonController
							.getRightColors(0)
							|| balloonSprite.GetBalloonColor() == ballonController
									.getRightColors(1)
							|| balloonSprite.GetBalloonColor() == ballonController
									.getRightColors(2)) {
						myPopSound.play();
						gmController.increaseBurstBallonCont();
						gmController.increaseScore();
						gmController.increaseCoins();
						gmController.increaseCoinsPerMatch();
						gmController.increaseCombo(
								balloonSprite.GetBalloonColor(), doubleCombo);
						if (gmController.getCombo() != 1)
							comboPopup(balloonSprite.getX(),
									balloonSprite.getY());
						if (gmController.getBurstBalloonCont() >= gmController
								.getLvlChangeScore()) {
							comboActivated = false;
							gmController.verifyLevelChange();
						}
					} else {
						gmController.clearBurstBalloonCont();
						gmController.decreaseScore();
						if (sPreferences
								.loadComboPreferences(sharedPreferences) < gmController
								.getCombo()) {
							sPreferences.saveComboPreferences("combo",
									gmController.getCombo(), sharedPreferences);
						}
						gmController.clearCombo();
					}
				} else if (ballonController.getRightColors(1) != 0) {
					if (balloonSprite.GetBalloonColor() == ballonController
							.getRightColors(0)
							|| balloonSprite.GetBalloonColor() == ballonController
									.getRightColors(1)) {
						myPopSound.play();

						gmController.increaseBurstBallonCont();
						gmController.increaseScore();
						gmController.increaseCoins();
						gmController.increaseCoinsPerMatch();
						gmController.increaseCombo(
								balloonSprite.GetBalloonColor(), doubleCombo);
						if (gmController.getCombo() != 1)
							comboPopup(balloonSprite.getX(),
									balloonSprite.getY());
						if (gmController.getBurstBalloonCont() >= gmController
								.getLvlChangeScore()) {
							comboActivated = false;
							gmController.verifyLevelChange();
						}

					} else {
						gmController.clearBurstBalloonCont();
						gmController.decreaseScore();
						if (sPreferences
								.loadComboPreferences(sharedPreferences) < gmController
								.getCombo()) {
							sPreferences.saveComboPreferences("combo",
									gmController.getCombo(), sharedPreferences);
						}
						gmController.clearCombo();
					}
				} else if (ballonController.getRightColors(0) != 0) {
					if (balloonSprite.GetBalloonColor() == ballonController
							.getRightColors(0)) {
						myPopSound.play();
						gmController.increaseBurstBallonCont();
						gmController.increaseScore();
						gmController.increaseCoins();
						gmController.increaseCoinsPerMatch();
						gmController.increaseCombo(
								balloonSprite.GetBalloonColor(), doubleCombo);
						if (gmController.getCombo() != 1)
							comboPopup(balloonSprite.getX(),
									balloonSprite.getY());

						if (gmController.getBurstBalloonCont() >= gmController
								.getLvlChangeScore()) {
							comboActivated = false;
							gmController.verifyLevelChange();
						}

					} else {
						gmController.clearBurstBalloonCont();
						gmController.decreaseScore();
						if (sPreferences
								.loadComboPreferences(sharedPreferences) < gmController
								.getCombo()) {
							sPreferences.saveComboPreferences("combo",
									gmController.getCombo(), sharedPreferences);
						}
						gmController.clearCombo();
					}
				}
			}
		} else if (balloonSprite.GetBalloonColor() == bType.Coin) {
			int bonus = (int) (Math.random() * 21);
			gmController.setCoins(gmController.getIntCoins() + bonus);
			gmController.setCoinsPerMatch(gmController.getCoinsPerMatch() + bonus);
			coinpopupText.setText("$" + bonus);
			coinsPopup(balloonSprite.getX(), balloonSprite.getY());

		} else {
			/*
			 * int xItemPosition = 0; if (itemSet[0] == 0) { itemSet[0] =
			 * balloonSprite.GetBalloonColor(); xItemPosition = xItemOne; } else
			 * if (itemSet[1] == 0) { itemSet[1] =
			 * balloonSprite.GetBalloonColor(); xItemPosition = xItemTwo; } else
			 * if (itemSet[2] == 0) { itemSet[2] =
			 * balloonSprite.GetBalloonColor(); xItemPosition = xItemThree; }
			 */
			if (balloonSprite.GetBalloonColor() == bType.Cloud) {
				/*
				 * btBlaster = addBlasterItem(xItemPosition, yItem);
				 * gameHUD.registerTouchArea(btBlaster);
				 * gameHUD.attachChild(btBlaster);
				 */
				addCloudBlock();
			} else if (balloonSprite.GetBalloonColor() == bType.AddLife) {
				// btLifePower = addLifePowerItem(xItemPosition, yItem);
				gameHUD.registerTouchArea(btLifePower);
				gameHUD.attachChild(btLifePower);
			} else if (balloonSprite.GetBalloonColor() == bType.Combo) {
				/*
				 * btDoubleCombo = addDoubleComboItem(xItemPosition, yItem);
				 * gameHUD.registerTouchArea(btDoubleCombo);
				 * gameHUD.attachChild(btDoubleCombo);
				 */
				runDoubleCombo();
			} else if (balloonSprite.GetBalloonColor() == bType.Blizzard) {
				/*
				 * btFreeze = addFreezeItem(xItemPosition, yItem);
				 * gameHUD.registerTouchArea(btFreeze);
				 * gameHUD.attachChild(btFreeze);
				 */
				runFreeze();
			}
		}
		balloonSprite.clearEntityModifiers();
		balloonSprite.animate(popDuration, 8, 9, true);
		balloonSprite.registerEntityModifier(new DelayModifier(0.1f,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						// TODO Auto-generated method stub
						runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								detachBalloon(balloonSprite);
							}
						});

					}
				}));

		aquiredMKeys.add(balloonSprite.GetBalloonColor());
		this.coinsText.setText(gmController.getCoins());
		this.comboText.setText("+" + (gmController.getCombo() - 1));
		this.scoreText.setText(Integer.toString(gmController.getScore()));

	}

	private BallonSprite getBallonSprite(int color) {
		int pX = 0;
		int pY = 0;

		int indexBalaoDisponivel = ballonController
				.getEnabledBallons(mCorBalao);
		BallonSprite balaoAux = null;
		if (indexBalaoDisponivel == -1) {
			BallonSprite sBalloon = null;
			if (color == bType.Yellow) {
				sBalloon = new BallonSprite(pX, pY,
						this.yellowBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Yellow);
				ballonController.getYellowBallons().add(sBalloon);
			} else if (color == bType.DarkBlue) {
				sBalloon = new BallonSprite(pX, pY,
						this.darkblueBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.DarkBlue);
				ballonController.getDarkBlueBallons().add(sBalloon);
			} else if (color == bType.Red) {
				sBalloon = new BallonSprite(pX, pY,
						this.redBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Red);
				ballonController.getRedBallons().add(sBalloon);
			} else if (color == bType.Orange) {
				sBalloon = new BallonSprite(pX, pY,
						this.orangeBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Orange);
				ballonController.getOrangeBallons().add(sBalloon);
			} else if (color == bType.Gray) {
				sBalloon = new BallonSprite(pX, pY,
						this.grayBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Gray);
				ballonController.getgrayBallons().add(sBalloon);
			} else if (color == bType.Black) {
				sBalloon = new BallonSprite(pX, pY,
						this.blackBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Black);
				ballonController.getBlackBallons().add(sBalloon);
			} else if (color == bType.Pink) {
				sBalloon = new BallonSprite(pX, pY,
						this.pinkBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Pink);
				ballonController.getPinkBallons().add(sBalloon);
			} else if (color == bType.LightBlue) {
				sBalloon = new BallonSprite(pX, pY,
						this.lightblueBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.LightBlue);
				ballonController.getLightBlueBallons().add(sBalloon);
			} else if (color == bType.Green) {
				sBalloon = new BallonSprite(pX, pY,
						this.greenBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Green);
				ballonController.getGreenBallons().add(sBalloon);
			} else if (color == bType.Coin) {
				sBalloon = new BallonSprite(pX, pY,
						this.coinBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Coin);
				ballonController.getCoinBalloons().add(sBalloon);
			} else if (color == bType.Combo) {
				sBalloon = new BallonSprite(pX, pY,
						this.comboBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Combo);
				ballonController.getComboBallons().add(sBalloon);
			} else if (color == bType.Blizzard) {
				sBalloon = new BallonSprite(pX, pY,
						this.blizzardBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Blizzard);
				ballonController.getBlizzardBallons().add(sBalloon);
			} else if (color == bType.AddLife) {
				sBalloon = new BallonSprite(pX, pY,
						this.addlifeBallonTextureRegion,
						this.getVertexBufferObjectManager(), bType.AddLife);
				ballonController.getAddlifeBallons().add(sBalloon);
			} else if (color == bType.Cloud) {
				sBalloon = new BallonSprite(pX, pY,
						this.cloudBalloonTextureRegion,
						this.getVertexBufferObjectManager(), bType.Cloud);
				ballonController.getBlasterBallons().add(sBalloon);
			}

			balaoAux = sBalloon;

		} else {

			if (color == bType.Yellow) {
				balaoAux = ballonController.getYellowBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.DarkBlue) {
				balaoAux = ballonController.getDarkBlueBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Red) {
				balaoAux = ballonController.getRedBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Orange) {
				balaoAux = ballonController.getOrangeBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Gray) {
				balaoAux = ballonController.getgrayBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Black) {
				balaoAux = ballonController.getBlackBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Pink) {
				balaoAux = ballonController.getPinkBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.LightBlue) {
				balaoAux = ballonController.getLightBlueBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Green) {
				balaoAux = ballonController.getGreenBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Coin) {
				balaoAux = ballonController.getCoinBalloons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Combo) {
				balaoAux = ballonController.getComboBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Blizzard) {
				balaoAux = ballonController.getBlizzardBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.Cloud) {
				balaoAux = ballonController.getBlasterBallons().get(
						indexBalaoDisponivel);
			} else if (color == bType.AddLife) {
				balaoAux = ballonController.getAddlifeBallons().get(
						indexBalaoDisponivel);
			}
		}

		return balaoAux;
	}

	private void addBalloonSprite(final float pX, final float pY) {
		GenerateColor();

		final BallonSprite sBalloon = getBallonSprite(mCorBalao);

		sBalloon.setIsInUse(true);
		sBalloon.animate(animationDuration, 0, 7, true);

		sBalloon.registerEntityModifier(new MoveModifier(ballonController
				.getSpeed(), (pX - 64 * indResolution),
				(pX - 64 * indResolution), CAMERA_HEIGHT, -128 * indResolution,
				EaseLinear.getInstance()));

		sBalloon.registerEntityModifier(new DelayModifier(ballonController
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

						if (sBalloon.GetBalloonColor() == ballonController
								.getRightColors(0)
								|| sBalloon.GetBalloonColor() == ballonController
										.getRightColors(1)
								|| sBalloon.GetBalloonColor() == ballonController
										.getRightColors(2)) {
							if (sPreferences
									.loadComboPreferences(sharedPreferences) < gmController
									.getCombo()) {
								sPreferences.saveComboPreferences("combo",
										gmController.getCombo(),
										sharedPreferences);
							}
							if (gmController.getScore() >= 5)
								gmController.setScore(gmController.getScore() - 5);
							else
								gmController.setScore(0);
							scoreText.setText(Integer.toString(gmController
									.getScore()));
							gmController.setLifes(gmController.getLifes() - 1);
							gmController.clearCombo();
							comboText.setText("x0");
						}

						detachBalloon(sBalloon);

					}
				});

			}
		}));

		/*
		 * final RotationModifier ccwRotation = new RotationModifier(2, 5, -10);
		 * final RotationModifier cwRotation = new RotationModifier(2, -10, 5);
		 * sBalloon.registerEntityModifier(ccwRotation);
		 * 
		 * sBalloon.registerUpdateHandler(new IUpdateHandler() {
		 * 
		 * @Override public void reset() { }
		 * 
		 * @Override public void onUpdate(float pSecondsElapsed) { if
		 * (ccwRotation.isFinished()) {
		 * sBalloon.unregisterEntityModifier(ccwRotation);
		 * sBalloon.registerEntityModifier(cwRotation); ccwRotation.reset(); }
		 * else if (cwRotation.isFinished()) {
		 * sBalloon.unregisterEntityModifier(cwRotation);
		 * sBalloon.registerEntityModifier(ccwRotation); cwRotation.reset(); } }
		 * });
		 */

		this.mScene.registerTouchArea(sBalloon);
		this.mScene.attachChild(sBalloon);

	}

	// -----------------------------------------------
	// Scenes
	// -----------------------------------------------

	private void createGameOverScene() {
		
		for (Integer i : purchasedItens) {
			System.out.println("ITENS COMPRADOS: " + i);
		}

		bottomBar.detachSelf();
		btPause.detachSelf();
		
		if (comboText.hasParent()) {
			comboText.detachSelf();
		}
		if (cloudblockRunning) {
			removeCloudBlock();
		}
		if (targetBlocker > 0) {
			targetBlocker = 0;
		}
		gameisOver = true;
		doubleCombo = false;
		comboText.setColor(0, 0, 0);

		int[] scores = sPreferences.loadScorePreferences(sharedPreferences);
				
		int coins = Integer.parseInt(gmController.getCoins());
		sPreferences.saveCoinsPreferences("coins", coins, sharedPreferences);

		finalScoreText = new Text(0,0, newFont, "+0", 30, new TextOptions(
				HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
		finalScoreText.setText(Integer.toString(gmController.getScore()));
				
		
		
		
		btRestart.setPosition((110 * indResolution), (Math.round(730 * indResolution)));
		btHome.setPosition((20 * indResolution), (Math.round(730 * indResolution)));
		moneySprite = new Sprite(Math.round(250 * indResolution),
				Math.round(730 * indResolution), moneyTextureRegion,
				this.getVertexBufferObjectManager());
		
		
		
		gameOverBackground = new Sprite(0,0, gameOverTextureRegion,
				this.getVertexBufferObjectManager());
		
		coinsPerMatch = new Text(400, 755, newFont, "0123456789", new TextOptions(
				HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
		coinsPerMatch.setText(Integer.toString(gmController.getCoinsPerMatch()));
		
		coinsPerMatch.setPosition( CAMERA_WIDTH/2, 218);
		coinsPerMatch.setColor(0,0,0);
		
		coinsText.setText(sPreferences.loadCoinsPreferences(sharedPreferences));
		coinsText.setPosition(Math.round(350 * indResolution),
				Math.round(750 * indResolution));
		coinsText.setColor(0.055f, 0.45f, 0.23f);
		
		gameHUD.attachChild(gameOverBackground);
		gameHUD.registerTouchArea(btRestart);
		gameHUD.attachChild(btRestart);
		gameHUD.registerTouchArea(btHome);
		gameHUD.attachChild(btHome);
		gameHUD.attachChild(moneySprite);
		gameHUD.attachChild(coinsText);
		gameHUD.attachChild(coinsPerMatch);
		gameHUD.attachChild(m1Text);
		gameHUD.attachChild(m2Text);
		gameHUD.attachChild(m3Text);
		/*gameHUD.attachChild(m1StatusText);
		gameHUD.attachChild(m2StatusText);
		gameHUD.attachChild(m3StatusText);*/
		gameHUD.attachChild(packText);
		
		recordBalloon = new ButtonSprite(Math.round(50 * indResolution),
				Math.round(30 * indResolution), recordBalloonITextureRegion,
				this.getVertexBufferObjectManager()){
					public boolean onAreaTouched(TouchEvent pTouchEvent,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						startActivity(new Intent(ChallengeMode.this, Ranking.class));
						ChallengeMode.this.finish();
						mScene.unregisterTouchArea(recordBalloon);
						return true;
						
					}
				};
		
		recordText = new Text(Math.round(44 * indResolution) + recordBalloon.getWidth()/2,
				Math.round(14 * indResolution) + recordBalloon.getHeight()/2, newFont, "+0", 30, new TextOptions(
				HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
		
		if (scores[0] < gmController.getScore()) {
									
			gmController.updateScore();
			String record = gmController.getRankingPosition();
			
			recordText.setText(record);
			recordText.setColor(0.996f,0.694f,0.004f);
			
			gameHUD.attachChild(recordBalloon);
			gameHUD.registerTouchArea(recordBalloon);
			if(record.equals("10"))
				recordText.setPosition(Math.round(42 * indResolution) + recordBalloon.getWidth()/2,
				Math.round(14 * indResolution) + recordBalloon.getHeight()/2);
			
			final ScaleModifier scale = new ScaleModifier(0.4f, 4.0f, 1.0f);
			DelayModifier dMod = new DelayModifier(1, new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					
					if(recordText.hasParent())
						recordText.detachSelf();
					gameHUD.attachChild(recordText);
					recordText.clearEntityModifiers();
					recordText.registerEntityModifier(scale);
				}
			});
			dMod.setAutoUnregisterWhenFinished(true);
			gameHUD.registerEntityModifier(dMod);
		}
		
		if(gmController.getScore() < 10){
		finalScoreText.setPosition(CAMERA_WIDTH/2 + finalScoreText.getWidth()/2,
				Math.round(15 * indResolution) + recordBalloon.getHeight()/2);
		}else if(gmController.getScore() > 999){
			finalScoreText.setPosition(CAMERA_WIDTH/2 - finalScoreText.getWidth()/4,
					Math.round(15 * indResolution) + recordBalloon.getHeight()/2);
		}else if(gmController.getScore() > 9999){
			finalScoreText.setPosition(CAMERA_WIDTH/2- finalScoreText.getWidth()/5,
					Math.round(15 * indResolution) + recordBalloon.getHeight()/2);
		}else{
			finalScoreText.setPosition(CAMERA_WIDTH/2- finalScoreText.getWidth()/2,
					Math.round(15 * indResolution) + recordBalloon.getHeight()/2);
		}
		finalScoreText.setColor(0,0,0);
		gameHUD.attachChild(finalScoreText);
		
		sPreferences.saveScorePreferences(gmController.getScoreArray(),
				sharedPreferences);

		
		
	}

	private void removeGameOverScene() {
		if (itemSet[0] == bType.Cloud || itemSet[1] == bType.Cloud
				|| itemSet[2] == bType.Cloud) {
			btBlaster.detachSelf();
		}
		if (itemSet[0] == bType.Combo || itemSet[1] == bType.Combo
				|| itemSet[2] == bType.Combo) {
			btDoubleCombo.detachSelf();
		}
		if (itemSet[0] == bType.Blizzard || itemSet[1] == bType.Blizzard
				|| itemSet[2] == bType.Blizzard) {
			btFreeze.detachSelf();
		}
		if (itemSet[0] == bType.AddLife || itemSet[1] == bType.AddLife
				|| itemSet[2] == bType.AddLife) {
			btLifePower.detachSelf();
		}
		purchasedItens = sPreferences
				.loadSpecialBalloonsPreferences(sharedPreferences);
		ballonController.resetBalloonController(this.mScene);
		System.gc();
		

		gmController.resetGame();
		
		
		if (bottomBar.hasParent())
			gameHUD.detachChild(bottomBar);
		gameHUD.attachChild(bottomBar);
		if (life1.hasParent())
			gameHUD.detachChild(life1);
		gameHUD.attachChild(life1);
		if (life2.hasParent())
			gameHUD.detachChild(life2);
		gameHUD.attachChild(life2);
		if (life3.hasParent())
			gameHUD.detachChild(life3);
		gameHUD.attachChild(life3);
		if (scoreText.hasParent())
			gameHUD.detachChild(scoreText);
		if(recordBalloon.hasParent())
			gameHUD.detachChild(recordBalloon);
		if(recordText.hasParent())
			gameHUD.detachChild(recordText);
		gameHUD.attachChild(scoreText);
		addPauseButton();

		comboText.setText("+0");
		scoreText.setText("0");
		freezeOn = false;
		dComboIsOver = false;
		blasterOver = false;
		lifePowerIsOver = false;
		// createSet();
		timerHandler.resume();
		
		// Detach game over scene entities
		btRestart.detachSelf();
		btHome.detachSelf();
		moneySprite.detachSelf();
		coinsText.detachSelf();
		gameOverBackground.detachSelf();
		coinsPerMatch.detachSelf();
		finalScoreText.detachSelf();
		if(gameisOver){
			gameisOver = false;
			mScene.unregisterTouchArea(btRestart);
		}
		this.mScene.clearChildScene();
		gameHUD.setIgnoreUpdate(false);
	}

	private void popupScene() {
		
		if (comboText.hasParent()) {
			comboText.detachSelf();
		}
		this.newFont72 = FontFactory.create(this.getFontManager(),
				this.getTextureManager(), 512, 512,
				Typeface.create("American Purpose Casual 01.ttf", Typeface.BOLD),
				loadFontSize());
		/*this.newFont72 = FontFactory.createFromAsset(getFontManager(),
				fontTexture, getAssets(), "American Purpose Casual 01.ttf",
				72.0f, false, Color.BLACK_ARGB_PACKED_INT);*/
		final ITexture levelFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		levelFontTexture.load();
		//Font levelTextFont = FontFactory.createFromAsset(getFontManager(), levelFontTexture, getAssets(), "American Purpose Casual 01.ttf", 122.0f, true, Color.WHITE_ABGR_PACKED_INT);
		Font strokeLevelTextFont = FontFactory.createStrokeFromAsset(getFontManager(), levelFontTexture, getAssets(), "American Purpose Casual 01.ttf", 124.0f, false, Color.WHITE_ARGB_PACKED_INT, 3, Color.BLACK_ABGR_PACKED_INT);
				
		//levelTextFont.load();
		strokeLevelTextFont.load();
		levelText = new Text(120, 250, strokeLevelTextFont, "Estoure:",
				new TextOptions(HorizontalAlign.CENTER),
				this.getVertexBufferObjectManager());
		levelText.setPosition(140*indResolution, (CAMERA_HEIGHT / 2 - (levelText.getHeight() / 2) - 190));
		levelText.setText("Level " + gmController.getLvl() + ":");
		gameHUD.attachChild(levelText);
		ballonController.generateMenuBallonsList();

		if (ballonController.getRightColorsCont() == 1) {
			ballonController.getBallonsMenu()[0].setPosition((xPositions[2]),
					CAMERA_HEIGHT / 2);
			this.gameHUD.attachChild(ballonController.getBallonsMenu()[0]);
		}
		if (ballonController.getRightColorsCont() == 2) {
			ballonController.getBallonsMenu()[0].setPosition((xPositions[1]),
					CAMERA_HEIGHT / 2);
			this.gameHUD.attachChild(ballonController.getBallonsMenu()[0]);
			ballonController.getBallonsMenu()[1].setPosition((xPositions[3]),
					CAMERA_HEIGHT / 2);
			this.gameHUD.attachChild(ballonController.getBallonsMenu()[1]);

		}
		if (ballonController.getRightColorsCont() == 3) {
			ballonController.getBallonsMenu()[0].setPosition((xPositions[1]),
					CAMERA_HEIGHT / 2);
			this.gameHUD.attachChild(ballonController.getBallonsMenu()[0]);
			ballonController.getBallonsMenu()[1].setPosition((xPositions[2]),
					CAMERA_HEIGHT / 2);
			this.gameHUD.attachChild(ballonController.getBallonsMenu()[1]);
			ballonController.getBallonsMenu()[2].setPosition((xPositions[3]),
					CAMERA_HEIGHT / 2);
			this.gameHUD.attachChild(ballonController.getBallonsMenu()[2]);
		}
	}

	private void removePopupScene() {
		this.gameHUD.detachChild(levelText);

		this.gameHUD.detachChild(ballonController.getBallonsMenu()[0]);
		if (ballonController.getRightColorsCont() >= 2) {
			this.gameHUD.detachChild(ballonController.getBallonsMenu()[1]);
			if (ballonController.getRightColorsCont() == 3) {
				this.gameHUD.detachChild(ballonController.getBallonsMenu()[2]);
			}
		}

	}

	// -----------------------------------------------
	// Game Control
	// -----------------------------------------------

	public void VerificarVidas() {
		if (gmController.getLifes() == 2) {
			gameHUD.detachChild(life1);
		} else if (gmController.getLifes() == 1) {
			gameHUD.detachChild(life2);
		} else if (gmController.getLifes() == 0) {
			gameHUD.detachChild(life3);

			if (!gameisOver) {
				timerHandler.pause();
				clearAllBallons();
				gameisOver = true;
				createGameOverScene();
			}
		}
	}

	private void setGameDynamics() {

		if (gmController.getLvl() >= 19) {

			ballonController.setDynamicsSpeed(gmController.getLvl(), freezeOn);
			timerHandler.setTimerSeconds(ballonController.getLevelDynamics()[0]
					.getTimer());
			definePosition();
			addBalloonSprite(balloonPosition, 0);
			defineSecondPosition();

			ballonController.setPenultPos(lastxIndex);
			lastxIndex = balloonPosition;
			addBalloonSprite(balloonPosition, 0);

		} else {

			ballonController.setDynamicsSpeed(gmController.getLvl(), freezeOn);
			timerHandler.setTimerSeconds(ballonController.getLevelDynamics()[gmController.getLvl()]
					.getTimer());
			definePosition();

			addBalloonSprite(balloonPosition, 0);

			if (gmController.getLvl() >= 4) {
				defineSecondPosition();

				ballonController.setPenultPos(lastxIndex);
				lastxIndex = balloonPosition;
				addBalloonSprite(balloonPosition, 0);
			}

		}

	}

	private void defineSecondPosition() {
		balloonPosition = xPositions[(int) (Math.random() * 7)];
		while (true) {
			if (cloudblockRunning) {
				if (balloonPosition == xPositions[0]
						|| balloonPosition == xPositions[6]) {
					balloonPosition = xPositions[(int) ((Math.random() * 5) + 1)];
				}
				if (balloonPosition == lastxIndex
						|| balloonPosition == ballonController.getPenultPos()) {
					balloonPosition = xPositions[(int) ((Math.random() * 5) + 1)];
				} else {
					break;
				}
			} else if (balloonPosition == lastxIndex
					|| balloonPosition == ballonController.getPenultPos()) {
				balloonPosition = xPositions[(int) (Math.random() * 7)];
			} else {
				ballonController.setPenultPos(lastxIndex);
				lastxIndex = balloonPosition;
				break;
			}
		}
	}

	private void definePosition() {
		balloonPosition = xPositions[(int) (Math.random() * 7)];
		while (true) {
			if (cloudblockRunning) {
				if (balloonPosition == xPositions[0]
						|| balloonPosition == xPositions[6]) {
					balloonPosition = xPositions[(int) ((Math.random() * 5) + 1)];
				}
				if (balloonPosition == lastxIndex) {
					balloonPosition = xPositions[(int) (Math.random() * 7)];
				} else {
					break;
				}
			} else if (balloonPosition == lastxIndex) {
				balloonPosition = xPositions[(int) (Math.random() * 7)];
			} else {
				break;
			}
		}
		lastxIndex = balloonPosition;
	}

	private void GenerateColor() {
		if (unlockerCont <= 10) {
			unlockerCont++;
			itemUnlockerCont++;
			this.mCorBalao = (int) (Math.random()
					* ballonController.getColorCont() + 1);
		} else {

			if (itemUnlockerCont >= 30) {
				itemRatio = 2;
			}

			if ((int) (Math.random() * 10) == 0) {
				if (((int) (Math.random() * itemRatio) == 0
						&& purchasedItens != null && purchasedItens.size() != 0)
						&& (itemSet[0] == 0 || itemSet[1] == 0 || itemSet[2] == 0)) {
					// novo codigo
					this.mCorBalao = purchasedItens
							.get((int) (Math.random() * purchasedItens.size()));
					System.out.println(purchasedItens.size());
					// purchasedItens.remove(this.mCorBalao);
					itemUnlockerCont = 0;
					itemRatio = 5;

				} else {
					this.mCorBalao = bType.Coin;
				}
				unlockerCont = 0;
			} else {
				this.mCorBalao = (int) (Math.random()
						* ballonController.getColorCont() + 1);
			}
		}
		if (wrongDoesntMatter) {
			if (ballonController.getRightColorsCont() == 1) {
				if (ballonController.getRightColors(0) != mCorBalao) {
					mCorBalao = bType.Orange;
				}
			} else if (ballonController.getRightColorsCont() == 2) {
				if (ballonController.getRightColors(0) != mCorBalao
						&& ballonController.getRightColors(1) != mCorBalao) {
					mCorBalao = bType.Orange;
				}
			} else {
				if (ballonController.getRightColors(0) != mCorBalao
						&& ballonController.getRightColors(1) != mCorBalao
						&& ballonController.getRightColors(2) != mCorBalao) {
					if (ballonController.getRightColors(0) != bType.Orange
							&& ballonController.getRightColors(1) != bType.Orange
							&& ballonController.getRightColors(2) != bType.Orange) {
						mCorBalao = bType.Orange;
					} else if (ballonController.getRightColors(0) != bType.LightBlue
							&& ballonController.getRightColors(1) != bType.LightBlue
							&& ballonController.getRightColors(2) != bType.LightBlue) {
						mCorBalao = bType.LightBlue;
					} else if (ballonController.getRightColors(0) != bType.LightBlue
							&& ballonController.getRightColors(1) != bType.LightBlue
							&& ballonController.getRightColors(2) != bType.LightBlue) {
						mCorBalao = bType.LightBlue;
					} else if (ballonController.getRightColors(0) != bType.Pink
							&& ballonController.getRightColors(1) != bType.Pink
							&& ballonController.getRightColors(2) != bType.Pink) {
						mCorBalao = bType.Pink;
					} else if (ballonController.getRightColors(0) != bType.Black
							&& ballonController.getRightColors(1) != bType.Black
							&& ballonController.getRightColors(2) != bType.Black) {
						mCorBalao = bType.Black;
					}
				}
			}
		}

	}

	private float loadResolutionX() {
		float value = 1.6f;
		return value;
	}

	private float loadFontSize() {
		float value = 72.0f;
		return value;
	}
	
	// -----------------------------------------------
	// Pop-up
	// -----------------------------------------------

	private void comboPopup(float x, float y) {
		if (comboText.hasParent()) {
			comboText.clearEntityModifiers();
			comboText.detachSelf();
		}
		if ((x + comboText.getWidth()) > CAMERA_WIDTH) {
			x = CAMERA_WIDTH - comboText.getWidth();
		}
		comboText.setPosition(x, y);
		mScene.attachChild(comboText);
		comboText.registerEntityModifier(new AlphaModifier(0.5f, 1.0f, 0));
		comboText.registerEntityModifier(new DelayModifier(0.5f,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
					}

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {

						runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								comboText.clearEntityModifiers();
								comboText.detachSelf();
							}
						});

					}
				}));
	}

	private void coinsPopup(float x, float y) {
		if (coinpopupText.hasParent()) {
			coinpopupText.clearEntityModifiers();
			coinpopupText.detachSelf();
		}
		if ((x + coinpopupText.getWidth()) > CAMERA_WIDTH) {
			x = CAMERA_WIDTH - coinpopupText.getWidth();
		}
		coinpopupText.setPosition(x, y);
		mScene.attachChild(coinpopupText);
		coinpopupText.registerEntityModifier(new AlphaModifier(0.5f, 1.0f, 0));
		coinpopupText.registerEntityModifier(new DelayModifier(0.5f,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
					}

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {

						runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								coinpopupText.clearEntityModifiers();
								coinpopupText.detachSelf();
							}
						});

					}
				}));
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
		if (gameisOver) {
			startActivity(new Intent(ChallengeMode.this, MainActivity.class));
			ChallengeMode.this.finish();
		} else  {
			pause();
		}

	}

	// -----------------------------------------------
	// Menu Pause
	// -----------------------------------------------

	private void addPauseButton() {

		btPause = new ButtonSprite(
				Math.round(410 * indResolution),
				(Math.round(730 * indResolution)), pauseTextureRegion,
				this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pTouchEvent.isActionUp()) {
					pause();
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
			startActivity(new Intent(ChallengeMode.this, MainActivity.class));
			ChallengeMode.this.finish();
			return true;
		case MENU_RESTART:
			
			return true;
		default:
			return false;
		}

	}

	private MenuScene createMenuScene() {
		
		this.mMenuScene = new MenuScene(camera);
		this.mMenuScene.setBackground(new Background(0, 420, 255));
		btHome.setPosition(20*indResolution, btHome.getY());
		btRestart.setPosition(110 * indResolution, btHome.getY());
		this.mMenuScene.setBackgroundEnabled(false);
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

	private void restartGame(){
		purchasedItens = sPreferences.loadSpecialBalloonsPreferences(sharedPreferences);
		ballonController.resetBalloonController(this.mScene);
		System.gc();

		gmController.resetGame();
		this.gameIsPaused = false;
		/*if (bottomBar.hasParent())
			gameHUD.detachChild(bottomBar);
		gameHUD.attachChild(bottomBar);*/
		if (life1.hasParent())
			gameHUD.detachChild(life1);
		gameHUD.attachChild(life1);
		if (life2.hasParent())
			gameHUD.detachChild(life2);
		gameHUD.attachChild(life2);
		if (life3.hasParent())
			gameHUD.detachChild(life3);
		gameHUD.attachChild(life3);
		if (scoreText.hasParent())
			gameHUD.detachChild(scoreText);

		comboText.setText("+0");
		scoreText.setText("0");
		gameHUD.attachChild(scoreText);
		freezeOn = false;
		dComboIsOver = false;
		blasterOver = false;
		lifePowerIsOver = false;
		//timerHandler.resume();
		
		
		// Detach game over scene entities
		this.mScene.clearChildScene();
		gameHUD.setIgnoreUpdate(false);		
		btRestart.detachSelf();
		btHome.detachSelf();
		m1Text.detachSelf();
		m2Text.detachSelf();
		m3Text.detachSelf();
		/*m1StatusText.detachSelf();
		m2StatusText.detachSelf();
		m3StatusText.detachSelf();*/
		missionsSprite.detachSelf();
		packText.detachSelf();
		
		
	}
	
	public void initializeMenuAndGameOverComponents(){
		btRestart = new ButtonSprite(Math.round(110 * indResolution),
				(Math.round(730 * indResolution)), backTextureRegion,
				this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(gameisOver){
					removeGameOverScene();
					
				}else{
					restartGame();
				}
				return true;
			}

		};

		btHome = new ButtonSprite(Math.round(20 * indResolution),
				(Math.round(730 * indResolution)), homeTextureRegion,
				this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				startActivity(new Intent(ChallengeMode.this, MainActivity.class));
				ChallengeMode.this.finish();
				mScene.unregisterTouchArea(btHome);
				return true;
			}

		};

		missionsSprite = new Sprite(0,0, missionTextureRegion,
				this.getVertexBufferObjectManager());
		
		missionsSprite.setPosition(CAMERA_WIDTH / 2
				- missionsSprite.getWidth() / 2, (CAMERA_HEIGHT / 2
				- missionsSprite.getWidth() / 2) - Math.round(62*indResolution));
		
	}

	private void pause() {
			if (!gameIsPaused) {
				if (!gameisOver) {
					gameIsPaused = true;
					gameHUD.registerTouchArea(btRestart);
					gameHUD.attachChild(btRestart);
					gameHUD.registerTouchArea(btHome);
					gameHUD.attachChild(btHome);
					gameHUD.attachChild(missionsSprite);
					gameHUD.attachChild(m1Text);
					gameHUD.attachChild(m2Text);
					gameHUD.attachChild(m3Text);
					/*gameHUD.attachChild(m1StatusText);
					gameHUD.attachChild(m2StatusText);
					gameHUD.attachChild(m3StatusText);*/
					gameHUD.attachChild(packText);
					MenuScene menuScene = createMenuScene();
					mScene.setChildScene(menuScene, false, true, true);
					menuScene.buildAnimations();
					menuScene.setBackgroundEnabled(false);
					gameHUD.setIgnoreUpdate(true);
				}
			} else {
				mScene.clearChildScene();
				gameHUD.setIgnoreUpdate(false);
				gameIsPaused = false;
				btRestart.detachSelf();
				btHome.detachSelf();
				m1Text.detachSelf();
				m2Text.detachSelf();
				m3Text.detachSelf();
				packText.detachSelf();
				missionsSprite.detachSelf();
			}

	}
	
	// -----------------------------------------------
	// HUD
	// -----------------------------------------------

	private void createHUD() {
		gameHUD = new HUD();
		this.font = FontFactory.create(this.getFontManager(),
				this.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32,
				Color.WHITE.hashCode());
		this.font.load();
		this.newFont = FontFactory.createFromAsset(getFontManager(),
				fontTexture, getAssets(), "American Purpose Casual 01.ttf",
				48.0f, true, Color.BLACK_ABGR_PACKED_INT);
		this.newFont.load();
		this.fontSize64 = FontFactory.create(this.getFontManager(),
				this.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD),
				loadFontSize());
		this.fontSize64.load();
		// CREATE SCORE TEXT
		scoreText = new Text(20, 420, newFont, "Score: 0123456789", 30,
				new TextOptions(HorizontalAlign.LEFT),
				this.getVertexBufferObjectManager());
		scoreText.setColor(1, 1, 1);
		comboText = new Text(20, 420, newFont, "+0", 30, new TextOptions(
				HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
		coinpopupText = new Text(20, 420, font, "x0", 30, new TextOptions(
				HorizontalAlign.LEFT), this.getVertexBufferObjectManager());

		comboText.setColor(0, 0, 0);
		coinsText = new Text(400, 755, newFont, "0123456789", new TextOptions(
				HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
		coinsText.setColor(0, 0, 0);
		coin = new Sprite(40, 40, coinTextureRegion,
				this.getVertexBufferObjectManager());
		bottomBar = new Sprite(480, 90, barraTextureRegion,
				this.getVertexBufferObjectManager());
		life1 = new Sprite(40, 40, lifesTextureRegion,
				this.getVertexBufferObjectManager());
		life2 = new Sprite(40, 40, lifesTextureRegion,
				this.getVertexBufferObjectManager());
		life3 = new Sprite(40, 40, lifesTextureRegion,
				this.getVertexBufferObjectManager());

		life3.setPosition(Math.round(194 * indResolution),
				Math.round(757 * indResolution));
		life2.setPosition(Math.round(229 * indResolution),
				Math.round(757 * indResolution));
		life1.setPosition(Math.round(263 * indResolution),
				Math.round(757 * indResolution));

		scoreText.setPosition(Math.round(331 * indResolution),
				Math.round(757 * indResolution));
		scoreText.setText("0");

		coin.setPosition(325, 750);
		bottomBar.setPosition(0, 0);
		comboText.setPosition(60, 760);

		gameHUD.attachChild(bottomBar);
		gameHUD.attachChild(scoreText);
		gameHUD.attachChild(life1);
		gameHUD.attachChild(life2);
		gameHUD.attachChild(life3);

		addPauseButton();
		camera.setHUD(gameHUD);

	}

	// -----------------------------------------------
	// SET
	// -----------------------------------------------

	/*
	 * private void createSet() { int[] items =
	 * sPreferences.loadSetPreferences(sharedPreferences); for (int i = 0; i <
	 * 3; i++) { if (items[i] == 1) { if (i == 0) btBlaster =
	 * addBlasterItem(xItemOne, yItem); if (i == 1) btBlaster =
	 * addBlasterItem(xItemTwo, yItem); if (i == 2) btBlaster =
	 * addBlasterItem(xItemThree, yItem);
	 * 
	 * gameHUD.registerTouchArea(btBlaster); gameHUD.attachChild(btBlaster); }
	 * if (items[i] == 2) { if (i == 0) btFreeze = addFreezeItem(xItemOne,
	 * yItem); if (i == 1) btFreeze = addFreezeItem(xItemTwo, yItem); if (i ==
	 * 2) btFreeze = addFreezeItem(xItemThree, yItem);
	 * 
	 * gameHUD.registerTouchArea(btFreeze); gameHUD.attachChild(btFreeze); } if
	 * (items[i] == 3) { if (i == 0) btDoubleCombo =
	 * addDoubleComboItem(xItemOne, yItem); if (i == 1) btDoubleCombo =
	 * addDoubleComboItem(xItemTwo, yItem); if (i == 2) btDoubleCombo =
	 * addDoubleComboItem(xItemThree, yItem);
	 * 
	 * gameHUD.registerTouchArea(btDoubleCombo);
	 * gameHUD.attachChild(btDoubleCombo); } if (items[i] == 4) { if (i == 0)
	 * btLifePower = addLifePowerItem(xItemOne, yItem); if (i == 1) btLifePower
	 * = addLifePowerItem(xItemTwo, yItem); if (i == 2) btLifePower =
	 * addLifePowerItem(xItemThree, yItem);
	 * 
	 * gameHUD.registerTouchArea(btLifePower); gameHUD.attachChild(btLifePower);
	 * } }
	 * 
	 * }
	 */

	// -----------------------------------------------
	// Itens da loja
	// -----------------------------------------------

	private void runDoubleCombo() {
		if (!gameisOver) {
			doubleCombo = true;
			comboText.setText("x" + gmController.getCombo());
			comboText.setColor(0, 0, 1);

			DelayModifier dMod = new DelayModifier(30,
					new IEntityModifierListener() {

						@Override
						public void onModifierStarted(
								IModifier<IEntity> pModifier, IEntity pItem) {
							System.out.println("Entrou no dCombo");
						}

						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {

							runOnUpdateThread(new Runnable() {
								@Override
								public void run() {
									doubleCombo = false;
									comboText.setColor(0, 0, 0);
									dComboIsOver = true;
									mScene.clearChildScene();
								}

							});

						}

					});
			dMod.setAutoUnregisterWhenFinished(true);
			mScene.registerEntityModifier(dMod);

		}

	}

	private void runFreeze() {
		if (!gameisOver) {
			clearAllBallons();
			freezeOn = true;

			DelayModifier dMod = new DelayModifier(30,
					new IEntityModifierListener() {

						@Override
						public void onModifierStarted(
								IModifier<IEntity> pModifier, IEntity pItem) {
							System.out.println("Entrou no dCombo");
						}

						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {

							runOnUpdateThread(new Runnable() {
								@Override
								public void run() {
									freezeOn = false;
									stopFreeze();
								}

							});

						}

					});
			dMod.setAutoUnregisterWhenFinished(true);
			mScene.registerEntityModifier(dMod);

		}

	}

	public void stopFreeze() {
		clearAllBallons();
	}

	private ButtonSprite addDoubleComboItem(float x, float y) {
		final ButtonSprite btDoubleCombo = new ButtonSprite(x, y,
				DoubleComboTextureRegion, this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (!gameisOver) {
					if (pTouchEvent.isActionUp()) {
						addTargetBlocker();

						/*
						 * doubleCombo = true;
						 * gameHUD.unregisterTouchArea(this); AlphaModifier
						 * alphaMod = new AlphaModifier(0.5f, 1.0f, 0);
						 * alphaMod.setAutoUnregisterWhenFinished(true);
						 * this.registerEntityModifier(alphaMod);
						 * comboText.setText("x" + gmController.getCombo());
						 * comboText.setColor(0, 0, 1); DelayModifier dMod = new
						 * DelayModifier(30, new IEntityModifierListener() {
						 * 
						 * @Override public void onModifierStarted(
						 * IModifier<IEntity> pModifier, IEntity pItem) { }
						 * 
						 * @Override public void onModifierFinished(
						 * IModifier<IEntity> pModifier, IEntity pItem) {
						 * 
						 * runOnUpdateThread(new Runnable() {
						 * 
						 * @Override public void run() { doubleCombo = false;
						 * comboText.setColor(0, 0, 0); dComboIsOver = true;
						 * mScene.clearChildScene(); }
						 * 
						 * });
						 * 
						 * }
						 * 
						 * }); dMod.setAutoUnregisterWhenFinished(true);
						 * this.registerEntityModifier(dMod);
						 */
					}

				}

				return true;
			}

		};
		return btDoubleCombo;

	}

	private ButtonSprite addBlasterItem(int x, int y) {
		final ButtonSprite btBlaster = new ButtonSprite(x, y,
				burstRightTextureRegion, this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pTouchEvent.isActionUp()) {
					gameHUD.unregisterTouchArea(this);
					clearRightBalloons();
					AlphaModifier alphaMod = new AlphaModifier(0.5f, 1.0f, 0);
					alphaMod.setAutoUnregisterWhenFinished(true);
					this.registerEntityModifier(alphaMod);

					DelayModifier dMod = new DelayModifier(30,
							new IEntityModifierListener() {

								@Override
								public void onModifierStarted(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {

									runOnUpdateThread(new Runnable() {
										@Override
										public void run() {
											blasterOver = true;
										}

									});

								}

							});
					dMod.setAutoUnregisterWhenFinished(true);
					this.registerEntityModifier(dMod);

				}

				return true;
			}
		};

		return btBlaster;

	}

	/*private ButtonSprite addLifePowerItem(int x, int y) {
		final ButtonSprite btLifePower = new ButtonSprite(x, y,
				lifePowerTextureRegion, this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pTouchEvent.isActionUp()) {

					if (gmController.getLifes() != 3) {
						gameHUD.unregisterTouchArea(this);
						this.registerEntityModifier(new AlphaModifier(0.5f,
								1.0f, 0));
						gmController.addLife();
						if (gmController.getLifes() == 2) {

							gameHUD.attachChild(life2);

						} else if (gmController.getLifes() == 3) {

							gameHUD.attachChild(life1);
						}

						AlphaModifier alphaMod = new AlphaModifier(0.5f, 1.0f,
								0);
						alphaMod.setAutoUnregisterWhenFinished(true);
						this.registerEntityModifier(alphaMod);

						DelayModifier dMod = new DelayModifier(30,
								new IEntityModifierListener() {

									@Override
									public void onModifierStarted(
											IModifier<IEntity> pModifier,
											IEntity pItem) {
									}

									@Override
									public void onModifierFinished(
											IModifier<IEntity> pModifier,
											IEntity pItem) {

										runOnUpdateThread(new Runnable() {
											@Override
											public void run() {
												lifePowerIsOver = true;
											}

										});

									}

								});
						dMod.setAutoUnregisterWhenFinished(true);
						this.registerEntityModifier(dMod);
					}
				}

				return true;
			}
		};

		return btLifePower;
	}
*/
	private ButtonSprite addFreezeItem(int x, int y) {
		final ButtonSprite freezebutton = new ButtonSprite(x, y,
				freezeButtonTextureRegion, this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				ballonController.setFreezeCont(gmController.getLvl() + 1);
				gameHUD.unregisterTouchArea(this);
				AlphaModifier alphaMod = new AlphaModifier(0.5f, 1.0f, 0);
				alphaMod.setAutoUnregisterWhenFinished(true);
				this.registerEntityModifier(alphaMod);

				DelayModifier dMod = new DelayModifier(30,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> pModifier, IEntity pItem) {
							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> pModifier, IEntity pItem) {

								runOnUpdateThread(new Runnable() {
									@Override
									public void run() {
										freezeOn = false;
									}

								});

							}

						});
				dMod.setAutoUnregisterWhenFinished(true);
				this.registerEntityModifier(dMod);

				return true;
			}

		};

		return freezebutton;

	}

	private void addCloudBlock() {
		if (!cloudblockRunning) {
			cloudblockRunning = true;

			// Animation
			// -----------------------------------------------
			float moveDuration = 0.5f;
			float itemDuration = 15.0f;
			leftCloud = new Sprite(0, 0, leftCloudBlockTexture,
					this.getVertexBufferObjectManager());
			rightCloud = new Sprite(0, 0, rightCloudBlockTexture,
					this.getVertexBufferObjectManager());

			float yposition = bottomBar.getY() - leftCloud.getHeight();
			float xrightposition = CAMERA_WIDTH - rightCloud.getWidth();

			leftCloud.setPosition(0, yposition);
			rightCloud.setPosition(xrightposition, yposition);
			this.mScene.attachChild(leftCloud);
			this.mScene.attachChild(rightCloud);

			leftCloudIn = new MoveModifier(moveDuration,
					(leftCloud.getWidth() * -1), 0, yposition, yposition,
					EaseLinear.getInstance());
			leftCloudOut = new MoveModifier(moveDuration, 0,
					(leftCloud.getWidth() * -1), yposition, yposition,
					EaseLinear.getInstance());
			rightCloudIn = new MoveModifier(moveDuration, CAMERA_WIDTH,
					xrightposition, yposition, yposition,
					EaseLinear.getInstance());
			rightCloudOut = new MoveModifier(moveDuration, xrightposition,
					CAMERA_WIDTH, yposition, yposition,
					EaseLinear.getInstance());

			dModFinish = new DelayModifier(moveDuration,
					new IEntityModifierListener() {

						@Override
						public void onModifierStarted(
								IModifier<IEntity> pModifier, IEntity pItem) {
						}

						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {

							runOnUpdateThread(new Runnable() {
								@Override
								public void run() {
									removeCloudBlock();
								}
							});

						}
					});
			dModStart = new DelayModifier(itemDuration - moveDuration,
					new IEntityModifierListener() {

						@Override
						public void onModifierStarted(
								IModifier<IEntity> pModifier, IEntity pItem) {
						}

						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {

							runOnUpdateThread(new Runnable() {
								@Override
								public void run() {
									leftCloud.clearEntityModifiers();
									rightCloud.clearEntityModifiers();
									leftCloud
											.registerEntityModifier(leftCloudOut);
									rightCloud
											.registerEntityModifier(rightCloudOut);
									leftCloud
											.registerEntityModifier(dModFinish);
								}
							});

						}
					});

			leftCloud.registerEntityModifier(leftCloudIn);
			leftCloud.registerEntityModifier(dModStart);
			rightCloud.registerEntityModifier(rightCloudIn);
		}
	}

	public void removeCloudBlock() {
		System.out.println("REMOVE CLOUD");
		leftCloud.clearEntityModifiers();
		rightCloud.clearEntityModifiers();
		leftCloud.detachSelf();
		rightCloud.detachSelf();
		leftCloudIn.reset();
		leftCloudOut.reset();
		rightCloudIn.reset();
		rightCloudOut.reset();
		dModFinish.reset();
		dModStart.reset();
		cloudblockRunning = false;
	}

	public void addColorReminder() {
		colorreminderUsed = true;
		levelText.setText("Level " + gmController.getLvl() + ":");
		gameHUD.attachChild(levelText);
		clearAllBallons();

		if (ballonController.getRightColorsCont() == 1) {
			ballonController.getBallonsMenu()[0].setPosition((xPositions[2]),
					CAMERA_HEIGHT / 2);
			this.gameHUD.attachChild(ballonController.getBallonsMenu()[0]);
		}
		if (ballonController.getRightColorsCont() == 2) {
			ballonController.getBallonsMenu()[0].setPosition((xPositions[1]),
					CAMERA_HEIGHT / 2);
			this.gameHUD.attachChild(ballonController.getBallonsMenu()[0]);
			ballonController.getBallonsMenu()[1].setPosition((xPositions[3]),
					CAMERA_HEIGHT / 2);
			this.gameHUD.attachChild(ballonController.getBallonsMenu()[1]);

		}
		if (ballonController.getRightColorsCont() == 3) {
			ballonController.getBallonsMenu()[0].setPosition((xPositions[1]),
					CAMERA_HEIGHT / 2);
			this.gameHUD.attachChild(ballonController.getBallonsMenu()[0]);
			ballonController.getBallonsMenu()[1].setPosition((xPositions[2]),
					CAMERA_HEIGHT / 2);
			this.gameHUD.attachChild(ballonController.getBallonsMenu()[1]);
			ballonController.getBallonsMenu()[2].setPosition((xPositions[3]),
					CAMERA_HEIGHT / 2);
			this.gameHUD.attachChild(ballonController.getBallonsMenu()[2]);
		}
	}

	public void addTargetBlocker() {
		targetBlocker = 3;
	}

	public void useSecondChance() {

	}

	// -----------------------------------------------
	// Missions
	// -----------------------------------------------
	
	public void initMissions() {
		InputStream is = null;
		missionPack = sPreferences.loadPackagePreferences(sharedPreferences);
		try {
			is = this.getAssets().open("Mission.xml");
		} catch (IOException e) {
			Log.d("XML", "error creating input");
		}
		missions = saxParser.getMissions(is, missionPack);
		missionsStatus = sPreferences.loadMissionStatusPreferences(sharedPreferences);
		if(missionsStatus[0] == null || missionsStatus[0].isEmpty()){
			String[] stdValues = {"0","0","0"};
			sPreferences.saveMissionStatusPreferences(stdValues, sharedPreferences);
		}
			
	}
	
	public void initMissionText(){
		mDescripionFormatter();
		// Initialize Fonts
		final ITexture packTextFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		packTextFont = FontFactory.createFromAsset(getFontManager(),
				packTextFontTexture, getAssets(), "American Purpose Casual 01.ttf",
				122.0f, true, Color.WHITE_ABGR_PACKED_INT);
		packTextFont.load();
		missionStatusFont = FontFactory.createFromAsset(getFontManager(),
				fontTexture, getAssets(), "American Purpose Casual 01.ttf",
				22.0f, true, (Color.RED_ABGR_PACKED_INT + android.graphics.Color.argb(1, 245, 199, 73)));
		missionStatusFont.load();
		// Initialize text font
		m1Text = new Text(20, 420, newFont, "", 30, new TextOptions(
				HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
		m2Text = new Text(20, 420, newFont, "", 30, new TextOptions(
				HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
		m3Text = new Text(20, 420, newFont, "", 30, new TextOptions(
				HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
		m1StatusText = new Text(20, 420, newFont, "", 30, new TextOptions(
				HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
		m2StatusText = new Text(20, 420, newFont, "", 30, new TextOptions(
				HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
		m3StatusText = new Text(20, 420, newFont, "", 30, new TextOptions(
				HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
		packText = new Text(20, 420, packTextFont, "", 30, new TextOptions(
				HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
		//packText.setScale(3.0f);
		
		// Set text
		m1Text.setText(missions[0].getDescription());
		m2Text.setText(missions[1].getDescription());
		m3Text.setText(missions[2].getDescription());
		m1StatusText.setText(missionsStatus[0]+"/"+missions[0].getSingleRequiredKeys());
		m2StatusText.setText(missionsStatus[1]+"/"+missions[1].getSingleRequiredKeys());
		m3StatusText.setText(missionsStatus[2]+"/"+missions[2].getSingleRequiredKeys());
		packText.setText(String.valueOf(missionPack));
		
		// Define position
		m1Text.setPosition(181, 500);
		m2Text.setPosition(181, 625);
		m3Text.setPosition(181, 750);
		m1StatusText.setPosition(181, 600);
		m2StatusText.setPosition(181, 725);
		m3StatusText.setPosition(181, 850);
		packText.setPosition(180, 345);
		
	}

	public void mDescripionFormatter(){
		String aux = missions[0].getDescription().substring(0, 18) + "\n"+missions[0].getDescription().substring(18, missions[0].getDescription().length());
		missions[0].setDescription(aux);
		aux = missions[1].getDescription().substring(0, 18) + "\n"+missions[1].getDescription().substring(18, missions[1].getDescription().length());
		missions[1].setDescription(aux);
		aux = missions[2].getDescription().substring(0, 18) + "\n"+missions[2].getDescription().substring(18, missions[2].getDescription().length());
		missions[2].setDescription(aux);
	}

}