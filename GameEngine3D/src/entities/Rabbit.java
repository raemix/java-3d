package entities;



	import java.util.ArrayList;
	import java.util.List;

	import org.lwjgl.util.vector.Vector3f;

	import models.TexturedModel;
	import objConverter.ModelData;
	import renderEngine.*;
	import textures.ModelTexture;
	@SuppressWarnings("unused")

public class Rabbit extends Entity {
	
    private static Loader loader = new Loader();

	private float initialY;
	private static TexturedModel rabbit = 
			new TexturedModel(OBJLoader.loadObjModel("rabbit", loader),new ModelTexture(loader.loadTexture("rabbit")));
	
	public Rabbit(boolean b, Vector3f position, float scale) {
		super(position, scale);
		
		this.model = rabbit;
		this.initialY = this.getPosition().y;

	}
	

	@Override
	public void update(List<Entity> entities) {
		
		
	}

}
