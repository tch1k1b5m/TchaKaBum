package com.tchakabum.ultimatetchakabum;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
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
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.EaseLinear;

import Controller.ItemController;
import Model.BallonSprite;
import Model.BalloonType;
import Model.CardSprite;
import Model.Item;
import SharedPreferences.SharedPreferencesController;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

public class StoreBalloons extends SimpleBaseGameActivity {

	private Camera camera;
	private static final int CAMERA_WIDTH = 960;
	private static final int CAMERA_HEIGHT = 1600;
	private Scene mScene;
	SharedPreferences sharedPreferences;
	private BuildableBitmapTextureAtlas backgroundTextureAtlas;

	// Cards
	private BuildableBitmapTextureAtlas cardsTextureAtlas;
	private ITextureRegion backgroundTextureRegion;
	private ITextureRegion BlasterCardTexture;
	private ITextureRegion FreezeCardTexture;
	private ITextureRegion DoubleComboCardTexture;
	private ITextureRegion LifeBoosterCardTexure;

	// Buttons
	private BuildableBitmapTextureAtlas buyTextureAtlas;
	private BuildableBitmapTextureAtlas equipTextureAtlas;
	private ITextureRegion buyTexture;

	// private ITextureRegion buyVipTexture;
	private ITextureRegion equipTexture;
	
	
	private Sprite btEquip;
	private Sprite btBuy;

	// Item Icons Textures
	private ITextureRegion comboPowerTextureRegion;
	private BuildableBitmapTextureAtlas comboPowerTextureAtlas;
	private ITextureRegion burstRightTextureRegion;
	private BuildableBitmapTextureAtlas burstRightTextureAtlas;
	private ITextureRegion lifePowerTextureRegion;
	private BuildableBitmapTextureAtlas lifePowerTextureAtlas;
	private ITextureRegion freezeButtonTextureRegion;
	private BuildableBitmapTextureAtlas freezeTextureAtlas;

	

	// Coins
	private BitmapTextureAtlas fontTexture;
	private Font font;
	private Text coinsText;

	// Teste Data Bind
	private ItemController itemController = new ItemController();
	private boolean IsUnderAnimation = false;
	Item itemSelected = null;
	private float ACT_START_X = 0;

	// Buy
	private int coinsLeftForAnimation = 0;
	private int coinsDecreaser = 25;
	
	//Balloon Type
	private BalloonType balloonType = new BalloonType();

	SharedPreferencesController SPController = new SharedPreferencesController();

	// -----------------------------------------------
	// Activity
	// -----------------------------------------------

