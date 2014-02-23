package com.tchakabum.ultimatetchakabum;

import java.util.StringTokenizer;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
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
import org.andengine.util.debug.Debug;

import SharedPreferences.SharedPreferencesController;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

public class Ranking extends SimpleBaseGameActivity{

	private Camera camera;
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	private Scene mScene;
		
	private BuildableBitmapTextureAtlas backgroundTextureAtlas;
	private ITextureRegion backgroundTextureRegion;
	
	private Font font;
	private Text comboText;
	private Text firstText;
	private Text secondText;
	private Text thirdText;
	private Text fourthText;
	private Text fifthText;
	private Text sixthText;
	private Text seventhText;
	private Text eighthText;
	private Text ninthText;
	private Text tenthText;
	private Text firstScoreText;
	private Text secondScoreText;
	private Text thirdScoreText;
	private Text fourthScoreText;
	private Text fifthScoreText;
	private Text sixthScoreText;
	private Text seventhScoreText;
	private Text eighthScoreText;
	private Text ninthScoreText;
	private Text tenthScoreText;
	
	SharedPreferences sharedPreferences;
	private SharedPreferencesController sPreferences = new SharedPreferencesController();	
	
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
				this.getTextureManager(), 512, 1024, TextureOptions.DEFAULT);
		this.backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(backgroundTextureAtlas, this, "background.png");
		try {
			this.backgroundTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.backgroundTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	@Override
	protected Scene onCreateScene() {
		this.mScene = new Scene();
		this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				backgroundTextureRegion, this.getVertexBufferObjectManager())));
		createRanking();
		createChallengeRanking();
		return this.mScene;
	}
	
	private void createRanking(){
		this.font = FontFactory.create(this.getFontManager(),
				this.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.font.load();
		firstText = new Text(160, 270, font, "1. ",30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		secondText = new Text(160, 310, font, "2. ",30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		thirdText = new Text(160, 350, font, "3. ",30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		fourthText = new Text(160, 390, font, "4. ",30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		fifthText = new Text(160, 430, font, "5. ",30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		sixthText = new Text(160, 470, font, "6. ",30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		seventhText = new Text(160, 510, font, "7. ",30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		eighthText = new Text(160, 550, font, "8. ",30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		ninthText = new Text(160, 590, font, "9. ",30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		tenthText = new Text(160, 630, font, "10. ",30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		
		this.mScene.attachChild(firstText);
		this.mScene.attachChild(secondText);
		this.mScene.attachChild(thirdText);
		this.mScene.attachChild(fourthText);
		this.mScene.attachChild(fifthText);
		this.mScene.attachChild(sixthText);
		this.mScene.attachChild(seventhText);
		this.mScene.attachChild(eighthText);
		this.mScene.attachChild(ninthText);
		this.mScene.attachChild(tenthText);
	}
	
	private void createChallengeRanking(){
		this.font = FontFactory.create(this.getFontManager(),
				this.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.font.load();
		int scores [] = sPreferences.loadScorePreferences(sharedPreferences);
		comboText = new Text(160, 210, font, "Combo: "+ sPreferences.loadComboPreferences(sharedPreferences),30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		firstScoreText = new Text(220, 270, font, " "+scores[9],30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		secondScoreText = new Text(220, 310, font, " "+scores[8],30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		thirdScoreText = new Text(220, 350, font, " "+scores[7],30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		fourthScoreText = new Text(220, 390, font, " "+scores[6],30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		fifthScoreText = new Text(220, 430, font, " "+scores[5],30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		sixthScoreText = new Text(220, 470, font, " "+scores[4],30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		seventhScoreText = new Text(220, 510, font, " "+scores[3],30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		eighthScoreText = new Text(220, 550, font, " "+scores[2],30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		ninthScoreText = new Text(220, 590, font, " "+scores[1],30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		tenthScoreText = new Text(220, 630, font, " "+scores[0],30, new TextOptions(
				HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		
				
		this.mScene.attachChild(comboText);
		this.mScene.attachChild(firstScoreText);
		this.mScene.attachChild(secondScoreText);
		this.mScene.attachChild(thirdScoreText);
		this.mScene.attachChild(fourthScoreText);
		this.mScene.attachChild(fifthScoreText);
		this.mScene.attachChild(sixthScoreText);
		this.mScene.attachChild(seventhScoreText);
		this.mScene.attachChild(eighthScoreText);
		this.mScene.attachChild(ninthScoreText);
		this.mScene.attachChild(tenthScoreText);
		
	}
		// -----------------------------------------------
		// Game Engine
		// -----------------------------------------------

		@Override
		public void onBackPressed() {
			startActivity(new Intent(Ranking.this, MainActivity.class));
			Ranking.this.finish();
	
		}
		
}
