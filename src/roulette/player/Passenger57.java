package roulette.player;

import java.util.LinkedList;

import roulette.bet.Bet;
import roulette.exceptions.InvalidBetError;
import roulette.outcome.Outcome;
import roulette.outcome.RouletteOutcome;
import roulette.table.Table;

/**
 * Mock Player necessary for preliminary testing of the Game class
 * 
 * @author hyperion
 * 
 */
public class Passenger57 extends Player {
	private Outcome black; // This player only bets on the "Black" bet, this
							// keeps the "Black" outcome.
	private int wins;
	private int losses;
	private static final int[] BLACK = { 2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22,
		24, 26, 28, 29, 31, 33, 35 };

	/**
	 * Creates a Passenger57 object
	 * 
	 * @param table
	 *            Table this Passenger57 is playing at
	 */
	public Passenger57(Table table) {
		super(table);
		this.black = new RouletteOutcome("Black", 1, BLACK);
		this.wins = 0;
	}

	/**
	 * Places the bets of this Passenger57 on the table (in this instance, the
	 * bet is always "Black").
	 */
	public void placeBets(Table table) {
		try {
			table
					.placeBet(new Bet(this, table.getMinimum() + 1, this.black));
		} catch (InvalidBetError e) {
			System.out.println("Invalid bet placed!");
		}
	}

	/**
	 * This method gets called to inform the Player that a bet of his has
	 * succeeded and that his balance should be increased according to the
	 * winAmount of the bet
	 * 
	 * @param bet
	 *            The winning bet
	 */
	@Override
	public void win(Bet bet) {
		this.wins++;
	}

	/**
	 * This method gets called to inform the Player that a bet of his has
	 * failed.
	 * 
	 * @param bet
	 *            The losing bet
	 */
	@Override
	public void lose(Bet bet) {
		this.losses++;
	}

	/**
	 * Returns the number of winning games since the instantiation of the
	 * Passenger57
	 * 
	 * @return The total number of wins
	 */
	public int getWins() {
		return this.wins;
	}
	
	public int getLosses() {
		return this.losses;
	}

	@Override
	public boolean isPlaying(Table table) {
		return true;
	}
	
	@Override
	public void receiveWinningOutcomes(LinkedList<Outcome> winningOutcomes) {
		// This Player ignores previously winning outcomes
	}
}
