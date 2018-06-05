package zairus.megaloot.util.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import zairus.megaloot.loot.LootItemHelper;
import zairus.megaloot.loot.LootWeaponEffect;

public class MLPacketJetpack extends MLPacket
{
	private boolean forward = false;
	private boolean back = false;
	private boolean left = false;
	private boolean right = false;
	private boolean jump = false;
	private boolean sneak = false;
	
	public MLPacketJetpack()
	{
		;
	}
	
	public MLPacketJetpack(boolean f, boolean b, boolean l, boolean r, boolean j, boolean s)
	{
		this.forward = f;
		this.back = b;
		this.left = l;
		this.right = r;
		this.jump = j;
		this.sneak = s;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeBoolean(this.forward);
		buffer.writeBoolean(this.back);
		buffer.writeBoolean(this.left);
		buffer.writeBoolean(this.right);
		buffer.writeBoolean(this.jump);
		buffer.writeBoolean(this.sneak);
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.forward = buffer.readBoolean();
		this.back = buffer.readBoolean();
		this.left = buffer.readBoolean();
		this.right = buffer.readBoolean();
		this.jump = buffer.readBoolean();
		this.sneak = buffer.readBoolean();
		
		buffer.release();
	}
	
	@Override
	public void handleServerSide(EntityPlayer player)
	{
		ItemStack chestplate = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		
		if (chestplate == null || chestplate.isEmpty() || !LootItemHelper.hasEffect(chestplate, LootWeaponEffect.JETPACK))
			return;
		
		float s = (float)LootWeaponEffect.getDurationFromStack(chestplate, LootWeaponEffect.JETPACK.getId()) / 100.0F;
		
		double speed = (double)s / 10.0D; // 0.5D;
		
		if (this.jump)
		{
			player.motionY = speed;
			((EntityPlayerMP)player).connection.sendPacket(new SPacketEntityVelocity(player));
		}
		
		if (!player.onGround)
		{
			float yaw = player.rotationYaw;
			float pitch = 0.0F;
			
			float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
			float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
			float f2 = -MathHelper.cos(-pitch * 0.017453292F);
			float f3 = MathHelper.sin(-pitch * 0.017453292F);
			Vec3d vForward = new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
			
			Vec3d vLeft = new Vec3d(vForward.z, vForward.y, -vForward.x);
			
			double mX = 0.0D;
			double mZ = 0.0D;
			
			if (this.forward)
			{
				mX += vForward.x * speed;
				mZ += vForward.z * speed;
			}
			
			if (this.back)
			{
				mX += vForward.x * -speed;
				mZ += vForward.z * -speed;
			}
			
			if (this.left)
			{
				mX += vLeft.x * speed;
				mZ += vLeft.z * speed;
			}
			
			if (this.right)
			{
				mX += vLeft.x * -speed;
				mZ += vLeft.z * -speed;
			}
			
			player.motionX = mX;
			player.motionZ = mZ;
			
			((EntityPlayerMP)player).connection.sendPacket(new SPacketEntityVelocity(player));
		}
	}
}
