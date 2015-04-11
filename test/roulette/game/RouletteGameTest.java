package roulette.game;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import roulette.bin.BinBuilder;
import roulette.player.Passenger57;
import roulette.table.Table;
import roulette.wheel.NonRandom;
import roulette.wheel.Wheel;

public class RouletteGameTest {

	@Test
	public void playerShouldBeNotifiedOfWinningBet() {
		Table table = new Table(10, 1000);
		Random nonRandom = new NonRandom();
		nonRandom.setSeed(2L);
		Wheel wheel = new Wheel(nonRandom);
		BinBuilder.buildBins(wheel);

		Passenger57 player = new Passenger57(table);

		for (int i = 0; i < 3; i++) {
			RouletteGame.cycle(wheel, table);
		}
		assertEquals(3, player.getWins());
	}
	
	@Test
	public void playerShouldBeNotifiedOfLosingBet() {
		Table table = new Table(10, 1000);
		Random nonRandom = new NonRandom();
		nonRandom.setSeed(1L);
		Wheel wheel = new Wheel(nonRandom);
		BinBuilder.buildBins(wheel);

		Passenger57 player = new Passenger57(table);

		for (int i = 0; i < 3; i++) {
			RouletteGame.cycle(wheel, table);
		}
		assertEquals(3, player.getLosses());
	}

}
