package zairus.megaloot.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.ILootEffectAction;
import zairus.megaloot.loot.LootItemHelper;
import zairus.megaloot.loot.LootRarity;
import zairus.megaloot.loot.LootWeaponEffect;

public class MLItem extends Item
{
	private static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
	
	protected MLItem()
	{
		this.setCreativeTab(MegaLoot.creativeTabMain);
	}
	
	public static final String WEAPONCASE_COMMON_ID = "weaponcase_common";
	public static final String WEAPONCASE_RARE_ID = "weaponcase_rare";
	public static final String WEAPONCASE_EPIC_ID = "weaponcase_epic";
	public static final String WEAPONSWORD_ID = "weaponsword";
	public static final String WEAPONBOW_ID = "weaponbow";
	public static final String BAUBLERING_ID = "baublering";
	public static final String ARMOR_BOOTS_ID = "armor_boots";
	public static final String ARMOR_LEGGINGS_D = "armor_leggings";
	public static final String ARMOR_CHESTPLATE_ID = "armor_chestplate";
	public static final String ARMOR_HELMET_ID = "armor_helmet";
	public static final String TOOL_AXE_ID = "tool_axe";
	public static final String TOOL_PICKAXE_ID = "tool_pickaxe";
	public static final String TOOL_SHOVEL_ID = "tool_shovel";
	
	public static final String SHARD_COMMON_ID = "shard_common";
	public static final String SHARD_RARE_ID = "shard_rare";
	public static final String SHARD_EPIC_ID = "shard_epic";
	public static final String UPGRADECHARM_COMMON_ID = "upgradecharm_common";
	public static final String UPGRADECHARM_RARE_ID = "upgradecharm_rare";
	public static final String UPGRADECHARM_EPIC_ID = "upgradecharm_epic";
	public static final String RIFT_STONE_ID = "rift_stone";
	
	public static final String INFUSED_EMERALD_COMMON_ID = "infused_emerald_common";
	public static final String INFUSED_EMERALD_RARE_ID = "infused_emerald_rare";
	
	public static final String LOOT_TAG = "MegaLoot";
	public static final String LOOT_TAG_NAME = "loot_name";
	public static final String LOOT_TAG_LOOTSET = "loot_set";
	public static final String LOOT_TAG_RARITY = "rarity";
	public static final String LOOT_TAG_MODEL = "model";
	public static final String LOOT_TAG_DAMAGE = "damage";
	public static final String LOOT_TAG_SPEED = "speed";
	public static final String LOOT_TAG_ARMOR = "armor";
	public static final String LOOT_TAG_TOUGHNESS = "toughness";
	public static final String LOOT_TAG_EFFICIENCY = "efficiency";
	public static final String LOOT_TAG_DURABILITY = "durability";
	public static final String LOOT_TAG_KILLS = "playerkills";
	public static final String LOOT_TAG_EFFECTLIST = "effectList";
	
	public static final String LOOT_TAG_DRAWSPEED = "drawspeed";
	public static final String LOOT_TAG_MULTISHOT = "miltishot";
	public static final String LOOT_TAG_POWER = "power";
	public static final String LOOT_TAG_UUID = "uuid";
	
	public static final String LOOT_TAG_SKIN_LIST = "loot_skins";
	
	public static final String LOOT_TAG_EFFECT_ACTIVE = "effect_active";
	public static final String LOOT_TAG_EFFECT_LEVEL = "effect_level";
	
	public static final String LOOT_TAG_UPGRADES = "upgrades";
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String displayName = super.getItemStackDisplayName(stack);
		
		LootRarity rarity = LootRarity.fromId(LootItemHelper.getLootStringValue(stack, MLItem.LOOT_TAG_RARITY));
		
		if (rarity != null)
			displayName = rarity.getColor() + displayName;
		
