package roulette.bin;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import roulette.outcome.Outcome;
import roulette.outcome.RouletteOutcome;

public class BinTest {

	@Test
	public void toStringShouldYieldDesiredString() {
		Outcome outcome = new RouletteOutcome("name", 2, 1);
		Outcome otherOutcome = new RouletteOutcome("otherName", 4, 2);
		Bin bin = new Bin();
		bin.add(outcome);
		bin.add(otherOutcome);

		assertEquals(
				bin.toString(),
				String.format("[%s, %s]", outcome.toString(),
						otherOutcome.toString()));
	}

}
