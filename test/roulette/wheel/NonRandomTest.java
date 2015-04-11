package roulette.wheel;

import static org.junit.Assert.*;

import org.junit.Test;

import roulette.wheel.NonRandom;

public class NonRandomTest {

	@Test
	public void nonRandomShouldReturnSeed() {
		long seed = Integer.MAX_VALUE;
		NonRandom nonRandom = new NonRandom(seed);
		assertEquals(nonRandom.nextInt(), seed);
	}
	
	@Test
	public void nonRandomShouldCorrectlyTruncateLongSeeds() {
		long highSeed = Long.MAX_VALUE;
		long lowSeed = Long.MIN_VALUE;
		NonRandom nonRandom = new NonRandom();
		
		nonRandom.setSeed(highSeed);
		assertEquals(nonRandom.nextInt(), Integer.MAX_VALUE);
		
		nonRandom.setSeed(lowSeed);
		assertEquals(nonRandom.nextInt(), Integer.MIN_VALUE);
	}

}
