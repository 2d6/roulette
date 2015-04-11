package roulette.wheel;

public class NonRandomFromArray extends NonRandom {

	private static final long serialVersionUID = 9108451640769177563L;
	private int[] seedArray;
	private int index;
	
	public void setSeedArray(int[] seedArray) {
		this.seedArray = seedArray;
		index = 0;
	}
	
	@Override
	public int nextInt() {
		int returnVal = (int)this.seedArray[index];
		index = (index == seedArray.length - 1) ? 0 : index + 1;
		return returnVal;
	}
	
	@Override
	public int nextInt(int upperLimit) {
		return this.nextInt();
	}
}
