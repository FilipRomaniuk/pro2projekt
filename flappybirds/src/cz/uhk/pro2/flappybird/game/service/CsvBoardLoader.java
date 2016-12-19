package cz.uhk.pro2.flappybird.game.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import cz.uhk.pro2.flappybird.game.GameBoard;
import cz.uhk.pro2.flappybird.game.Tile;
import cz.uhk.pro2.flappybird.game.tiles.BonusTile;
import cz.uhk.pro2.flappybird.game.tiles.EmptyTile;
import cz.uhk.pro2.flappybird.game.tiles.WallTile;

public class CsvBoardLoader implements BoardLoader {
	//zapis logovacich hlasek - pomocny objekt pro zapisovani hlasek o prubehu programu
		static final Logger logger= Logger.getLogger(CsvBoardLoader.class.getName());
		InputStream is;//stream ze kterého se naèíta level
		
		public CsvBoardLoader(InputStream is) {
			this.is =is;
		}

		@Override
		public GameBoard getGameboard() {
			
			try(BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"))) {
				String[] line = br.readLine().split(";");
				//kolik typù dlazdic
				int numberOfTypes = Integer.parseInt(line[0]);
				
				//logger.log(Level.FINE, "Number of tile types: " + numberOfTypes);
			//	System.out.println("number of type " + numberOfTypes );
				
				//zpracovavame zdroje obrazku k matici a obrazek ptaka
				BufferedImage imageOfTheBird = null;
				Map<String, Tile> tileTypes = new HashMap<>();
				for(int i =0; i<numberOfTypes;i++){
					line = br.readLine().split(";");
					String type = line[0];
					String clazz = line[1];
					int spriteX =Integer.parseInt(line[2]);
					int spriteY = Integer.parseInt(line[3]);
					int spriteWidth = Integer.parseInt(line[4]);
					int spriteHeight = Integer.parseInt(line[5]);
					String url = line[6];
					//pouziva se u bonusu
					String extraInfo =(line.length>=8)? line[7]: "";
					Tile referenceTile=tileTypes.get(extraInfo);
					if(clazz.equals("Bird")){
						//specialni radek - definice ptaka
					imageOfTheBird = getImage(spriteX, spriteY, spriteWidth, spriteHeight, url);
					}else{
						//normalni dlazdice
						Tile tile = createTile(clazz, spriteX, spriteY, spriteWidth, spriteHeight, url, referenceTile);
						tileTypes.put(type, tile);
					}
					
					
				}
				//radek s pocty radku a sloupcu v matici herni plochy
				line = br.readLine().split(";");
				int rows = Integer.parseInt(line[0]);
				int colums = Integer.parseInt(line[1]);
				
		//		System.out.println("radky a sloupce " + rows +" " +colums);
				//vyrobime matici dlazdic
				Tile[][] tiles = new Tile[rows][colums];
		
			
				//projdeme radky s matici
				for(int i = 0; i<rows;i++){
					line=br.readLine().split(";");
					for(int j =0; j<colums;j++){
						//retezec v dane bunce
						String t;
						//osetreni, kdyby v csv chybely prazdne bunky na konci radku
						if(j<line.length){
							//v poradku, bunka je v csv
							t = line[j];
							
						}else{
							//bunka chyby, povazujeme ji za prazdnou
							t="";
						}
						if("B".equals(t)){
				//			tiles[i][j] = ((BonusTile)tileTypes.get("B")).clone();
							continue;
						}
						tiles[i][j]=tileTypes.get(t);
						
					}
				}
				GameBoard  gb = new GameBoard(tiles);
				return gb;
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Chyba pri cteni souboru s levelem - spatna znakova sada",e);
				//throw new RuntimeException("Nepodporovane kodovani");
				//e.printStackTrace();
			} catch (IOException e) {
				throw new RuntimeException("Chyba pri cteni souboru s levelem - neexitujici soubor nebo obrazek",e);
				//e.printStackTrace();
			} 
			
		}

		private Tile createTile(String clazz, int spriteX, int spriteY, int spriteWidth, int spriteHeight, String url, Tile referenceTile)
				throws IOException {
			BufferedImage resized = getImage(spriteX, spriteY, spriteWidth, spriteHeight, url);
			//podle typu vytvorime instanci patricne tridy
			switch (clazz) {
			case "Wall":
				return new WallTile(resized);
				
			case "Bonus":
				return new BonusTile(resized, referenceTile );
			
			case "Empty":
				return new EmptyTile(resized);
			
			default:
				throw new RuntimeException("Neznama trida dlazdice " + clazz);
			}
			
		}

		private BufferedImage getImage(int spriteX, int spriteY, int spriteWidth, int spriteHeight, String url)
				throws IOException, MalformedURLException {
			//nacist obrazek z url
			BufferedImage original = ImageIO.read(new URL(url));
			//vyriznout cast z obrazku (sprite) podle x,y sirka vyska
			BufferedImage cropped = original.getSubimage(spriteX, spriteY, spriteWidth, spriteHeight);
			//zveetsime/zmensime sprite, aby pasoval do dlazdice
			BufferedImage resized = new BufferedImage(Tile.SIZE, Tile.SIZE, BufferedImage.TYPE_INT_ARGB);
			//TODO nastavit dalsi parametry pro skalovani
			Graphics2D g = (Graphics2D)resized.getGraphics();
			g.drawImage(cropped, 0, 0, Tile.SIZE, Tile.SIZE, null);
			return resized;
		}
		
		
		
		


}