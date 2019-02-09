package fr.novaria.skygrid.bukkit;

import com.google.common.collect.ImmutableList;
import fr.novaria.skygrid.api.SkyGridApi;
import fr.novaria.skygrid.api.SkyGridWorld;
import fr.novaria.utils.base.LocationFunction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.List;
import java.util.Random;

import static java.util.Objects.requireNonNull;

public class SkyGridChunkGenerator extends ChunkGenerator {

    private final SkyGridWorld skyGridWorld;

    private final SkyGridApi skyGridApi;

    private final List<BlockPopulator> populators;

    public SkyGridChunkGenerator(SkyGridWorld skyGridWorld, SkyGridApi skyGridApi) {
        this.skyGridWorld = requireNonNull(skyGridWorld);
        this.skyGridApi = requireNonNull(skyGridApi);
        this.populators = ImmutableList.<BlockPopulator>of(new SkyGridBlockPopulator(skyGridWorld));
    }

    @Override
    public ChunkData generateChunkData(World world, final Random random, int x, int z, BiomeGrid biome) {
        final ChunkData chunkData = createChunkData(world);

        skyGridWorld.chunkIteration(new LocationFunction() {
            @Override
            public void apply(int x, int y, int z) {
                final Material material = skyGridWorld.getRandomBlock(random);
                if (material != null) {
                    chunkData.setBlock(x, y, z, material);

                    final Material plant = skyGridApi.getRandomPlant(material, random);
                    if (plant != null && !Material.AIR.equals(plant)) {
                        chunkData.setBlock(x, y + 1, z , plant);
                    }
                }
            }
        });

        return chunkData;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return populators;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0.5D, skyGridWorld.getHighestBlockY() + 1, 0.5D);
    }
}
