package roulette.table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import roulette.bet.Bet;
import roulette.exceptions.InvalidBetError;
import roulette.outcome.Outcome;
import roulette.outcome.RouletteOutcome;

public class TableTest {

	@Test
	public void betWithTooLowAmountShouldBeInvalid() {
		Table table = new Table(10, 100);
		Bet lowBet = new Bet(null, 1, null);
		assertFalse(table.isValid(lowBet));
	}

	@Test
	public void betWithTooHighAmountShouldBeInvalid() {
		Table table = new Table(10, 100);
		Bet highBet = new Bet(null, 1000, null);
		assertFalse(table.isValid(highBet));
	}

	@Test
	public void assignmentOfInvalidBetShouldThrowError() {
		Table table = new Table(10, 100);
		Bet invalidBet = new Bet(null, 0, null);
		InvalidBetError err = null;
		try {
			table.placeBet(invalidBet);
		} catch (InvalidBetError e) {
			err = e;
		} finally {
			assertTrue(err != null);
		}
	}

	@Test
	public void placedBetMayBeRetrieved() {
		Table table = new Table(10, 100);
		Bet validBet = new Bet(null, 11, new RouletteOutcome("Testoutcome", 1, 1));
		try {
			table.placeBet(validBet);
		} catch (InvalidBetError e) {
			fail("Placement of valid bet throws InvalidBetError.");
		} finally {
			assertTrue(table.getBets().contains(validBet));
		}
	}

	@Test
	public void betMayBeRemoved() {
		Table table = new Table(10, 100);
		Bet validBet = new Bet(null, 11, new RouletteOutcome("Testoutcome", 1, 1));
		try {
			table.placeBet(validBet);
		} catch (InvalidBetError e) {
			fail("Placement of valid bet throws InvalidBetError.");
		} finally {
			table.removeBet(validBet);
			assertFalse(table.getBets().contains(validBet));
		}
	}

	@Test
	public void toStringMethodReturnsListOfPlacedBets() {
		Table table = new Table(10, 100);
		Bet validBetA = new Bet(null, 11, new RouletteOutcome("TestoutcomeA", 1, 1));
		Bet validBetB = new Bet(null, 11, new RouletteOutcome("TestoutcomeB", 1, 1));
		try {
			table.placeBet(validBetA);
			table.placeBet(validBetB);
		} catch (InvalidBetError e) {
			fail("Placement of valid bet throws InvalidBetError.");
		} finally {
			String correctString = String.format("%s\n%s\n%s\n",
					"Table, containing the following bets:",
					validBetA.toString(), validBetB.toString());
			assertEquals(table.toString(), correctString);
		}
	}

}
