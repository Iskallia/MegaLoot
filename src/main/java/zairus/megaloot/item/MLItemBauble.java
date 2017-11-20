package zairus.megaloot.item;

import java.util.List;

import javax.annotation.Nullable;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.loot.LootItemHelper;

@Optional.Interface( modid = "Baubles", iface = "baubles.api.IBauble" )
public class MLItemBauble extends MLItem implements IBauble
{
	protected MLItemBauble()
	{
		super();
		
		this.addPropertyOverride(new ResourceLocation("model"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
			{
				float model = 1.0F;
				
				model = (float)LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_MODEL);
				
				return model;
			}
		});
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if (entity instanceof EntityPlayer && itemSlot == 0)
		{
			EntityPlayer player = (EntityPlayer)entity;
			
			applyEffects(stack, player);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		tooltip.add("");
		
		LootItemHelper.addInformation(stack, tooltip, false);
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack stack)
	{
		return BaubleType.RING;
	}
	
	@Override
	public boolean canEquip(ItemStack stack, EntityLivingBase entity)
	{
		return true;
	}
	
	@Override
	public boolean canUnequip(ItemStack stack, EntityLivingBase entity)
	{
		return true;
	}
	
	@Override
	public void onEquipped(ItemStack stack, EntityLivingBase entity)
	{
		;
	}
	
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase entity)
	{
		;
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase entity)
	{
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			applyEffects(stack, player);
		}
	}
}
