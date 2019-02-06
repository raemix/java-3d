package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity{

	private static final float RUN_SPEED = 40;
	private static final float TURN_SPEED = 100;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;
	
	
	private float upwardSpeed = 0;
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	
	public Player(int ID, boolean isPlayer, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(ID, isPlayer, model, position, rotX, rotY, rotZ, scale);
		// TODO Auto-generated constructor stub
	}
	
	public float getCurrentSpeed() {
		return this.currentSpeed;
	}
	
	public void move(Terrain terrain) {
		checkInput();
		super.increaseRotation(0, 
				currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);

		upwardSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if (super.getPosition().y < terrainHeight) {
			super.getPosition().y = terrainHeight;
		}
	}
	
	private void jump() {
		this.upwardSpeed = JUMP_POWER;
	}
	
	private void checkInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.currentSpeed = RUN_SPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.currentSpeed = -RUN_SPEED;
		} else {
			this.currentSpeed = 0;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;

		} else {
			this.currentTurnSpeed = 0;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
		
	}
	
	@Override
	public void update() {
		
		
	}
}
