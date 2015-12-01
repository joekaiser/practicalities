package practicalities.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.Container;
import practicalities.gui.IContainerField;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class TextFieldPacket implements IMessage {
	int field;
	String text;
	
	public TextFieldPacket() {
	}
	
    public TextFieldPacket(int field, String text) {
    	this.field = field;
    	this.text = text;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	field = buf.readInt();
    	text = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeInt(field);
    	ByteBufUtils.writeUTF8String(buf, text);
    }

    public static class Handler implements IMessageHandler<TextFieldPacket, IMessage> {
        
        @Override
        public IMessage onMessage(TextFieldPacket message, MessageContext ctx) {
        	Container cont = ctx.getServerHandler().playerEntity.openContainer;
        	if(cont instanceof IContainerField) {
        		((IContainerField)cont).fieldChange(message.field, message.text);
        	}
            return null;
        }
    }	
}
