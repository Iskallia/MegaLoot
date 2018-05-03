package zairus.megaloot.loot;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public interface ILootEffectAction
{
	public void toggleAction(EntityPlayer player, ItemStack stack);
	
	public void handleHarvest(EntityPlayer player, ItemStack stack, List<ItemStack> drops);
	
	public void handleUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected);
	
	public ActionResult<ItemStack> handleUse(ActionResult<ItemStack> defaultAction, World world, EntityPlayer player, EnumHand hand);
	
	public ITextComponent modificationResponseMessage(EntityPlayer player, ItemStack stack);
	
	public String getStatusString(ItemStack stack);
}
