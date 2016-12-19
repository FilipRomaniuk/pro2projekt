package cz.uhk.pro2.flappybird.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.peer.MouseInfoPeer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.swing.*;

import cz.uhk.pro2.flappybird.game.GameBoard;
import cz.uhk.pro2.flappybird.game.service.BoardLoader;
import cz.uhk.pro2.flappybird.game.service.CsvBoardLoader;

public class MainWindow extends JFrame {
	BoardPanel pnl = new BoardPanel();
	GameBoard gameBoard;
	long x = 0;

	// vnitrni trida
	class BoardPanel extends JPanel {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			gameBoard.draw(g);

		}
	}

	public MainWindow() {

		try (InputStream is = new FileInputStream("Muj_level.csv")) {
			BoardLoader loader = new CsvBoardLoader(is);
			gameBoard = loader.getGameboard();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		setTitle("Flappy Birds");
		//gameBoard = new GameBoard();
		add(pnl, BorderLayout.CENTER);
		pnl.setPreferredSize(new Dimension(200, 200));// TODO
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();

		gameBoard.setWidthPix(pnl.getWidth());

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO kick the bird - nakopni pt�ka - metoda kickTheBird
				System.out.println("Mys");

				gameBoard.kickTheBird();

			}
		});

		Timer t = new Timer(20, e -> {// jak �asto se timer spou�t� v ms
			gameBoard.tick(x++);// prom�n�, kter� udr�uje po�et tick� od za��tku
			pnl.repaint();// refresh obrazovky
		});
		t.start();
	}

	public static void main(String[] args) {
		// d�ky swingUtilities jede gui ve vlastn�m vl�knu
		SwingUtilities.invokeLater(() -> {
			MainWindow w = new MainWindow();
			w.setVisible(true);
		});

	}

}
