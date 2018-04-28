package zairus.megaloot.client.gui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MLConstants;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.inventory.MLContainerSkinTable;
import zairus.megaloot.tileentity.MLTileEntitySkinTable;
import zairus.megaloot.util.network.MLPacketSkinTable;

@SideOnly(Side.CLIENT)
public class MLGuiSkinTable extends GuiContainer
{
	public static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(MLConstants.MOD_ID, "textures/gui/container/skin_table.png");
	
	private final int BUTTON_PREV_ID = 0;
	private final int BUTTON_NEXT_ID = 1;
	
	private MLGuiButton buttonPrev;
	private MLGuiButton buttonNext;
	
	private final IInventory inventory;
	
	public MLGuiSkinTable(IInventory playerInv, IInventory inventorySlots, EntityPlayer player)
	{
		super(new MLContainerSkinTable(playerInv, inventorySlots, player));
		this.inventory = inventorySlots;
	}
	
	@Override
	public void initGui()
	{
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		
		this.buttonList.add(buttonPrev = new MLGuiButton(this.BUTTON_PREV_ID, 0, i + 56, j + 63, 18, 18, "", GUI_BACKGROUND));
		this.buttonList.add(buttonNext = new MLGuiButton(this.BUTTON_NEXT_ID, 1, i + 102, j + 63, 18, 18, "", GUI_BACKGROUND));
		
		this.buttonPrev.tooltip.add("Previous skin list");
		
		this.buttonNext.tooltip.add("Next skin list");
		
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		switch(button.id)
		{
		case 0:
			if (this.inventory instanceof MLTileEntitySkinTable)
			{
				MLTileEntitySkinTable skinTable = (MLTileEntitySkinTable)this.inventory;
				MegaLoot.packetPipeline.sendToServer(new MLPacketSkinTable(skinTable.getPos().getX(), skinTable.getPos().getY(), skinTable.getPos().getZ(), 2));
			}
			break;
		case 1:
			if (this.inventory instanceof MLTileEntitySkinTable)
			{
				MLTileEntitySkinTable skinTable = (MLTileEntitySkinTable)this.inventory;
				MegaLoot.packetPipeline.sendToServer(new MLPacketSkinTable(skinTable.getPos().getX(), skinTable.getPos().getY(), skinTable.getPos().getZ(), 3));
			}
			break;
		}
		updateButtons();
	}
	
	private void updateButtons()
	{
		;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		
		if (
				mouseY > j + 80
				|| (
						mouseX > i + 70
						&& mouseX < i + 100
						&& mouseY > j + 35
						&& mouseY < j + 80))
		{
			this.renderHoveredToolTip(mouseX, mouseY);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString("Cosmetic Transmutation", 7, 4, 4210752);
		
		//int i = (this.width - this.xSize) / 2;
        //int j = (this.height - this.ySize) / 2;
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
		
		this.drawTexturedModalRect(i + 156, j + 6, 176, 0, 12, 72);
	}
	
	private void setButtonPositions(int left, int top)
	{
		;
	}
	
	public static class GuiExecutorConfigureButton extends GuiButton
	{
		private final int type;
		public final int maxStep;
		public int curStep = 0;
		
		public List<List<String>> tooltip = new ArrayList<List<String>>();
		
		public GuiExecutorConfigureButton(int buttonId, int buttonType, int maxStep, int x, int y, int width, int height, String buttonText)
		{
			super(buttonId, x, y, width, height, buttonText);
			this.type = buttonType;
			this.maxStep = maxStep;
		}
		
		@Override
		public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks)
		{
			if (this.visible)
			{
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				minecraft.getTextureManager().bindTexture(MLGuiSkinTable.GUI_BACKGROUND);
				
				this.drawTexturedModalRect(this.x, this.y, 0 + ((this.curStep % this.maxStep) * 14), 166 + (13 * this.type), 14, 13);
			}
		}
		
		public void setScreenPos(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		@Nullable
		public List<String> getStepTooltip()
		{
			if (this.curStep < this.tooltip.size())
			{
				return this.tooltip.get(curStep);
			}
			
			return null;
		}
	}
}
