package me.alegian.thavma.impl.init.data.worldgen.tree;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.init.data.worldgen.tree.trunk.GreatwoodTrunkPlacer;
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Optional;

public class GreatwoodTree {
  public static final String NAME = "greatwood";
  public static final ResourceLocation LOCATION = Thavma.INSTANCE.rl("tree_" + GreatwoodTree.NAME);
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE = ResourceKey.create(Registries.CONFIGURED_FEATURE, GreatwoodTree.LOCATION);
  public static final TreeGrower GROWER = new TreeGrower(GreatwoodTree.NAME, Optional.of(GreatwoodTree.CONFIGURED_FEATURE), Optional.empty(), Optional.empty());
  public static final ResourceKey<PlacedFeature> PLACED_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, GreatwoodTree.LOCATION);
  public static final ResourceKey<BiomeModifier> BIOME_MODIFIER = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, GreatwoodTree.LOCATION);

  public static TreeConfiguration.TreeConfigurationBuilder createGreatwood() {
    return new TreeConfiguration.TreeConfigurationBuilder(
        BlockStateProvider.simple(T7Blocks.INSTANCE.getGREATWOOD_LOG().get()),
        new GreatwoodTrunkPlacer(24, 2, 6),
        BlockStateProvider.simple(T7Blocks.INSTANCE.getGREATWOOD_LEAVES().get()),
        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
        new TwoLayersFeatureSize(1, 0, 1)
    );
  }

  public static void registerConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    context.register(
        GreatwoodTree.CONFIGURED_FEATURE,
        new ConfiguredFeature<>(Feature.TREE, GreatwoodTree.createGreatwood().ignoreVines().build())
    );
  }

  public static void registerPlaced(BootstrapContext<PlacedFeature> context) {
    HolderGetter<ConfiguredFeature<?, ?>> otherRegistry = context.lookup(Registries.CONFIGURED_FEATURE);

    context.register(GreatwoodTree.PLACED_FEATURE, new PlacedFeature(
        otherRegistry.getOrThrow(GreatwoodTree.CONFIGURED_FEATURE),
        List.of(
            RarityFilter.onAverageOnceEvery(9),
            InSquarePlacement.spread(),
            SurfaceWaterDepthFilter.forMaxDepth(0),
            PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
            BiomeFilter.biome(),
            PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
        )
    ));
  }

  public static void registerBiomeModifier(BootstrapContext<BiomeModifier> context) {
    HolderGetter<PlacedFeature> placedFeatureRegistry = context.lookup(Registries.PLACED_FEATURE);
    HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);

    context.register(GreatwoodTree.BIOME_MODIFIER,
        new BiomeModifiers.AddFeaturesBiomeModifier(
            biomeRegistry.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatureRegistry.getOrThrow(GreatwoodTree.PLACED_FEATURE)),
            GenerationStep.Decoration.VEGETAL_DECORATION
        )
    );
  }
}
