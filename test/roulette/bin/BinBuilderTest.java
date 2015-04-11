package roulette.bin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.Test;

import roulette.outcome.Outcome;
import roulette.outcome.RouletteOutcome;
import roulette.wheel.Wheel;

//TODO check odds!
public class BinBuilderTest {

	@Test
	public void straightBetsShouldBeGettable() {
		assertEquals(new RouletteOutcome("Straight", 35, 0),
				BinBuilder.getStraightOutcome(0));
		assertEquals(new RouletteOutcome("Straight", 35, 1),
				BinBuilder.getStraightOutcome(1));
		assertEquals(new RouletteOutcome("Straight", 35, 37),
				BinBuilder.getStraightOutcome(37));
	}

	@Test
	public void callForInexistantStraightBetsShouldReturnNull() {
		assertEquals(null, BinBuilder.getStraightOutcome(38));
	}

	@Test
	public void horizontalSplitBetsShouldBeGettable() {
		int[] bins = { 1, 2 };
		int[] unsortedBins = { 2, 1 };
		assertEquals(new RouletteOutcome("Split", 17, bins),
				BinBuilder.getSplitBetOutcome(bins));
		assertTrue(BinBuilder.getSplitBetOutcome(unsortedBins) == BinBuilder
				.getSplitBetOutcome(bins));
	}

	@Test
	public void verticalSplitBetsShouldBeGettable() {
		int[] bins = { 1, 4 };
		int[] unsortedBins = { 4, 1 };
		assertEquals(new RouletteOutcome("Split", 17, bins),
				BinBuilder.getSplitBetOutcome(bins));
		assertTrue(BinBuilder.getSplitBetOutcome(unsortedBins) == BinBuilder
				.getSplitBetOutcome(bins));
	}

	@Test
	public void callForInexistantSplitBetsShouldReturnNull() {
		int[] bins = { 1, 3 };
		assertEquals(null, BinBuilder.getSplitBetOutcome(bins));
	}

	@Test
	public void streetBetsShouldBeGettable() {
		int[] bins = { 1, 2, 3 };
		int[] unsortedBins = { 3, 1, 2 };
		assertEquals(new RouletteOutcome("Street", 11, bins),
				BinBuilder.getStreetBetOutcome(bins));
		assertTrue(BinBuilder.getStreetBetOutcome(unsortedBins) == BinBuilder
				.getStreetBetOutcome(bins));
	}

	@Test
	public void callForInexistantStreetBetsShouldReturnNull() {
		int[] bins = { 1, 3, 5 };
		assertEquals(null, BinBuilder.getStreetBetOutcome(bins));
	}

	@Test
	public void cornerBetsShouldBeGettable() {
		int[] bins = { 1, 2, 4, 5 };
		int[] unsortedBins = { 4, 1, 5, 2 };
		assertEquals(new RouletteOutcome("Corner", 8, bins),
				BinBuilder.getCornerBetOutcome(bins));
		assertTrue(BinBuilder.getCornerBetOutcome(bins) == BinBuilder
				.getCornerBetOutcome(unsortedBins));
	}

	@Test
	public void callForInexistantCornerBetsShouldReturnNull() {
		int[] bins = { 1, 2, 3, 4 };
		assertEquals(null, BinBuilder.getStreetBetOutcome(bins));
	}

	@Test
	public void fiveBetShouldBeGettable() {
		int[] bins = {0, 1, 2, 3, 37};
		assertEquals(new RouletteOutcome("Five", 6, bins),
				BinBuilder.getFiveBetOutcome());
	}

	@Test
	public void lineBetsShouldBeGettable() {
		int[] bins = { 1, 2, 3, 4, 5, 6 };
		int[] unsortedBins = { 3, 6, 1, 2, 5, 4 };
		assertEquals(new RouletteOutcome("Line", 5, bins),
				BinBuilder.getLineBetOutcome(bins));
		assertTrue(BinBuilder.getLineBetOutcome(bins) == BinBuilder
				.getLineBetOutcome(unsortedBins));
	}

	@Test
	public void callForInexistantLineBetsShouldReturnNull() {
		int[] bins = { 1, 2, 3, 4, 5, 7 };
		assertEquals(null, BinBuilder.getLineBetOutcome(bins));
	}

	@Test
	public void rangeBetsShouldBeGettable() {
		int[] bins = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		int[] unsortedBins = { 5, 6, 7, 3, 2, 1, 4, 11, 12, 8, 10, 9 };
		String outcomeName = String.format("Range First",
				Arrays.toString(bins));
		;
		assertEquals(new RouletteOutcome(outcomeName, 2, bins),
				BinBuilder.getRangeBetOutcome(bins));
		assertTrue(BinBuilder.getRangeBetOutcome(bins) == BinBuilder
				.getRangeBetOutcome(unsortedBins));
	}

	@Test
	public void callForInexistantRangeBetsShouldReturnNull() {
		int[] bins = { 12, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13 };
		assertEquals(null, BinBuilder.getRangeBetOutcome(bins));
	}

