package practicalities.items;

import cofh.core.item.ItemBase;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemPracticalitiesBase extends ItemBase {

	public ItemPracticalitiesBase(){
		super("practicalities");
		
	}
	
	protected void init(String name){
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		
	}
	
}
