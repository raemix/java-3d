package toolbox;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;

public class Time {
	private static final int FPS_CAP = 120;
	private static long lastFrameTime;
	private static float delta;
	
	
	public static void updateTime() {
		
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000f;

		lastFrameTime = currentFrameTime;
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	public static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
		
	}

	public static long getLastFrameTime() {
		return lastFrameTime;
	}

	public static void setLastFrameTime(long lastFrameTime) {
		lastFrameTime = lastFrameTime;
	}

	public static float getDelta() {
		return delta;
	}

	public static void setDelta(float delta) {
		Time.delta = delta;
	}
	
	
}
