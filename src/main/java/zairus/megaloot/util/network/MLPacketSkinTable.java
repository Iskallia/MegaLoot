package zairus.megaloot.util.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import zairus.megaloot.tileentity.MLTileEntitySkinTable;

public class MLPacketSkinTable extends MLPacket
{
	private double x;
	private double y;
	private double z;
	
	private int action;
	
	public MLPacketSkinTable()
	{
		;
	}
	
	public MLPacketSkinTable(double x, double y, double z, int action)
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
		BlockPos pos = new BlockPos(this.x, this.y, this.z);
		TileEntity tileEntity = player.world.getTileEntity(pos);
		
		if (tileEntity instanceof MLTileEntitySkinTable)
		{
			MLTileEntitySkinTable skinTable = (MLTileEntitySkinTable)tileEntity;
			
			switch(this.action)
			{
			case 0:
				skinTable.populateSkins(player, true, 0);
				break;
			case 1:
				skinTable.clearInput();
			case 2:
				// Previous model list
				skinTable.populateSkins(player, false, -1);
				break;
			case 3:
				// Next model list
				skinTable.populateSkins(player, false, 1);
				break;
			}
		}
	}
	
	@Override
	public void handleServerSide(EntityPlayer player)
	{
		BlockPos pos = new BlockPos(this.x, this.y, this.z);
		TileEntity tileEntity = player.world.getTileEntity(pos);
		
		if (tileEntity instanceof MLTileEntitySkinTable)
		{
			MLTileEntitySkinTable skinTable = (MLTileEntitySkinTable)tileEntity;
			
			switch(this.action)
			{
			case 0:
				skinTable.populateSkins(player, true, 0);
				break;
			case 1:
				skinTable.clearInput();
			case 2:
				// Previous model list
				skinTable.populateSkins(player, false, -1);
				break;
			case 3:
				// Next model list
				skinTable.populateSkins(player, false, 1);
				break;
			}
		}
	}
}
