package roulette.bin;

import java.util.Arrays;
import java.util.HashMap;
import roulette.outcome.Outcome;
import roulette.outcome.RouletteOutcome;
import roulette.wheel.Wheel;

public class BinBuilder {

	/*
	 * These static variables hold the bin indexes for various bets
	 */
	public static final int[] RED = { 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21,
			23, 25, 27, 30, 32, 34, 36 };
	public static final int[] BLACK = { 2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22,
			24, 26, 28, 29, 31, 33, 35 };
	public static final int[] RANGEFIRST;
	public static final int[] RANGESECOND;
	public static final int[] RANGETHIRD;
	public static final int[] COL1;
	public static final int[] COL2;
	public static final int[] COL3;
	public static final int[] ODD;
	public static final int[] EVEN;
	public static final int[] LOW;
	public static final int[] HIGH;
	private static HashMap<String, RouletteOutcome> outcomes;

	static {
		ODD = new int[18];
		EVEN = new int[18];
		LOW = new int[18];
		HIGH = new int[18];
		RANGEFIRST = new int[12];
		RANGESECOND = new int[12];
		RANGETHIRD = new int[12];
		COL1 = new int[12];
		COL2 = new int[12];
		COL3 = new int[12];
		for (int i = 0; i < 18; i++) {
			ODD[i] = 2 * i + 1;
			EVEN[i] = 2 * i + 2;
			LOW[i] = i + 1;
			HIGH[i] = i + 19;
		}
		for (int i = 0; i < 12; i++) {
			RANGEFIRST[i] = i + 1;
			RANGESECOND[i] = i + 13;
			RANGETHIRD[i] = i + 25;
			COL1[i] = 3 * i + 1;
			COL2[i] = 3 * i + 2;
			COL3[i] = 3 * i + 3;
		}
	}

	/**
	 * Assigns all possible Outcomes to the Bins on a Wheel in a game of
	 * Roulette.
	 * 
	 * @param wheel
	 *            Wheel to be modified
	 */
	public static void buildBins(Wheel wheel) {
		buildOutcomeMap();
		//For all Outcomes in the outcomes HashMap
		for (RouletteOutcome outcome : outcomes.values()) {
			for (int binIndex : outcome.getBins()) {
				wheel.addOutcomeToBin(binIndex, outcome);
			}
		}
	}

	/**
	 * Forces the creation and of the outcomes Map and fills it with all
	 * available outcomes.
	 */
	private static void buildOutcomeMap() {
		getStraightOutcome(0);
		getSplitBetOutcome(new int[2]);
		getStreetBetOutcome(new int[3]);
		getCornerBetOutcome(new int[4]);
		getFiveBetOutcome();
		getLineBetOutcome(new int[6]);
		getRangeBetOutcome(RANGEFIRST);
		getColumnBetOutcome(COL1);
		getHighLowBetOutcome(LOW);
		getColorBetOutcome(RED);
		getEvenOddBetOutcome(EVEN);
	}

	/**
	 * Returns the concatenation of the outcome identifier and it's constituting
	 * bin numbers given within the numbers array.
	 * 
	 * @param identifier
	 *            Identifier (Type) of the outcome, e.g. "Street", "Line" etc.
	 * @param bins
	 *            Array of the bin numbers constituting the outcome
	 * @return The concatenation of the identifier and the constituting numbers,
	 *         following the pattern
	 *         "IDENTIFIER numbers[0]-numbers[1]-...-numbers[numbers.length]",
	 *         e.g. "Split 4-5".
	 */
	public static String createOutcomeName(String identifier, int[] bins) {
		// TODO: Unit test
		int bin;

		StringBuffer outcomeName = new StringBuffer();
		outcomeName.append(identifier);

		if (identifier != "") {
			outcomeName.append(" ");
		}

		if (bins.length > 0) {
			for (int i = 0; i < bins.length; i++) {
				bin = bins[i];
				/*
				 * Bin number 37 is the representation of "00", which cannot be
				 * represented by an Integer
				 */
				if (bin == 37) {
					outcomeName.append("00");
				} else {
					outcomeName.append(bin);
				}

				if (bins.length > 1 && i + 1 != bins.length) {
					outcomeName.append("-");
				}
			}
		}

		return outcomeName.toString();
	}

