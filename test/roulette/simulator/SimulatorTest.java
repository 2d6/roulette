package roulette.simulator;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;

import roulette.bin.BinBuilder;
import roulette.game.RouletteGame;
import roulette.player.Martingale;
import roulette.table.Table;
import roulette.wheel.NonRandomFromArray;
import roulette.wheel.Wheel;

public class SimulatorTest {
	
	@Test
	public void sessionShouldProduceExpectedListOfStakes() {
		int[] randArray = {1, 36, 35, 0, 4, 25, 6, 7, 8, 9, 37, 0}; // "Random" list of successive bin index results
		int betAmount = 10; // initial betting Value of the Martingale player
		LinkedList<Integer> expectedStakes = new LinkedList<Integer>(); // List of stakes predicted from the initial array
		int currentStake = 100;
		
		for (int entry : randArray) {
			if (betAmount > currentStake) {break;}
			currentStake -= betAmount;
			
			if (entry > 0 && entry < 19) {
				currentStake += 2 * betAmount;
				betAmount = 10;
			}
			else {
				betAmount *= 2;
			}
			
			expectedStakes.add(currentStake);
		}
		
		Table table = new Table(10,1000);
		NonRandomFromArray rng = new NonRandomFromArray();
		rng.setSeedArray(randArray);
		Wheel wheel = new Wheel((Random)rng);
		BinBuilder.buildBins(wheel);
		
		Martingale player = new Martingale(table, 100, -1);
		player.setOutcome();
		
		Simulator simulator = new Simulator(player, wheel, table, 100, 11);
		
		if (expectedStakes.size() > 11) {
			expectedStakes.removeLast(); //remove last entry, as only 11 cycles will be played
		}
		LinkedList<Integer> actualStakes = simulator.session();
		assertEquals(expectedStakes,actualStakes);
	}

}
