package roulette.table;

import java.util.Iterator;
import java.util.LinkedList;

import roulette.bet.Bet;
import roulette.exceptions.InvalidBetError;
import roulette.player.Player;

/**
 * Representation of the table in a game of roulette.
 * 
 * @author hyperion
 * 
 */
public class Table {
	private int minimum;
	private int limit;
	private LinkedList<Bet> bets;
	private LinkedList<Player> players;

	/**
	 * Creates a new Table object
	 * 
	 * @param minimum
	 *            The minimum of the table, i.e. the minimum amount a bet needs
	 *            to possess to be valid at this table
	 * @param limit
	 *            The limit of the table, i.e. the maximum amount a bet may
	 *            possess to be valid at this table
	 */
	public Table(int minimum, int limit) {
		this.minimum = minimum;
		this.limit = limit;
		this.bets = new LinkedList<Bet>();
		this.players = new LinkedList<Player>();
	}

	/**
	 * Get the minimum amount for bets to be registered to the table
	 * 
	 * @return The minimum amount of bets to be registered to the table
	 */
	public int getMinimum() {
		return minimum;
	}

	/**
	 * Get the limit of the table, i.e. the maximum amount for bets
	 * 
	 * @return The table limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * Checks the validity of the indicated bet. The amount of the bet is
	 * checked with the table limit and minimum. Note: this should be changed to
	 * the sum of all bets from a single player, once the Player class has been
	 * implemented.
	 * 
	 * @param bet
	 *            Bet to be checked on validity
	 * @return True if the bet is valid, false if the bet is invalid
	 */
	public boolean isValid(Bet bet) {
		// TODO: Limit check should be done on player per player basis
		// TODO: Check for negative bets
		// TODO: Check for null bets
		return (bet.getAmount() >= this.getMinimum())
				&& (bet.getAmount() <= this.getLimit());
	}

	/**
	 * Returns all currently registered bets
	 * 
	 * @return LinkedList of all currently registered bets
	 */
	public LinkedList<Bet> getBets() {
		return bets;
	}

	/**
	 * Add the indicated bet to the table
	 * 
	 * @param bet
	 *            Bet to be added
	 */
	public void placeBet(Bet bet) throws InvalidBetError {
		if (isValid(bet)) {
			this.bets.add(bet);
		} else {
			throw new InvalidBetError();
		}
	}

	/**
	 * Removes the indicated bet from the table
	 * 
	 * @param bet
	 *            Bet to be removed
	 */
	public void removeBet(Bet bet) {
		if (this.bets.contains(bet)) {
			this.bets.remove(bet);
		}
	}

	/**
	 * @return An Iterator for the Bets placed at this Table
	 */
	public Iterator<Bet> getPlacedBetsIterator() {
		return this.bets.iterator();
	}
	
	/**
	 * @param player The player to add to the table
	 */
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	/**
	 * @param player A player to remove from the table
	 */
	public void removePlayer(Player player) {
		this.players.remove(player);
	}
	
	/**
	 * Removes all players from the table
	 */
	public void removeAllPlayers() {
		this.players.clear();
	}
	
	/**
	 * @return A LinkedList of Players currently on the table
	 */
	public LinkedList<Player> getPlayers() {
		return this.players;
	}
	
	/**
	 * @return An Iterator for the Players currently on the table
	 */
	public Iterator<Player> getPlayersIterator() {
		return this.players.iterator();
	}

	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Table, containing the following bets:\n");
		for (Bet bet : this.getBets()) {
			stringBuffer.append(bet.toString()).append("\n");
		}
		return stringBuffer.toString();
	}
}
