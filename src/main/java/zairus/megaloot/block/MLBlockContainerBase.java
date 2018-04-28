package zairus.megaloot.block;

import javax.annotation.Nullable;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import zairus.megaloot.tileentity.MLTileEntityBase;

public class MLBlockContainerBase extends BlockContainer
{
	protected MLBlockContainerBase(Material material)
	{
		super(material);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return null;
	}
	
	@Nullable
	public ILockableContainer getLockableContainer(World world, BlockPos pos)
	{
		return this.getContainer(world, pos, false);
	}
	
	@Nullable
	public ILockableContainer getContainer(World world, BlockPos pos, boolean flag)
	{
		TileEntity tileentity = world.getTileEntity(pos);
		
		if (!(tileentity instanceof MLTileEntityBase))
		{
			return null;
		}
		else
		{
			ILockableContainer ilockablecontainer = (MLTileEntityBase)tileentity;
			
			if (this.isBlocked(world, pos))
			{
				return null;
			}
			else
			{
				return ilockablecontainer;
			}
		}
	}
	
	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return true;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		if (!blockState.canProvidePower())
		{
			return 0;
		}
		else
		{
			int i = 0;
			
			return MathHelper.clamp(i, 0, 15);
		}
	}
	
	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return side == EnumFacing.UP ? blockState.getWeakPower(blockAccess, pos, side) : 0;
	}
	
	private boolean isBlocked(World worldIn, BlockPos pos)
	{
		return this.isBelowSolidBlock(worldIn, pos);
	}
	
	private boolean isBelowSolidBlock(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos.up()).isSideSolid(worldIn, pos.up(), EnumFacing.DOWN);
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return Container.calcRedstoneFromInventory(this.getLockableContainer(worldIn, pos));
	}
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
	{
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = world.getTileEntity(pos);
		
		if (tileentity != null && tileentity instanceof IInventory)
			world.updateComparatorOutputLevel(pos, this);
		
		if (tileentity != null && tileentity instanceof MLTileEntityBase)
		{
			MLTileEntityBase te = (MLTileEntityBase)tileentity;
			
			dropWithContents(world, state, pos, te);
		}
		
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
	{
	}
	
	private void dropWithContents(World world, IBlockState state, BlockPos pos, @Nullable TileEntity te)
	{
		Item item = this.getItemDropped(state, world.rand, 0);
		ItemStack itemStack = new ItemStack(item, 1, this.damageDropped(state));
		
		if (te != null && te instanceof MLTileEntityBase)
		{
			if (!((MLTileEntityBase)te).isEmpty())
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag = ((MLTileEntityBase)te).writeToNBT(tag);
				
				itemStack.setTagCompound(new NBTTagCompound());
				itemStack.getTagCompound().setTag("chestContents", tag);
				
				if (tag.hasKey("CustomName"))
					itemStack.setStackDisplayName(tag.getString("CustomName"));
			}
		}
		
		spawnAsEntity(world, pos, itemStack);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		worldIn.setBlockState(pos, state, 3);
		
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if (tileentity instanceof MLTileEntityBase)
		{
			if (stack.hasDisplayName())
			{
				((MLTileEntityBase)tileentity).setCustomName(stack.getDisplayName());
			}
			
			if (stack.hasTagCompound())
			{
				if (stack.getTagCompound().hasKey("chestContents"))
				{
					NBTTagCompound tag = stack.getTagCompound().getCompoundTag("chestContents");
					((MLTileEntityBase)tileentity).readFromNBT(tag);
					tileentity.setPos(pos);
				}
			}
		}
	}
}
