package com.seb.gaiacores;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = GaiaCores.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static boolean lucentCoreGenEnabled = true;
    public static boolean verdantCoreGenEnabled = true;
    public static boolean volcanicCoreGenEnabled = true;
    public static boolean charredCoreGenEnabled = true;
    public static boolean adamantCoreGenEnabled = true;

    private static final ForgeConfigSpec.BooleanValue GENERATE_LUCENT_CORES = BUILDER
            .comment("Include Lucent Gaia Cores in World Generation")
            .define("generateLucentCores", true);
    private static final ForgeConfigSpec.BooleanValue GENERATE_VERDANT_CORES = BUILDER
            .comment("Include Verdant Gaia Cores in World Generation")
            .define("generateVerdantCores", true);
    private static final ForgeConfigSpec.BooleanValue GENERATE_VOLCANIC_CORES = BUILDER
            .comment("Include Volcanic Gaia Cores in World Generation")
            .define("generateVolcanicCores", true);
    private static final ForgeConfigSpec.BooleanValue GENERATE_CHARRED_CORES = BUILDER
            .comment("Include Charred Gaia Cores in World Generation")
            .define("generateCharredCores", true);
    private static final ForgeConfigSpec.BooleanValue GENERATE_ADAMANT_CORES = BUILDER
            .comment("Include Adamant Gaia Cores in World Generation")
            .define("generateAdamantCores", true);

    private static final ForgeConfigSpec.IntValue LUCENT_CORE_COOLDOWN = BUILDER
            .comment("Lucent Gaia Core cooldown (ticks)")
            .defineInRange("lucentCoreCooldown", 100, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue VERDANT_CORE_COOLDOWN = BUILDER
            .comment("Verdant Gaia Core cooldown (ticks)")
            .defineInRange("verdantCoreCooldown", 200, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue VOLCANIC_CORE_COOLDOWN = BUILDER
            .comment("Volcanic Gaia Core cooldown (ticks)")
            .defineInRange("volcanicCoreCooldown", 20, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue CHARRED_CORE_COOLDOWN = BUILDER
            .comment("Charred Gaia Core cooldown (ticks)")
            .defineInRange("charredCoreCooldown", 100, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue ADAMANT_CORE_COOLDOWN = BUILDER
            .comment("Adamant Gaia Core cooldown (ticks)")
            .defineInRange("adamantCoreCooldown", 100, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ORE_FREQUENCIES_RAW;

    public static final ForgeConfigSpec SPEC;
    static {
        List<String> defaultFrequencies = List.of(
                "minecraft:coal_ore=0",
                "minecraft:iron_ore=50",
                "minecraft:copper_ore=50",
                "minecraft:gold_ore=10",
                "minecraft:redstone_ore=10",
                "minecraft:lapis_ore=10",
                "minecraft:diamond_ore=2",
                "minecraft:emerald_ore=1",
                "minecraft:nether_quartz_ore=5"
        );

        ORE_FREQUENCIES_RAW = BUILDER
                .comment("Lucent Gaia Core ore list and frequency (namespace:block=frequency)")
                .defineListAllowEmpty("oresAndFrequencies", defaultFrequencies, obj -> obj instanceof String);

        SPEC = BUILDER.build();
    }

    private static Map<String, Integer> validatedOreFrequencies = new HashMap<>();

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (!event.getConfig().getSpec().equals(SPEC)) return;

        lucentCoreGenEnabled = GENERATE_LUCENT_CORES.get();
        verdantCoreGenEnabled = GENERATE_VERDANT_CORES.get();
        volcanicCoreGenEnabled = GENERATE_VOLCANIC_CORES.get();
        charredCoreGenEnabled = GENERATE_CHARRED_CORES.get();
        adamantCoreGenEnabled = GENERATE_ADAMANT_CORES.get();

        parseOreFrequencies();
    }

    private static void parseOreFrequencies() {
        validatedOreFrequencies = ORE_FREQUENCIES_RAW.get().stream()
                .map(entry -> entry.split("=", 2))
                .filter(parts -> parts.length == 2)
                .collect(Collectors.toMap(
                        parts -> parts[0],
                        parts -> {
                            try {
                                return Integer.parseInt(parts[1]);
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        }
                ));

        validatedOreFrequencies = validatedOreFrequencies.entrySet().stream()
                .filter(e -> {
                    ResourceLocation key = ResourceLocation.tryParse(e.getKey());
                    return key != null && ForgeRegistries.BLOCKS.containsKey(key);
                })
                .filter(e -> e.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static int getLucentCoreCooldown() {
        return LUCENT_CORE_COOLDOWN.get();
    }
    public static int getVerdantCoreCooldown() {
        return VERDANT_CORE_COOLDOWN.get();
    }
    public static int getVolcanicCoreCooldown() {
        return VOLCANIC_CORE_COOLDOWN.get();
    }
    public static int getCharredCoreCooldown() {
        return CHARRED_CORE_COOLDOWN.get();
    }
    public static int getAdamantCoreCooldown() {
        return ADAMANT_CORE_COOLDOWN.get();
    }

    public static Map<String, Integer> getOreFrequencies() {
        return validatedOreFrequencies;
    }
}
