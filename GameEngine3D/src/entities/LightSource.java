package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class LightSource extends Entity{
	private static float RED = 1.0f;
	private static float GREEN = 1.0f;
	private static float BLUE = 1.0f;
	

	public LightSource(boolean isPlayer, TexturedModel model, Vector3f position, float rotX,
			float rotY, float rotZ, float scale) {
		super(isPlayer, model, position, rotX, rotY, rotZ, scale);
		// TODO Auto-generated constructor stub
	}


	public float getRED() {
		return RED;
	}


	public float getGREEN() {
		return GREEN;
	}


	public float getBLUE() {
		return BLUE;
	}

	
	
}
