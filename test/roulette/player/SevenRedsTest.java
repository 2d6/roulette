package roulette.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.Test;

import roulette.outcome.Outcome;
import roulette.outcome.RouletteOutcome;
import roulette.table.Table;

public class SevenRedsTest {

	private static final int[] RED = { 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21,
			23, 25, 27, 30, 32, 34, 36 };
	private static final int[] BLACK = { 2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22,
			24, 26, 28, 29, 31, 33, 35 };

	@Test
	public void ShouldPlaceBetOnBlackAfterSevenSubsequentRedOutcomes() {
		Table table = new Table(10, 1000);
		SevenReds sevenReds = new SevenReds(table, 100, -1);

		Outcome black = new RouletteOutcome("Black", 1, BLACK);
		Outcome red = new RouletteOutcome("Red", 1, RED);
		LinkedList<Outcome> winningOutcomes = new LinkedList<Outcome>();
		winningOutcomes.add(red);

		for (int i = 0; i < 7; i++) {
			sevenReds.receiveWinningOutcomes(winningOutcomes);
			sevenReds.placeBets(table);
		}
		try {
			assertEquals(black, table.getBets().getFirst().getOutcome());
		} catch (NoSuchElementException e) {
			fail("Encountered NoSuchElementException during retrieval of bets. This means that no bet has been submitted although this was expected.");
		}
	}
	
	@Test
	public void redWinningOutcomeShouldReduceRedCountByOne() {
		Table table = new Table(10, 1000);
		SevenReds sevenReds = new SevenReds(table, 100, -1);
		
		Outcome red = new RouletteOutcome("Red", 1, RED);
		LinkedList<Outcome> winningOutcomes = new LinkedList<Outcome>();
		winningOutcomes.add(red);
		sevenReds.receiveWinningOutcomes(winningOutcomes);
		assertEquals(6,sevenReds.getRedCount());
	}
	
	@Test
	public void blackWinningOutcomeShouldNotReduceRedCount() {
		Table table = new Table(10, 1000);
		SevenReds sevenReds = new SevenReds(table, 100, -1);
		
		Outcome black = new RouletteOutcome("Black", 1, BLACK);
		LinkedList<Outcome> winningOutcomes = new LinkedList<Outcome>();
		winningOutcomes.add(black);
		sevenReds.receiveWinningOutcomes(winningOutcomes);
		assertEquals(7,sevenReds.getRedCount());
	}

}
