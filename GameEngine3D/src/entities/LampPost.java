package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import textures.ModelTexture;

public class LampPost extends LightSource{
	
    private static Loader loader = new Loader();

	
	private static TexturedModel model = new TexturedModel(OBJLoader.loadObjModel("lamp", loader),new ModelTexture(loader.loadTexture("lamp")));

	public LampPost(boolean isPlayer, Vector3f position, float rotX, float rotY,
			float rotZ, float scale) {
		super(isPlayer, model, position, rotX, rotY, rotZ, scale);

		// TODO Auto-generated constructor stub
	}

	

}
