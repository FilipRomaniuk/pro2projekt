package cz.uhk.pro2.flappybird.game;
/**
 * rozhraní pro objekty, které potøebují vìdìt, kolik èasu (tick) ubìhlo od zaèátku hry
 * @author nemecon3
 *
 */
public interface TickAware {
	/**
	 * zmìní stav herní nentity s ohledem na zmìnu herniho èasu
	 * 
	 * @param ticksSinceStart èas - poèet tiku - od zahájení hry
	 */
	void tick(long ticksSinceStart);

}
