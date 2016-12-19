package cz.uhk.pro2.flappybird.game.service;

import cz.uhk.pro2.flappybird.game.GameBoard;

/**
*Rozhrani pro vsechny tridy, ktere umi nejak nacist herni plochu.
*
*
*/
public interface BoardLoader {
	/*
	*Nacte a vrati  plchu
	*
	*/
	
	public GameBoard getGameboard();
	
	
}
