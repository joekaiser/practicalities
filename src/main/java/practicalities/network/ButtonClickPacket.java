package practicalities.network;

import practicalities.gui.IContainerButtons;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.Container;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ButtonClickPacket implements IMessage {

	int button, action;
	
	public ButtonClickPacket() {
	}
	
    public ButtonClickPacket(int button, int action) {
    	this.button = button;
    	this.action = action;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	button = buf.readInt();
    	action = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeInt(button);
    	buf.writeInt(action);
    }

    public static class Handler implements IMessageHandler<ButtonClickPacket, IMessage> {
        
        @Override
        public IMessage onMessage(ButtonClickPacket message, MessageContext ctx) {
        	Container cont = ctx.getServerHandler().playerEntity.openContainer;
        	if(cont instanceof IContainerButtons) {
        		((IContainerButtons)cont).buttonClick(message.button, message.action);
        	}
            return null;
        }
    }
}

