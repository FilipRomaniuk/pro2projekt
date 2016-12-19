package cz.uhk.pro2.flappybird.game;

import java.awt.*;

import cz.uhk.pro2.flappybird.game.tiles.WallTile;

public class GameBoard implements TickAware{
	Tile[][] tiles;//matice dlazdic na herni plose
	int shiftX; // o kolik pixelu se svet posunul
	int widthPix;//sirka hraci plochy
	Bird bird = new Bird(100, 100);
	//Bird bird;
	
	public GameBoard(){
		//TODO jen testovaci data, nutno udelat nacitani dat ze souboru
		tiles = new Tile[10][10];
		tiles[2][1] =new WallTile();
		bird = new Bird(100, 100);
	}
	
	
	public GameBoard(Tile [] [] tiles){
		this.tiles = tiles;
		bird = new Bird(100, 100);
	}
	
	
	public void setWidthPix(int widthPix) {
		this.widthPix = widthPix;
	}
	
	
	/**
	 * vykresli celou herni plochu (sloupy, bonusy, ptaka) na herni platno g
	 * 
	 * @param g
	 */
	public void draw(Graphics g){
		//j souradnice prvni drazdice vlevo, kterou je potreba kreslit
		int minJ = shiftX/Tile.SIZE;
		//pocet dlazdic na sirku, kolik je nutno kreslit do viewportu
		//+2 muze chybet cast bunky vlevo a pravo kvuli celocislenu deleni
		int countJ = widthPix/Tile.SIZE+2;
		//i je cislo radku
		for (int i=0;i<tiles.length;i++){
			for(int j=minJ;j<countJ+minJ;j++){
				//chceme aby lvl bezel porad dokola, takze modJ se
				//na konci pole vraci zase na 0; tiles[0].length je pocet sloupcu.
			
				int modJ = j % tiles[0].length;
				
				
				Tile t =  tiles[i][modJ];
				if(t!=null){
					//v bunce je nejaka dlazdice - ne pozadi
					//vykresli
					int viewportX=j*Tile.SIZE-shiftX;
					int viewportY=i*Tile.SIZE;
					t.draw(g, viewportX,viewportY);
					
				}
			}
		}
		//TODO vykreslit ptaka
		bird.draw(g);
		
	}

	@Override
	public void tick(long ticksSinceStart) {
		//s každým tikem ve høe posuneme hru o jeden pixel
		//tj. poèet ticku a pixelu se rovnají
		shiftX=(int)ticksSinceStart;
		
		//TODO dáme vìdìt ptákovi, že hodiny tickly
		bird.tick(ticksSinceStart);
	}
	
	public void kickTheBird(){
		bird.kick();
	}
	
}
