package cz.uhk.pro2.flappybird.game;

import java.awt.*;

import cz.uhk.pro2.flappybird.game.tiles.BonusTile;
import cz.uhk.pro2.flappybird.game.tiles.WallTile;

public class GameBoard implements TickAware{
	Tile[][] tiles;//matice dlazdic na herni plose
	int shiftX; // o kolik pixelu se svet posunul
	int widthPix;//sirka hraci plochy
	Bird bird; //herní pták
	private boolean gameOver; //true pokud doslo ke kolizi a hra MA skoncit
	private Player player;

	Image imageOfTheBird;
	
	public GameBoard(){
		//vytvori jednu dlazdici
		tiles = new Tile[20][20];
		//tiles[2][1] =new WallTile();
		
		reset();
	}
	
	public GameBoard(Tile[][] tiles, Image imageOfTheBird){
		this.tiles = tiles;;
		this.imageOfTheBird=imageOfTheBird;
		reset();
	}
	public boolean isGameOver(){
		return gameOver;
	}
	public void setWidthPix(int widthPix) {
		this.widthPix = widthPix;
	}

	public void kickTheBird(){
		bird.kick();
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * vykresli celou herni plochu (sloupy, bonusy, ptaka) na herni platno g
	 * 
	 * @param g
	 */
	public void drawAndDetectColisions(Graphics g){
		//j souradnice prvni drazdice vlevo, kterou je potreba kreslit
		int minJ = shiftX/Tile.SIZE;
		//pocet dlazdic na sirku, kolik je nutno kreslit do viewportu
		//+2 muze chybet cast bunky vlevo a pravo kvuli celocislenu deleni
		int countJ = widthPix/Tile.SIZE+2;
		//i je cislo radku
		for (int i=0;i<tiles.length;i++){
			for(int j=minJ;j<countJ+minJ;j++){
				 //aby level bìzìl poøád dokola, j se na konci pole vrací na 0; tiles0 je pocet sloupcù
				int modJ = j% tiles[0].length;
				Tile t =  tiles[i][modJ];
				/*
				if(t instanceof BonusTile){
					System.out.println("prepis t");
					Tile emp = ((BonusTile) t).getEmptyTile();
					Image im = ((BonusTile) t).getImage();
					t = new BonusTile(im, emp);
				}
				*/
				if(t!=null ){//&& eaten(t) 
					//vykresli
					int viewportX=j*Tile.SIZE-shiftX;
					int viewportY=i*Tile.SIZE;
					t.draw(g, viewportX,viewportY);
					//testovani kolizi
					if(t instanceof WallTile){
						//t je zed
						//otestujeme, zda t koliduje s ptakem
						if(bird.collidesWithRectangle(viewportX, viewportY, Tile.SIZE, Tile.SIZE)){
							if (!gameOver)
								player.writeScore();
							
							gameOver = true; //toslo ke kolizi, hra ma skoncit
						}
					}
					
					if(t instanceof BonusTile){
						if(bird.collidesWithRectangle(viewportX, viewportY, Tile.SIZE, Tile.SIZE)){
							if (!gameOver && !((BonusTile) t).isEaten()) {
								player.increaseScore(1000);
							}
							((BonusTile) t).setEaten(true);//ptak snedl bonus
							//t.draw(g, viewportX,viewportY);
						}
					}
					

					
					if(t instanceof BonusTile && j == (countJ + minJ - 2)  ){//
						((BonusTile)t).setEaten(false);
						

					}
					
				}
			}
		}
		// vykreslit ptaka
		bird.draw(g);
		
	}
//zde je bonus již snìdeny
	private boolean eaten(Tile t) {
		if(t instanceof BonusTile && ((BonusTile) t).isEaten()){
			return false;
		}else{
			return true;
		}
		
		
		
		
		
	}

	@Override
	public void tick(long ticksSinceStart) {
		if(!gameOver){
			//s každým tikem ve høe posuneme hru o jeden pixel
			//tj. poèet ticku a pixelu se rovnají
			shiftX=(int)ticksSinceStart;
			
			//TODO dáme vìdìt ptákovi, že hodiny tickly
			bird.tick(ticksSinceStart);
			player.increaseScore(1);
		}//else pri game over hra stoji na miste
		
	}
	 public void reset(){
		 gameOver= false;
		 
		 if (player != null)
			 player.setScore(0);

		 bird = new Bird(100, 100,imageOfTheBird ); 
		 
		 
	 }
	
}

