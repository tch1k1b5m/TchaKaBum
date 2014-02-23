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
import Model.CardSprite;
import Model.Item;
import SharedPreferences.SharedPreferencesController;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

public class StoreItems extends SimpleBaseGameActivity {

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
	private BuildableBitmapTextureAtlas buyVipTextureAtlas;
	private ITextureRegion buyTexture;

	// private ITextureRegion buyVipTexture;
	private ITextureRegion equipTexture;
	private ITextureRegion unequipTexture;
	private Sprite btUnequip;
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

	// Sprite Boxes
	private BuildableBitmapTextureAtlas boxTextureAtlas;
	private ITextureRegion boxTexture;
	private ButtonSprite[] boxes = new ButtonSprite[3];
	private ButtonSprite box1;
	private ButtonSprite box2;
	private ButtonSprite box3;

	// Box functions
	private int selectedBox = 1;

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
		this.buyVipTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 512, 252, TextureOptions.DEFAULT);
		this.boxTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT);
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
		// this.buyVipTexture =
		// BitmapTextureAtlasTextureRegionFactory.createFromAsset(buyVipTextureAtlas,
		// this, "btgetvip.png");
		this.equipTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(equipTextureAtlas, this, "btequip.png");
		this.unequipTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(equipTextureAtlas, this, "btunequip.png");
		// Box
		this.boxTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(boxTextureAtlas, this, "box.png");
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

			this.buyVipTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.buyVipTextureAtlas.load();
			this.equipTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.equipTextureAtlas.load();

			// Box
			this.boxTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.boxTextureAtlas.load();

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
		this.sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		createCards();
		createButtons();
		addBoxes();
		addCoins();
		ManageButtons();
		if (SPController.loadSetPreferences(sharedPreferences).length == 0
				|| SPController.loadSetPreferences(sharedPreferences) == null) {
			System.out.println("Set created");
			int[] set = { 0, 0, 0 };
			SPController.saveSetPreferences(set, sharedPreferences);
		}
		loadIcons();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				backgroundTextureRegion, this.getVertexBufferObjectManager())));

		return this.mScene;
	}

	// -----------------------------------------------
	// Populate Scene
	// -----------------------------------------------

	private void loadIcons() {
		int[] set = SPController.loadSetPreferences(sharedPreferences);
		if (set[0] != 0) {
			System.out.println(set[0]);
			itemController.getByIndex(set[0]).getIconSprite()
					.setPosition(box1.getX() + 25, box1.getY() + 25);
			mScene.attachChild(itemController.getByIndex(set[0])
					.getIconSprite());
		}
		if (set[1] != 0) {
			itemController.getByIndex(set[1]).getIconSprite()
					.setPosition(box2.getX() + 25, box2.getY() + 25);
			mScene.attachChild(itemController.getByIndex(set[1])
					.getIconSprite());
		}
		if (set[2] != 0) {
			itemController.getByIndex(set[2]).getIconSprite()
					.setPosition(box3.getX() + 25, box3.getY() + 25);
			mScene.attachChild(itemController.getByIndex(set[2])
					.getIconSprite());
		}

	}

	public void onBackPressed() {
		startActivity(new Intent(StoreItems.this, MainActivity.class));
		StoreItems.this.finish();

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

		CardSprite aux = new CardSprite(iconBurstBalloons, 1, "Blaster", 150,
				180, 625, BlasterCardTexture,
				this.getVertexBufferObjectManager()) {
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

		itemController.AddCard(new CardSprite(iconFreeze, 2, "Freeze", 200,
				180, 625, FreezeCardTexture, this
						.getVertexBufferObjectManager()));
		itemController.AddCard(new CardSprite(iconComboPower, 3,
				"Double Combo", 300, 180, 625, DoubleComboCardTexture, this
						.getVertexBufferObjectManager()));
		itemController.AddCard(new CardSprite(iconLifePower, 4,
				"Life Booster", 1000, 180, 625, LifeBoosterCardTexure, this
						.getVertexBufferObjectManager()));
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

			public boolean onAreaTouched(TouchEvent touchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (touchEvent.isActionDown()) {
					System.out.println("Equip");
					int[] set = SPController
							.loadSetPreferences(sharedPreferences);
					if (set[selectedBox - 1] == 0) {
						set[selectedBox - 1] = itemController.getActualCard()
								.getItem().getId();
						SPController.saveSetPreferences(set, sharedPreferences);
						if (selectedBox == 1)
							itemController
									.getActualCard()
									.getIconSprite()
									.setPosition(box1.getX() + 25,
											box1.getY() + 25);
						if (selectedBox == 2)
							itemController
									.getActualCard()
									.getIconSprite()
									.setPosition(box2.getX() + 25,
											box2.getY() + 25);
						if (selectedBox == 3)
							itemController
									.getActualCard()
									.getIconSprite()
									.setPosition(box3.getX() + 25,
											box3.getY() + 25);
						mScene.attachChild(itemController.getActualCard()
								.getIconSprite());
						ManageButtons();
					}

				}
				return true;
			}

		};
		btUnequip = new Sprite(525, 1400, unequipTexture,
				this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent touchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (touchEvent.isActionDown()) {
					System.out.println("unequip");
					UnequipItem();

				}
				return true;
			}

		};

		btBuy.setPosition((CAMERA_WIDTH / 2 - btBuy.getWidth() / 2), 1400);
		btEquip.setPosition((CAMERA_WIDTH / 2 - btEquip.getWidth() / 2), 1400);
		btUnequip.setPosition((CAMERA_WIDTH / 2 - btUnequip.getWidth() / 2),
				1400);

		// Sprite btGetVip = new Sprite(125, 1400, buyVipTexture,
		// this.getVertexBufferObjectManager());
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
				new MoveModifier(0.3f, x11, x21, itemController.getActualCard()
						.getY(), itemController.getActualCard().getY(),
						EaseLinear.getInstance()));
		secondCard.registerEntityModifier(new MoveModifier(0.3f, x12, x22,
				itemController.getActualCard().getY(), itemController
						.getActualCard().getY(), EaseLinear.getInstance()));
		secondCard.registerEntityModifier(new DelayModifier(0.3f,
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

	private void addBoxes() {
		box1 = new ButtonSprite(0, 0, boxTexture,
				this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				selectBox(1);
				return true;
			}

		};
		box1.setPosition(((CAMERA_WIDTH / 5) - (box1.getWidth() / 2)), 400);
		box1.setAlpha(0.8f);

		box2 = new ButtonSprite(0, 0, boxTexture,
				this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				selectBox(2);
				return true;
			}

		};
		box2.setPosition((CAMERA_WIDTH / 2 - box2.getWidth() / 2), 400);
		box2.setAlpha(0.8f);

		box3 = new ButtonSprite(0, 0, boxTexture,
				this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				selectBox(3);
				return true;
			}

		};
		box3.setPosition(((CAMERA_WIDTH / 5) * 4 - box3.getWidth() / 2), 400);
		box3.setAlpha(0.8f);

		boxes[0] = box1;
		boxes[1] = box2;
		boxes[2] = box3;

		selectBox(1);

		this.mScene.attachChild(box1);
		this.mScene.attachChild(box2);
		this.mScene.attachChild(box3);
		this.mScene.registerTouchArea(box1);
		this.mScene.registerTouchArea(box2);
		this.mScene.registerTouchArea(box3);
	}

	private void selectBox(int boxIndex) {
		box1.setAlpha(0.8f);
		box2.setAlpha(0.8f);
		box3.setAlpha(0.8f);
		boxes[boxIndex - 1].setAlpha(1.0f);
		selectedBox = boxIndex;
	}

	private void BuyItem() {

		ArrayList<Integer> itemIds = SPController
				.loadChallengeItemsPreferences(sharedPreferences);

		if (!itemIds.contains(itemController.getActualCard().getItem().getId())) {
			int coins = Integer.parseInt((String) coinsText.getText());
			if (coins > itemController.getActualCard().getItem().getPrice()) {
				coins -= itemController.getActualCard().getItem().getPrice();
				coinsText.setText(String.valueOf(coins));
				itemIds.add(itemController.getActualCard().getItem().getId());
				SPController.saveChallengeItemsPreferences(itemIds,
						sharedPreferences);
				coinsLeftForAnimation = itemController.getActualCard().getItem()
						.getPrice();
				CoinsEffect();
				ManageButtons();
			}
		}

		

	}

	private void ManageButtons() {
		mScene.unregisterTouchArea(btEquip);
		mScene.unregisterTouchArea(btUnequip);
		mScene.unregisterTouchArea(btBuy);
		btEquip.detachSelf();
		btUnequip.detachSelf();
		btBuy.detachSelf();
		ArrayList<Integer> itemIds = SPController
				.loadChallengeItemsPreferences(sharedPreferences);
		int[] itensInSet = SPController.loadSetPreferences(sharedPreferences);
		if (!itemIds.contains(itemController.getActualCard().getItem().getId())) {
			mScene.attachChild(btBuy);
			mScene.registerTouchArea(btBuy);
		} else if (itensInSet[0] == itemController.getActualCard().getItem()
				.getId()
				|| itensInSet[1] == itemController.getActualCard().getItem()
						.getId()
				|| itensInSet[2] == itemController.getActualCard().getItem()
						.getId()) {
			mScene.attachChild(btUnequip);
			mScene.registerTouchArea(btUnequip);
		} else {
			mScene.attachChild(btEquip);
			mScene.registerTouchArea(btEquip);
		}
	}

	private void UnequipItem() {
		System.out.println("unequip");
		int[] set = SPController.loadSetPreferences(sharedPreferences);
		if (set[0] == itemController.getActualCard().getItem().getId()) {
			itemController
					.getByIndex(set[0])
					.getIconSprite()
					.setPosition(
							box1.getX()
									+ itemController.getByIndex(set[0])
											.getIconSprite().getWidth() / 2,
							box1.getY()
									+ itemController.getByIndex(set[0])
											.getIconSprite().getHeight() / 2);
			mScene.detachChild(itemController.getByIndex(set[0])
					.getIconSprite());
			set[0] = 0;
		}
		if (set[1] == itemController.getActualCard().getItem().getId()) {
			itemController.getByIndex(set[1]).getIconSprite()
					.setPosition(box2.getX() + 10, box2.getY() + 10);
			mScene.detachChild(itemController.getByIndex(set[1])
					.getIconSprite());
			set[1] = 0;
		}
		if (set[2] == itemController.getActualCard().getItem().getId()) {
			itemController.getByIndex(set[2]).getIconSprite()
					.setPosition(box3.getX() + 10, box3.getY() + 10);
			mScene.detachChild(itemController.getByIndex(set[2])
					.getIconSprite());
			set[2] = 0;
		}
		SPController.saveSetPreferences(set, sharedPreferences);
		ManageButtons();
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