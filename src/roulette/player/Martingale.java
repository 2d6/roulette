package roulette.player;

import java.util.LinkedList;

import roulette.bet.Bet;
import roulette.bin.BinBuilder;
import roulette.exceptions.InvalidBetError;
import roulette.outcome.Outcome;
import roulette.table.Table;

/**
 * Representation of a player at a game of roulette who follows the Martingale
 * betting strategy. The player starts betting at the table minimum. Each time
 * he loses, he doubles the amount of the new bet so as to recoup all previous
 * losses with the next win. Once he wins, he again starts with the table
 * minimum. If the betting amount would surpass the table limit, he withdraws
 * from the game.
 * 
 * @author hyperion
 * 
 */
public class Martingale extends Player {

	protected int lossCount;
	protected int betMultiplier;
	protected int startingBetAmount;
	protected Outcome outcome;

	/**
	 * Creates a new Martingale player at the Table table
	 * 
	 * @param table
	 *            Table the Martingale player is playing at
	 */
	public Martingale(Table table) {
		super(table);
		this.setLossCount(0);
		this.startingBetAmount = table.getMinimum();
		this.stake = 0;
		this.roundsToGo = -1;
	}

	public Martingale(Table table, int stake, int roundsToGo) {
		this(table);
		this.stake = stake;
		this.roundsToGo = roundsToGo;
	}
	
	public Martingale(Martingale martingale) {
		super(martingale);
		this.lossCount = martingale.lossCount;
		this.betMultiplier = martingale.betMultiplier;
		this.startingBetAmount = martingale.startingBetAmount;
		this.outcome = martingale.outcome;
	}
	
	@Override
	public void setStake(int stake) {
		super.setStake(stake);
		this.setLossCount(0);
	}

	/**
	 * Recalculates the betMultiplier, as it should always be equal to
	 * 2^lossCount.
	 */
	protected void recalculateBetMultiplier() {
		this.betMultiplier = (int)Math.pow(2,this.lossCount);
	}

	/**
	 * Set the lossCount to a new value
	 * 
	 * @param newCount
	 *            New value of the lossCount
	 */
	private void setLossCount(int newCount) {
		this.lossCount = newCount;
		this.recalculateBetMultiplier();
	}

	/**
	 * Increment the lossCount by one
	 */
	private void incrementLossCount() {
		this.setLossCount(this.lossCount + 1);
	}

	/**
	 * Sets the outcome the Martingale player bets on. Currently, this is set to
	 * the "Low" bet.
	 * 
	 * @param wheel
	 */
	public void setOutcome() {
		int[] bins = new int[18];
		for (int i = 1; i < 19; i++) {
			bins[i-1] = i;
		}
		this.outcome = BinBuilder.getHighLowBetOutcome(BinBuilder.LOW);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see roulette.player.Player#isPlaying() The Martingale player stays in
	 * the game while his stake is high enough to play a the table and if his
	 * next bet would not surpass the table limit. This method also returns
	 * false if the rounds to go reach zero and if the player has an outcome to
	 * bet on.
	 */
	@Override
	public boolean isPlaying(Table table) {
		boolean belowLim = (this.startingBetAmount * this.betMultiplier <= table
				.getLimit());
		boolean aboveMin = (this.stake >= table.getMinimum());
		boolean belowStake = this.startingBetAmount * this.betMultiplier <= this.stake;
		boolean nonNullOutcome =  this.outcome != null;
		boolean nonZeroRoundsToGo = (this.roundsToGo > 0 || this.roundsToGo == -1);
		return belowLim && aboveMin && belowStake && nonNullOutcome && nonZeroRoundsToGo;
	}

	@Override
	public void win(Bet bet) {
		this.stake += bet.getWinAmount();
		this.setLossCount(0);
	}

	@Override
	public void lose(Bet bet) {
		this.incrementLossCount();
	}
	
	@Override
	public void receiveWinningOutcomes(LinkedList<Outcome> winningOutcomes) {
		// This Player ignores previously winning outcomes
	}
	
	@Override
	public void placeBets(Table table) {
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
		this.roundsToGo = (this.roundsToGo > 0) ? this.roundsToGo - 1 : this.roundsToGo;
	}

}
