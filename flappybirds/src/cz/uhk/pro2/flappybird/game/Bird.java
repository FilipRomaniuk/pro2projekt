package cz.uhk.pro2.flappybird.game;

import java.awt.Color;
import java.awt.Graphics;

public class Bird implements TickAware{
	//fyzika
	static final double koefUp = -5.0;
	static final double koefDown = 2.0;
	static final int ticksFlyingUp = 4;
	
	//souøadnice støedu ptáka
	int viewportX;
	double viewportY; // double, aby se dala jemne ladit rychlost padani.
	
	//rychlost padání (pozitivní), vzletu(negativní)
	double velocityY = koefDown;
	//kolik ticku zbývá, než zaène padat
	int ticksToFall = 0;
	
	public Bird(int initialX, int initialY) {
		this.viewportX = initialX;
		this.viewportY = initialY;
	}
	
	public void kick(){
		velocityY = koefUp; // ma zacit letet nahodu
		ticksToFall = ticksFlyingUp;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.BLUE);
		g.fillOval(viewportX-Tile.SIZE/2, (int)viewportY-Tile.SIZE-2, Tile.SIZE,	Tile.SIZE);
		g.setColor(Color.BLACK);
		g.drawString(viewportX +", "+viewportY, viewportX,(int)viewportY);
	}
	
	@Override
	public void tick(long ticksSinceStart) {
		
		viewportY += velocityY;
		
		if(ticksToFall > 0){
			//ptak letel nahodu
			ticksToFall--;
		}else{
			//ptak ma padat
			velocityY = koefDown;
		}
	}

}
