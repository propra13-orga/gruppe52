package the.game.java;

public class Level1 {
	
	//public Level1(String title){
		//super(title);
		
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setSize(Runner.getWidthF(),Runner.getHeightF());
		//setResizable(false);
		//setVisible(true);
	//}
	
	public static void setWall(){
		//LevelCreator level1= 
		new LevelCreator();
		
		LevelCreator.createWall(0,0,48,1);
		LevelCreator.createWall(0,25,48,1);
		LevelCreator.createWall(0,0,1,12);
		LevelCreator.createWall(0,15,1,12);
		LevelCreator.createWall(47,0,1,12);
		LevelCreator.createWall(24,0,1,12);
		LevelCreator.createWall(24,15,1,12);
		LevelCreator.createWall(12,3,1,24);
		LevelCreator.createWall(36,3,1,24);
		LevelCreator.createWall(47,15,1,12);
	}
	
	//public static void main(String[] args){
		//Level1 frame= new Level1("Level 1. Viel Glück!");
		//setWall();
	//}
	
}
