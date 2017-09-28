package zairus.megaloot;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class MLConfig
{
	public static Configuration configuration;
	
	public static void init(File cFile)
	{
		configuration = new Configuration(cFile);
		
		configuration.load();
		configuration.save();
	}
}
