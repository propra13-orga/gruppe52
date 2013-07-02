package the.game.java;

public class Settings {
	private static String controlsType = "gamepad";
	
	
	public static boolean isControledByGamePad() {
		return controlsType.equals("gamepad");
	}
	public static boolean isControledByMouse() {
		return controlsType.equals("mouse");
	}
	
	public static void setControlsToGamePad() {
		controlsType = "gamepad";
	}
	public static void setControlsToMouse() {
		controlsType = "mouse";
	}
}
