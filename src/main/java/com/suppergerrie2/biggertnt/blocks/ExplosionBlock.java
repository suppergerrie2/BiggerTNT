package com.suppergerrie2.biggertnt.blocks;

import com.suppergerrie2.biggertnt.entities.EntityBiggerTNT;

import net.minecraft.block.BlockTNT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ExplosionBlock extends BlockTNT {

	public ExplosionBlock() {
		super();
		this.setRegistryName("explosion_block");
		this.setUnlocalizedName("explosion_block");
	}
	
	//We need to override this method so we can use our own entity
	@Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
    {
        if (!worldIn.isRemote)
        {
        	EntityBiggerTNT entitytntprimed = new EntityBiggerTNT(worldIn, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), explosionIn.getExplosivePlacedBy(), 100);
        	entitytntprimed.setFuse((short)(worldIn.rand.nextInt(entitytntprimed.getFuse() / 4) + entitytntprimed.getFuse() / 8));
            worldIn.spawnEntity(entitytntprimed);
        }
    }

	//We need to override this method so we can use our own entity
	@Override
	public void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter)
	{
		if (!worldIn.isRemote)
		{
			if (((Boolean)state.getValue(EXPLODE)).booleanValue())
			{
				EntityBiggerTNT entitytntprimed = new EntityBiggerTNT(worldIn, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), igniter, 100);
				worldIn.spawnEntity(entitytntprimed);
				worldIn.playSound((EntityPlayer)null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}
	}
}
