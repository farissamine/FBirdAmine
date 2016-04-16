package game;

public class Main {
	public static void main(String[]args){
		Game g = new Game();
		
		//Initialize game objects
		Pipes p = new Pipes();
		Bird b = new Bird(p);
		
		//Add updatables and renderables
		g.addUpdatable(p);
		g.addRenderable(p);
		
		g.addRenderable(b);
		g.addUpdatable(b);
		
		//start
		g.start();
	}

}