	@Override
	public EngineOptions onCreateEngineOptions() {
		System.gc();
		mScene = null;
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
		// Texture Atlas
		this.backgroundTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 2048, 2048, TextureOptions.DEFAULT);
		this.cardsTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 2048, 2048, TextureOptions.DEFAULT);
		this.buyTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 512, 252, TextureOptions.DEFAULT);
		
		
		this.equipTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT);

		// Icons
		this.freezeTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 150, 150, TextureOptions.DEFAULT); // freeze
		this.freezeButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(freezeTextureAtlas, this, "freezeicon140.png");
		this.comboPowerTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 150, 150, TextureOptions.DEFAULT);
		this.comboPowerTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(comboPowerTextureAtlas, this, "combox2140.png");
		this.burstRightTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 150, 150, TextureOptions.DEFAULT);
		this.burstRightTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(burstRightTextureAtlas, this, "blaster140.png");
		this.lifePowerTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 150, 150, TextureOptions.DEFAULT);
		this.lifePowerTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(lifePowerTextureAtlas, this, "addlife140.png");

		// Texture Region
		this.backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(backgroundTextureAtlas, this, "store22.png");
		this.BlasterCardTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(cardsTextureAtlas, this, "blastercard.png");
		this.FreezeCardTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(cardsTextureAtlas, this, "freezecard.png");
		this.DoubleComboCardTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(cardsTextureAtlas, this, "combox2card2.png");
		this.LifeBoosterCardTexure = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(cardsTextureAtlas, this, "lifeboosterCard.png");

		// Buttons
		this.buyTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(buyTextureAtlas, this, "btbuy.png");
		
		this.equipTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(equipTextureAtlas, this, "btequip.png");
		
		// Box
		
		fontTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024,
				1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		try {
			this.backgroundTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.backgroundTextureAtlas.load();

			// Buttons
			this.buyTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.buyTextureAtlas.load();

			
			this.equipTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.equipTextureAtlas.load();

			
			// Cards
			this.cardsTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.cardsTextureAtlas.load();

			// Icons
			this.freezeTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.freezeTextureAtlas.load();

			this.comboPowerTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.comboPowerTextureAtlas.load();

			this.burstRightTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.burstRightTextureAtlas.load();

			this.lifePowerTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.lifePowerTextureAtlas.load();

			this.fontTexture.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}

	}

	@Override
	protected Scene onCreateScene() {
		this.mScene = new Scene();
		this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		createCards();
		createButtons();
		addCoins();
		ManageButtons();
		if (SPController.loadSetPreferences(sharedPreferences).length == 0
				|| SPController.loadSetPreferences(sharedPreferences) == null) {
			System.out.println("Set created");
			int[] set = { 0, 0, 0 };
			SPController.saveSetPreferences(set, sharedPreferences);
		}
		
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				backgroundTextureRegion, this.getVertexBufferObjectManager())));

		return this.mScene;
	}

	// -----------------------------------------------
	// Populate Scene
	// -----------------------------------------------

	public void onBackPressed() {
		startActivity(new Intent(StoreBalloons.this, MainActivity.class));
		StoreBalloons.this.finish();

	}

	private void addCoins() {
		this.font = FontFactory.create(this.getFontManager(),
				this.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 98,
				Color.BLACK.hashCode());
		font.load();
		coinsText = new Text(20, 300, font, "x0", 30, new TextOptions(
				HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
		coinsText.setText(SPController.loadCoinsPreferences(sharedPreferences));
		// mScene.attachChild(coinsText);
	}

	private void createCards() {
		Sprite iconComboPower = new Sprite(193, 740, comboPowerTextureRegion,
				this.getVertexBufferObjectManager());
		Sprite iconBurstBalloons = new Sprite(260, 740,
				burstRightTextureRegion, this.getVertexBufferObjectManager());
		Sprite iconLifePower = new Sprite(135, 740, lifePowerTextureRegion,
				this.getVertexBufferObjectManager());
		Sprite iconFreeze = new Sprite(60, 740, freezeButtonTextureRegion,
				this.getVertexBufferObjectManager());

		CardSprite aux = new CardSprite(iconBurstBalloons, balloonType.Cloud, "Cloud", 150,180, 500, BlasterCardTexture,	this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent touchEvent,
					final float X, final float Y) {
				if (touchEvent.isActionDown())
					ACT_START_X = X;
				if (touchEvent.isActionMove()) {
					// Esquerda
					if ((ACT_START_X - 100) > X) {
						if (!IsUnderAnimation) {
							StartCardsAnimation(true);
						}
					}
					// Direita
					else if ((ACT_START_X + 100) < X) {
						if (!IsUnderAnimation) {
							StartCardsAnimation(false);
						}
					}
				}
				return true;
			}
		};
		this.mScene.registerTouchArea(aux);
		itemController.AddCard(aux);

		//NOVO CÓDIGO
		itemController.AddCard(new CardSprite(iconFreeze, balloonType.Blizzard, "Freeze", 200,	180, 500, FreezeCardTexture, this.getVertexBufferObjectManager()));
		itemController.AddCard(new CardSprite(iconComboPower, balloonType.Combo,"Double Combo", 300, 180, 500, DoubleComboCardTexture, this.getVertexBufferObjectManager()));
		itemController.AddCard(new CardSprite(iconLifePower, balloonType.AddLife,"Life Booster", 1000, 180, 500, LifeBoosterCardTexure, this.getVertexBufferObjectManager()));
		this.mScene.attachChild(itemController.getActualCard());

	}

	private void createButtons() {
		// x and y postitions of btbuy with getvip 525, 1400
		btBuy = new Sprite(525, 1400, buyTexture,
				this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent touchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (touchEvent.isActionDown()) {
					BuyItem();
					System.out.println("buy");
				}
				return true;
			}

		};
		btEquip = new Sprite(525, 1400, equipTexture,
				this.getVertexBufferObjectManager()) {

		

		};
	
		btBuy.setPosition((CAMERA_WIDTH / 2 - btBuy.getWidth() / 2), 1400);
		btEquip.setPosition((CAMERA_WIDTH / 2 - btEquip.getWidth() / 2), 1400);		

		
	}

	// -----------------------------------------------
	// Functions
	// -----------------------------------------------

	private void StartCardsAnimation(final boolean toLeft) {

		float x11, x21, x12, x22;
		IsUnderAnimation = true;
		final CardSprite secondCard;
		if (toLeft) {
			x11 = itemController.getActualCard().getX();
			x21 = itemController.getActualCard().getWidth() * -1;
			x12 = CAMERA_WIDTH;
			x22 = itemController.getActualCard().getX();
			secondCard = itemController.getNextCard();

		} else {
			x12 = (itemController.getActualCard().getWidth() * -1);
			x22 = itemController.getActualCard().getX();
			x11 = itemController.getActualCard().getX();
			x21 = CAMERA_WIDTH;
			secondCard = itemController.getPrevCard();
		}
		IsUnderAnimation = true;
		mScene.attachChild(secondCard);
		/*
		 * itemController.getNextCard().setZIndex(2);
		 * itemController.getActualCard().setZIndex(1); mScene.sortChildren();
		 */
		itemController.getActualCard().registerEntityModifier(
				new MoveModifier(0.3f, x11, x21, itemController.getActualCard().getY(), itemController.getActualCard().getY(),EaseLinear.getInstance()));
		secondCard.registerEntityModifier(new MoveModifier(0.3f, x12, x22,itemController.getActualCard().getY(), itemController.getActualCard().getY(), EaseLinear.getInstance()));
		secondCard.registerEntityModifier(new DelayModifier(0.3f,new IEntityModifierListener() {

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
								secondCard.clearEntityModifiers();
								secondCard.clearUpdateHandlers();
								itemController.getActualCard()
										.clearEntityModifiers();
								itemController.getActualCard()
										.clearUpdateHandlers();
								itemController.getActualCard().setPosition(180,
										625);
								itemController.getActualCard().detachSelf();
								if (toLeft)
									itemController.increaseCardIndex();
								else
									itemController.decreaseCardIndex();
								IsUnderAnimation = false;
								ManageButtons();
							}
						});

					}
				}));

	}
	
	private void BuyItem() {

		ArrayList<Integer> balloonsIds = SPController.loadSpecialBalloonsPreferences(sharedPreferences);

		if (!balloonsIds.contains(itemController.getActualCard().getItem().getId())) {
			int coins = Integer.parseInt((String) coinsText.getText());
			if (coins > itemController.getActualCard().getItem().getPrice()) {
				coins -= itemController.getActualCard().getItem().getPrice();
				coinsText.setText(String.valueOf(coins));
				balloonsIds.add(itemController.getActualCard().getItem().getId());
				SPController.saveSpecialBalloonsPreferences(balloonsIds,sharedPreferences);
				coinsLeftForAnimation = itemController.getActualCard().getItem().getPrice();
				CoinsEffect();
				ManageButtons();
			}
		}

		

	}

	private void ManageButtons() {
		mScene.unregisterTouchArea(btEquip);
		mScene.unregisterTouchArea(btBuy);
		btEquip.detachSelf();
		
		btBuy.detachSelf();
		ArrayList<Integer> balloonsIds = SPController.loadSpecialBalloonsPreferences(sharedPreferences);
		//int[] itensInSet = SPController.loadSetPreferences(sharedPreferences);
		if (!balloonsIds.contains(itemController.getActualCard().getItem().getId())) {
			mScene.attachChild(btBuy);
			mScene.registerTouchArea(btBuy);
		} else {
			mScene.attachChild(btEquip);
			mScene.registerTouchArea(btEquip);
		}
	}

	public void CoinsEffect() {
		float timer = 0.1f;
		if (coinsLeftForAnimation > 500) {
			timer = 0.05f;
			coinsDecreaser = 20;
		} else {
			coinsDecreaser = 25;
		}
		itemController.getActualCard().registerEntityModifier(
				new AlphaModifier(0.3f, 1.0f, 0.3f));
		coinsText.setPosition((CAMERA_WIDTH / 2 - coinsText.getWidth() / 2),
				CAMERA_HEIGHT / 2);
		mScene.attachChild(coinsText);

		mScene.registerUpdateHandler(new TimerHandler(0.1f, true,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {

						runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								if (coinsLeftForAnimation == 0) {

									try {
										mEngine.stop();
										Thread.sleep(1000);
										mEngine.start();
									} catch (Exception e) {
									}
									itemController.getActualCard()
											.registerEntityModifier(
													new AlphaModifier(0.3f,
															0.3f, 1.0f));
									mScene.clearUpdateHandlers();
									coinsText.detachSelf();
								}
								System.out.println("loop moedinha");
								int coins = Integer.parseInt((String) coinsText
										.getText());
								coinsLeftForAnimation -= coinsDecreaser;
								coinsText.setText(String.valueOf(coins
										- coinsDecreaser));
								coinsText.clearEntityModifiers();

							}
						});

					}

				}));

	}
}