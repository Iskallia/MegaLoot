package zairus.megaloot.item;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.LootItemHelper;
import zairus.megaloot.loot.LootWeaponEffect;

public class MLItemToolAxe extends ItemAxe implements IMegaLoot
{
	protected MLItemToolAxe()
	{
		super(ToolMaterial.DIAMOND);
		
		this.setCreativeTab(MegaLoot.creativeTabMain);
		this.setNoRepair();
		
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
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return false;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return MLItem.getMegaLootDisplayName(stack, super.getItemStackDisplayName(stack));
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
	{
		boolean onbreak = false;
		
		if (LootItemHelper.hasEffect(itemstack, LootWeaponEffect.AREA_MINER) && LootItemHelper.getLootIntValue(itemstack, MLItem.LOOT_TAG_EFFECT_LEVEL) > 1)
		{
			RayTraceResult raytrace = MLItem.getBlockOnReach(player.world, player);
			
			if (raytrace != null)
			{
				int level = LootItemHelper.getLootIntValue(itemstack, MLItem.LOOT_TAG_EFFECT_LEVEL);
				onbreak = MLItem.breakBlocks(itemstack, level, player.world, pos, raytrace.sideHit, player);
			}
		}
		
		return onbreak;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if (entity instanceof EntityPlayer && isSelected)
		{
			EntityPlayer player = (EntityPlayer)entity;
			
			MLItem.applyEffects(stack, player, itemSlot, isSelected);
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ActionResult<ItemStack> defaultAction = super.onItemRightClick(world, player, hand);
		
		defaultAction = MLItem.use(defaultAction, world, player, hand);
		/*
		ItemStack stack = player.getHeldItemMainhand();
		
		if (stack != null && !stack.isEmpty() && LootItemHelper.hasEffect(stack, LootWeaponEffect.SELECTIVE) && player.isSneaking() && !world.isRemote)
		{
			player.openGui(MegaLoot.instance, MLGuiHandler.GUI_VOID_FILTER, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
		*/
		return defaultAction;
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> multimap = MLItem.modifiersForStack(slot, stack, super.getAttributeModifiers(slot, stack), "Tool modifier");
		
		return multimap;
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state)
	{
		return MLItem.getEfficiency(stack, state);
	}
	
	@Override
	public Set<String> getToolClasses(ItemStack stack)
	{
		List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(stack);
		
		for (LootWeaponEffect e : effects)
		{
			if (e == LootWeaponEffect.MULTI)
				return Sets.<String>newHashSet("axe", "pickaxe", "shovel");
		}
		
		return super.getToolClasses(stack);
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState)
	{
		List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(stack);
		
		for (LootWeaponEffect e : effects)
		{
			if (e == LootWeaponEffect.MULTI)
				return 3;
		}
		
		return super.getHarvestLevel(stack, toolClass, player, blockState);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (GuiScreen.isShiftKeyDown())
		{
			LootItemHelper.addInformation(stack, tooltip);
		}
		else
		{
			tooltip.add(TextFormatting.RESET + "" + "Axe");
			
			float efficiency = LootItemHelper.getLootFloatValue(stack, MLItem.LOOT_TAG_EFFICIENCY);
			tooltip.add(TextFormatting.GRAY + "" + ItemStack.DECIMALFORMAT.format(efficiency) + " Mining Speed");
			
			tooltip.add(TextFormatting.AQUA + "" + TextFormatting.ITALIC + "Shift" + TextFormatting.DARK_GRAY + " for more...");
		}
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		boolean hit = super.hitEntity(stack, target, attacker);
		
		MLItem.handleEffectsAfterHit(stack, target, attacker);
		
		return hit;
	}
	
	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return LootItemHelper.getMaxDamage(stack);
	}
}
