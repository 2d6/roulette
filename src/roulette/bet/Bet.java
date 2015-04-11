package roulette.bet;

import roulette.outcome.Outcome;
import roulette.player.Player;

/**
 * Representation of bets in a game of roulette
 * 
 * @author hyperion
 * 
 */
public class Bet {
	private int amountBet;
	private Outcome outcomeBetOn;
	private Player player;

	/**
	 * Creates a new Bet
	 * 
	 * @param player
	 *            The Player which placed the bet
	 * @param amount
	 *            Amount of the bet
	 * @param outcome
	 *            Outcome bet on
	 */
	public Bet(Player player, int amount, Outcome outcome) {
		this.player = player;
		this.amountBet = amount;
		this.outcomeBetOn = outcome;
	}

	/**
	 * Returns the amount of the bet
	 * 
	 * @return Amount of the bet
	 */
	public int getAmount() {
		return this.amountBet;
	}

	/**
	 * Returns the outcome this bet is corresponding to
	 * 
	 * @return The Outcome bet on
	 */
	public Outcome getOutcome() {
		return this.outcomeBetOn;
	}
	
	/**
	 * Returns the Player which placed this bet
	 * 
	 * @return The Player which placed this bet
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Returns the amount won if the bet was correct. The returned amount
	 * includes the initially bet amount, i.e. at odds of n:1, the return value
	 * is defined as (n+1) times the initially bet amount.
	 * 
	 * @return Amount won, calculated as stated above.
	 */
	public int getWinAmount() {
		int output = (this.outcomeBetOn == null) ? 0 : (this.outcomeBetOn
				.getOdds() + 1) * this.amountBet; // +1 is necessary because the
													// n:1 ratio does not
													// include the return of the
													// initially bet amount
		return output;
	}

	@Override
	public String toString() {
		return String.format("%d$ on %s", this.amountBet,
				this.outcomeBetOn.toString());
	}
}
