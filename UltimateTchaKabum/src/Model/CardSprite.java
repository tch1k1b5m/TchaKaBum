package Model;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class CardSprite extends Sprite{

	private Item item;
	private Sprite iconSprite;
	
	public CardSprite(Sprite iconSprite,int id, String name, int price,float pX, float pY, ITextureRegion pNormalTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion,pVertexBufferObjectManager);
		item = new Item(id, name, "", price);
		this.iconSprite = iconSprite;
	}


	public Sprite getIconSprite() {
		return iconSprite;
	}


	public Item getItem() {
		return item;
	}


	/*public void setItem(Item item) {
		this.item = item;
	}*/

}
