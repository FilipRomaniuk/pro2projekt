package cz.uhk.pro2.flappybird.game;

import java.awt.*;
/**
 * spolecny interface pro vsechny dlazdice na herny plose
 * 
 * @author nemecon3
 *
 */
public interface Tile {
	/**
	 * vyska a sirka dlazdice v pixelech
	 */
	public static final int SIZE =20;
	/**
	 * vykresli dlazdici na platno g
	 * 
	 * @param g
	 * @param x x-ova souradnice drazdice ve viewportu v pixelech
	 * @param y y-ova souradnice dlazdice ve viewportu v pixelech
	 */
	void draw(Graphics g, int x, int y);//vykresli
}
