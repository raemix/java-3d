package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import toolbox.Time;

@SuppressWarnings("unused")
public class DisplayManager {
	
	private static final int WIDTH = 1020;
	private static final int HEIGHT = 680;
	
	
	

	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3,2)
		.withForwardCompatible(true)
		.withProfileCompatibility(true);
		
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();

			Display.setTitle("My First Display");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		Time.setLastFrameTime(Time.getCurrentTime());
	}
	
	
	
//	public static void updateDisplay(int fpsCap, long currentFrameTime) {
//		
//		Display.sync(fpsCap);
//		Display.update();
//		currentFrameTime = getCurrentTime();
//		delta = (currentFrameTime - lastFrameTime)/1000f;
//
//		lastFrameTime = currentFrameTime;
//	}
	
	
	public static void closeDisplay() {
		
		Display.destroy();
		
	}
	
	
	
	
}