	@Test
	public void columnBetsShouldBeGettable() {
		int[] bins = { 1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34 };
		int[] unsortedBins = { 34, 31, 28, 25, 22, 19, 16, 13, 10, 7, 4, 1 };
		String outcomeName = "Column 1"
		;
		assertEquals(new RouletteOutcome(outcomeName, 2, bins),
				BinBuilder.getColumnBetOutcome(bins));
		assertTrue(BinBuilder.getColumnBetOutcome(bins) == BinBuilder
				.getColumnBetOutcome(unsortedBins));
	}

	@Test
	public void callForInexistantColumnBetsShouldReturnNull() {
		int[] bins = { 1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 35 };
		assertEquals(null, BinBuilder.getColumnBetOutcome(bins));
	}

	@Test
	public void highLowBetsShouldBeGettable() {
		int[] bins = new int[18];
		for (int i = 0; i < 18; i++) {
			bins[i] = i + 1;
		}

		int[] unsortedBins = new int[18];
		for (int i = 0; i < 18; i++) {
			unsortedBins[i] = 18 - i;
		}

		String outcomeName = "Low";
		;
		assertEquals(new RouletteOutcome(outcomeName, 1, bins),
				BinBuilder.getHighLowBetOutcome(bins));
		assertTrue(BinBuilder.getHighLowBetOutcome(bins) == BinBuilder
				.getHighLowBetOutcome(unsortedBins));
	}

	@Test
	public void callForInexistantHighLowBetsShouldReturnNull() {
		int[] bins = new int[18];
		for (int i = 0; i < 18; i++) {
			bins[i] = i + 1;
		}
		bins[0] = 36;
		assertEquals(null, BinBuilder.getHighLowBetOutcome(bins));
	}

	@Test
	public void colorBetsShouldBeGettable() {
		int[] bins = { 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30,
				32, 34, 36 };
		int[] unsortedBins = { 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25,
				27, 30, 32, 36, 34 };

		String outcomeName = "Red";
		
		assertEquals(new RouletteOutcome(outcomeName, 1, bins),
				BinBuilder.getColorBetOutcome(bins));
		assertTrue(BinBuilder.getColorBetOutcome(bins) == BinBuilder
				.getColorBetOutcome(unsortedBins));
	}

	@Test
	public void callForInexistantColorBetsShouldReturnNull() {
		int[] bins = { 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21,
				23, 25, 27, 30, 32, 34, 37 };
		assertEquals(null, BinBuilder.getColorBetOutcome(bins));
	}
	
	@Test
	public void evenOddBetsShouldBeGettable() {
		int[] bins = { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23,25,27,29,31,33,35};
		int[] unsortedBins = { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23,25,27,29,31,35,33};

		String outcomeName = "Odd";
		
		assertEquals(new RouletteOutcome(outcomeName, 1, bins),
				BinBuilder.getEvenOddBetOutcome(bins));
		assertTrue(BinBuilder.getEvenOddBetOutcome(bins) == BinBuilder
				.getEvenOddBetOutcome(unsortedBins));
	}

	@Test
	public void callForInexistantEvenOddBetsShouldReturnNull() {
		int[] bins = { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23,25,27,29,31,33,36};
		assertEquals(null, BinBuilder.getEvenOddBetOutcome(bins));
	}
	
	@Test
	public void allOutcomesShouldBeRegisteredToAWheel() {
		Wheel wheel = new Wheel();
		BinBuilder.buildBins(wheel);
		
		Collection<Outcome>outcomes = new LinkedList<Outcome>();
		
		assertTrue(wheel.getBin(1).getOutcomes().contains(BinBuilder.getStraightOutcome(1)));
		
		int[] splitHor = {1,2};
		assertTrue(wheel.getBin(1).getOutcomes().contains(BinBuilder.getSplitBetOutcome(splitHor)));
		
		int[] splitVer = {1,4};
		assertTrue(wheel.getBin(1).getOutcomes().contains(BinBuilder.getSplitBetOutcome(splitVer)));
		
		assertTrue(wheel.getBin(1).getOutcomes().contains(BinBuilder.getHighLowBetOutcome(BinBuilder.LOW)));
		
		assertTrue(wheel.getBin(1).getOutcomes().contains(BinBuilder.getColorBetOutcome(BinBuilder.RED)));
		
		int[] street = {1,2,3};
		assertTrue(wheel.getBin(1).getOutcomes().contains(BinBuilder.getStreetBetOutcome(street)));
		
		assertTrue(wheel.getBin(1).getOutcomes().contains(BinBuilder.getRangeBetOutcome(BinBuilder.RANGEFIRST)));
		
		int[] corner = {1,2,4,5};
		assertTrue(wheel.getBin(1).getOutcomes().contains(BinBuilder.getCornerBetOutcome(corner)));
		
		int[] line = {1,2,3,4,5,6};
		assertTrue(wheel.getBin(1).getOutcomes().contains(BinBuilder.getLineBetOutcome(line)));
		
		assertTrue(wheel.getBin(1).getOutcomes().contains(BinBuilder.getColumnBetOutcome(BinBuilder.COL1)));
		
		assertTrue(wheel.getBin(1).getOutcomes().contains(BinBuilder.getEvenOddBetOutcome(BinBuilder.ODD)));
	}
}
