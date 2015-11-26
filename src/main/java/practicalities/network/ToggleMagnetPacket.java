package practicalities.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import practicalities.items.ItemMagnet;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ToggleMagnetPacket implements IMessage {

    public ToggleMagnetPacket() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<ToggleMagnetPacket, IMessage> {
        
        @Override
        public IMessage onMessage(ToggleMagnetPacket message, MessageContext ctx) {
        	EntityPlayer p = ctx.getServerHandler().playerEntity;
        	ItemStack[] inv = p.inventory.mainInventory;
        	for (int i = 0; i < inv.length; i++) {
        		if(inv[i] != null && inv[i].getItem() instanceof ItemMagnet)
        			inv[i].setItemDamage(inv[i].getItemDamage() == 0 ? 1 : 0);
			}
            return null; // no response in this case
        }
    }
}

