package zairus.megaloot.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MLConstants;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.client.settings.MLKeyBindings;
import zairus.megaloot.item.IMegaLoot;
import zairus.megaloot.item.MLItemWeaponBow;
import zairus.megaloot.item.MLItems;
import zairus.megaloot.loot.ILootEffectAction;
import zairus.megaloot.loot.LootItemHelper;
import zairus.megaloot.loot.LootWeaponEffect;
import zairus.megaloot.util.network.MLPacketJetpack;
import zairus.megaloot.util.network.MLPacketLootBrag;
import zairus.megaloot.util.network.MLPacketToolUse;

@Mod.EventBusSubscriber(modid = MLConstants.MOD_ID)
public class MLEventHandler
{
	@SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent event)
	{
		ItemStack toRepair = event.getLeft();
		ItemStack ingredient = event.getRight();
		
		if (toRepair.isEmpty() || ingredient.isEmpty())
			return;
		
		List<Item> modItems = new ArrayList<Item>();
		
		modItems.add(MLItems.ARMOR_HELMET);
		modItems.add(MLItems.ARMOR_CHESTPLATE);
		modItems.add(MLItems.ARMOR_LEGGINGS);
		modItems.add(MLItems.ARMOR_BOOTS);
		modItems.add(MLItems.WEAPONSWORD);
		modItems.add(MLItems.WEAPONBOW);
		modItems.add(MLItems.TOOL_AXE);
		modItems.add(MLItems.TOOL_PICKAXE);
		modItems.add(MLItems.TOOL_SHOVEL);
		
		if (modItems.contains(toRepair.getItem()) && toRepair.getItem() == ingredient.getItem() && event.isCancelable())
		{
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onAnvilRepair(AnvilRepairEvent event)
	{
		;
	}
	
	@SubscribeEvent
	public void onEntityAttacked(LivingAttackEvent event)
	{
		Entity sourceEntity = event.getSource().getTrueSource();
		
		if (sourceEntity != null && !sourceEntity.world.isRemote)
		{
			if (sourceEntity instanceof EntityPlayer)
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
	
	@SubscribeEvent
	public void onHarvest(BlockEvent.HarvestDropsEvent event)
	{
		EntityPlayer player = event.getHarvester();
		
		if (player != null && !player.world.isRemote)
		{
			List<Item> tools = new ArrayList<Item>();
			tools.add(MLItems.TOOL_AXE);
			tools.add(MLItems.TOOL_PICKAXE);
			tools.add(MLItems.TOOL_SHOVEL);
			
			ItemStack tool = player.getHeldItemMainhand();
			
			if (tools.contains(tool.getItem()))
			{
				List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(tool);
				
				for (LootWeaponEffect effect : effects)
				{
					ILootEffectAction action = effect.getAction();
					
					if (action != null)
					{
						action.handleHarvest(player, tool, event.getDrops());
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLeftClickBlock(LeftClickBlock event)
	{
		;
	}
	
	@SubscribeEvent
	public void onLootTableLoad(LootTableLoadEvent event)
	{
		final LootPool main = event.getTable().getPool("main");
		
		if (main == null)
			return;
		
		if (
				event.getName().equals(LootTableList.CHESTS_NETHER_BRIDGE)
				|| event.getName().equals(LootTableList.CHESTS_STRONGHOLD_LIBRARY)
				|| event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CROSSING)
				|| event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR)
				|| event.getName().equals(LootTableList.CHESTS_JUNGLE_TEMPLE)
				|| event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID))
		{
			addPoolEntry(main, MLItems.WEAPONCASE_COMMON, 10);
			addPoolEntry(main, MLItems.WEAPONCASE_RARE, 7);
			addPoolEntry(main, MLItems.WEAPONCASE_EPIC, 3);
		}
		else if (
				event.getName().equals(LootTableList.CHESTS_IGLOO_CHEST)
				|| event.getName().equals(LootTableList.CHESTS_END_CITY_TREASURE))
		{
			addPoolEntry(main, MLItems.WEAPONCASE_COMMON, 13);
			addPoolEntry(main, MLItems.WEAPONCASE_RARE, 8);
			addPoolEntry(main, MLItems.WEAPONCASE_EPIC, 5);
		}
		else if (
				event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON))
		{
			addPoolEntry(main, MLItems.WEAPONCASE_COMMON, 6);
			addPoolEntry(main, MLItems.WEAPONCASE_RARE, 2);
		}
		else if (
				event.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT))
		{
			addPoolEntry(main, MLItems.WEAPONCASE_COMMON, 5);
			addPoolEntry(main, MLItems.WEAPONCASE_RARE, 1);
		}
		else if (
				event.getName().equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH))
		{
			addPoolEntry(main, MLItems.WEAPONCASE_COMMON, 4);
			addPoolEntry(main, MLItems.WEAPONCASE_RARE, 1);
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
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event)
	{
		boolean f = Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown();
		boolean b = Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown();
		boolean l = Minecraft.getMinecraft().gameSettings.keyBindLeft.isKeyDown();
		boolean r = Minecraft.getMinecraft().gameSettings.keyBindRight.isKeyDown();
		boolean j = Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();
		boolean s = Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown();
		
		if (f || b || l || r || j || s)
		{
			MegaLoot.packetPipeline.sendToServer(new MLPacketJetpack(f, b, l, r, j, s));
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyHandle(InputEvent.KeyInputEvent event)
	{
		if (MLKeyBindings.activateEffect.isPressed())
		{
			MegaLoot.packetPipeline.sendToServer(new MLPacketToolUse());
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void keyboardEvent(KeyboardInputEvent.Post event)
	{
		if (
				GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindChat) 
				&& event.getGui() instanceof GuiContainer 
				&& GuiScreen.isShiftKeyDown())
		{
			GuiContainer gui = (GuiContainer) event.getGui();
			Slot slot = gui.getSlotUnderMouse();
			if(slot != null && slot.inventory != null && !"tmp".equals(slot.inventory.getName()))
			{
				ItemStack stack = slot.getStack();
				if(!stack.isEmpty() && stack.getItem() instanceof IMegaLoot)
				{
					MegaLoot.packetPipeline.sendToServer(new MLPacketLootBrag(stack));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void sleepingLocationCheck(SleepingLocationCheckEvent event)
	{
		if (event.getEntityPlayer().isPlayerSleeping() && !event.getEntityPlayer().world.isDaytime())
		{
			event.setResult(Result.ALLOW);
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		ItemStack chestplate = event.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		
		if (chestplate != null && !chestplate.isEmpty() && LootItemHelper.hasEffect(chestplate, LootWeaponEffect.JETPACK))
		{
			if(!event.player.onGround)
			{
				int hover = LootWeaponEffect.getAmplifierFromStack(chestplate, LootWeaponEffect.JETPACK.getId());
				
				double factor = 1.0D - ((double)hover / 100.0D); // 0.93D;
				
				if (event.player.isSneaking())
					factor = 0.97D;
				
				if (event.player.motionY < 0.0D)
				{
					event.player.motionY *= factor;
					event.player.fallDistance *= factor;
				}
			}
		}
	}
}
