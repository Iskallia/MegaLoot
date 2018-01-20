package zairus.megaloot.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import zairus.megaloot.MLConstants;

public class MLSoundEvents
{
	public static SoundEvent WEAPON_LOOP;
	public static SoundEvent BLADE;
	public static SoundEvent POWER;
	public static SoundEvent DRAW;
	public static SoundEvent BOW_OPEN;
	public static SoundEvent CASE_OPEN;
	
	public static SoundEvent registerSound(ResourceLocation location)
	{
		SoundEvent sound = new SoundEvent(location).setRegistryName(location);
		
		return sound;
	}
	
	private static SoundEvent registerSound(String location)
	{
		return registerSound(new ResourceLocation(MLConstants.MOD_ID, location));
	}
	
	static
	{
		WEAPON_LOOP = registerSound("weapon_loop");
		BLADE = registerSound("blade");
		POWER = registerSound("power");
		DRAW = registerSound("draw");
		BOW_OPEN = registerSound("bow_open");
		CASE_OPEN = registerSound("case_open");
	}
}
