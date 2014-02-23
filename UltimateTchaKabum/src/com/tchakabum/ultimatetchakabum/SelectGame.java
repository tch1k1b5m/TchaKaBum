package com.tchakabum.ultimatetchakabum;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.primitive.Rectangle;
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
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.font.Letter;
import org.andengine.opengl.font.exception.LetterNotFoundException;
import org.andengine.opengl.texture.ITexture;
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
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;

import Controller.ChallengeBalloonController;
import Controller.ChallengeGameController;
import Extension.PausableTimerHandler;
import Model.BallonSprite;
import android.R.bool;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.os.Looper;
import android.text.format.Time;
import android.view.KeyEvent;
import android.widget.PopupMenu;
import android.widget.Toast;

public class SelectGame extends SimpleBaseGameActivity {

	private Camera camera;
	private static final int CAMERA_WIDTH = 768;// 480
	private static final int CAMERA_HEIGHT = 1280;// 800
	private Scene mScene;

	private BuildableBitmapTextureAtlas backgroundTextureAtlas;
	private ITextureRegion backgroundTextureRegion;
	private BuildableBitmapTextureAtlas btChallengeTextureAtlas;
	private ITextureRegion btChallengeTextureRegion;
	private ITextureRegion btGeniusTextureRegion;
	private BuildableBitmapTextureAtlas bottombarTextureAtlas;
	private TextureRegion bottombarTextureRegion;
	private BuildableBitmapTextureAtlas btGeniusTextureAtlas;
	private BuildableBitmapTextureAtlas btTutorialTextureAtlas;
	private TextureRegion btTutorialTextureRegion;

