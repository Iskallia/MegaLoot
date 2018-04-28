package zairus.megaloot.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import zairus.megaloot.MLConstants;
import zairus.megaloot.inventory.MLContainerEvolutionChamber;
import zairus.megaloot.tileentity.MLTileEntityEvolutionChamber;

public class MLGuiEvolutionChamber extends GuiContainer
{
	public static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(MLConstants.MOD_ID, "textures/gui/container/evolution_chamber.png");
	
	private IInventory inventory;
	
	public MLGuiEvolutionChamber(IInventory playerInv, IInventory inventorySlots, EntityPlayer player)
	{
		super(new MLContainerEvolutionChamber(playerInv, inventorySlots, player));
		this.inventory = inventorySlots;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString(I18n.format("container.evolution_chamber.title"), 7, 4, 4210752);
		
		int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        
        for (GuiButton b : this.buttonList)
        {
        	if (b instanceof MLGuiButton)
        	{
        		MLGuiButton db = (MLGuiButton)b;
        		
        		if (mouseX > db.x && mouseX < db.x + db.width && mouseY > db.y && mouseY < db.y + db.height)
        		{
        			this.drawHoveringText(db.getToolTip(isShiftKeyDown()), mouseX - i, mouseY - j +10);
        		}
        	}
        }
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_BACKGROUND);
		this.ySize = 166;
		
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		
		if (this.inventory instanceof MLTileEntityEvolutionChamber)
		{
			MLTileEntityEvolutionChamber ec = (MLTileEntityEvolutionChamber)this.inventory;
			
			float percentage = (float)ec.getEvolutionTime() / (float)MLTileEntityEvolutionChamber.TOTAL_EVOLUTION_TIME;
			int progress_total = (int)((float)46 * percentage);
			
			int progress_sides = (progress_total > 25)? 25 : progress_total; //25 complee
			
			this.drawTexturedModalRect(i + 63, j + 24, 176, 0, progress_sides, 9);
			this.drawTexturedModalRect(i + 63 + 25 + (25 - progress_sides), j + 24, 176 + 25 + (25 - progress_sides), 0, 25, 9);
			
			int progress_down = (progress_total > 25)? (progress_total - 25) : 0; // 21 complete
			this.drawTexturedModalRect(i + 83, j + 32, 176, 9, 186 - 176, progress_down);
		}
	}
}
