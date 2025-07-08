package com.seb.gaiacores.component;

import com.mojang.serialization.Codec;
import com.seb.gaiacores.GaiaCores;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, GaiaCores.MOD_ID);

    public static final RegistryObject<DataComponentType<String>> BLOCK_TRANSLATION_KEY = register("block_translation_key",
            builder -> builder.persistent(Codec.STRING));

    public static final RegistryObject<DataComponentType<CompoundTag>> BLOCK_ENTITY_NBT = register("block_entity_nbt",
            builder -> builder
                    .persistent(CompoundTag.CODEC)
                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG)
    );

    private static <T>RegistryObject<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
