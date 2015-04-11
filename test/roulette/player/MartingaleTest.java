package roulette.player;

import static org.junit.Assert.*;

import org.junit.Test;

import roulette.bet.Bet;
import roulette.outcome.Outcome;
import roulette.table.Table;

public class MartingaleTest {

	@Test
	public void betAmountShouldDoubleUponLoss() {
		Table table = new Table(1, 1000);

		Martingale martingale = new Martingale(table, 10000, -1);
		martingale.setOutcome();

		Outcome outcome = new Outcome("Testoutcome", 1);

		Bet bet = new Bet(martingale, 10, outcome);

		martingale.placeBets(table);
		Bet initialBet = table.getBets().getFirst();
		martingale.lose(bet);
		table.removeBet(initialBet);

		martingale.placeBets(table);
		Bet secondBet = table.getBets().getFirst();

		assertEquals(initialBet.getAmount() * 2, secondBet.getAmount());
	}

	@Test
	public void betAmountShouldReturnToStartingBetUponWin() {
		Table table = new Table(1, 1000);

		Martingale martingale = new Martingale(table, 10000, -1);
		martingale.setOutcome();

		martingale.placeBets(table);
		Bet initialBet = table.getBets().getFirst();
		martingale.lose(initialBet);
		table.removeBet(initialBet);

		martingale.placeBets(table);
		martingale.win(table.getBets().getFirst());
		table.removeBet(table.getBets().getFirst());

		martingale.placeBets(table);
		Bet thirdBet = table.getBets().getFirst();
		table.removeBet(thirdBet);

		assertEquals(initialBet.getAmount(), thirdBet.getAmount());
	}

	@Test
	public void noBetShouldBePlacedWithInsufficientFunds() {
		Table table = new Table(1, 10);

		Martingale martingale = new Martingale(table);

		assertFalse(martingale.isPlaying(table));

		martingale.placeBets(table);
		Bet bet = table.getBets().peek();
		assertEquals(null, bet);
	}

	@Test
	public void noBetAboveTableLimitShouldBePlaced() {
		Table table = new Table(2, 3);

		Martingale martingale = new Martingale(table, 1000, -1);

		martingale.lose(null);

		assertFalse(martingale.isPlaying(table));

		martingale.placeBets(table);
		Bet bet = table.getBets().peek();
		assertEquals(null, bet);
	}

	@Test
	public void isPlayingShouldBeFalseWhenZeroRoundsToGo() {
		Table table = new Table(2, 3);

		Martingale martingale = new Martingale(table, 1000, 0);

		assertFalse(martingale.isPlaying(table));
	}

}
