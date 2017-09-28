package zairus.megaloot.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MegaLoot;

public class MLItemWeaponSword extends ItemSword
{
	protected MLItemWeaponSword()
	{
		super(ToolMaterial.DIAMOND);
		
		this.setCreativeTab(MegaLoot.creativeTabMain);
		
		/*
		EntityLiving e = null;
		Potion p;
		
		p = MobEffects.BLINDNESS; // Potion.getPotionById(Potion.getIdFromPotion(MobEffects.BLINDNESS));
		
		e.addPotionEffect(new PotionEffect(p, 20));
		*/
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		/*
		if (!stack.hasTagCompound())
		{
			NBTTagCompound tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
		
		stack.getTagCompound().setInteger("HideFlags", 0);
		*/
		
		tooltip.add("");
		tooltip.add("10 Damage | 1.6 Atack Speed");
		tooltip.add("6.25 DPS");
		tooltip.add(TextFormatting.AQUA + "Weakness on Attack");
		tooltip.add("840 Durability");
		
		//stack.getEnchantmentTagList();
	}
}