		return displayName;
	}
	
	public static String getMegaLootDisplayName(ItemStack stack, String current)
	{
		String displayName = current;
		
		LootRarity rarity = LootRarity.fromId(LootItemHelper.getLootStringValue(stack, MLItem.LOOT_TAG_RARITY));
		String megaLootName = LootItemHelper.getLootStringValue(stack, MLItem.LOOT_TAG_NAME);
		
		displayName = (megaLootName.length() > 0)? megaLootName : displayName;
		
		if (rarity != null)
			displayName = rarity.getColor() + displayName;
		
		return displayName;
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> modifiers = modifiersForStack(slot, stack, super.getAttributeModifiers(slot, stack), "Weapon modifier");
		
		return modifiers;
	}
	
	public static void applyEffects(ItemStack stack, EntityPlayer player)
	{
		applyEffects(stack, player, 0, true);
	}
	
	public static void applyEffects(ItemStack stack, EntityPlayer player, int slot, boolean isSelected)
	{
		List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(stack);
		
		if (effects != null)
		{
			for (LootWeaponEffect effect : effects)
			{
				if (effect != null)
				{
					PotionEffect pe = effect.getPotionEffect(240, LootWeaponEffect.getAmplifierFromStack(stack, effect.getId()));
					
					if (pe != null)
					{
						player.addPotionEffect(pe);
					}
					
					ILootEffectAction action = effect.getAction();
					
					if (action != null)
						action.handleUpdate(stack, player.world, player, slot, isSelected);
				}
			}
		}
	}
	
	public static float getEfficiency(ItemStack stack, IBlockState state)
	{
		float efficiency = LootItemHelper.getLootFloatValue(stack, MLItem.LOOT_TAG_EFFICIENCY);
		
		for (String type : stack.getItem().getToolClasses(stack))
		{
			if (state.getBlock().isToolEffective(type, state) || (state.getBlock() == Blocks.OBSIDIAN && type == "pickaxe"))
				return efficiency;
		}
		
		return 1.0F;
	}
	
	public static Multimap<String, AttributeModifier> modifiersForStack(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> initial, String modifierKey)
	{
		return modifiersForStack(slot, EntityEquipmentSlot.MAINHAND, stack, initial, modifierKey);
	}
	
	public static Multimap<String, AttributeModifier> modifiersForStack(EntityEquipmentSlot slot, EntityEquipmentSlot effectiveSlot, ItemStack stack, Multimap<String, AttributeModifier> initial, String modifierKey)
	{
		Multimap<String, AttributeModifier> modifiers = initial;
		
		if (slot == effectiveSlot)
		{
			int attackDamage = LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_DAMAGE);
			float attackSpeed = LootItemHelper.getLootFloatValue(stack, MLItem.LOOT_TAG_SPEED);
			float armorPoints = LootItemHelper.getLootFloatValue(stack, MLItem.LOOT_TAG_ARMOR);
			float armorToughness = LootItemHelper.getLootFloatValue(stack, MLItem.LOOT_TAG_TOUGHNESS);
			
			if (attackDamage > 0)
				applyAttributeModifier(modifiers, SharedMonsterAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, modifierKey, (double)attackDamage);
			
			if (attackSpeed > 0)
				applyAttributeModifier(modifiers, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, modifierKey, (double)attackSpeed);
			
			if (armorPoints > 0)
				applyAttributeModifier(modifiers, SharedMonsterAttributes.ARMOR, ARMOR_MODIFIERS[slot.getIndex()], modifierKey, (double)armorPoints);
			
			if (armorToughness > 0)
				applyAttributeModifier(modifiers, SharedMonsterAttributes.ARMOR_TOUGHNESS, ARMOR_MODIFIERS[slot.getIndex()], modifierKey, (double)armorToughness);
			
			List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(stack);
			
			String uuid_string = LootItemHelper.getLootStringValue(stack, LOOT_TAG_UUID);
			
			if (uuid_string.length() > 0)
			{
				for (LootWeaponEffect effect : effects)
				{
					if (effect.getAttribute() != null)
					{
						modifiers.put(effect.getAttribute().getName(), new AttributeModifier(UUID.fromString(uuid_string), "Equipment modifier", (double)LootWeaponEffect.getAmplifierFromStack(stack, effect.getId()), 0));
					}
				}
			}
		}
		
		return modifiers;
	}
	
	private static void applyAttributeModifier(Multimap<String, AttributeModifier> modifiers, IAttribute attribute, UUID uuid, String modifierKey, double value)
	{
		Collection<AttributeModifier> curModifiers = new ArrayList<AttributeModifier>();
		double attributeValue = 0;
		
		curModifiers.clear();
		curModifiers.addAll(modifiers.get(attribute.getName()));
		
		for (AttributeModifier m : curModifiers)
			attributeValue += m.getAmount();
		
		modifiers.removeAll(attribute.getName());
		modifiers.put(attribute.getName(), new AttributeModifier(uuid, modifierKey, value + attributeValue, 0));
	}
	
	public static void handleEffectsAfterHit(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (target instanceof EntityPlayer && target.getHealth() <= 0.0)
		{
			int kills = LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_KILLS);
			kills++;
			LootItemHelper.setLootIntValue(stack, MLItem.LOOT_TAG_KILLS, kills);
		}
		
		List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(stack);
		
		if (effects.size() > 0)
		{
			for (LootWeaponEffect effect : effects)
			{
				effect.onHit(LootWeaponEffect.getDurationFromStack(stack, effect.getId()), LootWeaponEffect.getAmplifierFromStack(stack, effect.getId()), target, attacker);
			}
		}
	}
	
	public static boolean breakBlocks(ItemStack stack, int level, World world, BlockPos aPos, EnumFacing side, EntityPlayer player)
	{
		int xradN = 0;
		int xradP = 0;
		int yradN = 0;
		int yradP = 0;
		int zradN = 0;
		int zradP = 0;
		
		switch (level)
		{
		case 1: //2x2
			xradN = 0;
			xradP = 1;
			yradN = 1;
			yradP = 0;
			zradN = 0;
			zradP = 0;
			break;
		case 2: //3x3
			xradN = 1;
			xradP = 1;
			yradN = 1;
			yradP = 1;
			zradN = 0;
			zradP = 0;
			break;
		case 3: //4x4
			xradN = 1;
			xradP = 2;
			yradN = 1;
			yradP = 2;
			zradN = 0;
			zradP = 0;
			break;
		case 4: //5x5
			xradN = 2;
			xradP = 2;
			yradN = 2;
			yradP = 2;
			zradN = 0;
			zradP = 0;
			break;
		default:
			xradN = 0;
			xradP = 0;
			yradN = 0;
			yradP = 0;
			zradN = 0;
			zradP = 0;
			break;
		}
		
		if(side.getAxis() == Axis.Y)
		{
			zradN = xradN;
			zradP = xradP;
			yradN = 0;
			yradP = 0;
		}
		
		if(side.getAxis() == Axis.X)
		{
			xradN = 0;
			xradP = 0;
			zradN = yradN;
			zradP = yradP;
		}
		
		IBlockState state = world.getBlockState(aPos);
		float mainHardness = state.getBlockHardness(world, aPos);
		
		if(!tryHarvestBlock(world, aPos, false, stack, player))
		{
			return false;
		}
		
		if(level == 4 && side.getAxis() != Axis.Y)
		{
			aPos = aPos.up();
			IBlockState theState = world.getBlockState(aPos);
			if(theState.getBlockHardness(world, aPos) <= mainHardness + 5.0F)
			{
				tryHarvestBlock(world, aPos, true, stack, player);
			}
		}
		
		if(level > 0 && mainHardness >= 0.2F)
		{
			for(int xPos = aPos.getX()-xradN; xPos <= aPos.getX()+xradP; xPos++)
			{
				for(int yPos = aPos.getY()-yradN; yPos <= aPos.getY()+yradP; yPos++)
				{
					for(int zPos = aPos.getZ()-zradN; zPos <= aPos.getZ()+zradP; zPos++)
					{
						if(!(aPos.getX() == xPos && aPos.getY() == yPos && aPos.getZ() == zPos))
						{
							BlockPos thePos = new BlockPos(xPos, yPos, zPos);
							IBlockState theState = world.getBlockState(thePos);
							if(theState.getBlockHardness(world, thePos) <= mainHardness + 5.0F)
							{
								tryHarvestBlock(world, thePos, true, stack, player);
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	public static boolean tryHarvestBlock(World world, BlockPos pos, boolean isExtra, ItemStack stack, EntityPlayer player)
	{
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		float hardness = state.getBlockHardness(world, pos);
		
		boolean canHarvest = 
				(ForgeHooks.canHarvestBlock(block, player, world, pos) || stack.getItem().canHarvestBlock(state, stack)) 
				&& (!isExtra || stack.getItem().getDestroySpeed(stack, world.getBlockState(pos)) > 1.0F);
		
		if(hardness >= 0.0F && (!isExtra || (canHarvest && !block.hasTileEntity(world.getBlockState(pos)))))
		{
			return breakExtraBlock(stack, world, player, pos);
		}
		return false;
	}
	
	public static boolean breakExtraBlock(ItemStack stack, World world, EntityPlayer player, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		
		if (player.capabilities.isCreativeMode)
		{
			if (block.removedByPlayer(state, world, pos, player, false))
			{
				block.onBlockDestroyedByPlayer(world, pos, state);
			}
			
			if (!world.isRemote)
			{
				((EntityPlayerMP)player).connection.sendPacket(new SPacketBlockChange(world, pos));
			}
			
			return true;
		}
		
		stack.onBlockDestroyed(world, state, pos, player);
		
		if (!world.isRemote)
		{
			int xp = ForgeHooks.onBlockBreakEvent(world, ((EntityPlayerMP) player).interactionManager.getGameType(), (EntityPlayerMP) player, pos);
			if (xp == -1) 
				return false;
			
			TileEntity tileEntity = world.getTileEntity(pos);
			
			if (block.removedByPlayer(state, world, pos, player, true))
			{
				block.onBlockDestroyedByPlayer(world, pos, state);
				block.harvestBlock(world, player, pos, state, tileEntity, stack);
				block.dropXpOnBlockBreak(world, pos, xp);
			}
			
			((EntityPlayerMP) player).connection.sendPacket(new SPacketBlockChange(world, pos));
			return true;
		}
		else
		{
			world.playEvent(2001, pos, Block.getStateId(state));
			
			if (block.removedByPlayer(state, world, pos, player, true))
			{
				block.onBlockDestroyedByPlayer(world, pos, state);
			}
			
			stack.onBlockDestroyed(world, state, pos, player);
			
			MegaLoot.proxy.sendBlockBreakPacket(pos);
			return true;
		}
	}
	
	public static RayTraceResult getBlockOnReach(World world, EntityPlayer player)
	{
		double distance = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
		
		float pitch = player.rotationPitch;
		float yaw = player.rotationYaw;
		double x = player.posX;
		double y = player.posY + (double) player.getEyeHeight();
		double z = player.posZ;
		
		Vec3d vec3 = new Vec3d(x, y, z);
		
		float f2 = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
		float f3 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
		float f4 = -MathHelper.cos(-pitch * 0.017453292F);
		float f5 = MathHelper.sin(-pitch * 0.017453292F);
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		
		Vec3d vec31 = vec3.addVector((double) f6 * distance, (double) f5 * distance, (double) f7 * distance);
		
		boolean stopOnLiquids = false;
		boolean ignoreNoBoundingBox = true;
		boolean returnLastUncollidable = false;
		
		return world.rayTraceBlocks(vec3, vec31, stopOnLiquids, ignoreNoBoundingBox, returnLastUncollidable);
	}
}
