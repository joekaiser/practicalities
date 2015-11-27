package practicalities.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cofh.lib.util.helpers.ItemHelper;

public class SlotRegion {

	String name;
	List<SlotRegion> subRegions = new ArrayList<SlotRegion>();
	List<SlotRegion> shiftTargets = new ArrayList<SlotRegion>();
	int[] slots = new int[0];
	
	/**
	 * Create new region with specific slosts
	 * @param name
	 * @param slots
	 */
	public SlotRegion(String name, int[] slots) {
		this.name = name;
		this.slots = slots;
	}
	
	/**
	 * Create new region with range. if begin > end sorting is backwards
	 * @param name
	 * @param begin
	 * @param end
	 */
	public SlotRegion(String name, int begin, int end) {
		this.name = name;
		slots = new int[Math.abs(end-begin)+1];
		if(begin > end) {
			int index = 0;
			for(int i = begin; i >= end; i--) {
				slots[index++] = i;
			}
		} else {
			int index = 0;
			for(int i = begin; i <= end; i++) {
				slots[index++] = i;
			}
		}
		
	}
	
	/**
	 * Create new region with sub-regions
	 * @param name
	 * @param regions
	 */
	public SlotRegion(String name, SlotRegion... regions) {
		this.name = name;
		for (int i = 0; i < regions.length; i++) {
			subRegions.add(regions[i]);
		}
	}
	
	/**
	 * Get list of targets for shift-clicking
	 * @return
	 */
	public List<SlotRegion> getShiftTargets() {
		return shiftTargets;
	}
	
	/**
	 * Add one or more shift-click target regions
	 * @param r
	 * @return this
	 */
	public SlotRegion addShiftTargets(SlotRegion... r) {
		for (int i = 0; i < r.length; i++) {
			shiftTargets.add(r[i]);
		}
		return this;
	}
	
	/**
	 * Get list of all slots contained in this region
	 * @return
	 */
	public int[] getSlotList() {
		int[] list = new int[getSize()];
		int index = 0;
		for (int i = 0; i < slots.length; i++) {
			list[index++] = slots[i];
		}
		for (SlotRegion region : subRegions) {
			int[] regionList = region.getSlotList();
			for (int i = 0; i < regionList.length; i++) {
				list[index++] = regionList[i];
			}
		}
		return list;
	}
	
	/**
	 * Get the number of slots that are contained in this region
	 * @return
	 */
	public int getSize() {
		int size = slots.length;
		for (SlotRegion region : subRegions) {
			size += region.getSize();
		}
		return size;
	}
	
	/**
	 * Test if this region contains the given slot
	 * @param slot
	 * @return
	 */
	public boolean contains(int slot) {
		for (int i = 0; i < slots.length; i++) {
			if(slots[i] == slot) {
				return true;
			}
		}
		for (SlotRegion region : subRegions) {
			if(region.contains(slot))
				return true;
		}
		return false;
	}
	
	/**
	 * Find a slot that can accept the given stack
	 * @param stack
	 * @param container
	 * @return
	 */
	public int findRestingPlaceFor(ItemStack stack, Container container) {
		int[] slots = getSlotList();
		for (int i = 0; i < slots.length; i++) {
			int slotIndex = slots[i];
			Slot slot = container.getSlot(slotIndex);
			
			if(!slot.isItemValid(stack))
				continue;
			
			if(!slot.getHasStack())
				return slotIndex;
			
			if(ItemHelper.itemsIdentical(slot.getStack(), stack) && slot.getStack().stackSize != slot.getStack().getMaxStackSize()) {
				return slotIndex;
			}
		}
		return -1;
	}
	
	/**
	 * Try to find a spot for the given itemstack, and put as much as possible from the stack into the slot
	 * @param container
	 * @param stack
	 * @return
	 */
	public boolean shiftClickInto(Container container, ItemStack stack) {
		int index = findRestingPlaceFor(stack, container);
		if(index != -1) {
			SlotRegion.transferStack(stack, container.getSlot(index));
			return true;
		}
		return false;
	}
	
	/**
	 * Shift-click from the container at the index provided. Regions are the regions that can be shift-clicked from.
	 * Each region provided should contain at least one shift-click target region.
	 * 
	 * When shift-clicking it will search in order through the regions, looking for the region that was clicked in.
	 * Once a region is found it will loop through all of the shift-click target regions until part of the stack is successfully moved.
	 * 
	 * @param container Container that was clicked in
	 * @param slotIndex Index of the slot clicked
	 * @param player Player that clicked slot
	 * @param regions Clickable regions of the screen
	 * @return Whatever transferStackInSlot should normally return
	 */
	public static ItemStack shiftClick(Container container, int slotIndex, EntityPlayer player, SlotRegion... regions) {
		ItemStack stack = null;
		Slot slot = (Slot) container.inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();
			boolean moved = false;
			
			for (SlotRegion region : regions) {
				
				if( region.contains(slotIndex) ) {
					for (SlotRegion target : region.getShiftTargets()) {
						if( target.shiftClickInto(container, stackInSlot) ) {
							moved = true;
							for(int i = 0; i < 64; i++) {
								if(stackInSlot.stackSize <= 0) {
									break;
								}
								target.shiftClickInto(container, stackInSlot);
							}
							if(moved)
								break;
						}
					}
					if(moved)
						break;
				}
				
			}

			if (stackInSlot.stackSize <= 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.putStack(stackInSlot);
			}

			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, stackInSlot);
		}
		
		return null;
	}
	
	/**
	 * Tries to merge the given stack into the given slot, modifying the stack's stackSize appropriately
	 * @param stack
	 * @param targetSlot
	 */
	public static void transferStack(ItemStack stack, Slot targetSlot) {
		int amountCanMove = stack.stackSize;
		if(targetSlot.getStack() != null) {
			amountCanMove = Math.min(amountCanMove, targetSlot.getStack().getMaxStackSize() - targetSlot.getStack().stackSize);
			targetSlot.getStack().stackSize += amountCanMove;
		} else {
			ItemStack stackToMove = stack.copy();
			stackToMove.stackSize = amountCanMove;
			targetSlot.putStack(stackToMove);
		}
		stack.stackSize -= amountCanMove;
	}
}