	@Override
	public EngineOptions onCreateEngineOptions() {
		System.gc();
		// igualar a cena a nulo, parece ter corrigido o bug de repeticao do
		// popup/
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

		this.backgroundTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT,
				TextureOptions.DEFAULT);
		this.backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(backgroundTextureAtlas, this,
						"Primeira Camada.png");
		this.bottombarTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT,
				TextureOptions.DEFAULT);
		this.bottombarTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(bottombarTextureAtlas, this,
						"CAMADA2LIMPA.png");
		this.btChallengeTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 592, 242, TextureOptions.DEFAULT);
		this.btChallengeTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btChallengeTextureAtlas, this,
						"CHALLENGE.png");
		this.btGeniusTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 592, 242, TextureOptions.DEFAULT);
		this.btGeniusTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btGeniusTextureAtlas, this,
						"GENIUS.png");
		this.btTutorialTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 100, 100, TextureOptions.DEFAULT);
		this.btTutorialTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btTutorialTextureAtlas, this,
						"TUTORIAL.png");
		
		
		try {
			this.backgroundTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.backgroundTextureAtlas.load();
			
			this.bottombarTextureAtlas
			.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 0));
			this.bottombarTextureAtlas.load();
			
			this.btChallengeTextureAtlas
						.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.btChallengeTextureAtlas.load();
			this.btGeniusTextureAtlas
			.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
				0, 1, 0));
			this.btGeniusTextureAtlas.load();
			this.btTutorialTextureAtlas
			.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 0));
			this.btTutorialTextureAtlas.load();
			
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}

	}

	@Override
	protected Scene onCreateScene() {
		this.mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				backgroundTextureRegion, this.getVertexBufferObjectManager())));
		addMainButtons();
		return this.mScene;
	}
	
	private void addMainButtons() {
		float duration = 3.0f;
		float px;
		float py;
		Sprite bottombar = new Sprite(0, 0, bottombarTextureRegion, this.getVertexBufferObjectManager());
		this.mScene.attachChild(bottombar);
		final ButtonSprite btChallenge = new ButtonSprite(70, 250,
				btChallengeTextureRegion, this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pTouchEvent.isActionUp()) {
					startActivity(new Intent(SelectGame.this,
							ChallengeMode.class));
					SelectGame.this.finish();
				}
				return true;
			}

		};
		final ButtonSprite btGenius = new ButtonSprite(70, 480,
				btGeniusTextureRegion, this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pTouchEvent.isActionUp()) {
					startActivity(new Intent(SelectGame.this, GeniusMode.class));
					SelectGame.this.finish();
				}
				return true;
			}

		};
		
		ButtonSprite btTutorial = new ButtonSprite(70, 480,
				btTutorialTextureRegion, this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pTouchEvent.isActionUp()) {
					
				}
				return true;
			}

		};
		btChallenge.setPosition(CAMERA_WIDTH/2 - (btChallenge.getWidth()/2), 240);
		btGenius.setPosition(CAMERA_WIDTH/2 - (btGenius.getWidth()/2), 600);
		btTutorial.setPosition(CAMERA_WIDTH/2 - (btTutorial.getWidth()/2), CAMERA_HEIGHT - (btTutorial.getHeight() + 20));
		this.mScene.attachChild(btChallenge);
		this.mScene.registerTouchArea(btChallenge);
		this.mScene.attachChild(btGenius);
		this.mScene.registerTouchArea(btGenius);
		this.mScene.attachChild(btTutorial);
		px = btChallenge.getX();
		py = btChallenge.getY();
		float py2 = btGenius.getY();
		final ScaleModifier zoomIn = new ScaleModifier(duration, 0.95f, 1.0f);
		final ScaleModifier zoomOut = new ScaleModifier(duration, 1.0f, 0.95f);
		final MoveModifier moveDown = new MoveModifier(duration, px, px + 5, btChallenge.getY(), py + 5,
				EaseLinear.getInstance());
		final MoveModifier moveUp = new MoveModifier(duration, px + 5, px, py +5, py,
				EaseLinear.getInstance());
		final ScaleModifier zoomIn2 = new ScaleModifier(duration, 0.95f, 1.0f);
		final ScaleModifier zoomOut2 = new ScaleModifier(duration, 1.0f, 0.95f);
		final MoveModifier moveDown2 = new MoveModifier(duration, px, px + 5, btGenius.getY(), py2 + 5,
				EaseLinear.getInstance());
		final MoveModifier moveUp2 = new MoveModifier(duration, px + 5, px, py2 +5, py2,
				EaseLinear.getInstance());
		btChallenge.registerEntityModifier(zoomOut);
		btChallenge.registerEntityModifier(moveDown);
		btGenius.registerEntityModifier(zoomOut2);
		btGenius.registerEntityModifier(moveDown2);
		btChallenge.registerUpdateHandler(new IUpdateHandler() {
			
			@Override
			public void reset() {
			}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				if(zoomOut.isFinished()){
					btChallenge.unregisterEntityModifier(zoomOut);
					btChallenge.unregisterEntityModifier(moveDown);
					btChallenge.registerEntityModifier(zoomIn);
					btChallenge.registerEntityModifier(moveUp);
					btGenius.unregisterEntityModifier(zoomOut2);
					btGenius.unregisterEntityModifier(moveDown2);
					btGenius.registerEntityModifier(zoomIn2);
					btGenius.registerEntityModifier(moveUp2);
					zoomOut.reset();
					moveDown.reset();
					zoomOut2.reset();
					moveDown2.reset();
				}else if(zoomIn.isFinished()){
					btChallenge.unregisterEntityModifier(zoomIn);
					btChallenge.unregisterEntityModifier(moveUp);
					btChallenge.registerEntityModifier(zoomOut);
					btChallenge.registerEntityModifier(moveDown);
					
					btGenius.unregisterEntityModifier(zoomIn2);
					btGenius.unregisterEntityModifier(moveUp2);
					btGenius.registerEntityModifier(zoomOut2);
					btGenius.registerEntityModifier(moveDown2);
					zoomIn.reset();
					moveUp.reset();
					zoomIn2.reset();
					moveUp2.reset();
				}
			}
		});
	}

	public void onBackPressed() {
		startActivity(new Intent(SelectGame.this, MainActivity.class));
		SelectGame.this.finish();

	}

}