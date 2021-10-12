package com.jdawg3636.icbm.common.reg;

import com.jdawg3636.icbm.common.entity.*;
import com.jdawg3636.icbm.ICBMReference;
import com.jdawg3636.icbm.common.blast.event.BlastEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ICBMReference.MODID)
public final class EntityReg {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ICBMReference.MODID);

    // Primed Explosives Registration
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_CONDENSED         = registerPrimedExplosives(BlastEvent.Condensed::new,           ItemReg.EXPLOSIVES_CONDENSED);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_SHRAPNEL          = registerPrimedExplosives(BlastEvent.Shrapnel::new,            ItemReg.EXPLOSIVES_SHRAPNEL);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_INCENDIARY        = registerPrimedExplosives(BlastEvent.Incendiary::new,          ItemReg.EXPLOSIVES_INCENDIARY);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_DEBILITATION      = registerPrimedExplosives(BlastEvent.Debilitation::new,        ItemReg.EXPLOSIVES_DEBILITATION);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_CHEMICAL          = registerPrimedExplosives(BlastEvent.Chemical::new,            ItemReg.EXPLOSIVES_CHEMICAL);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_ANVIL             = registerPrimedExplosives(BlastEvent.Anvil::new,               ItemReg.EXPLOSIVES_ANVIL);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_REPULSIVE         = registerPrimedExplosives(BlastEvent.Repulsive::new,           ItemReg.EXPLOSIVES_REPULSIVE);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_ATTRACTIVE        = registerPrimedExplosives(BlastEvent.Attractive::new,          ItemReg.EXPLOSIVES_ATTRACTIVE);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_NIGHTMARE         = registerPrimedExplosives(BlastEvent.Nightmare::new,           ItemReg.EXPLOSIVES_NIGHTMARE);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_FRAGMENTATION     = registerPrimedExplosives(BlastEvent.Fragmentation::new,       ItemReg.EXPLOSIVES_FRAGMENTATION);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_CONTAGION         = registerPrimedExplosives(BlastEvent.Contagion::new,           ItemReg.EXPLOSIVES_CONTAGION);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_SONIC             = registerPrimedExplosives(BlastEvent.Sonic::new,               ItemReg.EXPLOSIVES_SONIC);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_BREACHING         = registerPrimedExplosives(BlastEvent.Breaching::new,           ItemReg.EXPLOSIVES_BREACHING);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_REJUVENATION      = registerPrimedExplosives(BlastEvent.Rejuvenation::new,        ItemReg.EXPLOSIVES_REJUVENATION);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_THERMOBARIC       = registerPrimedExplosives(BlastEvent.Thermobaric::new,         ItemReg.EXPLOSIVES_THERMOBARIC);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_NUCLEAR           = registerPrimedExplosives(BlastEvent.Nuclear::new,             ItemReg.EXPLOSIVES_NUCLEAR);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_EMP               = registerPrimedExplosives(BlastEvent.Emp::new,                 ItemReg.EXPLOSIVES_EMP);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_EXOTHERMIC        = registerPrimedExplosives(BlastEvent.Exothermic::new,          ItemReg.EXPLOSIVES_EXOTHERMIC);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_ENDOTHERMIC       = registerPrimedExplosives(BlastEvent.Endothermic::new,         ItemReg.EXPLOSIVES_ENDOTHERMIC);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_ANTIGRAVITATIONAL = registerPrimedExplosives(BlastEvent.Antigravitational::new,   ItemReg.EXPLOSIVES_ANTIGRAVITATIONAL);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_ENDER             = registerPrimedExplosives(BlastEvent.Ender::new,               ItemReg.EXPLOSIVES_ENDER);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_HYPERSONIC        = registerPrimedExplosives(BlastEvent.Hypersonic::new,          ItemReg.EXPLOSIVES_HYPERSONIC);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_ANTIMATTER        = registerPrimedExplosives(BlastEvent.Antimatter::new,          ItemReg.EXPLOSIVES_ANTIMATTER);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> EXPLOSIVES_REDMATTER         = registerPrimedExplosives(BlastEvent.Redmatter::new,           ItemReg.EXPLOSIVES_REDMATTER);
    public static final RegistryObject<EntityType<EntityPrimedExplosives>> S_MINE                       = registerPrimedExplosives(BlastEvent.SMine::new,               ItemReg.S_MINE, 0.875F, 0.125F);

    // Missile Registration
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_MODULE            = registerMissile(BlastEvent::new, /*TODO*/             ItemReg.MISSILE_MODULE);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_CONVENTIONAL      = registerMissile(BlastEvent.Condensed::new,            ItemReg.MISSILE_CONVENTIONAL);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_SHRAPNEL          = registerMissile(BlastEvent.Shrapnel::new,             ItemReg.MISSILE_SHRAPNEL);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_INCENDIARY        = registerMissile(BlastEvent.Incendiary::new,           ItemReg.MISSILE_INCENDIARY);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_DEBILITATION      = registerMissile(BlastEvent.Debilitation::new,         ItemReg.MISSILE_DEBILITATION);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_CHEMICAL          = registerMissile(BlastEvent.Chemical::new,             ItemReg.MISSILE_CHEMICAL);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_ANVIL             = registerMissile(BlastEvent.Anvil::new,                ItemReg.MISSILE_ANVIL);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_REPULSIVE         = registerMissile(BlastEvent.Repulsive::new,            ItemReg.MISSILE_REPULSIVE);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_ATTRACTIVE        = registerMissile(BlastEvent.Attractive::new,           ItemReg.MISSILE_ATTRACTIVE);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_NIGHTMARE         = registerMissile(BlastEvent.Nightmare::new,            ItemReg.MISSILE_NIGHTMARE);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_FRAGMENTATION     = registerMissile(BlastEvent.Fragmentation::new,        ItemReg.MISSILE_FRAGMENTATION, 1F, 5F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_CONTAGION         = registerMissile(BlastEvent.Contagion::new,            ItemReg.MISSILE_CONTAGION, 1F, 5F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_SONIC             = registerMissile(BlastEvent.Sonic::new,                ItemReg.MISSILE_SONIC, 1F, 5F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_BREACHING         = registerMissile(BlastEvent.Breaching::new,            ItemReg.MISSILE_BREACHING, 1F, 5F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_REJUVENATION      = registerMissile(BlastEvent.Rejuvenation::new,         ItemReg.MISSILE_REJUVENATION, 1F, 5F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_THERMOBARIC       = registerMissile(BlastEvent.Thermobaric::new,          ItemReg.MISSILE_THERMOBARIC, 1F, 5F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_NUCLEAR           = registerMissile(BlastEvent.Nuclear::new,              ItemReg.MISSILE_NUCLEAR, 1F, 7F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_EMP               = registerMissile(BlastEvent.Emp::new,                  ItemReg.MISSILE_EMP, 1F, 7F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_EXOTHERMIC        = registerMissile(BlastEvent.Exothermic::new,           ItemReg.MISSILE_EXOTHERMIC, 1F, 7F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_ENDOTHERMIC       = registerMissile(BlastEvent.Endothermic::new,          ItemReg.MISSILE_ENDOTHERMIC, 1F, 7F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_ANTIGRAVITATIONAL = registerMissile(BlastEvent.Antigravitational::new,    ItemReg.MISSILE_ANTIGRAVITATIONAL, 1F, 5F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_ENDER             = registerMissile(BlastEvent.Ender::new,                ItemReg.MISSILE_ENDER, 1F, 6F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_HYPERSONIC        = registerMissile(BlastEvent.Hypersonic::new,           ItemReg.MISSILE_HYPERSONIC, 1F, 7F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_ANTIMATTER        = registerMissile(BlastEvent.Antimatter::new,           ItemReg.MISSILE_ANTIMATTER);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_REDMATTER         = registerMissile(BlastEvent.Redmatter::new,            ItemReg.MISSILE_REDMATTER);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_HOMING            = registerMissile(BlastEvent::new, /*TODO*/             ItemReg.MISSILE_HOMING);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_ANTIBALLISTIC     = registerMissile(BlastEvent::new, /*TODO*/             ItemReg.MISSILE_ANTIBALLISTIC, 1F, 5F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_CLUSTER           = registerMissile(BlastEvent::new, /*TODO*/             ItemReg.MISSILE_CLUSTER, 1F, 5F);
    public static final RegistryObject<EntityType<EntityMissile>> MISSILE_CLUSTER_NUCLEAR   = registerMissile(BlastEvent::new, /*TODO*/             ItemReg.MISSILE_CLUSTER_NUCLEAR, 1F, 5F);

    // Blast Utility Entity Registration
    public static final RegistryObject<EntityType<EntityShrapnel>>       SHRAPNEL           = ENTITIES.register("shrapnel", ()->EntityType.Builder.<EntityShrapnel>of(EntityShrapnel::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("shrapnel"));
    public static final RegistryObject<EntityType<EntityLingeringBlast>> BLAST_CHEMICAL     = registerBlastUtilityEntity("blast_chemical",     EntityLingeringBlastChemical::new);
    public static final RegistryObject<EntityType<EntityLingeringBlast>> BLAST_CONTAGION    = registerBlastUtilityEntity("blast_contagion",    EntityLingeringBlastContagion::new);
    public static final RegistryObject<EntityType<EntityLingeringBlast>> BLAST_DEBILITATION = registerBlastUtilityEntity("blast_debilitation", EntityLingeringBlastDebilitation::new);

    public static RegistryObject<EntityType<EntityPrimedExplosives>> registerPrimedExplosives(BlastEvent.BlastEventProvider blastEventProvider, RegistryObject<Item> itemForm) {
        return registerPrimedExplosives(blastEventProvider, itemForm, 0.98F, 0.98F);
    }

    public static RegistryObject<EntityType<EntityPrimedExplosives>> registerPrimedExplosives(BlastEvent.BlastEventProvider blastEventProvider, RegistryObject<Item> itemForm, float width, float height) {
        return ENTITIES.register(
                itemForm.getId().getPath(),
                () -> {
                    return EntityType.Builder.<EntityPrimedExplosives>of(
                            (type, world) -> new EntityPrimedExplosives(type, world, blastEventProvider, itemForm, 0, 0, 0, null),
                            EntityClassification.MISC
                    )
                    .fireImmune()
                    .sized(width, height)
                    .clientTrackingRange(10)
                    .updateInterval(10)
                    .build(itemForm.getId().getPath());
                }
        );
    }

    public static RegistryObject<EntityType<EntityMissile>> registerMissile(BlastEvent.BlastEventProvider blastEventProvider, RegistryObject<Item> itemForm) {
        return registerMissile(blastEventProvider, itemForm, 1F, 4F);
    }

    public static RegistryObject<EntityType<EntityMissile>> registerMissile(BlastEvent.BlastEventProvider blastEventProvider, RegistryObject<Item> itemForm, float width, float height) {
        return ENTITIES.register(
                itemForm.getId().getPath(),
                () -> {
                    return EntityType.Builder.<EntityMissile>of(
                            (type, world) -> {
                                EntityMissile toReturn = new EntityMissile(type, world, blastEventProvider, itemForm);
                                toReturn.setRot(0F, 90F);
                                return toReturn;
                            },
                            EntityClassification.MISC
                    )
                    .fireImmune()
                    .sized(width, height)
                    .clientTrackingRange(10)
                    .updateInterval(2)
                    .build(itemForm.getId().getPath());
                }
        );
    }

    public static <T extends Entity> RegistryObject<EntityType<T>> registerBlastUtilityEntity(String entityName, EntityType.IFactory<T> entityConstructor) {
        return ENTITIES.register(
                entityName,
                ()->EntityType.Builder.<T>of(
                        entityConstructor,
                        EntityClassification.MISC
                ).build(entityName)
        );
    }

}
