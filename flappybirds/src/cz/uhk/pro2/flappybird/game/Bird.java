package cz.uhk.pro2.flappybird.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Ellipse2D;

public class Bird implements TickAware{
	//fyzika
	static final double koefUp = -5.0;
	static final double koefDown = 2.0;
	static final int ticksFlyingUp = 4;
	
	//souøadnice støedu ptáka
	int viewportX;
	double viewportY;//aby se dala jemnì ladit rychlost padání
	
	//rychlost padání (pozitivní), vzletu(negativní)
	double velocityY = koefDown;
	//kolik ticku zbývá, než zaène padat
	int ticksToFall = 0;
	//obrazek ptaka
	final Image image;
	public Bird(int initialX, int initialY, Image image) {
		this.viewportX = initialX;
		this.viewportY = initialY;
		this.image = image;
	}
	
	public void kick(){
		velocityY = koefUp; //letí nahoru - mìní stav
		ticksToFall = ticksFlyingUp; //jak dlouho letí nahoru
		
	}
	
	public void draw(Graphics g){
		g.setColor(Color.GREEN);
		//g.fillOval(viewportX-Tile.SIZE/2, (int)viewportY-Tile.SIZE-2, Tile.SIZE,	Tile.SIZE);
		//kresli obrazek ptaka
		g.drawImage(image, viewportX-Tile.SIZE/2, (int)viewportY-Tile.SIZE-2, null);
		g.setColor(Color.BLACK);
		g.drawString(viewportX +", "+(int)viewportY, viewportX,(int)viewportY);
		
	}
	
	@Override
	public void tick(long ticksSinceStart) {
		viewportY +=velocityY;
		if(ticksToFall >0){//ptak letel nahoru
			ticksToFall --;
		}else{
			velocityY=koefDown;//ptak zacne padat
		}
	}
	
	public boolean collidesWithRectangle(int x, int y, int w, int h){//zadany obdelnik
		//vytvorime kruh jako objekt reprezentujici obrys ptaka
		//TODO vytvaret bird boundary jen kdyz je potreba
		Ellipse2D.Float birdBoundary
			= new Ellipse2D.Float(viewportX-Tile.SIZE/2, (int)viewportY-Tile.SIZE-2, Tile.SIZE,	Tile.SIZE);
		//testujememe, jestli ptak koliduje s obdelnikem
		return birdBoundary.intersects(x,y,w,h);
	}

}