	/**
	 * Assigns a newly created Outcome to the corresponding bins given by the
	 * numbers in the "bins" array to the Wheel. Creates a new Outcome object
	 * from the outcome identifier and it's constituting bin numbers, as well as
	 * the given odds. The name is constructed in the same fashion as in the
	 * createOutcomeName function. @see createOutcomeName
	 * 
	 * @param identifier
	 *            Identifier (Type) of the outcome, e.g. "Street", "Line" etc.
	 * @param bins
	 *            Array of the bin numbers constituting the outcome
	 * @param odds
	 *            The odds of the outcome as the numerator n in (n:1) notation.
	 * @return The outcome created from the input parameters
	 */

	public static Outcome getStraightOutcome(int bin) {
		if (outcomes == null) {
			outcomes = new HashMap<String, RouletteOutcome>();
		}
		if (!outcomes.containsKey(bin)) {
			for (int index = 0; index < 38; index++) {
				outcomes.put(Integer.toString(index), new RouletteOutcome(
						"Straight", 35, index));
			}
		}
		return outcomes.get(Integer.toString(bin));
	}

	/**
	 * Assigns the Outcomes corresponding to Split Bets (i.e. adjacent number
	 * bets) to the Wheel. The odds for these bets are 17:1.
	 * 
	 * @param bins
	 *            Array of bin indexes corresponding to the outcome, e.g. from
	 *            the static final values included in this class.
	 */
	public static Outcome getSplitBetOutcome(int[] bins) {
		Arrays.sort(bins);
		if (bins.length == 2) {

			if (outcomes == null) {
				outcomes = new HashMap<String, RouletteOutcome>();
			}

			if (!outcomes.containsKey(Arrays.toString(bins))) {
				for (int index = 1; index < 36; index++) {

					if (index % 3 != 0) { // Get the horizontal split bets
						int[] binArray = { index, index + 1 };
						String binString = Arrays.toString(binArray);
						outcomes.put(binString, new RouletteOutcome("Split",
								17, binArray));
					}
					if (index < 34) { // Get the vertical split bets
						int[] binArray = { index, index + 3 };
						String binString = Arrays.toString(binArray);
						outcomes.put(binString, new RouletteOutcome("Split",
								17, binArray));
					}
				}
			}
			return outcomes.get(Arrays.toString(bins));
		}
		return null;
	}

	/**
	 * Assigns the Outcomes corresponding to Street Bets (i.e. entire row bets,
	 * e.g. 4-5-6) to the Wheel. The odds for these bets are 11:1.
	 * 
	 * @param bins
	 *            Array of bin indexes corresponding to the outcome, e.g. from
	 *            the static final values included in this class.
	 */
	public static Outcome getStreetBetOutcome(int[] bins) {
		Arrays.sort(bins);
		if (bins.length == 3) {

			if (outcomes == null) {
				outcomes = new HashMap<String, RouletteOutcome>();
			}

			if (!outcomes.containsKey(Arrays.toString(bins))) {

				for (int index = 1; index < 37; index += 3) {
					int[] binArray = new int[3];
					binArray[0] = index;
					binArray[1] = index + 1;
					binArray[2] = index + 2;
					String binString = Arrays.toString(binArray);
					outcomes.put(binString, new RouletteOutcome("Street", 11,
							binArray));
				}
			}
			return outcomes.get(Arrays.toString(bins));
		}
		return null;
	}

