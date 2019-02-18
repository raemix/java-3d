package skybox;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import models.RawModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import shaders.TerrainShader;

public class SkyboxRenderer {

	private static final float SIZE = 500f;
	
	private static final float[] VERTICES = {        
	    -SIZE,  SIZE, -SIZE,
	    -SIZE, -SIZE, -SIZE,
	    SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	    -SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE
	};
	
	
	private static String[] TEXTURE_FILES = {"sbright", "sbleft", "sbtop", "sbbottom", "sbback", "sbfront"};
	private static String[] NIGHT_TEXTURE_FILES = {"sbnightRight", "sbnightLeft", "sbnightTop", "sbnightBottom", "sbnightBack", "sbnightFront"};

	private TerrainShader terrainShader = new TerrainShader();
	private RawModel cube;
	private int texture;
	private int nightTexture;
	private SkyboxShader shader;
	private float time = 0.0f;
	private float timeSpeed = 0.01f;
	private float maxTime = 24;
	
	public SkyboxRenderer(Loader loader, Matrix4f projectionMatrix) {
		cube = loader.loadToVAO(VERTICES, 3);
		texture = loader.loadCubeMap(TEXTURE_FILES);
		nightTexture = loader.loadCubeMap(NIGHT_TEXTURE_FILES);

		
		shader = new SkyboxShader();
		shader.start();
		shader.connectTextureUnits();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(Camera camera, float r, float g, float b) {
		shader.start();
		shader.loadViewMatrix(camera);
		shader.loadFogColor(r, g, b);
		GL30.glBindVertexArray(cube.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		bindTextures(r,g,b);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	
//	private void bindTextures() {
//		time += DisplayManager.getFrameTimeSeconds() * 1000;
//		time %= 24000;
//		int texture1;
//		int texture2;
//		float blendFactor;		
//		if(time >= 0 && time < 5000){
//			texture1 = nightTexture;
//			texture2 = nightTexture;
//			blendFactor = (time - 0)/(5000 - 0);
//		}else if(time >= 5000 && time < 8000){
//			texture1 = nightTexture;
//			texture2 = texture;
//			blendFactor = (time - 5000)/(8000 - 5000);
//		}else if(time >= 8000 && time < 21000){
//			texture1 = texture;
//			texture2 = texture;
//			blendFactor = (time - 8000)/(21000 - 8000);
//		}else{
//			texture1 = texture;
//			texture2 = nightTexture;
//			blendFactor = (time - 21000)/(24000 - 21000);
//		}
//
//		GL13.glActiveTexture(GL13.GL_TEXTURE0);
//		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
//		GL13.glActiveTexture(GL13.GL_TEXTURE1);
//		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture2);
//		shader.loadBlendFactor(blendFactor);
//	}
	
	private void bindTextures(float r, float g, float b) {
		time+=timeSpeed;
		if (time >= maxTime) {
			time = maxTime;
			timeSpeed *= -1;
		}
		if (time <= 0) {
			time = 0;
			timeSpeed*= -1;
		}
		int texture2 = texture;
		int texture1 = nightTexture;
		float blendFactor = time/maxTime;
		System.out.println(blendFactor);
		r = r * blendFactor;
		g = g * blendFactor;
		b = b * blendFactor;
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture2);
		shader.loadBlendFactor(blendFactor);
		shader.loadFogColor(r, g, b);
		MasterRenderer.setSkyRed(r);
		MasterRenderer.setSkyGreen(g);
		MasterRenderer.setSkyBlue(b);

		
	}
	
}
