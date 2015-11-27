package practicalities;

import net.minecraft.item.ItemStack;

public interface IItemFilter<T> {
	
	/**
	 * Test whether an ItemStack matches the filter
	 * @param testOn the object that will be tested on
	 * @param stack Stack to test
	 * @return Whether the stack matches the filter
	 */
	public boolean filter(T testOn, ItemStack stack);
	
	/**
	 * Test whether an ItemStack matches a specific filter
	 * @param testOn the object that will be tested on
	 * @param stack Stack to test
	 * @param filterNum Filter number, cannot be larger than {@code filterCount()-1}
	 * @return Whether the stack matches the filter
	 */
	public boolean filter(T testOn, ItemStack stack, int filterNum);
	
	/**
	 * The number of distinct filters
	 * @param testOn the object that will be tested on
	 * @return
	 */
	public int filterCount(T testOn);
}
