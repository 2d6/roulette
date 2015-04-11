package roulette.wheel;

import java.util.ArrayList;
import java.util.Random;

import roulette.bin.Bin;
import roulette.outcome.Outcome;

public class Wheel {
	/**
	 * Contains the bins of a roulette wheel from 0 through 36. Item 37 of this
	 * ArrayList is the "00" bin.
	 */
	private ArrayList<Bin> bins;
	private Random rng;
	
	public Wheel() {
		Random rng = new Random();
		this.setRandom(rng);
		this.bins = new ArrayList<Bin>(38);
		for (int i = 0; i < 38; i++) {
			this.addBin(new Bin());
		}
	}

	public Wheel(Random rng) {
		this();
		this.setRandom(rng);
	}

	/**
	 * Returns the Bin at the index specified. The bin indexes correspond to the
	 * roulette bins of 0-36. Index 37 corresponds to the "00" bin.
	 * 
	 * @param index
	 *            Index of the desired bin. Should lie within 0 and 37
	 *            (boundaries included).
	 * @return Bin corresponding to the index
	 */
	public Bin getBin(int index) {
		Bin binResult = null;
		if (index >= 0 && index < this.bins.size()) {
			binResult = this.bins.get(index);
		}
		return binResult;
	}

	public void setRandom(Random rng) {
		this.rng = rng;
	}

	public void addBin(Bin bin) {
		this.bins.add(bin);
	}

	/**
	 * Associates the bin at index binIndex with the given Outcome. If the index
	 * is valid (i.e. this.getBin != null), the Outcome is registered with the
	 * wheel and bin and may be retrieved by this.getOutcome.
	 * 
	 * @param binIndex
	 *            Index of the bin the outcome should be registered to
	 * @param outcome
	 *            Outcome that should be registered with the bin corresponding
	 *            to the given index
	 */
	public void addOutcomeToBin(int binIndex, Outcome outcome) {
		Bin bin = this.getBin(binIndex);
		if (bin != null) {
			bin.add(outcome);
		}
	}

	/**
	 * @return a random Bin in Wheel.bins
	 */
	public Bin next() {
		int randomIndex = this.rng.nextInt(this.bins.size());
		return this.getBin(randomIndex);
	}
}
