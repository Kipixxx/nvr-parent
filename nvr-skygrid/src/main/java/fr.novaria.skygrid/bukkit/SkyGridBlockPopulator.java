package fr.novaria.skygrid.bukkit;

import fr.novaria.skygrid.api.SkyGridWorld;
import fr.novaria.utils.base.LocationFunction;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

public class SkyGridBlockPopulator extends BlockPopulator {

    private final SkyGridWorld skyGridWorld;

    public SkyGridBlockPopulator(SkyGridWorld skyGridWorld) {
        this.skyGridWorld = checkNotNull(skyGridWorld);
    }

    @Override
    public void populate(World world, final Random random, final Chunk chunk) {
        skyGridWorld.chunkIteration(new LocationFunction() {
            @Override
            public void apply(int x, int y, int z) {
                final Block block = chunk.getBlock(x, y, z);
                populate(block, random);
            }
        });
    }

    public void populate(Block block, Random random) {

        if (block == null) {
            return;
        }

        final Material type = block.getType();
        if (Material.CHEST.equals(type)) {
            populateChest(block, random);
        }
        else if (Material.SPAWNER.equals(type)) {
            populateSpawner(block, random);
        }
    }

    public void populateChest(Block block, Random random) {
        final Chest chest = (Chest) block.getState();

        final ItemStack[] itemStacks = skyGridWorld.buildItemStacks(random);
        if (itemStacks == null || itemStacks.length == 0) {
            return;
        }

        chest.getInventory().addItem(itemStacks);
    }

    public void populateSpawner(Block block, Random random) {
        final CreatureSpawner spawner = (CreatureSpawner) block.getState();

        final EntityType creature = skyGridWorld.getRandomCreature(random);
        if (creature == null) {
            return;
        }

        spawner.setSpawnedType(creature);
    }
}
