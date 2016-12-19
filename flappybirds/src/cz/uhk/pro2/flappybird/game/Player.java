package cz.uhk.pro2.flappybird.game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Player {

	private String name;
	private int score = 0;
	
	public Player(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public void increaseScore(int i) {
		score += i;
		
		System.out.println(String.valueOf(score));
	}

	public void writeScore() {
		try {
			File file = new File("score.txt");
			try (FileWriter writer = new FileWriter(file, true)) {
				String s = String.format("%s;%d%n",name,score);
				writer.append(s);
				writer.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
