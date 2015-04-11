package roulette.wheel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

import roulette.bin.Bin;
import roulette.bin.BinBuilder;
import roulette.outcome.Outcome;
import roulette.outcome.RouletteOutcome;

public class WheelTest {

	@Test
	public void binsMayBeSet() {
		Outcome outcomeA = new RouletteOutcome("outcome A", 1, 1);
		Outcome outcomeB = new RouletteOutcome("outcome B", 1, 1);

		Bin binA = new Bin();
		binA.add(outcomeA);
		Bin binB = new Bin();
		binB.add(outcomeA);
		binB.add(outcomeB);

		long seed = Integer.MAX_VALUE;
		Wheel wheel = new Wheel(new NonRandom(seed));
		wheel.addBin(binA);
		wheel.addBin(binB);
		
		assertEquals(binA,wheel.getBin(38));
		assertEquals(binB,wheel.getBin(39));
	}
	
	@Test
	public void outcomesMayBeRegistered() {
		long seed = Integer.MAX_VALUE;
		Wheel wheel = new Wheel(new NonRandom(seed));
		BinBuilder.buildBins(wheel);
		
		LinkedList<Outcome> outcomes = wheel.getBin(37).getOutcomes();
		assertEquals(2,outcomes.size());
		int[] fiveBins = {0,1,2,3,37};
		assertTrue(wheel.getBin(37).getOutcomes().contains(new RouletteOutcome("Five", 6, fiveBins)));
		assertTrue(wheel.getBin(37).getOutcomes().contains(new RouletteOutcome("Straight", 35, 37)));
	}

}
