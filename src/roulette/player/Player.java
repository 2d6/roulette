package roulette.player;

import java.util.LinkedList;

import roulette.bet.Bet;
import roulette.outcome.Outcome;
import roulette.table.Table;

/**
 * Representation of a player of a game of roulette. Places bets on outcomes.
 * 
 * @author hyperion
 * 
 */
public abstract class Player {

	protected int stake;
	protected int roundsToGo;

	public Player() {
	}

	/**
	 * Creates a new Player at the Table table.
	 * 
	 * @param table
	 *            Table this Player is playing at
	 */
	public Player(Table table) {
		table.addPlayer(this);
	}

	/**
	 * Creates a new Player at the Table table, with a given stake and rounds to
	 * go. The player will only place bets while the amount of rounds he has
	 * played is less than the value of roundsToGo.
	 * 
	 * @param table
	 *            Table this Player is playing at
	 * @param stake
	 *            Initial stake of the Player
	 * @param roundsToGo
	 *            Initial amount of rounds to go for the player. If set to -1,
	 *            the player will stay in the game indefinitely, unless other
	 *            factors force him to leave (e.g. the stake falls below the
	 *            table minimum).
	 */
	public Player(Table table, int stake, int roundsToGo) {
		this(table);
		this.stake = stake;
		this.roundsToGo = roundsToGo;
	}
	
	public Player(Player player) {
		this.stake = player.stake;
		this.roundsToGo = player.roundsToGo;
	}

	/**
	 * Sets the stake of the player to the desired value
	 * 
	 * @param stake
	 *            The new stake of the player
	 */
	public void setStake(int stake) {
		this.stake = stake;
	}

	/**
	 * @return The current stake of the player
	 */
	public int getStake() {
		return this.stake;
	}

	/**
	 * Sets the amount of rounds to go of the player to the desired value
	 * 
	 * @param roundsToGo
	 *            The new amount of rounds to go for the player
	 */
	public void setRoundsToGo(int roundsToGo) {
		this.roundsToGo = roundsToGo;
	}

	/**
	 * Enables the Player to receive the winning outcomes, e.g. to change the
	 * bet in subsequent rounds.
	 * 
	 * @param winningOutcomes
	 */
	public abstract void receiveWinningOutcomes(
			LinkedList<Outcome> winningOutcomes);

	/**
	 * Indicates whether the player wants to play in the next round
	 * 
	 * @return True if the player wants to stay in the game, false if he wants
	 *         to leave the table.
	 */
	public abstract boolean isPlaying(Table table);

	/**
	 * This method gets called to inform the Player that a bet of his has
	 * succeeded and that his stake should be increased according to the
	 * winAmount of the bet
	 * 
	 * @param bet
	 *            The winning bet
	 */
	public abstract void win(Bet bet);

	/**
	 * May be called to inform the Player that a bet of his has failed.
	 * 
	 * @param bet
	 *            The losing bet
	 */
	public abstract void lose(Bet bet);

	/**
	 * Creates a Bet and adds it to the table
	 */
	public abstract void placeBets(Table table);
}
