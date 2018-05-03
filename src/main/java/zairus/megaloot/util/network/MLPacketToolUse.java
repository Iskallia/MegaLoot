package zairus.megaloot.util.network;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import zairus.megaloot.item.MLItems;
import zairus.megaloot.loot.ILootEffectAction;
import zairus.megaloot.loot.LootWeaponEffect;

public class MLPacketToolUse extends MLPacket
{
	private final Set<Item> tools = Sets.newHashSet(MLItems.TOOL_AXE, MLItems.TOOL_PICKAXE, MLItems.TOOL_SHOVEL);
	
	public MLPacketToolUse()
	{
		;
	}
	
	@Override
	public void handleServerSide(EntityPlayer player)
	{
		ItemStack tool = player.getHeldItemMainhand();
		
		if (tools.contains(tool.getItem()))
		{
			List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(tool);
			
			for (LootWeaponEffect effect : effects)
			{
				ILootEffectAction action = effect.getAction();
				
				if (action != null)
				{
					action.toggleAction(player, tool);
					player.sendMessage(action.modificationResponseMessage(player, tool));
				}
			}
		}
	}
}
