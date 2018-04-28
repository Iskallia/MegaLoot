package zairus.megaloot.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.gui.MLGuiHandler;
import zairus.megaloot.tileentity.MLTileEntitySkinTable;

public class MLBlockSkinTable extends MLBlockContainerBase
{
	protected static final AxisAlignedBB AXIS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.60D, 1.0D);
	
	protected MLBlockSkinTable()
	{
		super(Material.CIRCUITS);
		this.setCreativeTab(MegaLoot.creativeTabMain);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.GLASS);
		this.setHarvestLevel("hand", 0);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			player.openGui(MegaLoot.instance, MLGuiHandler.GUI_SKIN_TABLE, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new MLTileEntitySkinTable();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AXIS_AABB;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return true;
	}
}
