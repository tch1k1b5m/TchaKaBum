package com.tchakabum.ultimatetchakabum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.SoundFactory;
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
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
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
import SharedPreferences.SharedPreferencesController;
import android.R.bool;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.view.KeyEvent;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends SimpleBaseGameActivity {

	private Camera camera;
	private static final int CAMERA_WIDTH = 480;// 480
	private static final int CAMERA_HEIGHT = 800;
	private Scene mScene;

	private BuildableBitmapTextureAtlas btMainTextureAtlas;
	private ITextureRegion btSelecaoModoTextureRegion;
	private ITextureRegion btRankingTextureRegion;
	private ITextureRegion btStoreTextureRegion;

	private ITextureRegion btMusicStopTextureRegion;
	private ITextureRegion btMusicPlayTextureRegion;// music
	private Music myMusic;

	private ITextureRegion btSoundStopTextureRegion;
	private ITextureRegion btSoundPlayTextureRegion;//sound
	
	private ITextureRegion btCreditsTextureRegion;
	
	private ITextureRegion logoTextureRegion;
	
	private ButtonSprite btMusicStop;
	private ButtonSprite btMusicPlay;
	
	private ButtonSprite btSoundStop;
	private ButtonSprite btSoundPlay;
	
	private ButtonSprite btCredits;
	
	private Sprite logoSprite;
	
	SharedPreferencesController SPController = new SharedPreferencesController();
	SharedPreferences sharedPreferences;
	private boolean playMusic;

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

		engineoptions.getAudioOptions().setNeedsSound(true);// NOVO para som
		engineoptions.getAudioOptions().setNeedsMusic(true);

		return engineoptions;
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		MusicFactory.setAssetBasePath("mfx/"); // music

		this.btMainTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 512, 1024, TextureOptions.DEFAULT);
		this.btSelecaoModoTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btMainTextureAtlas, this, "Jogar.png");
		this.btRankingTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btMainTextureAtlas, this, "Recordes.png");
		this.btStoreTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btMainTextureAtlas, this, "Loja.png");
		this.btMusicStopTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btMainTextureAtlas, this, "Music - ON.png");
		this.btMusicPlayTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btMainTextureAtlas, this, "Music - OFF.png");
		this.btSoundStopTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btMainTextureAtlas, this, "Sound - ON.png");
		this.btSoundPlayTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btMainTextureAtlas, this, "Sound - OFF.png");
		this.btCreditsTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btMainTextureAtlas, this, "Creditos.png");
		this.logoTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btMainTextureAtlas, this, "Logo.png");
		try {
			
			this.btMainTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.btMainTextureAtlas.load();

		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}

		// MUSIC
		try {
			this.myMusic = MusicFactory.createMusicFromAsset(
					this.mEngine.getMusicManager(), this, "tchakabum.mp3");
			this.myMusic.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}

		myMusic.play();

		// Music
	}

	@Override
	protected Scene onCreateScene() {
		this.mScene = new Scene();
		this.sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		mScene.setBackground(new Background(0.643f,0.843f,0.921f));
		playMusic = SPController.loadMusicPreferences(sharedPreferences);
		addMainButtons();
		addLogo();
		return this.mScene;
	}

	private void addMainButtons() {
		final ButtonSprite btPlay = new ButtonSprite((CAMERA_WIDTH/2 - 177), 240,
				btSelecaoModoTextureRegion, this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pTouchEvent.isActionUp()) {
					startActivity(new Intent(MainActivity.this,
							SelectGame.class));
					MainActivity.this.finish();
				}
				return true;
			}

		};
		ButtonSprite btRanking = new ButtonSprite((CAMERA_WIDTH/2 - 177), 380,
				btRankingTextureRegion, this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pTouchEvent.isActionUp()) {
					startActivity(new Intent(MainActivity.this, Ranking.class));
					MainActivity.this.finish();
				}
				return true;
			}

		};
		ButtonSprite btStore = new ButtonSprite((CAMERA_WIDTH/2 - 177), 530, btStoreTextureRegion,
				this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pTouchEvent.isActionUp()) {
					startActivity(new Intent(MainActivity.this, SelectStore.class));
					MainActivity.this.finish();
				}
				return true;
			}

		};

		// btmusic
		
		btMusicStop = new ButtonSprite(320, 685, btMusicStopTextureRegion,
				this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pTouchEvent.isActionUp()) {
					SPController.saveMusicPreferences(false, sharedPreferences);
					mScene.attachChild(btMusicPlay);
					mScene.unregisterTouchArea(btMusicStop);
					mScene.registerTouchArea(btMusicPlay);
					btMusicStop.detachSelf();

					myMusic.pause();

				}
				return true;
			}
		};

		btMusicPlay = new ButtonSprite(320, 685, btMusicPlayTextureRegion,
				this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pTouchEvent.isActionUp()) {
					SPController.saveMusicPreferences(true, sharedPreferences);
					mScene.attachChild(btMusicStop);
					mScene.unregisterTouchArea(btMusicPlay);
					mScene.registerTouchArea(btMusicStop);
					btMusicPlay.detachSelf();

					myMusic.resume();

				}
				return true;
			}
		};
		//btSound
		
				btSoundStop = new ButtonSprite(400, 685, btSoundStopTextureRegion,
						this.getVertexBufferObjectManager()) {
					public boolean onAreaTouched(TouchEvent pTouchEvent,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						if (pTouchEvent.isActionUp()) {
							
							mScene.attachChild(btSoundPlay);
							mScene.unregisterTouchArea(btSoundStop);
							mScene.registerTouchArea(btSoundPlay);
							btSoundStop.detachSelf();

						}
						return true;
					}
				};

				btSoundPlay = new ButtonSprite(400, 685, btSoundPlayTextureRegion,
						this.getVertexBufferObjectManager()) {
					public boolean onAreaTouched(TouchEvent pTouchEvent,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						if (pTouchEvent.isActionUp()) {
							mScene.attachChild(btSoundStop);
							mScene.unregisterTouchArea(btSoundPlay);
							mScene.registerTouchArea(btSoundStop);
							btSoundPlay.detachSelf();
							

						}
						return true;
					}
				};
				
				btCredits = new ButtonSprite(0, 721, btCreditsTextureRegion,
						this.getVertexBufferObjectManager()) {
					public boolean onAreaTouched(TouchEvent pTouchEvent,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						if (pTouchEvent.isActionUp()) {
														

						}
						return true;
					}
				};
				
			this.mScene.attachChild(btPlay);
			this.mScene.registerTouchArea(btPlay);
			this.mScene.attachChild(btRanking);
			this.mScene.registerTouchArea(btRanking);
			this.mScene.attachChild(btStore);
			this.mScene.registerTouchArea(btStore);
			this.mScene.attachChild(btCredits);
			this.mScene.registerTouchArea(btCredits);
		
		
		// btmusic
		if (playMusic) {
			this.mScene.attachChild(btMusicStop);
			this.mScene.attachChild(btSoundStop);
			this.mScene.registerTouchArea(btMusicStop);
			this.mScene.registerTouchArea(btSoundStop);
		} else {
			this.mScene.attachChild(btMusicPlay);
			this.mScene.registerTouchArea(btMusicPlay);
			this.mScene.attachChild(btSoundPlay);
			this.mScene.registerTouchArea(btSoundPlay);
			myMusic.pause();
		}
		
						
	}
	
	public void addLogo(){
		
		logoSprite = new Sprite((CAMERA_WIDTH/2 - 223), 30, logoTextureRegion,
				this.getVertexBufferObjectManager());
		float duration = 2.0f;
		float px = logoSprite.getX();
		float py= logoSprite.getY();
		final ScaleModifier zoomIn = new ScaleModifier(duration, 0.98f, 1.0f);
		final ScaleModifier zoomOut = new ScaleModifier(duration, 1.0f, 0.98f);
		final MoveModifier moveDown = new MoveModifier(duration, px, px + 5, logoSprite.getY(), py + 5,
				EaseLinear.getInstance());
		final MoveModifier moveUp = new MoveModifier(duration, px + 5, px, py +5, py,
				EaseLinear.getInstance());
		logoSprite.registerEntityModifier(zoomOut);
		logoSprite.registerEntityModifier(moveDown);
		logoSprite.registerUpdateHandler(new IUpdateHandler() {
			
			@Override
			public void reset() {
			}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				if(zoomOut.isFinished()){
					logoSprite.unregisterEntityModifier(zoomOut);
					logoSprite.unregisterEntityModifier(moveDown);
					logoSprite.registerEntityModifier(zoomIn);
					logoSprite.registerEntityModifier(moveUp);
					zoomOut.reset();
					moveDown.reset();
				}else if(zoomIn.isFinished()){
					logoSprite.unregisterEntityModifier(zoomIn);
					logoSprite.unregisterEntityModifier(moveUp);
					logoSprite.registerEntityModifier(zoomOut);
					logoSprite.registerEntityModifier(moveDown);
					zoomIn.reset();
					moveUp.reset();
				}
			}
		});
		this.mScene.attachChild(logoSprite);
		
	}


}