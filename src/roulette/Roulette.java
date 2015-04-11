package roulette;

import java.util.LinkedList;

import roulette.bin.BinBuilder;
import roulette.game.RouletteGame;
import roulette.player.Martingale;
import roulette.player.SevenReds;
import roulette.simulator.Simulator;
import roulette.table.Table;
import roulette.wheel.Wheel;

public class Roulette {

	/**
	 * Returns the average value of a LinkedList of Integers
	 * 
	 * @param list
	 *            List of Integers
	 * @return Double value of the average of the list
	 */
	private static double average(LinkedList<Integer> list) {
		double average = 0;
		for (int entry : list) {
			average += entry;
		}
		average = (list.size() > 0) ? average / list.size() : -1;
		return average;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Table table = new Table(5, 1000);
		Wheel wheel = new Wheel();
		BinBuilder.buildBins(wheel);
		Martingale player = new Martingale(table);
		player.setOutcome();

		Simulator simulator = new Simulator(player, wheel, table, 100, 250);

		simulator.gather();

		System.out.println("Martingale\n--------------");
		System.out.println(simulator.getDurations());
		System.out.println(simulator.getMaxima());
		System.out.println(String.format("Number of games simulated: %d",
				simulator.getDurations().size()));
		System.out.println(String.format("Average maximum stakes: %.2f",
				average(simulator.getMaxima())));
		System.out.println(String.format("Average duration: %.2f",
				average(simulator.getDurations())));
		
		table = new Table(5,1000);
		player = new SevenReds(table);
		player.setOutcome();
		simulator = new Simulator(player, wheel, table, 100, 250);
		simulator.gather();

		System.out.println("\n\nSevenReds\n--------------");
		System.out.println(simulator.getDurations());
		System.out.println(simulator.getMaxima());
		System.out.println(String.format("Number of games simulated: %d",
				simulator.getDurations().size()));
		System.out.println(String.format("Average maximum stakes: %.2f",
				average(simulator.getMaxima())));
		System.out.println(String.format("Average duration: %.2f",
				average(simulator.getDurations())));
	}
}
