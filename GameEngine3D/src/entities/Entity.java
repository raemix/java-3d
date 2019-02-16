package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import objConverter.ModelData;
@SuppressWarnings("unused")

public class Entity {

	TexturedModel model;
	private Vector3f position;
	private Vector3f measurments;
	protected float rotX, rotY, rotZ;
	private float scale;
	private int ID;
	private boolean isPlayer;
	public boolean shouldRender = true;
	
	private int textureIndex = 0;
	
	public Entity(int ID, boolean isPlayer, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.ID = ID;
		this.isPlayer = isPlayer;
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public Entity(int ID, boolean isPlayer, TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.ID = ID;
		this.isPlayer = isPlayer;
		this.model = model;
		this.textureIndex = textureIndex;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public Entity(TexturedModel model, Vector3f position, float scale) {

		this.model = model;
		this.position = position;
		this.rotX = this.rotY = this.rotZ = 0;
		this.scale = scale;
	}
	
	public Entity(Vector3f position, float scale) {
		this.position = position;
		this.rotX = this.rotY = this.rotZ = 0;
		this.scale = scale;
	}
	
	

	public void increasePosition(float dx, float dy, float dz) {
		this.position.x+=dx;
		this.position.y+=dy;
		this.position.z+=dz;

	}

	public void increaseRotation(float dx, float dy, float dz) {
		
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
		
	}
	public TexturedModel getModel() {
		return model;
	}
	public float getDistanceBetweenEntities(Vector3f entityThis, Vector3f entityOther) {
		if (entityThis == entityOther) return -1;
		return (float) (
				(Math.abs(entityThis.x - entityOther.x)) + 
				(Math.abs(entityThis.y - entityOther.y)) +
				(Math.abs(entityThis.z - entityOther.z)));
	}
	
	public void setModel(TexturedModel model) {
		this.model = model;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public float getRotX() {
		return rotX;
	}
	public void setRotX(float rotX) {
		this.rotX = rotX;
	}
	public float getRotY() {
		return rotY;
	}
	public void setRotY(float rotY) {
		this.rotY = rotY;
	}
	public float getRotZ() {
		return rotZ;
	}
	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	
	
	public TexturedModel getTexturedModel() {
		return model;
	}

	public boolean isShouldRender() {
		return shouldRender;
	}

	public int getID() {
		return ID;
	}

	public boolean getIsPlayer() {
		return isPlayer;
	}

	public void update() {
		
		
	}
	public void popEntity(int ID, List entities) {
		entities.remove(ID);
	}

	public void update(List<Entity> entities) {
		// TODO Auto-generated method stub
		
	}
	
	public float getTextureXOffset() {
		
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float) column / (float) model.getTexture().getNumberOfRows();
	}
	
	public float getTextureYOffset() {
		
		int row = textureIndex / model.getTexture().getNumberOfRows();
		return (float) row / (float) model.getTexture().getNumberOfRows();
	}
	
	
}
