package zairus.megaloot.loot;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import zairus.megaloot.item.MLItem;

@SuppressWarnings("deprecation")
public class LootEffectActionVoid implements ILootEffectAction
{
	@Override
	public void toggleAction(EntityPlayer player, ItemStack stack)
	{
		boolean active = false;
		
		if (stack == null)
			return;
		
		active = !LootItemHelper.getLootBooleanValue(stack, MLItem.LOOT_TAG_EFFECT_ACTIVE);
		LootItemHelper.setLootBooleanValue(stack, MLItem.LOOT_TAG_EFFECT_ACTIVE, active);
	}
	
	@Override
	public void handleHarvest(EntityPlayer player, ItemStack stack, List<ItemStack> drops)
	{
		boolean effectActive = LootItemHelper.getLootBooleanValue(stack, MLItem.LOOT_TAG_EFFECT_ACTIVE);
		
		if (!effectActive)
			return;
		
		drops.clear();
	}
	
	@Override
	public ITextComponent modificationResponseMessage(EntityPlayer player, ItemStack stack)
	{
		boolean active = LootItemHelper.getLootBooleanValue(stack, MLItem.LOOT_TAG_EFFECT_ACTIVE);
		
		String message = "[MegaLoot]: ";
		
		message += stack.getDisplayName();
		message += TextFormatting.RESET;
		message += "'s ";
		message += I18n.translateToLocal("weaponeffect.void.name");
		message += ", has been set to: ";
		message += active;
		
		return new TextComponentString(message);
	}
	
	@Override
	public void handleUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
	}
	
	@Override
	public String getStatusString(ItemStack stack)
	{
		String status = "[Off]";
		
		if (LootItemHelper.getLootBooleanValue(stack, MLItem.LOOT_TAG_EFFECT_ACTIVE))
			status = "[On]";
		
		return status;
	}
}
