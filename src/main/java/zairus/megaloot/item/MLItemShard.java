package zairus.megaloot.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.loot.LootRarity;

public class MLItemShard extends MLItem
{
	private final LootRarity rarity;
	private boolean renderEffect = true;
	
	protected MLItemShard(LootRarity rarity)
	{
		super();
		
		this.rarity = rarity;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String displayName = super.getItemStackDisplayName(stack);
		
		if (rarity != null)
		{
			displayName = rarity.getColor() + displayName;
		}
		
		return displayName;
	}
	
	public MLItemShard setRenderEffect(boolean renderEffect)
	{
		this.renderEffect = renderEffect;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return this.renderEffect;
	}
	
	public LootRarity getShardRariry()
	{
		return this.rarity;
	}
}
