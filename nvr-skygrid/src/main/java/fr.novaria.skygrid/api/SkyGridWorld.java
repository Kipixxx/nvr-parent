package fr.novaria.skygrid.api;

import fr.novaria.skygrid.api.block.BlockGroup;
import fr.novaria.skygrid.api.block.RandomBlockGroup;
import fr.novaria.skygrid.api.chest.ChestItem;
import fr.novaria.skygrid.api.chest.ChestItems;
import fr.novaria.skygrid.api.chest.ChestQuantity;
import fr.novaria.skygrid.api.chest.RandomChestItems;
import fr.novaria.skygrid.api.creature.CreatureGroup;
import fr.novaria.skygrid.api.creature.EntityTypeCreatureGroup;
import fr.novaria.skygrid.api.creature.RandomCreatureGroup;
import fr.novaria.utils.MinecraftUtils;
import fr.novaria.utils.base.LocationFunction;
import fr.novaria.utils.collect.RandomCollection;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SkyGridWorld {

    public static final int BLOCK_SPACE = 4;

    public static final int DEFAULT_WORLD_HEIGHT = 128;

    public static final ItemStack[] EMPTY_ITEM_STACK_ARRAY = new ItemStack[0];

    private int height;

    private final RandomBlockGroup blockGroups = new RandomBlockGroup();

    private final RandomChestItems chestItems = new RandomChestItems();

    private final RandomCollection<ChestQuantity> chestQuantities = new RandomCollection<>();

    private final RandomCreatureGroup creatureGroups = new RandomCreatureGroup();

    public SkyGridWorld() {
        this(DEFAULT_WORLD_HEIGHT);
    }

    public SkyGridWorld(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHighestBlockY() {
        final int heightIndex = height - 1;
        final int rest = heightIndex % BLOCK_SPACE;
        return heightIndex - rest;
    }

    public Material getRandomBlock(Random random) {
        return blockGroups.getRandomBlock(random);
    }

    public SkyGridWorld addBlockGroup(Material material, double weight) {
        this.blockGroups.addBlockGroup(material, weight);
        return this;
    }

    public SkyGridWorld addBlockGroup(BlockGroup blockGroup, double weight) {
        this.blockGroups.addBlockGroup(blockGroup, weight);
        return this;
    }

    public SkyGridWorld addBlockGroups(Iterator<? extends Map.Entry<? extends BlockGroup, ? extends Number>> blockGroups) {
        this.blockGroups.addBlockGroups(blockGroups);
        return this;
    }

    public ChestItem getRandomChestItem(Random random) {
        final ChestItems chestItems = this.chestItems.getRandomChestItem(random);
        return chestItems == null ? null : chestItems.getRandomChestItem(random);
    }

    public SkyGridWorld addChestItems(Material material, int count, double weight) {
        this.chestItems.addChestItem(material, count, weight);
        return this;
    }

    public SkyGridWorld addChestItems(ChestItems chestItems, double weight) {
        this.chestItems.addChestItem(chestItems, weight);
        return this;
    }

    public SkyGridWorld addChestItems(Iterator<? extends Map.Entry<? extends ChestItems, ? extends Number>> chestItems) {
        this.chestItems.addChestItems(chestItems);
        return this;
    }

    public int getRandomChestQuantity(Random random) {
        final ChestQuantity chestQuantity = this.chestQuantities.get(random);
        return chestQuantity == null ? 0 : chestQuantity.getRandomCount(random);
    }

    public SkyGridWorld addChestQuantities(Iterator<? extends Map.Entry<? extends ChestQuantity, ? extends Number>> quantities) {
        this.chestQuantities.addAll(quantities);
        return this;
    }

    public SkyGridWorld addChestQuantity(int quantity, double weight) {
        this.chestQuantities.add(new ChestQuantity(quantity), weight);
        return this;
    }

    public EntityType getRandomCreature(Random random) {
        return creatureGroups.getRandomCreature(random);
    }

    public SkyGridWorld addCreatureGroup(EntityType creature, double weight) {
        return addCreatureGroup(new EntityTypeCreatureGroup(creature), weight);
    }

    public SkyGridWorld addCreatureGroup(CreatureGroup creatureGroup, double weight) {
        this.creatureGroups.addCreatureGroup(creatureGroup, weight);
        return this;
    }

    public SkyGridWorld addCreatureGroups(Iterator<? extends Map.Entry<? extends CreatureGroup, ? extends Number>> creatureGroups) {
        this.creatureGroups.addCreatureGroups(creatureGroups);
        return this;
    }

    public void chunkIteration(LocationFunction function) {
        final int highestBlockY = getHighestBlockY();
        chunkIteration(BLOCK_SPACE, highestBlockY, function);
    }

    public void chunkIteration(int step, int maxY, LocationFunction function) {
        for (int y = 0; y <= maxY; y += step) {
            for (int x = 0; x < MinecraftUtils.CHUNK_SIZE; x += step) {
                for (int z = 0; z < MinecraftUtils.CHUNK_SIZE; z += step) {
                    function.apply(x, y, z);
                }
            }
        }
    }

    public ItemStack[] buildItemStacks(Random random) {

        final int quantity = getRandomChestQuantity(random);
        if (quantity == 0) {
            return EMPTY_ITEM_STACK_ARRAY;
        }

        final List<ItemStack> itemStacks = new ArrayList<>(quantity);

        for (int i = 0; i < quantity; i++) {

            final ChestItem chestItem = getRandomChestItem(random);
            if (chestItem == null) {
                continue;
            }

            final Material material = chestItem.getRandomBlock(random);
            if (material == null) {
                continue;
            }

            final int count = chestItem.getRandomCount(random);
            itemStacks.add(new ItemStack(material, count));
        }

        return itemStacks.isEmpty() ? EMPTY_ITEM_STACK_ARRAY : itemStacks.toArray(new ItemStack[0]);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, blockGroups, chestItems, chestQuantities, creatureGroups);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof SkyGridWorld)) {
            return false;
        }

        final SkyGridWorld that = (SkyGridWorld) obj;
        return height == that.height &&
                blockGroups.equals(that.blockGroups) &&
                chestItems.equals(that.chestItems) &&
                chestQuantities.equals(that.chestQuantities) &&
                creatureGroups.equals(that.creatureGroups);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