	/**
	 * Assigns the Outcomes corresponding to Corner Bets (i.e. bets on a square
	 * of numbers) to the Wheel. The odds for these bets are 8:1.
	 * 
	 * @param bins
	 *            Array of bin indexes corresponding to the outcome, e.g. from
	 *            the static final values included in this class.
	 */
	public static Outcome getCornerBetOutcome(int[] bins) {
		Arrays.sort(bins);
		if (bins.length == 4) {

			if (outcomes == null) {
				outcomes = new HashMap<String, RouletteOutcome>();
			}

			if (!outcomes.containsKey(Arrays.toString(bins))) {
				int index = 1;
				while (index < 34) {

					int[] binArray = new int[4];
					binArray[0] = index;
					binArray[1] = index + 1;
					binArray[2] = index + 3;
					binArray[3] = index + 4;
					String binString = Arrays.toString(binArray);

					outcomes.put(binString, new RouletteOutcome("Corner", 8,
							binArray));

					if (index % 3 == 1) {
						index++;
					} else {
						index += 2;
					}
				}
			}
			return outcomes.get(Arrays.toString(bins));
		}
		return null;
	}

	/**
	 * Assigns the Outcomes corresponding to the Five bet (i.e. 00-0-1-2-3) to
	 * the Wheel. The odds for this bet is 6:1.
	 * 
	 * @param bins
	 *            Array of bin indexes corresponding to the outcome, e.g. from
	 *            the static final values included in this class.
	 */
	public static Outcome getFiveBetOutcome() {
		if (outcomes == null) {
			outcomes = new HashMap<String, RouletteOutcome>();
		}
		if (!outcomes.containsKey("Five")) {
			int[] binArray = { 0, 1, 2, 3, 37 };
			outcomes.put("Five", new RouletteOutcome("Five", 6, binArray));
		}
		return outcomes.get("Five");
	}

	/**
	 * Assigns the Outcomes corresponding to Line Bets (i.e. bets on two
	 * adjacent rows, e.g. 4-5-6-7-8-9) to the Wheel. The odds for these bets
	 * are 5:1.
	 * 
	 * @param bins
	 *            Array of bin indexes corresponding to the outcome, e.g. from
	 *            the static final values included in this class.
	 */
	public static Outcome getLineBetOutcome(int[] bins) {
		Arrays.sort(bins);
		if (bins.length == 6) {
			if (outcomes == null) {
				outcomes = new HashMap<String, RouletteOutcome>();
			}
			if (!outcomes.containsKey(Arrays.toString(bins))) {

				for (int index = 1; index < 34; index += 3) {
					int[] binArray = new int[6];
					for (int j = 0; j < binArray.length; j++) {
						binArray[j] = index + j;
					}

					String binString = Arrays.toString(binArray);
					outcomes.put(binString, new RouletteOutcome("Line", 5,
							binArray));
				}
			}
			return outcomes.get(Arrays.toString(bins));
		}
		return null;
	}

	/**
	 * Assigns the Outcomes corresponding to the Range bets (i.e. bets on the
	 * number ranges 1-12, 13-24 or 25-36, respectively) to the Wheel. The odds
	 * for these bets are 2:1.
	 * 
	 * @param bins
	 *            Array of bin indexes corresponding to the outcome, e.g. from
	 *            the static final values included in this class.
	 */
	public static Outcome getRangeBetOutcome(int[] bins) {
		Arrays.sort(bins);
		if (bins.length == 12) {
			if (outcomes == null) {
				outcomes = new HashMap<String, RouletteOutcome>();
			}
			if (!outcomes.containsKey(Arrays.toString(bins))) {

				String binString = Arrays.toString(RANGEFIRST);
				outcomes.put(binString, new RouletteOutcome("Range First", 2,
						RANGEFIRST));

				binString = Arrays.toString(RANGESECOND);
				outcomes.put(binString, new RouletteOutcome("Range Second", 2,
						RANGESECOND));

				binString = Arrays.toString(RANGETHIRD);
				outcomes.put(binString, new RouletteOutcome("Range Third", 2,
						RANGETHIRD));
			}
			return outcomes.get(Arrays.toString(bins));
		}
		return null;
	}

