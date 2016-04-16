package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game {
	public final static int HEIGHT=600, WIDTH = 800;
	
	private String gameName = "Flappy Bird";
	
	private Canvas game = new Canvas();
	
	private Input input;
	//act as storage for all updatable input and renderable input
	private ArrayList<Updatable> updatables = new ArrayList<>();
	private ArrayList<Renderable> renderables = new ArrayList<>();
	
	public void addUpdatable(Updatable u){
		updatables.add(u);
	}
	
	public void removeUpdatable(Updatable u){
		updatables.remove(u);
	}
	public void addRenderable(Renderable r){
		renderables.add(r);
	}
	public void removeRenderable(Renderable r){
		renderables.remove(r);
	}
	
	public void start(){
		//init windows
		Dimension gameSize = new Dimension(Game.WIDTH, Game.HEIGHT);
		JFrame gameWindow = new JFrame(gameName);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setSize(gameSize);
		gameWindow.setResizable(false);
		gameWindow.setVisible(true);
		game.setSize(gameSize);
		game.setMinimumSize(gameSize);
		game.setMaximumSize(gameSize);
		game.setPreferredSize(gameSize);
		gameWindow.add(game);
		gameWindow.setLocationRelativeTo(null);
		
		//Init Input
		input = new Input();
		game.addKeyListener(input);
		//*********Game Loop**********
		final int TICKS_PER_SECOND = 60;
		final int TIME_PER_TICK = 1000/TICKS_PER_SECOND;
		final int MAX_FRAMESKIPS = 5;
		
		long nextGameTick = System.currentTimeMillis();
		int loops;
		float interpolation;
		
		long timeAtLastFPSCheck = 0;
		int ticks = 0;
		
		//boolean running = true;
		
		while(true){
			//Updating
			loops = 0;
			
			while(System.currentTimeMillis() > nextGameTick && loops < MAX_FRAMESKIPS){
				update();
				ticks++;
				
				nextGameTick += TIME_PER_TICK;
				loops++;
				
			}
			
			//Rendering		
			interpolation = (float)(System.currentTimeMillis() + TIME_PER_TICK - nextGameTick)/(float) TIME_PER_TICK;
			render(interpolation);
	
			//FPS Check
			if(System.currentTimeMillis()- timeAtLastFPSCheck >= 1000){
				System.out.println("FPS: " + ticks);
				gameWindow.setTitle(gameName + " - FPS: " + ticks);
				ticks = 0;
				timeAtLastFPSCheck = System.currentTimeMillis();
			}
			
		}
		//Game end
	}
	
	private void update(){
		for(Updatable u : updatables){
			u.update(input);
		}
	}
	
	private void render(float interpolation){
		//double rendering
		BufferStrategy b = game.getBufferStrategy();
		if(b==null){
			game.createBufferStrategy(2);
			return;
		}
		
		Graphics2D g = (Graphics2D)b.getDrawGraphics();
		g.clearRect(0, 0, game.getWidth(), game.getHeight());
		for(Renderable r : renderables){
			r.render(g, interpolation);
		}
		g.dispose();
		b.show();
		
	}
}
