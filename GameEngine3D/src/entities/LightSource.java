package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class LightSource extends Entity{

	public LightSource(boolean isPlayer, TexturedModel model, Vector3f position, float rotX,
			float rotY, float rotZ, float scale) {
		super(isPlayer, model, position, rotX, rotY, rotZ, scale);
		// TODO Auto-generated constructor stub
	}

}
