package zairus.megaloot.util.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import zairus.megaloot.tileentity.MLTileEntityDisenchanter;

public class MLPacketToolRepair extends AbstractPacket
{
	private double x;
	private double y;
	private double z;
	
	private int action;
	
	public MLPacketToolRepair()
	{
		;
	}
	
	public MLPacketToolRepair(double x, double y, double z, int action)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.action = action;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
		buffer.writeInt(action);
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		x = buffer.readDouble();
		y = buffer.readDouble();
		z = buffer.readDouble();
		this.action = buffer.readInt();
	}
	
	@Override
	public void handleClientSide(EntityPlayer player)
	{
	}
	
	@Override
	public void handleServerSide(EntityPlayer player)
	{
		BlockPos pos = new BlockPos(this.x, this.y, this.z);
		
		TileEntity tileEntity = player.world.getTileEntity(pos);
		
		if (tileEntity instanceof MLTileEntityDisenchanter)
		{
			MLTileEntityDisenchanter disenchanter = (MLTileEntityDisenchanter)tileEntity;
			
			switch(this.action)
			{
			case 0:
				disenchanter.applyRepair();
				break;
			case 1:
				disenchanter.setDisenchanterPlayer(player);
				disenchanter.disenchantStart();
				break;
			}
		}
	}
}
