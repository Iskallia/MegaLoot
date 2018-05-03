package zairus.megaloot.util.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class MLPacketLootBrag extends MLPacket
{
	private ItemStack stack;
	private boolean sent = false;
	
	public MLPacketLootBrag()
	{
		;
	}
	
	public MLPacketLootBrag(ItemStack stack)
	{
		this.stack = stack;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		ByteBufUtils.writeItemStack(buffer, this.stack);
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		stack = ByteBufUtils.readItemStack(buffer);
	}
	
	@Override
	public void handleServerSide(EntityPlayer player)
	{
		if (!sent)
		{
			ITextComponent comp = new TextComponentString("[");
			comp.appendSibling(player.getDisplayName());
			comp.appendSibling(new TextComponentString(" Brags about]: "));
			comp.appendSibling(stack.getTextComponent());
			
			player.getServer().getPlayerList().sendMessage(comp, false);
			
			sent = true;
		}
	}
}
