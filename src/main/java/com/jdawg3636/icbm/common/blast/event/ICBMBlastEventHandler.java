package com.jdawg3636.icbm.common.blast.event;

import com.jdawg3636.icbm.common.entity.EntityLingeringBlast;
import com.jdawg3636.icbm.common.entity.EntityShrapnel;
import com.jdawg3636.icbm.common.reg.EntityReg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SExplosionPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;

/**
 * Separate Event Handler for Mod's Own Events
 * Keeps stuff like blast behavior separate from core functionality such as registration
 */
public class ICBMBlastEventHandler {

    public static void doBlastSoundAndParticles(BlastEvent event) {
        // Loosely Based on net.minecraft.world.Explosion.doExplosionB(boolean spawnParticles)
        if (!event.getBlastWorld().isClientSide) {
            // Sound
            event.getBlastWorld().playSound((PlayerEntity) null, event.getBlastPosition().getX(), event.getBlastPosition().getY(), event.getBlastPosition().getZ(), SoundEvents.GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (event.getBlastWorld().random.nextFloat() - event.getBlastWorld().random.nextFloat()) * 0.2F) * 0.7F);
            // Particles - Handled Client Side by net.minecraft.client.network.play.ClientPlayNetHandler.handleParticles(SSpawnParticlePacket packetIn)
            event.getBlastWorld().sendParticles(ParticleTypes.EXPLOSION_EMITTER, event.getBlastPosition().getX(), event.getBlastPosition().getY(), event.getBlastPosition().getZ(), 1, 0, 0, 0, 1.0D);
        }
    }

    public static void doVanillaExplosion(BlastEvent event) {
        doVanillaExplosion(event, 4.0F);
    }

    public static void doVanillaExplosion(BlastEvent event, float explosionPower) {
        event.getBlastWorld().explode(null, event.getBlastPosition().getX(), event.getBlastPosition().getY(), event.getBlastPosition().getZ(), explosionPower, Explosion.Mode.BREAK);
    }

    @SubscribeEvent
    public static void onBlastAnvil(BlastEvent.Anvil event) {
        doBlastSoundAndParticles(event);
        doVanillaExplosion(event);
        for(double i = -0.5; i <= 0.5; i += 0.0625) {
            for(double j = -0.5; j <= 0.5; j += 0.0625) {
                if(i * i + j * j > 0.5 * 0.5) continue; // Circle, not a square.
                FallingBlockEntity fallingblockentity = new FallingBlockEntity(event.getBlastWorld(), (double)event.getBlastPosition().getX() + 0.5D, (double)event.getBlastPosition().getY() + 1, (double)event.getBlastPosition().getZ() + 0.5D, Blocks.DAMAGED_ANVIL.defaultBlockState());
                fallingblockentity.time = 1;
                fallingblockentity.cancelDrop = true;
                fallingblockentity.fallDamageAmount = 6.0F;
                fallingblockentity.setDeltaMovement(i, 1, j);
                fallingblockentity.setHurtsEntities(true);
                event.getBlastWorld().addFreshEntity(fallingblockentity);
            }
        }
    }

    @SubscribeEvent
    public static void onBlastAntimatter(BlastEvent.Antimatter event) {

        doBlastSoundAndParticles(event);

        if (!event.getBlastWorld().isClientSide) {
            int radius = 50;
            int radiusSq = radius * radius;
            for(int i = -radius; i <= radius; i++)
                for(int j = -radius; j <= radius; j++)
                    for(int k = -radius; k <= radius; k++) {
                        BlockPos candidatePos = new BlockPos(event.getBlastPosition().getX() + i, event.getBlastPosition().getY() + j, event.getBlastPosition().getZ() + k);
                        if (event.getBlastPosition().distSqr(candidatePos) < radiusSq) {
                            event.getBlastWorld().setBlock(candidatePos, Blocks.AIR.defaultBlockState(), 3);
                        }
                    }
        }

    }

    @SubscribeEvent
    public static void onBlastConventional(BlastEvent.Condensed event) {
        doVanillaExplosion(event, 1.75F * 4.0F);
    }

    @SubscribeEvent
    public static void onBlastShrapnel(BlastEvent.Shrapnel event) {
        doBlastSoundAndParticles(event);
        doVanillaExplosion(event);
        for(double i = -0.5; i <= 0.5; i += 0.0625) {
            for(double j = -0.5; j <= 0.5; j += 0.0625) {
                if(i * i + j * j > 0.5 * 0.5) continue; // Circle, not a square.
                EntityShrapnel shrapnelEntity = new EntityShrapnel(event.getBlastWorld(), event.getBlastPosition().getX(), event.getBlastPosition().getY(), event.getBlastPosition().getZ(), false, event.getBlastType());
                shrapnelEntity.setDeltaMovement(i, event.getBlastWorld().random.nextFloat(), j);
                event.getBlastWorld().addFreshEntity(shrapnelEntity);
            }
        }
    }

    @SubscribeEvent
    public static void onBlastIncendiary(BlastEvent.Incendiary event) {

        doBlastSoundAndParticles(event);
        doVanillaExplosion(event);

        // Copied (with slight modifications) from old icbm.content.blast.BlastFire
        // Would like to clean this up a bit if possible
        if (!event.getBlastWorld().isClientSide) {

            int radius = (!(event.getBlastType() == BlastEvent.Type.GRENADE)) ? 14 : 7;

            for (int x = 0; x < radius; ++x) {
                for (int y = 0; y < radius; ++y) {
                    for (int z = 0; z < radius; ++z) {

                        if (x == 0 || x == radius - 1 || y == 0 || y == radius - 1 || z == 0 || z == radius - 1) {

                            double xStep = x / (radius - 1.0F) * 2.0F - 1.0F;
                            double yStep = y / (radius - 1.0F) * 2.0F - 1.0F;
                            double zStep = z / (radius - 1.0F) * 2.0F - 1.0F;
                            double diagonalDistance = Math.sqrt(xStep * xStep + yStep * yStep + zStep * zStep);
                            xStep /= diagonalDistance;
                            yStep /= diagonalDistance;
                            zStep /= diagonalDistance;
                            float var14 = radius * (0.7F + event.getBlastWorld().random.nextFloat() * 0.6F);
                            double var15 = event.getBlastPosition().getX();
                            double var17 = event.getBlastPosition().getY();
                            double var19 = event.getBlastPosition().getZ();

                            for (float var21 = 0.3F; var14 > 0.0F; var14 -= var21 * 0.75F) {

                                BlockPos targetPosition = new BlockPos(var15, var17, var19);
                                double distanceFromCenter = Math.sqrt(event.getBlastPosition().distSqr(targetPosition));
                                BlockState blockState = event.getBlastWorld().getBlockState(targetPosition);
                                Block block = blockState.getBlock();

                                if (!block.isAir(blockState, event.getBlastWorld(), targetPosition))
                                    var14 -= (block.getExplosionResistance() + 0.3F) * var21;

                                if (var14 > 0.0F) {

                                    // Set fire by chance and distance
                                    double chance = radius - (Math.random() * distanceFromCenter);

                                    if (chance > distanceFromCenter * 0.55) {

                                        boolean canReplace = blockState.getMaterial().isReplaceable() || block.isAir(blockState, event.getBlastWorld(), targetPosition);

                                        if (canReplace)
                                            event.getBlastWorld().setBlockAndUpdate(targetPosition, Blocks.FIRE.defaultBlockState());
                                        else if (block == Blocks.ICE) {
                                            event.getBlastWorld().setBlockAndUpdate(targetPosition, Blocks.WATER.defaultBlockState());
                                            event.getBlastWorld().neighborChanged(targetPosition, Blocks.WATER, targetPosition);
                                        }

                                    }

                                }

                                var15 += xStep * var21;
                                var17 += yStep * var21;
                                var19 += zStep * var21;

                            }
                        }
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public static void onBlastDebilitation(BlastEvent.Debilitation event) {
        doBlastSoundAndParticles(event);
        doVanillaExplosion(event);
        EntityLingeringBlast entity = EntityReg.BLAST_DEBILITATION.get().create(event.getBlastWorld());
        entity.setPos(event.getBlastPosition().getX(), event.getBlastPosition().getY(), event.getBlastPosition().getZ());
        entity.ticksRemaining = 400;
        event.getBlastWorld().addFreshEntity(entity);
    }

    @SubscribeEvent
    public static void onBlastChemical(BlastEvent.Chemical event) {
        doBlastSoundAndParticles(event);
        doVanillaExplosion(event);
        EntityLingeringBlast entity = EntityReg.BLAST_CHEMICAL.get().create(event.getBlastWorld());
        entity.setPos(event.getBlastPosition().getX(), event.getBlastPosition().getY(), event.getBlastPosition().getZ());
        entity.ticksRemaining = 100;
        event.getBlastWorld().addFreshEntity(entity);
    }

    @SubscribeEvent
    public static void onBlastFragmentation(BlastEvent.Fragmentation event) {
        doBlastSoundAndParticles(event);
        doVanillaExplosion(event);
        for(double i = -0.5; i <= 0.5; i += 0.0625) {
            for(double j = -0.5; j <= 0.5; j += 0.0625) {
                if(i * i + j * j > 0.5 * 0.5) continue; // Circle, not a square.
                EntityShrapnel shrapnelEntity = new EntityShrapnel(event.getBlastWorld(), event.getBlastPosition().getX(), event.getBlastPosition().getY(), event.getBlastPosition().getZ(), true, event.getBlastType());
                shrapnelEntity.setDeltaMovement(i, event.getBlastWorld().random.nextFloat(), j);
                event.getBlastWorld().addFreshEntity(shrapnelEntity);
            }
        }
    }

    // Implements the explosion when shrapnel from a fragmentation explosive impacts a block/player
    @SubscribeEvent
    public static void onShrapnelImpact(BlastEvent.ShrapnelImpact event) {
        doVanillaExplosion(event, 0.5F * 4.0F);
    }

    @SubscribeEvent
    public static void onBlastContagion(BlastEvent.Contagion event) {
        doBlastSoundAndParticles(event);
        doVanillaExplosion(event);
        EntityLingeringBlast entity = EntityReg.BLAST_CONTAGION.get().create(event.getBlastWorld());
        entity.setPos(event.getBlastPosition().getX(), event.getBlastPosition().getY(), event.getBlastPosition().getZ());
        entity.ticksRemaining = 400;
        event.getBlastWorld().addFreshEntity(entity);
    }

    public static void doMovementBlast(BlastEvent event, double movementMultiplier) {
        doBlastSoundAndParticles(event);
        doVanillaExplosion(event, 4.0F / 2F);

        double radius = 10;

        event.getBlastWorld().getEntities(null, new AxisAlignedBB(event.getBlastPosition()).inflate(radius)).forEach(
                (Entity entity) -> {

                    double deltaX = entity.getX() - event.getBlastPosition().getX();
                    double deltaY = entity.getY() - event.getBlastPosition().getY();
                    double deltaZ = entity.getZ() - event.getBlastPosition().getZ();
                    double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
                    double basePushDistance = movementMultiplier * 10D;

                    Vector3d deltaMovement = new Vector3d(basePushDistance * deltaX / distance, basePushDistance * deltaY / distance / 3, basePushDistance * deltaZ / distance);
                    entity.setDeltaMovement(entity.getDeltaMovement().add(deltaMovement));
                    if (entity instanceof ServerPlayerEntity) {
                        // Player movement (other than teleportation) is controlled exclusively client-side (which is dumb, but it's a vanilla mechanic that we can't control)
                        // so we trick the client into moving for us by telling it that there is a vanilla explosion that destroys no blocks and moves the player by our desired amount.
                        // This is handled client-side by net.minecraft.client.network.play.ClientPlayNetHandler::handleExplosion (MCP Class Names and Package Structure, Official Method/Field Mappings, Minecraft 1.16.5).
                        ((ServerPlayerEntity) entity).connection.send(new SExplosionPacket(event.getBlastPosition().getX(), event.getBlastPosition().getY(), event.getBlastPosition().getZ(), 0F, new ArrayList<BlockPos>(), deltaMovement));
                    }

                }
        );
    }

    @SubscribeEvent
    public static void onBlastRepulsive(BlastEvent.Repulsive event) {
        doMovementBlast(event, 1);
    }

    @SubscribeEvent
    public static void onBlastAttractive(BlastEvent.Attractive event) {
        doMovementBlast(event, -1);
    }

    @SubscribeEvent
    public static void onBlastThermobaric(BlastEvent.Thermobaric event) {
        doVanillaExplosion(event, 4F * 4.0F);
    }

}