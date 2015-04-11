package roulette.bet;

import static org.junit.Assert.*;

import org.junit.Test;

import roulette.outcome.Outcome;

public class BetTest {

	@Test
	public void getWinAmountGivesCorrectOutput() {
		Outcome outcome = new Outcome("Testoutcome 1-2-3",3);
		Bet bet = new Bet(null,100,outcome);
		assertEquals(bet.getWinAmount(),400);
	}
	
	@Test
	public void toStringProducesStringCorrespondingToAmountAndOutcome() {
		Outcome outcome = new Outcome("Testoutcome 1-2-3",3);
		Bet bet = new Bet(null,100,outcome);
		assertEquals(bet.toString(),"100$ on \"Testoutcome 1-2-3\" (3:1)");
	}

}
