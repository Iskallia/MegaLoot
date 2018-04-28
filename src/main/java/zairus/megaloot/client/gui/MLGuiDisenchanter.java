package zairus.megaloot.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MLConstants;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.inventory.MLContainerDisenchanter;
import zairus.megaloot.tileentity.MLTileEntityDisenchanter;
import zairus.megaloot.util.network.MLPacketToolRepair;

@SideOnly(Side.CLIENT)
public class MLGuiDisenchanter extends GuiContainer
{
	public static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(MLConstants.MOD_ID, "textures/gui/container/disenchanter.png");
	
	private final int BUTTON_REPAIR_ID = 0;
	private final int BUTTON_DISENCHANT_ID = 1;
	
	private MLGuiButton buttonRepair;
	private MLGuiButton buttonDisenchant;
	
	private IInventory inventory;
	
	public MLGuiDisenchanter(IInventory playerInv, IInventory inventorySlots, EntityPlayer player)
	{
		super(new MLContainerDisenchanter(playerInv, inventorySlots, player));
		this.inventory = inventorySlots;
	}
	
	@Override
	public void initGui()
	{
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		
		this.buttonList.add(buttonRepair = new MLGuiButton(this.BUTTON_REPAIR_ID, 0, i + 25, j + 15, 18, 18, "", GUI_BACKGROUND));
		this.buttonList.add(buttonDisenchant = new MLGuiButton(this.BUTTON_DISENCHANT_ID, 1, i + 133, j + 62, 18, 18, "", GUI_BACKGROUND));
		
		this.buttonRepair.tooltip.add("Apply repair / upgrade");
		
		this.buttonRepair.tooltipExtended.add("Use shards of same type to repair.");
		this.buttonRepair.tooltipExtended.add("Use upgrade charm of same type to");
		this.buttonRepair.tooltipExtended.add("add an extra attribute.");
		
		this.buttonDisenchant.tooltip.add("Disenchant.");
		
		this.buttonDisenchant.tooltipExtended.add("Will destory the tool");
		this.buttonDisenchant.tooltipExtended.add("to shards.");
		
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		switch (button.id)
		{
		case BUTTON_REPAIR_ID:
			if (this.inventory instanceof MLTileEntityDisenchanter)
			{
				MLTileEntityDisenchanter d = (MLTileEntityDisenchanter)this.inventory;
				
				MegaLoot.packetPipeline.sendToServer(new MLPacketToolRepair(d.getPos().getX(), d.getPos().getY(), d.getPos().getZ(), 0));
			}
			break;
		case BUTTON_DISENCHANT_ID:
			if (this.inventory instanceof MLTileEntityDisenchanter)
			{
				MLTileEntityDisenchanter d = (MLTileEntityDisenchanter)this.inventory;
				
				MegaLoot.packetPipeline.sendToServer(new MLPacketToolRepair(d.getPos().getX(), d.getPos().getY(), d.getPos().getZ(), 1));
			}
			break;
			
		}
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
		this.fontRenderer.drawString(I18n.format("container.disenchanter.title"), 7, 4, 4210752);
		
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
		
		this.setButtonPositions(i, j);
		
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		
		if (this.inventory instanceof MLTileEntityDisenchanter)
		{
			MLTileEntityDisenchanter d = (MLTileEntityDisenchanter)this.inventory;
			
			this.drawTexturedModalRect(i + 82, j + 58, 176, (20 * d.getDisenchantStep()), 29, 20);
		}
	}
	
	private void setButtonPositions(int left, int top)
	{
		;
	}
}
