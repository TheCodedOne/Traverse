package prospector.traverse.blocks;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import prospector.shootingstar.ShootingStar;
import prospector.shootingstar.model.ModelCompound;
import prospector.traverse.TraverseCommon;
import prospector.traverse.core.TraverseConstants;
import prospector.traverse.core.TraverseTab;

import static prospector.traverse.util.TUtils.getBlock;

public class BlockTraverseDeadGrass extends BlockTallGrass {

    public BlockTraverseDeadGrass() {
        super();
        setRegistryName(new ResourceLocation(TraverseConstants.MOD_ID, "dead_grass"));
        setUnlocalizedName(getRegistryName().toString());
        setCreativeTab(TraverseTab.TAB);
        setSoundType(SoundType.PLANT);
        useNeighborBrightness = true;
        ShootingStar.registerModel(new ModelCompound(TraverseConstants.MOD_ID, this, "plant", TYPE));
    }

    public boolean canSustainBush(IBlockState state) {
        return state.getBlock() == getBlock("red_rock") || state.getBlock() == Blocks.SAND || state.getBlock() == Blocks.HARDENED_CLAY || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this, 1, 0));
    }

    public int damageDropped(IBlockState state) {
        return 0;
    }

    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    public int getMetaFromState(IBlockState state) {
        return 0;
    }
}
