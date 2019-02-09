	
package engineTester;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
 
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import shaders.ShaderProgram;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import entities.Box;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
 
public class MainGameLoop {
	

	
 
    public static void main(String[] args) {
 
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        int nextID = 0;
        int mapSize = 1600;
        
        //**************** TERRAIN TEXTURE STUFF **********************
        
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
        
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        
        Terrain terrain = new Terrain(mapSize, 0,0,loader, texturePack, blendMap,"heightmap");
        //*******************************************************************
        
        
        
        
        
        RawModel playerModel = OBJLoader.loadObjModel("person", loader);
        TexturedModel person = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("playerTexture")));
        
        Player player = new Player(nextID, true, person, new Vector3f(500,25,50), 0, 0, 0, 1);
        nextID++;
        
        
        
        
        
        TexturedModel lowPolyTree = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader),
        		new ModelTexture(loader.loadTexture("lowPolyTree")));
         
        
        
        TexturedModel tree = new TexturedModel(OBJLoader.loadObjModel("tree", loader),
        		new ModelTexture(loader.loadTexture("tree")));
        
        TexturedModel box = new TexturedModel(OBJLoader.loadObjModel("box", loader),
        		new ModelTexture(loader.loadTexture("box")));
        
        
        
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
        		new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        
        
        
        
        ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
        fernTextureAtlas.setNumberOfRows(2);
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);
        
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        
        entities.add(0,player);
        
        for(int i=0;i<100;i++){
        	float x = random.nextFloat() * mapSize;
        	float z = random.nextFloat() * mapSize;
        	float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(nextID, false, tree, new Vector3f(x, y, z)
            		,0,i,0,8));
            nextID++;
        }
        
        for(int i=0;i<200;i++){
        	float x = random.nextFloat() * mapSize;
        	float z = random.nextFloat() * mapSize;
        	float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Box(nextID, false, new Vector3f(x, y + 10, z), 1));
            nextID++;

        }
        
        for(int i=0;i<100;i++){
        	float x = random.nextFloat() * mapSize;
        	float z = random.nextFloat() * mapSize;
        	float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(nextID, false, lowPolyTree, new Vector3f(x,
            		y,z),0,i,0,1));
            nextID++;

        }
        
        for(int i=0;i<500;i++){
        	float x = random.nextFloat() * mapSize;
        	float z = random.nextFloat() * mapSize;
        	float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(nextID, false, grass, new Vector3f(x,
            		y,z),0,i,0,1));
            nextID++;

        }
        
        for(int i=0;i<500;i++){
        	float x = random.nextFloat() * mapSize;
        	float z = random.nextFloat() * mapSize;
        	float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(nextID, false, fern, random.nextInt(4), new Vector3f(x,
            		y,z),0,i,0,1));
            nextID++;

        }
        
         
        Light light = new Light(new Vector3f(20000,20000,2000),
        		new Vector3f(1,1,1));

        MasterRenderer renderer = new MasterRenderer();
        
        Camera camera = new Camera(player);   

        
        while(!Display.isCloseRequested()){
            player.move(terrain);
            camera.move();
            for (Entity e : entities) {
            	e.update(entities);
            }
            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            


            for(Entity entity:entities){
            	if (entity.shouldRender) {
                renderer.processEntity(entity);
            	}
            }
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }
 
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
 
    }
 
}