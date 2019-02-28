	
package engineTester;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
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
import toolbox.MousePicker;
import toolbox.Time;
import entities.Box;
import entities.Camera;
import entities.Entity;
import entities.LampPost;
import entities.Light;
import entities.LightSource;
import entities.Player;
import entities.Rabbit;
import guis.GuiRenderer;
import guis.GuiTexture;
 
public class MainGameLoop {
	
    static Loader loader;
    static int mapSize = 1200;
    static final float MAX_LIGHT = 1.0f;
	static Terrain terrain;
	static List<Entity> entities;
	static Player player;
	static List<Light> lights;
	static List<LightSource> lightSources;
	

 
    public static void main(String[] args) {
 
    	loader = new Loader();
    	DisplayManager.createDisplay();

        RawModel playerModel = OBJLoader.loadObjModel("person", loader);
        TexturedModel person = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("playerTexture")));
    	player = new Player(true, person, new Vector3f(200,25,200), 0, 0, 0, 1);


        MasterRenderer renderer = new MasterRenderer(loader);
        Camera camera = new Camera(player);   
        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiRenderer guiRenderer = new GuiRenderer(loader);
        MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
        
    	initEntities();

		/*
		 * ******************************************************************** 
		 * Game
		 * Logic
		 ********************************************************************
		 */        
        
        while(!Display.isCloseRequested()){
        	
        	if (player.isGoAheadAndFlush()) {
        		player.setGoAheadAndFlush(false);
        		player.setReadyToFlush(false);
        		flushEntities();
        	}
            player.move(terrain);
            camera.move();
            picker.update();
            
            
            
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
            renderer.render(lights, camera);
            guiRenderer.render(guis);
            Time.updateTime();
        }
 
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
 
    }
    
    public static void flushEntities() {
    	
    	entities.clear();
    	lights.clear();
    	lightSources.clear();
    	initEntities();
    }
    
    private static void initEntities() {
    	//**************** TERRAIN TEXTURE STUFF **********************
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        terrain = new Terrain(mapSize, 0,0,loader, texturePack, blendMap,"heightmap");
        //*******************************************************************

        TexturedModel lowPolyTree = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader),
        		new ModelTexture(loader.loadTexture("lowPolyTree")));
         
        TexturedModel tree = new TexturedModel(OBJLoader.loadObjModel("tree", loader),
        		new ModelTexture(loader.loadTexture("tree")));
        
        TexturedModel box = new TexturedModel(OBJLoader.loadObjModel("box", loader),
        		new ModelTexture(loader.loadTexture("box")));
        
        TexturedModel rabbit = new TexturedModel(OBJLoader.loadObjModel("rabbit", loader),
        		new ModelTexture(loader.loadTexture("rabbit")));
        
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
        		new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
                
        ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
        fernTextureAtlas.setNumberOfRows(2);
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);
        
        entities = new ArrayList<Entity>();
        Random random = new Random();
        
        entities.add(0,player);
        
        for(int i=0;i<50;i++){
        	float x = random.nextFloat() * mapSize;
        	float z = random.nextFloat() * mapSize;
        	float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(false, tree, new Vector3f(x, y, z)
            		,0,i,0,8));
        }
        
        for(int i=0;i<100;i++){
        	float x = random.nextFloat() * mapSize;
        	float z = random.nextFloat() * mapSize;
        	float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Box(false, new Vector3f(x, y + 10, z), 1));
        }
        
        for(int i=0;i<200;i++){
        	float x = random.nextFloat() * mapSize;
        	float z = random.nextFloat() * mapSize;
        	float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Rabbit(false, new Vector3f(x, y, z), 0.33f));
        }
        
        for(int i=0;i<50;i++){
        	float x = random.nextFloat() * mapSize;
        	float z = random.nextFloat() * mapSize;
        	float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(false, lowPolyTree, new Vector3f(x,
            		y,z),0,i,0,1));
        }
        
        for(int i=0;i<500;i++){
        	float x = random.nextFloat() * mapSize;
        	float z = random.nextFloat() * mapSize;
        	float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(false, grass, new Vector3f(x,
            		y,z),0,i,0,1));
        }
        
        for(int i=0;i<100;i++){
        	float x = random.nextFloat() * mapSize;
        	float z = random.nextFloat() * mapSize;
        	float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(false, fern, random.nextInt(4), new Vector3f(x,
            		y,z),0,i,0,1));
        }
        
        lights = new ArrayList<Light>();
        lightSources = new ArrayList<LightSource>();

        //Add Sun
        lights.add(new Light(new Vector3f(0,1000,-7000), new Vector3f(MAX_LIGHT,MAX_LIGHT,MAX_LIGHT)));
        
        // add lamps to map
        for (int i = 0;i<3;i++) {
        	float x = player.getPosition().x + ((random.nextFloat() * 100) - 50);
        	float z = player.getPosition().z + ((random.nextFloat() * 100) - 50);
        	float y = terrain.getHeightOfTerrain(x, z);
            lightSources.add(new LampPost(false,new Vector3f(x,y,z),0,0,0,1));
        }
        
        //bind light source to lamp
        for (LightSource lightSource : lightSources) {
        	lightSource.getTexturedModel().getTexture().setUseFakeLighting(true);
        	lights.add(new Light(new Vector3f(lightSource.getPosition().x,
        			lightSource.getPosition().y + 13.0f,lightSource.getPosition().z),
        			new Vector3f(lightSource.getRED(),lightSource.getGREEN(),lightSource.getBLUE()),new Vector3f(1.0f,0.01f,0.002f)));
        	entities.add(lightSource);
        }
    	
    }
 
}