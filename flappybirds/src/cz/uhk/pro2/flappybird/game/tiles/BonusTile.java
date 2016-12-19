package cz.uhk.pro2.flappybird.game.tiles;

import java.awt.Graphics;
import java.awt.Image;

import cz.uhk.pro2.flappybird.game.*;

public class BonusTile extends AbstractTile{
	private boolean eaten;//zda je bonus sneden
	private Tile emptyTile;
	
	
	public BonusTile(Image image, Tile emptyTile) {
		
		super(image);
		eaten = false;
		this.emptyTile=emptyTile;
	}
	
	public boolean isEaten() {
		return eaten;
	}

	public void setEaten(boolean eaten) {
		this.eaten = eaten;
	}
	public Tile getEmptyTile(){
		return emptyTile;
	}
	@Override
	public void draw(Graphics g, int x, int y){
		if(eaten == false){
			super.draw(g, x, y);
		}else{
			emptyTile.draw(g, x, y);
		}
	}
	@Override
	public BonusTile clone(){
		return new BonusTile(image, emptyTile);
	}
	

}
