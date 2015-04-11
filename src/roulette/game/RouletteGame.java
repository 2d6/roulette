package roulette.game;

import java.util.Iterator;

import roulette.bet.Bet;
import roulette.bin.Bin;
import roulette.player.Player;
import roulette.table.Table;
import roulette.wheel.Wheel;

/**
 * Class responsible for organizing a game of roulette
 * 
 * @author hyperion
 * 
 */
public class RouletteGame {

	/**
	 * Executes a round of the game with the Player player. This includes
	 * placement of bets, determining the winning bets and resolving the wins
	 * and losses.
	 * 
	 */
	public static void cycle(Wheel wheel, Table table) {
		for (Iterator<Player> i = table.getPlayersIterator(); i.hasNext();) {
			Player player = i.next();
			player.placeBets(table);
		}

		Bin winningBin = wheel.next();

		for (Iterator<Bet> i = table.getPlacedBetsIterator(); i
				.hasNext();) {
			Bet bet = i.next();
			if (winningBin.getOutcomes().contains(bet.getOutcome())) {
				bet.getPlayer().win(bet);
			} else {
				bet.getPlayer().lose(bet);
			}
			table.removeBet(bet);
		}// for
		for (Iterator<Player> i = table.getPlayersIterator(); i.hasNext();) {
			Player player = i.next();
			player.receiveWinningOutcomes(winningBin.getOutcomes());
		}
	}// if
}// cycle
