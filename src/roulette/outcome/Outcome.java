package roulette.outcome;

/**
 * A possible outcome in a game of Roulette, e.g. a corner bet.
 * @author hyperion
 */
public class Outcome implements Comparable<Outcome> {

	/**
	 * Holds the name of the Outcome
	 */
	protected String name;

	/**
	 * Holds the odds of the Outcome
	 */
	protected int odds;

	/**
	 * @param name The name of the Outcome
	 * @param odds Numerator n of the odds of the Outcome as in (n:1)
	 */
	public Outcome(String name, int odds) {
		this.name = name;
		this.odds = odds;
	}

	/**
	 * @return The name of the Outcome
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name The name of the Outcome
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Numerator n of the odds of the Outcome as in (n:1)
	 */
	public int getOdds() {
		return this.odds;
	}

	/**
	 * @param odds Numerator n of the odds of the Outcome as in (n:1)
	 */
	public void setOdds(int odds) {
		this.odds = odds;
	}

	/**
	 * Calculates the amount won by betting on a winning Outcome
	 * 
	 * @param amount
	 *            Amount bet on the outcome
	 * @return Amount won
	 */
	public int winAmount(int amount) {
		return amount * this.getOdds();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		else if (object.getClass() != this.getClass()) {
			return false;
		}
		else {
			Outcome otherOutcome = (Outcome) object;
			return (this.getName().equals(otherOutcome.getName())) && (this.getOdds() == otherOutcome.getOdds());
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return String.format("\"%s\" (%s:1)", this.getName(), this.getOdds());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object) This sorting method
	 * is necessary in order to enable using Outcome as an element within
	 * ordered Sets. However, the comparison by UTF8 value chosen below is not
	 * suitable to achieve alphabetically ordered structures of Outcomes.
	 */
	@Override
	public int compareTo(Outcome other) {
		if (this.getName() == null && other.getName() == null) {
			return 0;
		}
		if (this.getName() == null) {
			return -1;
		}
		if (other.getName() == null) {
			return 1;
		}
		int output = this.getName().compareTo(other.getName());
		if (output != 0) {
			output /= Math.abs(output);
		}

		return output;
	}
}
