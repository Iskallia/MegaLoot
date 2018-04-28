package zairus.megaloot.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class MLBlock extends Block
{
	public static final String SKIN_TABLE_ID = "skin_table";
	public static final String DISENCHANTER_ID = "disenchanter";
	public static final String EVOLUTION_CHAMBER_ID = "evolution_chamber";
	
	protected MLBlock(Material material)
	{
		super(material);
	}
}
