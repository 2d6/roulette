package roulette.bin;

import java.util.LinkedList;

import roulette.outcome.Outcome;

public class Bin {
	private LinkedList<Outcome> outcomes;

	public Bin() {
		this.outcomes = new LinkedList<Outcome>();
	}

	public Bin(Outcome[] outcomeArray) {
		this.outcomes = new LinkedList<Outcome>();
		for (Outcome outcome : outcomeArray) {
			this.outcomes.add(outcome);
		}
	}

	public Bin(LinkedList<Outcome> outcomeCollection) {
		this.outcomes = outcomeCollection;
	}

	public LinkedList<Outcome> getOutcomes() {
		return outcomes;
	}

	public void add(Outcome outcome) {
		this.outcomes.add(outcome);
	}

	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append('[');
		for (Outcome outcome : this.outcomes) {
			stringBuffer.append(", ").append(outcome.toString());
		}
		stringBuffer.delete(1, 3);
		stringBuffer.append(']');
		return stringBuffer.toString();
	}
}
