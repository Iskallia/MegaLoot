package zairus.megaloot.sound;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;
import zairus.megaloot.MLConstants;

@ObjectHolder(MLConstants.MOD_ID)
public class MLSoundEvents
{
	public static final SoundEvent WEAPON_LOOP;
	public static final SoundEvent BLADE;
	public static final SoundEvent POWER;
	public static final SoundEvent DRAW;
	public static final SoundEvent BOW_OPEN;
	public static final SoundEvent CASE_OPEN;
	public static final SoundEvent TOOL_REPAIR;
	public static final SoundEvent TOOL_BREAK;
	public static final SoundEvent TOOL_PUT;
	public static final SoundEvent TOOL_TAKE;
	public static final SoundEvent SNORE;
	public static final SoundEvent THRUST;
	
	protected static final List<SoundEvent> SOUNDS = new ArrayList<SoundEvent>();
	
	static
	{
		WEAPON_LOOP = registerSound("weapon_loop");
		BLADE = registerSound("blade");
		POWER = registerSound("power");
		DRAW = registerSound("draw");
		BOW_OPEN = registerSound("bow_open");
		CASE_OPEN = registerSound("case_open");
		TOOL_REPAIR = registerSound("tool_repair");
		TOOL_BREAK = registerSound("tool_break");
		TOOL_PUT = registerSound("tool_put");
		TOOL_TAKE = registerSound("tool_take");
		SNORE = registerSound("snore");
		THRUST = registerSound("thrust");
	}
	
	private static SoundEvent registerSound(ResourceLocation location)
	{
		SoundEvent sound = new SoundEvent(location).setRegistryName(location);
		SOUNDS.add(sound);
		return sound;
	}
	
	private static SoundEvent registerSound(String location)
	{
		return registerSound(new ResourceLocation(MLConstants.MOD_ID, location));
	}
	
	@Mod.EventBusSubscriber(modid = MLConstants.MOD_ID)
	public static class SoundRegistry
	{
		@SubscribeEvent
		public static void register(final RegistryEvent.Register<SoundEvent> event)
		{
			final IForgeRegistry<SoundEvent> registry = event.getRegistry();
			
			registry.registerAll(SOUNDS.toArray( new SoundEvent[] {  } ));
		}
	}
}
