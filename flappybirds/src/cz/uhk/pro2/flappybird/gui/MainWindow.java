package cz.uhk.pro2.flappybird.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.peer.MouseInfoPeer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.swing.*;

import cz.uhk.pro2.flappybird.game.GameBoard;
import cz.uhk.pro2.flappybird.game.service.BoardLoader;
import cz.uhk.pro2.flappybird.game.service.CsvBoardLoader;
import cz.uhk.pro2.flappybird.gui.MainWindow.BoardPanel;

public class MainWindow extends JFrame {
	BoardPanel pnl = new BoardPanel();
	GameBoard gameBoard;
	long x = 0;
	
	//vnitrni trida
	class BoardPanel extends JPanel {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			gameBoard.drawAndDetectColisions(g);
			
		}
	}
	
	
	public MainWindow(){
		
		try(InputStream is = new FileInputStream("Muj_level.csv")){
		//*
			//vytvorime si loader
			BoardLoader loader = new CsvBoardLoader(is);
			gameBoard = loader.getGameboard();
			
		} catch (FileNotFoundException e1) {
			gameBoard = new GameBoard();
			e1.printStackTrace();
		} catch (IOException e1) {
			gameBoard = new GameBoard();
			e1.printStackTrace();
		}
		
		
		
		add(pnl,BorderLayout.CENTER);
		
		pnl.setPreferredSize(new Dimension(200,200));//TODO
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		gameBoard.setWidthPix(pnl.getWidth());
		
		Timer t = new Timer(20, e->{//jak èasto se timer spouští v ms
			gameBoard.tick(x++);//promìná, která udržuje poèet tickù od zaèátku
			pnl.repaint();//refresh obrazovky
		});
		//t.start();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON1){
					//kdyz jeste nebezi timer, tak nastartovat
					if(!t.isRunning()){
						t.start();
					}else{
						//jinak nakopnout ptaka
						gameBoard.kickTheBird();
					}
					
					gameBoard.kickTheBird();
				}else if(gameBoard.isGameOver() && e.getButton()==MouseEvent.BUTTON3){
					x=0;//posun hermiho sveta
					gameBoard.reset();
					//prekreslit, zastavit timer
					gameBoard.tick(0);
					pnl.repaint();
					t.stop();
				}

				
			}
			
			
		});
		
		
		
		
		
		
	}

	public static void main(String[] args) {
		//díky swingUtilities jede gui ve vlastním vláknu
		SwingUtilities.invokeLater(()->{
			MainWindow w = new MainWindow();
			w.setVisible(true);
		});

	}

}
