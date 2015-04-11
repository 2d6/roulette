package roulette.simulator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import roulette.game.RouletteGame;
import roulette.player.Player;
import roulette.table.Table;
import roulette.wheel.Wheel;

/**
 * Simulates a number of games of Roulette, using a given betting strategy
 * implemented by the Player.
 * 
 * @author hyperion
 */
public class Simulator {
	private int initDuration = 250; // Rounds to go new players will be
									// initialized with
	private int initStake = 100; // Amount that a
									// player's stake will be initialized with
	private int samples = 5000; // The number of games to simulate
	private LinkedList<Integer> durations; // List of the number of rounds the
											// player remained in the game
	private LinkedList<Integer> maxima; // List of the maximum stake earned
										// during each game
	private Player player;
	private Wheel wheel;
	private Table table;

	/**
	 * Creates a new Simulation with the betting strategy implemented by Player
	 * player.
	 * 
	 * @param player
	 *            The player to be used in the simulation
	 * @param game
	 *            The Game organizing the Roulette game
	 * @param initStake
	 *            The initial stake assigned to the player before each
	 *            simulation session
	 * @param initDuration
	 *            The initial maximum number of rounds assigned to the player
	 *            before each simulation session
	 */
	public Simulator(Player player, Wheel wheel, Table table, int initStake,
			int initDuration) {
		this.player = player;
		this.durations = new LinkedList<Integer>();
		this.maxima = new LinkedList<Integer>();
		this.initStake = initStake;
		this.initDuration = initDuration;
		this.wheel = wheel;
		this.table = table;
	}

	/**
	 * @return LinkedList of the durations (i.e. cycles played) during each
	 *         session of the Simulation
	 */
	public LinkedList<Integer> getDurations() {
		return this.durations;
	}

	/**
	 * @return LinkedList of the maximum player's stakes during each session of
	 *         the Simulation
	 */
	public LinkedList<Integer> getMaxima() {
		return this.maxima;
	}

	/**
	 * Executes a session of the Simulation, i.e. initializes a new Player and
	 * plays a round of Roulette
	 * 
	 * @return LinkedList of the player's stake after each round of the game
	 */
	public LinkedList<Integer> session() {
		LinkedList<Integer> stakes = new LinkedList<Integer>();
		/*
		 * Reflection necessary so as to only operate on clean copies of the
		 * player
		 */
		Class<? extends Player> playerClass = this.player.getClass();
		Player sessionPlayer;
		try {
			Constructor<? extends Player> playerConstructor = playerClass
					.getConstructor(playerClass);
			sessionPlayer = (Player) playerConstructor.newInstance(this.player);
		} catch (NoSuchMethodException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			System.out
					.println("Warning: Construction of new session player object failed. Executing session with originally referenced object");
			sessionPlayer = this.player;
		}

		table.removeAllPlayers();
		table.addPlayer(sessionPlayer);

		while (sessionPlayer.isPlaying(table)) {
			RouletteGame.cycle(this.wheel, this.table);
			stakes.add(sessionPlayer.getStake());
		}
		this.table.removeAllPlayers();
		return stakes;
	}

	/**
	 * Analyzes the LinkedList of stakes resulting from a number of sessions and
	 * stores the results as LinkedLists of the durations and maximum player's
	 * stakes reached during each session.
	 */
	public void gather() {
		LinkedList<Integer> stakes = new LinkedList<Integer>();
		int max;
		this.player.setRoundsToGo(initDuration);
		this.player.setStake(initStake);

		for (int sample = 0; sample < samples; sample++) {
			stakes = this.session();
			// System.out.println(stakes);
			this.durations.add(stakes.size());
			max = -1;
			for (int stake : stakes) {
				max = (stake > max) ? stake : max;
			}
			this.maxima.add(max);
		}
	}
}
