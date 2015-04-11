package roulette.outcome;

import java.util.Arrays;

public class RouletteOutcome extends Outcome {
	protected int[] bins;

	public RouletteOutcome(String name, int odds, int[] bins) {
		super(name, odds);
		this.bins = bins;
	}
	
	public RouletteOutcome(String name, int odds, int bin) {
		super(name, odds);
		this.bins = new int[1];
		this.bins[0] = bin;
	}

	public int[] getBins() {
		return bins;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		} else if (object.getClass() != this.getClass()) {
			return false;
		} else {
			RouletteOutcome otherOutcome = (RouletteOutcome) object;
			return (this.getName().equals(otherOutcome.getName()))
					&& (this.getOdds() == otherOutcome.getOdds())
					&& Arrays.equals(this.getBins(),otherOutcome.getBins());
		}
	}

	@Override
	public String toString() {
		return String.format("%s %s (%d:1)",this.name,Arrays.toString(this.bins),this.odds);
	}

}
