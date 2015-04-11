package roulette.outcome;

import static org.junit.Assert.*;

import org.junit.Test;

public class RouletteOutcomeTest {

	@Test
	public void equalityIncludesBinsArray() {
		RouletteOutcome outcome1 = new RouletteOutcome("Test", 1, 1);
		RouletteOutcome outcome2 = new RouletteOutcome("Test", 1, 1);
		RouletteOutcome different = new RouletteOutcome("Test", 1, 2);
		assertEquals(outcome1,outcome2);
		assertNotEquals(outcome1, different);
	}

}
