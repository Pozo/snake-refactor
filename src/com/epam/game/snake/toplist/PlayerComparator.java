package com.epam.game.snake.toplist;
import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
	/*
	 * A Comparator belsõ függvénye, melynek segítségével sorba rendezi a
	 * Toplistát
	 */
	@Override
    public int compare(Player pont1, Player pont2) {
		int p1 = pont1.getScore();
		int p2 = pont2.getScore();

		if (p1 > p2) {
			return -1;
		} else if (p1 < p2) {
			return +1;
		} else {
			return 0;
		}
	}

}
