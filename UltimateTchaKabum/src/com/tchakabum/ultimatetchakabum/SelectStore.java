package com.tchakabum.ultimatetchakabum;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import android.content.Intent;

public class SelectStore extends SimpleBaseGameActivity {

	private Camera camera;
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	private Scene mScene;

	private BuildableBitmapTextureAtlas backgroundTextureAtlas;
	private ITextureRegion backgroundTextureRegion;
	private BuildableBitmapTextureAtlas btChallengeTextureAtlas;
	private ITextureRegion btChallengeTextureRegion;
	private ITextureRegion btGeniusTextureRegion;

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
		this.btChallengeTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 350, 207, TextureOptions.DEFAULT);
		this.btChallengeTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btChallengeTextureAtlas, this,
						"btnChallengeMode.png");
		this.btGeniusTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(backgroundTextureAtlas, this,
						"btnGeniusMode.png");
		try {
			this.backgroundTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.backgroundTextureAtlas.load();
			this.btChallengeTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.btChallengeTextureAtlas.load();
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
		ButtonSprite btChallenge = new ButtonSprite(70, 250,
				btChallengeTextureRegion, this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pTouchEvent.isActionUp()) {
					startActivity(new Intent(SelectStore.this,StoreItems.class));
					SelectStore.this.finish();
				}
				return true;
			}

		};
		ButtonSprite btGenius = new ButtonSprite(70, 480,
				btGeniusTextureRegion, this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent pTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pTouchEvent.isActionUp()) {
					startActivity(new Intent(SelectStore.this, StoreBalloons.class));
					SelectStore.this.finish();
				}
				return true;
			}

		};

		this.mScene.attachChild(btChallenge);
		this.mScene.registerTouchArea(btChallenge);
		this.mScene.attachChild(btGenius);
		this.mScene.registerTouchArea(btGenius);
	}

	public void onBackPressed() {
		startActivity(new Intent(SelectStore.this, MainActivity.class));
		SelectStore.this.finish();

	}
}
