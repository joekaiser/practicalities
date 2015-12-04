package practicalities.items.netherbane;

import cofh.lib.util.TimeTracker;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import practicalities.items.ModItems;

public class EntityNetherbane extends EntityItem {

	private TimeTracker timer;
	private int step = 0;
	private int subStep = 0;

	private static final String[] names = new String[] { "Ghost Rider", "The Lich", "Jack Skellington", "Skeletor", "Bonejangles",
			"Dry Bones","Smitty Werbenjagermanjensen" };

	public EntityNetherbane(World world, double x, double y, double z, ItemStack itemStack) {
		super(world, x, y, z, itemStack);
		this.isImmuneToFire = true;
		this.invulnerable = true;
		timer = new TimeTracker();

	}

	@Override
	public void onUpdate() {

		if (worldObj.isRemote) {
			return;
		}
		super.onUpdate();

		switch (step) {
		case 0:
			if (onGround) {
				if (timer.hasDelayPassed(worldObj, 70)) {
					nextStep();
				}
			}
			break;
		case 1:
			if (timer.hasDelayPassed(worldObj, 6 + rand.nextInt(6))) {
				worldObj.createExplosion(this, posX + rand.nextDouble(), posY, posZ + rand.nextDouble(), 1, false);

				EntitySkeleton skele = new EntitySkeleton(worldObj);
				skele.dimension = this.dimension;
				skele.setLocationAndAngles(posX, posY, posZ, MathHelper.wrapAngleTo180_float(rand.nextFloat() * 360.0F),
						0.0F);

				skele.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 20, 2, false));
				skele.addPotionEffect(new PotionEffect(Potion.regeneration.id, 20, 1, false));
				skele.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1, false));
				skele.setCustomNameTag(names[subStep]);
				skele.setSkeletonType(1);
				skele.playLivingSound();

				worldObj.spawnEntityInWorld(skele);

				subStep++;
				if (subStep > 6) {
					nextStep();
				}
			}
			break;

		case 2:
			if (timer.hasDelayPassed(worldObj, 200)) {
				this.setEntityItemStack(new ItemStack(ModItems.netherBlade));
			}
			break;
		}

	}

	private void nextStep() {
		step++;
		subStep = 0;
	}

	public static EntityNetherbane convert(EntityItem entity) {
		EntityNetherbane newEntity = new EntityNetherbane(entity.worldObj, entity.posX, entity.posY, entity.posZ,
				entity.getEntityItem());
		newEntity.dimension = entity.dimension;
		newEntity.motionX = entity.motionX;
		newEntity.motionY = entity.motionY;
		newEntity.motionZ = entity.motionZ;
		newEntity.age = entity.age;
		newEntity.delayBeforeCanPickup = entity.delayBeforeCanPickup;
		newEntity.hoverStart = entity.hoverStart;
		newEntity.lifespan = entity.lifespan;
		return newEntity;
	}

}
