package zairus.megaloot.util.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import zairus.megaloot.sound.MLSoundEvents;

public class MLPacketSleep extends MLPacket
{
	public MLPacketSleep()
	{
		;
	}
	
	@Override
	public void handleClientSide(EntityPlayer player)
	{
		player.sleeping = true;
		player.sleepTimer = 0;
		player.bedLocation = player.getPosition();
		player.motionX = 0.0D;
		player.motionY = 0.0D;
		player.motionZ = 0.0D;
		
		player.world.playSound(
				player
				, player.getPosition()
				, MLSoundEvents.SNORE
				, SoundCategory.MASTER
				, 1.0F
				, 1.2F / (player.world.rand.nextFloat() * 0.2f + 0.9f));
	}
	
	@Override
	public void handleServerSide(EntityPlayer player)
	{
		player.sleeping = true;
		player.sleepTimer = 0;
		player.bedLocation = player.getPosition();
		player.motionX = 0.0D;
		player.motionY = 0.0D;
		player.motionZ = 0.0D;
		
		player.world.updateAllPlayersSleepingFlag();
		
		player.world.playSound(
				(EntityPlayer)null
				, player.getPosition()
				, MLSoundEvents.SNORE
				, SoundCategory.MASTER
				, 1.0F
				, 1.2F / (player.world.rand.nextFloat() * 0.2f + 0.9f));
	}
}
