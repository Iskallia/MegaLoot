package zairus.megaloot.util.network;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import zairus.megaloot.item.MLItems;
import zairus.megaloot.loot.ILootEffectAction;
import zairus.megaloot.loot.LootWeaponEffect;

public class MLPacketToolUse extends AbstractPacket
{
	private final Set<Item> tools = Sets.newHashSet(MLItems.TOOL_AXE, MLItems.TOOL_PICKAXE, MLItems.TOOL_SHOVEL);
	
	public MLPacketToolUse()
	{
		;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		;
	}
	
	@Override
	public void handleClientSide(EntityPlayer player)
	{
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
		
		/*
		ItemStack stack = player.getHeldItemMainhand();
		
		boolean active = toggleStackTagValue(stack);
		
		if (tools.contains(stack.getItem()))
		{
			player.sendMessage(new TextComponentString("[MegaLoot]: " + stack.getDisplayName() + TextFormatting.RESET + "'s effects set to " + active));
		}
		*/
	}
	/*
	private boolean toggleStackTagValue(ItemStack stack)
	{
		boolean active = false;
		
		if (stack == null)
			return active;
		
		if (tools.contains(stack.getItem()))
		{
			active = !LootItemHelper.getLootBooleanValue(stack, MLItem.LOOT_TAG_EFFECT_ACTIVE);
			
			LootItemHelper.setLootBooleanValue(stack, MLItem.LOOT_TAG_EFFECT_ACTIVE, active);
		}
		
		return active;
	}
	*/
}
