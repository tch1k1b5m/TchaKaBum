package Model;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


public class BallonSprite extends AnimatedSprite{

	private int balloonColor;
	
	private boolean IsInUse;
	
	
	public BallonSprite(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject, int balloonColor) {
		super(pX, pY, pTiledTextureRegion, pTiledSpriteVertexBufferObject);
		this.balloonColor = balloonColor;
		
	}
	
	public BallonSprite(float pX, float pY,
			TiledTextureRegion TextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			int balloonColor) {
		super(pX, pY, TextureRegion, vertexBufferObjectManager);
		this.balloonColor = balloonColor;
	}

	public boolean getIsInUse() {
		return IsInUse;
	}

	public void setIsInUse(boolean isInUse) {
		IsInUse = isInUse;
	}

	public int GetBalloonColor(){
		return balloonColor;
	}



}