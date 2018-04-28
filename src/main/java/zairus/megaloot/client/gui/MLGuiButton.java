package zairus.megaloot.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class MLGuiButton extends GuiButton
{
	private final ResourceLocation background;
	
	public int icon = 0;
	public List<String> tooltip = new ArrayList<String>();
	public List<String> tooltipExtended = new ArrayList<String>();
	
	public MLGuiButton(int buttonId, int icon, int x, int y, int width, int height, String buttonText, ResourceLocation background)
	{
		super(buttonId, x, y, width, height, buttonText);
		this.background = background;
		this.icon = icon;
	}
	
	@Override
	public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks)
	{
		if (this.visible)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			minecraft.getTextureManager().bindTexture(this.background);
			
			boolean mouseOver = false;
			
			if (mouseX > this.x && mouseY > this.y && mouseX < this.x + this.width && mouseY < this.y + this.height)
				mouseOver = true;
			
			this.drawTexturedModalRect(this.x, this.y, 0 + ((mouseOver)? this.width : 0), 166, this.width, this.height);
			this.drawTexturedModalRect(this.x, this.y, 0 + (this.icon * this.width), 166 + 18, this.width, this.height);
		}
	}
	
	public List<String> getToolTip(boolean extended)
	{
		List<String> button_tooltip = new ArrayList<String>();
		
		button_tooltip.addAll(this.tooltip);
		
		if (tooltipExtended.size() > 0)
		{
			if (extended)
			{
				button_tooltip.addAll(this.tooltipExtended);
			}
			else
			{
				button_tooltip.add(TextFormatting.GRAY + "" + TextFormatting.ITALIC + "Shift for more...");
			}
		}
		
		return button_tooltip;
	}
	
	public void setScreenPos(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
