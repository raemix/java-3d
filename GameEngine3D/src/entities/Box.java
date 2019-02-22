package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import objConverter.ModelData;
import renderEngine.*;
import textures.ModelTexture;
@SuppressWarnings("unused")

public class Box extends Entity {
	
    private static Loader loader = new Loader();

	private static float hoverDistance = 0.8f;
	private float initialY;
	private float dHoverY  = 0.01f;
	private static TexturedModel box = 
			new TexturedModel(OBJLoader.loadObjModel("box", loader),new ModelTexture(loader.loadTexture("box")));
	
	public Box(boolean b, Vector3f position, float scale) {
		super(position, scale);
		
		this.model = box;
		this.initialY = this.getPosition().y;
		this.increasePosition(0, (float) (Math.random() * 2 * hoverDistance - 1), 0);

	}
	

	@Override
	public void update(List<Entity> entities) {
		Entity player = entities.get(0);
	
		if (shouldRender) {
			if (this.getPosition().y >= this.initialY + hoverDistance)  {
				this.setPosition(new Vector3f(this.getPosition().x, this.initialY + hoverDistance, this.getPosition().z));
				this.dHoverY *= -1;
			}
			if (this.getPosition().y <= this.initialY - hoverDistance) {
				this.setPosition(new Vector3f(this.getPosition().x, this.initialY - hoverDistance, this.getPosition().z));
				this.dHoverY *= -1;
			}
			this.increasePosition(0, this.dHoverY, 0);
			this.rotY++;
			
		if (this.checkCollision(this, player) < 15.0f && this.checkCollision(this, player) > 0) {
			System.out.println(this.getPosition());
			this.shouldRender = false;
			}
		}
	}

	private float checkCollision(Entity thisEntity, Entity otherEntity) {
		return this.getDistanceBetweenEntities(thisEntity.getPosition(), otherEntity.getPosition());
	}

	

}
