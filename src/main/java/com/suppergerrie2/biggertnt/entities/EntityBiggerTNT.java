package com.suppergerrie2.biggertnt.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityBiggerTNT extends EntityTNTPrimed {

	float explosionSize = 4;
	
	public EntityBiggerTNT(World world) {
		this(world, 0, 0, 0, null, 4);
	}

	public EntityBiggerTNT(World worldIn, double x, double y, double z, EntityLivingBase igniter, float size) {
		super(worldIn, x, y, z, igniter);
		this.explosionSize = size;
	}

	//We need to copy this from vanilla to override the explode behaviour, we can't override the explode method because it is private
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (!this.hasNoGravity())
		{
			this.motionY -= 0.03999999910593033D;
		}

		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;

		if (this.onGround)
		{
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
			this.motionY *= -0.5D;
		}
		
		this.setFuse(this.getFuse()-1);

		if (this.getFuse() <= 0)
		{
			this.setDead();

			if (!this.world.isRemote)
			{
				this.explode();
			}
		}
		else
		{
			this.handleWaterMovement();
			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	private void explode()
	{
		this.world.createExplosion(this, this.posX, this.posY + (double)(this.height / 16.0F), this.posZ, explosionSize, true);
	}
	
	//Save and load the explosion size
	protected void writeEntityToNBT(NBTTagCompound compound)
    {
		super.writeEntityToNBT(compound);
        compound.setFloat("ExplosionSize", this.explosionSize);
    }

    protected void readEntityFromNBT(NBTTagCompound compound)
    {
    	super.readEntityFromNBT(compound);
        this.explosionSize = compound.getFloat("ExplosionSize");
    }

}
