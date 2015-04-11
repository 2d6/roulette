/**
 * 
 */
package roulette.player;

import java.util.LinkedList;

import roulette.bet.Bet;
import roulette.bin.BinBuilder;
import roulette.exceptions.InvalidBetError;
import roulette.outcome.Outcome;
import roulette.table.Table;

/**
 * A Player which first waits for seven successive red bins before he starts
 * betting on black via the Martingale system.
 * 
 * @author hyperion
 * 
 */
public class SevenReds extends Martingale {
	private int redCount;

	/**
	 * Creates a new SecenReds player at the Table table
	 * 
	 * @param table
	 *            Table the SevenReds player is playing at
	 */
	public SevenReds(Table table) {
		super(table);
		this.redCount = 7;
		this.setOutcome();
	}
	
	public SevenReds(SevenReds sevenReds) {
		super(sevenReds);
		this.redCount = sevenReds.redCount;
	}
	
	public int getRedCount() {
		return this.redCount;
	}

	/**
	 * Creates a new SecenReds player at the Table table, with a given stake and
	 * rounds to go.
	 * 
	 * @param table
	 * @param stake
	 * @param roundsToGo
	 */
	public SevenReds(Table table, int stake, int roundsToGo) {
		super(table, stake, roundsToGo);
		this.redCount = 7;
		this.setOutcome();
	}
	
	/* (non-Javadoc)
	 * @see roulette.player.Martingale#placeBets()
	 */
	@Override
	public void placeBets(Table table) {
		if (this.redCount == 0) {
			Bet nextBet = new Bet(this, this.startingBetAmount * this.betMultiplier,
					this.outcome);
			if (table.isValid(nextBet) && nextBet.getAmount() <= this.stake && isPlaying(table)) {
				try {
					table.placeBet(nextBet);
					this.stake -= nextBet.getAmount();
				} 
				catch (InvalidBetError e) {
					System.out
							.println("InvalidBetError encountered while placing a bet");
					this.roundsToGo = 0;
				}
			}
		}
		else {
		}
		this.roundsToGo = (this.roundsToGo > 0) ? this.roundsToGo - 1 : this.roundsToGo;
	}
	
	@Override
	public void setOutcome() {
		this.outcome = BinBuilder.getColorBetOutcome(BinBuilder.BLACK);
	}
	
	/* (non-Javadoc)
	 * @see roulette.player.Martingale#receiveWinningOutcomes(java.util.LinkedList)
	 */
	@Override
	public void receiveWinningOutcomes(LinkedList<Outcome> winningOutcomes) {
		Outcome red = BinBuilder.getColorBetOutcome(BinBuilder.RED);
		
		if (winningOutcomes.contains(red) && this.redCount > 0) {
			this.redCount--;
		}
		else if (this.redCount > 0) {
			this.redCount = 7;
		}
	}

}
