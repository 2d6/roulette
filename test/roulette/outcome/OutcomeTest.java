package roulette.outcome;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import roulette.outcome.Outcome;

public class OutcomeTest {

	@Test
	public void winAmountShouldBeProductOfAmountAndOdds() {
		int odds = 2;
		int amount = 10;
		Outcome outcome = new Outcome("name", 2);
		assertEquals(outcome.winAmount(amount), odds * amount);
	}

	@Test
	public void outcomeShouldBeEqualToItself() {
		Outcome outcome = new Outcome("name", 2);
		assertTrue(outcome.equals(outcome));
	}

	@Test
	public void equalityShouldBeMutual() {
		Outcome outcome = new Outcome("name", 2);
		Outcome otherOutcome = new Outcome("name", 2);
		assertTrue(outcome.equals(otherOutcome) && otherOutcome.equals(outcome));
	}

	@Test
	public void outcomeShouldNotBeEqualToADifferingOutcome() {
		Outcome outcome = new Outcome("name", 2);
		Outcome otherOutcome = new Outcome("otherName", 2);
		assertFalse(outcome.equals(otherOutcome));
	}

	@Test
	public void toStringShouldYieldDesiredString() {
		Outcome outcome = new Outcome("name", 2);
		assertEquals(outcome.toString(), String.format("\"%s\" (%s:1)",
				outcome.getName(), outcome.getOdds()));
	}
	
	@Test
	public void comparisonOfNullOutcomesShouldYieldZero() {
		Outcome outcome = new Outcome(null, 1);
		Outcome otherOutcome = new Outcome(null, 1);
		assertEquals(outcome.compareTo(otherOutcome),0);
	}
	
	@Test
	public void comparisonToNullOutcomesShouldYieldOne() {
		Outcome outcome = new Outcome("name", 1);
		Outcome otherOutcome = new Outcome(null, 1);
		assertEquals(outcome.compareTo(otherOutcome),1);
	}
	
	@Test
	public void comparisonOfNullOutcomeToNonNullOutcomeShouldYieldMinusOne() {
		Outcome outcome = new Outcome(null, 1);
		Outcome otherOutcome = new Outcome("name", 1);
		assertEquals(outcome.compareTo(otherOutcome),-1);
	}
	
	@Test
	public void comparisonShouldBeReversible() {
		Outcome outcome = new Outcome("a", 1);
		Outcome otherOutcome = new Outcome("b", 1);
		assertEquals(outcome.compareTo(otherOutcome),-otherOutcome.compareTo(outcome));
	}
	
	@Test
	public void comparisonShouldBeTransitive() {
		Outcome outcome = new Outcome("a", 1);
		Outcome secondOutcome = new Outcome("b", 1);
		Outcome thirdOutcome = new Outcome("c", 1);
		assertEquals(outcome.compareTo(secondOutcome),secondOutcome.compareTo(thirdOutcome));
		assertEquals(outcome.compareTo(secondOutcome),outcome.compareTo(thirdOutcome));
	}
	
	@Test
	public void comparingEqualOutcomesToDifferentOutcomeShouldYieldSameResult() {
		Outcome outcome = new Outcome("b", 1);
		Outcome equalOutcome = new Outcome("b", 1);
		Outcome secondOutcome = new Outcome("a", 1);
		Outcome thirdOutcome = new Outcome("c", 1);
		assertTrue(outcome.equals(equalOutcome));
		assertEquals(outcome.compareTo(secondOutcome),equalOutcome.compareTo(secondOutcome));
		assertEquals(outcome.compareTo(thirdOutcome),equalOutcome.compareTo(thirdOutcome));
	}
}
