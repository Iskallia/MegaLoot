package zairus.megaloot.util;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zairus.megaloot.item.MLItems;
import zairus.megaloot.loot.LootWeaponEffect;

@Mod.EventBusSubscriber
public class MLEventHandler
{
	@SubscribeEvent
	public void onAnvilRepair(AnvilRepairEvent event)
	{
		ItemStack result = event.getItemResult();
		
		if (result.getItem() == MLItems.WEAPONSWORD)
		{
			if (result.hasDisplayName())
			{
				char colorChar = '\u00A7';
				String name = result.getDisplayName();
				char fChar = name.charAt(0);
				
				if (fChar != colorChar && (fChar == 'a' || fChar == 'b' || fChar == 'c' || fChar == 'd' || fChar == 'e' || fChar == 'f' || fChar == '0' || fChar == '1' || fChar == '2' || fChar == '3' || fChar == '4' || fChar == '5' || fChar == '6' || fChar == '7' || fChar == '8' || fChar == '9'))
				{
					result.setStackDisplayName(colorChar + name);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityAttacked(LivingAttackEvent event)
	{
		Entity sourceEntity = event.getSource().getTrueSource();
		Entity targetEntity = event.getEntityLiving();
		
		if (sourceEntity != null && targetEntity != null && !sourceEntity.world.isRemote)
		{
			if (sourceEntity instanceof EntityPlayer && targetEntity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)sourceEntity;
				
				if (player.getHeldItemMainhand().getItem() == MLItems.WEAPONSWORD)
				{
					ItemStack stack = player.getHeldItemMainhand();
					
					List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(stack);
					
					if (effects.contains(LootWeaponEffect.getById("leechlife")))
					{
						float damageInflicted = Math.min(event.getAmount(), event.getEntityLiving().getHealth());
						int amplifier = LootWeaponEffect.getAmplifierFromStack(stack, "leechlife");
						float leech = damageInflicted * ((float)amplifier / 100.0F);
						player.heal(leech);
					}
				}
			}
		}
	}
}
