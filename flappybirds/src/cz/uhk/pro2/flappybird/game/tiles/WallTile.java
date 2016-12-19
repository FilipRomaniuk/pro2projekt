package cz.uhk.pro2.flappybird.game.tiles;

import java.awt.Graphics;

import cz.uhk.pro2.flappybird.game.*;

public class WallTile implements Tile{

	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawRect(x, y, Tile.SIZE, Tile.SIZE);
		
	}

}
