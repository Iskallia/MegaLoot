package zairus.megaloot.util;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MLConstants;
import zairus.megaloot.item.MLItemWeaponBow;
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
		Entity sourceEntity = event.getSource().getSourceOfDamage();
		Entity targetEntity = event.getEntityLiving();
		
		if (sourceEntity != null && targetEntity != null && !sourceEntity.worldObj.isRemote)
		{
			if (sourceEntity instanceof EntityPlayer && targetEntity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)sourceEntity;
				
				if (
						player != null 
						&& player.getHeldItemMainhand() != null 
						&& player.getHeldItemMainhand().getItem() != null 
						&& player.getHeldItemMainhand().getItem() == MLItems.WEAPONSWORD)
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
	
	@SubscribeEvent
	public void onLootTableLoad(LootTableLoadEvent event)
	{
		if (
				event.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT)
				|| event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID)
				|| event.getName().equals(LootTableList.CHESTS_END_CITY_TREASURE)
				|| event.getName().equals(LootTableList.CHESTS_IGLOO_CHEST)
				|| event.getName().equals(LootTableList.CHESTS_JUNGLE_TEMPLE)
				|| event.getName().equals(LootTableList.CHESTS_NETHER_BRIDGE)
				|| event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)
				|| event.getName().equals(LootTableList.CHESTS_SPAWN_BONUS_CHEST)
				|| event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR)
				|| event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CROSSING)
				|| event.getName().equals(LootTableList.CHESTS_STRONGHOLD_LIBRARY)
				|| event.getName().equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH))
		{
			final LootPool main = event.getTable().getPool("main");
			
			if (main != null)
			{
				addPoolEntry(main, MLItems.WEAPONCASE_COMMON, 10);
				addPoolEntry(main, MLItems.WEAPONCASE_RARE, 7);
				addPoolEntry(main, MLItems.WEAPONCASE_EPIC, 5);
			}
		}
	}
	
	private void addPoolEntry(LootPool lootPool, Item item, int weight)
	{
		lootPool.addEntry(
			new LootEntryItem(
				item, 
				weight, 
				0, 
				new LootFunction[0], 
				new LootCondition[0], 
				MLConstants.MOD_ID + ":" + item.getUnlocalizedName().substring(5)));
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onFOVUpdate(FOVUpdateEvent event)
	{
		if (!event.getEntity().isHandActive())
			return;
		
		ItemStack stack = event.getEntity().getActiveItemStack();
		
		if (stack != null && stack.getItem() instanceof MLItemWeaponBow)
		{
			MLItemWeaponBow item = (MLItemWeaponBow)stack.getItem();
			
			if (item.updatesFOV())
			{
				float newfov = event.getFov() / (event.getFov() + (item.getFOVValue(stack) * getItemInUsePercentaje(event.getEntity(), item.getFOVSpeedFactor(stack))));
				
				event.setNewfov(newfov);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	private float getItemInUsePercentaje(EntityPlayer player, float speedFactor)
	{
		if (player.getActiveItemStack().getItem() instanceof MLItemWeaponBow)
		{
			MLItemWeaponBow bow = (MLItemWeaponBow)player.getActiveItemStack().getItem();
			
			float maxUse = bow.getFOVDuration(player.getActiveItemStack());
			float curUse = (float)player.getItemInUseCount();
			float percent = 0.0f;
			
			percent = maxUse - (maxUse - (bow.getMaxItemUseDuration(player.getActiveItemStack()) - curUse));
			percent *= (speedFactor * ((percent > 100)? 1.0f : percent / 100.0f));
			percent = percent / maxUse;
			percent *= 1 +  percent;
			
			return (percent > 1.0f)? 1.0f : percent;
		}
		else
		{
			return 1.0f;
		}
	}
}
