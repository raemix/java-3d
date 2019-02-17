package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
@SuppressWarnings("unused")

public class Camera {
	
	private static float MIN_DISTANCE_FROM_PLAYER = 20.0f;
	private static float MAX_DISTANCE_FROM_PLAYER = 50.0f;
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	private Player player;
	private static boolean firstPerson = true;

	private Vector3f position = new Vector3f(0,0,0);
	
	private float pitch = 10;
	private float yaw = 0;
	private float roll = 0;
	private float speed = 1;
	
	
	public Camera(Player player) {
		this.player = player;
		position = new Vector3f(
				player.getPosition().x,player.getPosition().y,
				player.getPosition().z);
		
	}
	
	public void move() {
		if (player.getCurrentSpeed() > 0 ) {
			angleAroundPlayer = 0;
		}
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		
		float horizontalDistance = 0;
		float verticleDistance = 10;
		
		if (firstPerson) {
			horizontalDistance = 0;
			verticleDistance = 0;
		} else {
			horizontalDistance = calculateHorizontalDistance();
			verticleDistance = calculateVerticleDistance();
		}
		
		calculateCameraPosition(horizontalDistance,verticleDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}
	
	
	private void calculateCameraPosition(float horizontalDistance, float verticleDistance) {
		
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticleDistance + 10;
		
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticleDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
		if (distanceFromPlayer < MIN_DISTANCE_FROM_PLAYER) {
			distanceFromPlayer = MIN_DISTANCE_FROM_PLAYER;
		}
		if (distanceFromPlayer > MAX_DISTANCE_FROM_PLAYER) {
			distanceFromPlayer = MAX_DISTANCE_FROM_PLAYER;
		}
	}
	
	private void calculatePitch() {
		if (Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		}
	}
	
	private void calculateAngleAroundPlayer() {
		if (Mouse.isButtonDown(1)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
		
	}

	public boolean isFirstPerson() {
		return firstPerson;
	}

	public static void cycleFirstPerson() {
		firstPerson = !firstPerson;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
