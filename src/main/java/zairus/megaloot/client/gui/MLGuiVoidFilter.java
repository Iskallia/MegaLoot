package zairus.megaloot.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MLConstants;
import zairus.megaloot.inventory.MLContainerStack;
import zairus.megaloot.inventory.MLInventoryStack;

@SideOnly(Side.CLIENT)
public class MLGuiVoidFilter extends GuiContainer
{
	public static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(MLConstants.MOD_ID, "textures/gui/container/selective.png");
	
	private IInventory inventory;
	
	public MLGuiVoidFilter(IInventory playerInv, IInventory inventorySlots, EntityPlayer player)
	{
		super(new MLContainerStack(playerInv, inventorySlots, player));
		this.inventory = inventorySlots;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		this.ySize = 166;
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
		String title = "";
		
		if (this.inventory instanceof MLInventoryStack)
		{
			MLInventoryStack inventoryStack = (MLInventoryStack)this.inventory;
			title = inventoryStack.getHolderStack().getDisplayName() + TextFormatting.RESET + "'s Void Filter";
		}
		
		this.fontRenderer.drawString(I18n.format(title), 7, 5, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_BACKGROUND);
		//this.ySize = 125;
		
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}
}
