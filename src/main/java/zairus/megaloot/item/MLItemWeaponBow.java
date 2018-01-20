package zairus.megaloot.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.LootItemHelper;
import zairus.megaloot.loot.LootWeaponEffect;
import zairus.megaloot.sound.MLSoundEvents;

public class MLItemWeaponBow extends ItemBow
{
	protected MLItemWeaponBow()
	{
		super();
		this.setCreativeTab(MegaLoot.creativeTabMain);
		
		this.addPropertyOverride(new ResourceLocation("model"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
			{
				float model = 1.0F;
				
				model = (float)LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_MODEL);
				
				return model;
			}
		});
		this.addPropertyOverride(new ResourceLocation("ml_pull"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
			{
				float pull = 0.0F;
				
				if (entity == null)
				{
					return pull;
				}
				else
				{
					ItemStack itemstack = entity.getActiveItemStack();
					pull = itemstack != null && itemstack.getItem() == MLItems.WEAPONBOW ? (float)(stack.getMaxItemUseDuration() - entity.getItemInUseCount()) / 20.0F : 0.0F;
					
					return pull;
				}
			}
		});
		this.addPropertyOverride(new ResourceLocation("ml_pulling"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
			{
				return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;
			}
		});
	}
	
	@Override
	public int getItemEnchantability()
	{
		return Item.ToolMaterial.GOLD.getEnchantability();
	}
	
	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return LootItemHelper.getMaxDamage(stack);
	}
	
	@Nullable
	private ItemStack findAmmo(EntityPlayer player)
	{
		if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
		{
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
		else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
		{
			return player.getHeldItem(EnumHand.MAIN_HAND);
		}
		else
		{
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack itemstack = player.inventory.getStackInSlot(i);
				
				if (this.isArrow(itemstack))
				{
					return itemstack;
				}
			}
			
			return null;
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack itemStack = player.getHeldItem(hand);
		
		boolean flag = this.findAmmo(player) != null;
		
		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemStack, world, player, hand, flag);
		if (ret != null) return ret;
		
		if (!player.capabilities.isCreativeMode && !flag)
		{
			return !flag ? new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStack) : new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
		}
		else
		{
			player.setActiveHand(hand);
			world.playSound(
					(EntityPlayer)null, 
					player.posX, 
					player.posY, 
					player.posZ, 
					MLSoundEvents.DRAW, 
					SoundCategory.NEUTRAL, 
					1.0F, 
					1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
		}
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft)
	{
		if (entityLiving instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)entityLiving;
			boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			ItemStack itemstack = this.findAmmo(entityplayer);
			
			int time = this.getMaxItemUseDuration(stack) - timeLeft;
			time = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, world, (EntityPlayer)entityLiving, time, itemstack != null || flag);
			if (time < 0) return;
			
			if (itemstack != null || flag)
			{
				if (itemstack == null)
				{
					itemstack = new ItemStack(Items.ARROW);
				}
				
				float f = getArrowVelocity(time);
				float draw_factor = LootItemHelper.getLootFloatValue(stack, MLItem.LOOT_TAG_DRAWSPEED);
				if (draw_factor > 0)
					f *= draw_factor;
				
				if ((double)f < 0.1D)
				{
					return;
				}
				
				if (f > 1.0F)
				{
					f = 1.0F;
				}
				
				int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
				int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
				int m = EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack);
				
				List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(stack);
				
				int s = 1;
				
				if (effects.contains(LootWeaponEffect.getById("multishot")))
				{
					s = LootWeaponEffect.getAmplifierFromStack(stack, "multishot");
				}
				
				if (s == 0)
					s = 1;
				
				boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow ? ((ItemArrow)itemstack.getItem()).isInfinite(itemstack, stack, entityplayer) : false);
				
				for (int i = 0; i < s && !world.isRemote; ++i)
				{
					ItemArrow itemarrow = (ItemArrow)((ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.ARROW));
					EntityArrow entityarrow = itemarrow.createArrow(world, itemstack, entityplayer);
					
					if (effects.size() > 0)
					{
						EntityTippedArrow tippedArrow;
						
						if (entityarrow instanceof EntityTippedArrow)
						{
							tippedArrow = (EntityTippedArrow)entityarrow;
						}
						else
						{
							tippedArrow = new EntityTippedArrow(world, entityplayer);
						}
						
						for (LootWeaponEffect effect : effects)
						{
							PotionEffect potionEffect = effect.getPotionEffect(LootWeaponEffect.getDurationFromStack(stack, effect.getId()), LootWeaponEffect.getAmplifierFromStack(stack, effect.getId()));
							if (potionEffect != null)
								tippedArrow.addEffect(potionEffect);
						}
						
						entityarrow = tippedArrow;
						entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
					}
					
					entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);
					
					if (f == 1.0F)
						entityarrow.setIsCritical(true);
					if (j > 0)
						entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
					if (k > 0)
						entityarrow.setKnockbackStrength(k);
					if (m > 0)
						entityarrow.setFire(100);
					
					entityarrow.setDamage(entityarrow.getDamage() * LootItemHelper.getLootFloatValue(stack, MLItem.LOOT_TAG_POWER));
					
					stack.damageItem(1, entityplayer);
					
					if (flag1 || i > 0)
						entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
					
					world.spawnEntity(entityarrow);
				}
				
				world.playSound(
						(EntityPlayer)null, 
						entityplayer.posX, 
						entityplayer.posY, 
						entityplayer.posZ, 
						SoundEvents.ENTITY_ARROW_SHOOT, 
						SoundCategory.NEUTRAL, 
						1.0F, 
						1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
				
				if (!flag1)
				{
					itemstack.shrink(1);
					
					if (itemstack.isEmpty())
					{
						entityplayer.inventory.deleteStack(itemstack);
					}
				}
				
				entityplayer.addStat(StatList.getObjectUseStats(this));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add("");
		tooltip.add(TextFormatting.GRAY + "Draw speed modifier " + TextFormatting.BOLD + "" + ItemStack.DECIMALFORMAT.format(LootItemHelper.getLootFloatValue(stack, MLItem.LOOT_TAG_DRAWSPEED)));
		tooltip.add("Power multiplier " + TextFormatting.BOLD + "" + ItemStack.DECIMALFORMAT.format(LootItemHelper.getLootFloatValue(stack, MLItem.LOOT_TAG_POWER)));
		tooltip.add("");
		
		LootItemHelper.addInformation(stack, tooltip);
	}
}
