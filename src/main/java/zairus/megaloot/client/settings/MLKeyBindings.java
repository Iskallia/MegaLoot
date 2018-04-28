package zairus.megaloot.client.settings;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MLConstants;

@SideOnly(Side.CLIENT)
public class MLKeyBindings
{
	private static final String KEYBINDING_CATEGORY_ID = MLConstants.MOD_ID + ".key.categories.megaloot";
	
	public static final KeyBinding activateEffect = new KeyBinding(MLConstants.MOD_ID + ".key.activateeffect", Keyboard.KEY_LMENU, KEYBINDING_CATEGORY_ID);
	
	public static void init()
	{
		ClientRegistry.registerKeyBinding(activateEffect);
	}
}
