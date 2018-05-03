package zairus.megaloot.loot;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import zairus.megaloot.item.MLItem;

@SuppressWarnings("deprecation")
public class LootEffectActionAreaMiner implements ILootEffectAction
{
	@Override
	public void toggleAction(EntityPlayer player, ItemStack stack)
	{
		int level = LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_EFFECT_LEVEL);
		
		level = (level + 1) % 5;
		
		LootItemHelper.setLootIntValue(stack, MLItem.LOOT_TAG_EFFECT_LEVEL, level);
	}
	
	@Override
	public void handleHarvest(EntityPlayer player, ItemStack stack, List<ItemStack> drops)
	{
	}
	
	@Override
	public ITextComponent modificationResponseMessage(EntityPlayer player, ItemStack stack)
	{
		int level = LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_EFFECT_LEVEL);
		
		String message = "[MegaLoot]: ";
		
		message += stack.getDisplayName();
		message += TextFormatting.RESET;
		message += "'s ";
		message += I18n.translateToLocal("weaponeffect.area_miner.name");
		message += ", has been set to: ";
		message += "[" + (level + 1) + "x" + (level + 1) + "]";
		
		return new TextComponentString(message);
	}
	
	@Override
	public void handleUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
	}
	
	@Override
	public String getStatusString(ItemStack stack)
	{
		int level = LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_EFFECT_LEVEL);
		String status = "[" + (level + 1) + "x" + (level + 1) + "]";
		return status;
	}
	
	@Override
	public ActionResult<ItemStack> handleUse(ActionResult<ItemStack> defaultAction, World world, EntityPlayer player, EnumHand hand)
	{
		return defaultAction;
	}
}
