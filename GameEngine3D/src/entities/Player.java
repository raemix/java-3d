package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;
@SuppressWarnings("unused")

public class Player extends Entity{

	private static final float RUN_SPEED = 30;
	private static final float TURN_SPEED = 85;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;
	
	
	private float upwardSpeed = 0;
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;

	private float strafeSpeed = RUN_SPEED * 0.75f;
	private float currentStrafeSpeed = 0;
	private boolean ableToCycleFirstPerson = true;
	
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
		float strafeDistance = (float) (currentStrafeSpeed * DisplayManager.getFrameTimeSeconds());

		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		
		float sx = (float) (strafeDistance * Math.cos(Math.toRadians(super.getRotY())));
		float sz = (float) (strafeDistance * Math.sin(Math.toRadians(super.getRotY())));
		
		super.increasePosition(dx, 0, dz);
		super.increasePosition(sx, 0, -sz);
		
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
		
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			this.currentStrafeSpeed = strafeSpeed;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_E)){
			this.currentStrafeSpeed = -strafeSpeed;
		} else {
			this.currentStrafeSpeed = 0;
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
		
		if (Keyboard.isKeyDown(Keyboard.KEY_F5) && ableToCycleFirstPerson) {
			ableToCycleFirstPerson = false;
			Camera.cycleFirstPerson();
			
		}
		if (!Keyboard.isKeyDown(Keyboard.KEY_F5)) {
			ableToCycleFirstPerson = true;;

		}
	}
	
	@Override
	public void update() {
		
		
	}
}
