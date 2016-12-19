package cz.uhk.pro2.flappybird.game.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import cz.uhk.pro2.flappybird.game.GameBoard;
import cz.uhk.pro2.flappybird.game.Tile;
import cz.uhk.pro2.flappybird.game.tiles.WallTile;

public class CsvBoardLoader implements BoardLoader {

	// pomocny objekt pro zapisovani hlasek o prubehu programu
	// static final Logger Log =
	// Logger.getLogger(CsvBoardLoader.class.getName());

	InputStream is; // stream, ze ktereho nacitame level

	public CsvBoardLoader(InputStream is) {
		this.is = is;
	}

	@Override
	public GameBoard getGameboard() {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {

			// radek s poctem typu dlazdic
			String[] line = br.readLine().split(";");
			int numberOfTypes = Integer.parseInt(line[0]);
			// Logger.log(Level, "Number of tile typers: " + numberOfTypes);
			System.out.println("Number of tile typers: " + numberOfTypes);

			// zatim preskocime radky s typy dlazdic
			for (int i = 0; i < numberOfTypes; i++) {
				br.readLine().split(";");
				String type = line [0];
				String clazz = line[1];
				int spriteX = Integer.parseInt(line [2]);
				int spriteY = Integer.parseInt(line [3]);
				int spriteWidth = Integer.parseInt(line [4]);
				int spriteHeight = Integer.parseInt(line [5]);
				String url = line [6];
				// TODO
				
				
			}

			// radek s pocty radku a sloupcu v matici herni plochy
			line = br.readLine().split(";");

			int rows = Integer.parseInt(line[0]);
			int columns = Integer.parseInt(line[1]);
			// vyrobime matici dlazdic
			Tile[][] tiles = new Tile[rows][columns];

			// projdeme radky s matici
			for (int i = 0; i < rows; i++) {
				line = br.readLine().split(";");
				for (int j = 0; j < columns; j++) {
					String t;
					// osetrime pripad, ze be csv chybely prazdne bunky na konci
					// radku
					if (j < line.length) {
						// v poradku, bunku mame v csv
						t = line[j];
					} else {
						// bunku v CSV chybi, povazujeme ji za prazdnou
						t = "";

					}
					if (!"".equals(t)) {
						;
						tiles[i][j] = new WallTile();
					}
				}
			}

			GameBoard gb = new GameBoard(tiles);
			return gb;

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Nepodporovane kodovani souboru");
		} catch (IOException e) {
			throw new RuntimeException("Chyba pri cteni souboru s levelem");
		}
	}
}