package roulette.wheel;

import java.util.Random;

/**
 * @author hyperion
 * Non-random random number generator that just returns the seed it is initialized with
 */
public class NonRandom extends Random {

	private static final long serialVersionUID = -7403223086791227943L;

	private long seed;

	public NonRandom() {
		this.setSeed(0);
	}

	public NonRandom(long seed) {
		this.setSeed(seed);
	}

	@Override
	public void setSeed(long seed) {
		// This simply truncates Long values to Integers as it is sufficient for
		// our testing purposes
		seed = (seed < Integer.MAX_VALUE) ? seed : Integer.MAX_VALUE;
		seed = (seed > Integer.MIN_VALUE) ? seed : Integer.MIN_VALUE;
		this.seed = seed;
	}

	@Override
	public int nextInt() {
		return (int)this.seed;
	}
	
	@Override
	public int nextInt(int upperLimit) {
		int nextIntVal = (this.seed < upperLimit) ? (int)this.seed : upperLimit-1;
		return nextIntVal;
	}
}