	/**
	 * Assigns the Outcomes corresponding to Column Bets (i.e. bets on the left,
	 * middle or right column) to the Wheel. The odds for these bets are 2:1.
	 * 
	 * @param bins
	 *            Array of bin indexes corresponding to the outcome, e.g. from
	 *            the static final values included in this class.
	 */
	public static Outcome getColumnBetOutcome(int[] bins) {
		Arrays.sort(bins);
		if (bins.length == 12) {
			if (outcomes == null) {
				outcomes = new HashMap<String, RouletteOutcome>();
			}
			if (!outcomes.containsKey(Arrays.toString(bins))) {

				String binString = Arrays.toString(COL1);
				outcomes.put(binString,
						new RouletteOutcome("Column 1", 2, COL1));

				binString = Arrays.toString(COL2);
				outcomes.put(binString,
						new RouletteOutcome("Column 2", 2, COL2));

				binString = Arrays.toString(COL3);
				outcomes.put(binString,
						new RouletteOutcome("Column 3", 2, COL3));
			}
			return outcomes.get(Arrays.toString(bins));
		}
		return null;
	}

	/**
	 * Assigns the Outcomes corresponding to bets on either the low (1-18) or
	 * high (19-36) half of the playing field to the Wheel. The odds for these
	 * bets are 1:1.
	 * 
	 * @param bins
	 *            Array of bin indexes corresponding to the outcome, e.g. from
	 *            the static final values included in this class.
	 */
	public static Outcome getHighLowBetOutcome(int[] bins) {
		Arrays.sort(bins);
		if (Arrays.equals(bins, LOW) || Arrays.equals(bins, HIGH)) {
			if (outcomes == null) {
				outcomes = new HashMap<String, RouletteOutcome>();
			}
			if (!outcomes.containsKey(Arrays.toString(bins))) {

				String binString = Arrays.toString(LOW);
				outcomes.put(binString, new RouletteOutcome("Low", 1, LOW));

				binString = Arrays.toString(HIGH);
				outcomes.put(binString, new RouletteOutcome("High", 1, HIGH));
			}
			return outcomes.get(Arrays.toString(bins));
		}
		return null;
	}

	/**
	 * Assigns the Outcomes corresponding to Color Bets (i.e. bets on either red
	 * or black numbers) to the Wheel. The odds for these bets are 1:1.
	 * 
	 * @param bins
	 *            Array of bin indexes corresponding to the outcome, e.g. from
	 *            the static final values included in this class.
	 */
	public static Outcome getColorBetOutcome(int[] bins) {
		Arrays.sort(bins);
		if (Arrays.equals(bins, RED) || Arrays.equals(bins, BLACK)) {
			if (outcomes == null) {
				outcomes = new HashMap<String, RouletteOutcome>();
			}
			if (!outcomes.containsKey(Arrays.toString(bins))) {

				String binString = Arrays.toString(RED);
				outcomes.put(binString, new RouletteOutcome("Red", 1, RED));

				binString = Arrays.toString(BLACK);
				outcomes.put(binString, new RouletteOutcome("Black", 1, BLACK));
			}
			return outcomes.get(Arrays.toString(bins));
		}
		return null;
	}

	/**
	 * Assigns the Outcomes corresponding to Even or Odd Bets (i.e. bets on
	 * either the even or the odd numbers from 1 to 36) to the Wheel. The odds
	 * for these bets are 1:1.
	 * 
	 * @param bins
	 *            Array of bin indexes corresponding to the outcome, e.g. from
	 *            the static final values included in this class.
	 */
	public static Outcome getEvenOddBetOutcome(int[] bins) {
		Arrays.sort(bins);
		if (Arrays.equals(bins, EVEN) || Arrays.equals(bins, ODD)) {
			if (outcomes == null) {
				outcomes = new HashMap<String, RouletteOutcome>();
			}
			if (!outcomes.containsKey(Arrays.toString(bins))) {

				String binString = Arrays.toString(EVEN);
				outcomes.put(binString, new RouletteOutcome("Even", 1, EVEN));

				binString = Arrays.toString(ODD);
				outcomes.put(binString, new RouletteOutcome("Odd", 1, ODD));
			}
			return outcomes.get(Arrays.toString(bins));
		}
		return null;
	}
}
