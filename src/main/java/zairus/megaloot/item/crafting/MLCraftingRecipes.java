package zairus.megaloot.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zairus.megaloot.MLConstants;
import zairus.megaloot.block.MLBlocks;
import zairus.megaloot.item.MLItems;

public class MLCraftingRecipes
{
	public static void addRecipes()
	{
		GameRegistry.addShapedRecipe(
				new ResourceLocation(MLConstants.MOD_ID, "rift_stone")
				, new ResourceLocation(MLConstants.MOD_ID, "megaloot")
				, new ItemStack(MLItems.RIFT_STONE)
				, new Object[] {
						" s "
						,"sds"
						,'s'
						,Ingredient.fromItems(MLItems.SHARD_COMMON, MLItems.SHARD_RARE, MLItems.SHARD_EPIC)
						,'d'
						,Items.DIAMOND
				});
		
		GameRegistry.addShapedRecipe(
				new ResourceLocation(MLConstants.MOD_ID, "skin_table")
				, new ResourceLocation(MLConstants.MOD_ID, "megaloot")
				, new ItemStack(MLBlocks.SKIN_TABLE)
				, new Object[] {
						"igi"
						,"gsg"
						,"igi"
						,'i'
						,Blocks.IRON_BLOCK
						,'g'
						,Blocks.GLASS
						,'s'
						,MLItems.SHARD_EPIC
				});
		
		GameRegistry.addShapedRecipe(
				new ResourceLocation(MLConstants.MOD_ID, "disenchanter")
				, new ResourceLocation(MLConstants.MOD_ID, "megaloot")
				, new ItemStack(MLBlocks.DISENCHANTER)
				, new Object[] {
						" d "
						," a "
						,"iii"
						,'d'
						,Items.DIAMOND
						,'a'
						,Blocks.ANVIL
						,'i'
						,Blocks.IRON_BLOCK
				});
		
		GameRegistry.addShapedRecipe(
				new ResourceLocation(MLConstants.MOD_ID, "evolution_chamber")
				, new ResourceLocation(MLConstants.MOD_ID, "megaloot")
				, new ItemStack(MLBlocks.EVOLUTION_CHAMBER)
				, new Object[] {
						"rcr"
						, "cgc"
						, "rcr"
						, 'r'
						, Ingredient.fromItem(MLItems.SHARD_RARE)
						, 'c'
						, Ingredient.fromItem(MLItems.SHARD_COMMON)
						, 'g'
						, Blocks.GLASS
				});
		
		GameRegistry.addShapedRecipe(
				new ResourceLocation(MLConstants.MOD_ID, "upgradecharm_common")
				, new ResourceLocation(MLConstants.MOD_ID, "megaloot")
				, new ItemStack(MLItems.UPGRADECHARM_COMMON)
				, new Object[] {
						"sss"
						, "scs"
						, "sss"
						, 's'
						, Ingredient.fromItem(MLItems.SHARD_COMMON)
						, 'c'
						, Ingredient.fromItem(MLItems.WEAPONCASE_COMMON)
				});
		
		GameRegistry.addShapedRecipe(
				new ResourceLocation(MLConstants.MOD_ID, "upgradecharm_rare")
				, new ResourceLocation(MLConstants.MOD_ID, "megaloot")
				, new ItemStack(MLItems.UPGRADECHARM_RARE)
				, new Object[] {
						"sss"
						, "scs"
						, "sss"
						, 's'
						, Ingredient.fromItem(MLItems.SHARD_RARE)
						, 'c'
						, Ingredient.fromItem(MLItems.WEAPONCASE_RARE)
				});
		
		GameRegistry.addShapedRecipe(
				new ResourceLocation(MLConstants.MOD_ID, "upgradecharm_epic")
				, new ResourceLocation(MLConstants.MOD_ID, "megaloot")
				, new ItemStack(MLItems.UPGRADECHARM_EPIC)
				, new Object[] {
						"sss"
						, "scs"
						, "sss"
						, 's'
						, Ingredient.fromItem(MLItems.SHARD_EPIC)
						, 'c'
						, Ingredient.fromItem(MLItems.WEAPONCASE_EPIC)
				});
		
		GameRegistry.addShapedRecipe(
				new ResourceLocation(MLConstants.MOD_ID, "infused_emerald_common")
				, new ResourceLocation(MLConstants.MOD_ID, "megaloot")
				, new ItemStack(MLItems.INFUSED_EMERALD_COMMON)
				, new Object[] {
						" s "
						, " e "
						, " s "
						, 's'
						, Ingredient.fromItem(MLItems.SHARD_COMMON)
						, 'e'
						, Ingredient.fromItem(Items.EMERALD)
				});
		
		GameRegistry.addShapedRecipe(
				new ResourceLocation(MLConstants.MOD_ID, "infused_emerald_rare")
				, new ResourceLocation(MLConstants.MOD_ID, "megaloot")
				, new ItemStack(MLItems.INFUSED_EMERALD_RARE)
				, new Object[] {
						" s "
						, " e "
						, " s "
						, 's'
						, Ingredient.fromItem(MLItems.SHARD_RARE)
						, 'e'
						, Ingredient.fromItem(Items.EMERALD)
				});
		
		GameRegistry.addShapelessRecipe(
				new ResourceLocation(MLConstants.MOD_ID, "weaponcase_common")
				, new ResourceLocation(MLConstants.MOD_ID, "megaloot")
				, new ItemStack(MLItems.WEAPONCASE_COMMON)
				, Ingredient.fromItem(MLItems.SHARD_COMMON)
				, Ingredient.fromItem(MLItems.SHARD_COMMON)
				, Ingredient.fromItem(MLItems.SHARD_COMMON)
				, Ingredient.fromItem(MLItems.SHARD_COMMON)
				, Ingredient.fromItem(MLItems.SHARD_COMMON)
				, Ingredient.fromItem(MLItems.SHARD_COMMON)
				, Ingredient.fromItem(MLItems.SHARD_COMMON)
				, Ingredient.fromItem(MLItems.SHARD_COMMON)
				, Ingredient.fromItem(MLItems.SHARD_COMMON));
		
		GameRegistry.addShapelessRecipe(
				new ResourceLocation(MLConstants.MOD_ID, "weaponcase_rare")
				, new ResourceLocation(MLConstants.MOD_ID, "megaloot")
				, new ItemStack(MLItems.WEAPONCASE_RARE)
				, Ingredient.fromItem(MLItems.SHARD_RARE)
				, Ingredient.fromItem(MLItems.SHARD_RARE)
				, Ingredient.fromItem(MLItems.SHARD_RARE)
				, Ingredient.fromItem(MLItems.SHARD_RARE)
				, Ingredient.fromItem(MLItems.SHARD_RARE)
				, Ingredient.fromItem(MLItems.SHARD_RARE)
				, Ingredient.fromItem(MLItems.SHARD_RARE)
				, Ingredient.fromItem(MLItems.SHARD_RARE)
				, Ingredient.fromItem(MLItems.SHARD_RARE));
		
		GameRegistry.addShapelessRecipe(
				new ResourceLocation(MLConstants.MOD_ID, "weaponcase_epic")
				, new ResourceLocation(MLConstants.MOD_ID, "megaloot")
				, new ItemStack(MLItems.WEAPONCASE_EPIC)
				, Ingredient.fromItem(MLItems.SHARD_EPIC)
				, Ingredient.fromItem(MLItems.SHARD_EPIC)
				, Ingredient.fromItem(MLItems.SHARD_EPIC)
				, Ingredient.fromItem(MLItems.SHARD_EPIC)
				, Ingredient.fromItem(MLItems.SHARD_EPIC)
				, Ingredient.fromItem(MLItems.SHARD_EPIC)
				, Ingredient.fromItem(MLItems.SHARD_EPIC)
				, Ingredient.fromItem(MLItems.SHARD_EPIC)
				, Ingredient.fromItem(MLItems.SHARD_EPIC));
	}
}
