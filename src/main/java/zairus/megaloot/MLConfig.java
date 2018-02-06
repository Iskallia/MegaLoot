package zairus.megaloot;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class MLConfig
{
	public static Configuration configuration;
	
	public static int armorPiecesForBonus = 3;
	
	public static void init(File cFile)
	{
		configuration = new Configuration(cFile);
		
		configuration.load();
		
		configuration.getInt("armorPiecesForBonus", "DEFAULT", armorPiecesForBonus, 1, 4, "Sets the minimum number of armor pieces required for the set bonus to take effect.");
		
		configuration.save();
	}
}
