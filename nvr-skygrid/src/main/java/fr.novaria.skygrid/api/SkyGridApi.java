package fr.novaria.skygrid.api;

import com.google.common.base.Splitter;
import fr.novaria.skygrid.api.block.BlockGroup;
import fr.novaria.skygrid.api.block.MaterialBlockGroup;
import fr.novaria.skygrid.api.chest.RandomChestItems;
import fr.novaria.skygrid.api.creature.CreatureGroup;
import fr.novaria.skygrid.api.creature.EntityTypeCreatureGroup;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static fr.novaria.utils.collect.MapUtils.putAll;

public class SkyGridApi {

    public static final Splitter CONFIG_SPLITTER = Splitter.on('/').trimResults();

    public static final double DEFAULT_BLOCK_GROUP_WEIGHT = 100;

    public static final double DEFAULT_CREATURE_GROUP_WEIGHT = 1;

    public static final int DEFAULT_CHEST_ITEM_COUNT = 1;

    public static final boolean DEFAULT_PREVENT_SAND_FALL_WITH_CACTUS = true;

    private double defaultBlockGroupWeight = DEFAULT_BLOCK_GROUP_WEIGHT;

    private double defaultCreatureGroupWeight = DEFAULT_CREATURE_GROUP_WEIGHT;

    private int defaultChestItemCount = DEFAULT_CHEST_ITEM_COUNT;

    private int defaultWorldHeight = SkyGridWorld.DEFAULT_WORLD_HEIGHT;

    private boolean preventSandFallWithCactus = DEFAULT_PREVENT_SAND_FALL_WITH_CACTUS;

    private final Map<String, SkyGridWorld> worlds = new HashMap<>();

    private final Map<String, BlockGroup> blockGroups = new HashMap<>();

    private final Map<String, RandomChestItems> chestItems = new HashMap<>();

    private final Map<Material, BlockGroup> plants = new HashMap<>();

    private final Map<String, CreatureGroup> creatureGroups = new HashMap<>();

    public double getDefaultBlockGroupWeight() {
        return defaultBlockGroupWeight;
    }

    public void setDefaultBlockGroupWeight(double defaultBlockGroupWeight) {
        this.defaultBlockGroupWeight = defaultBlockGroupWeight;
    }

    public double getDefaultCreatureGroupWeight() {
        return defaultCreatureGroupWeight;
    }

    public void setDefaultCreatureGroupWeight(double defaultCreatureGroupWeight) {
        this.defaultCreatureGroupWeight = defaultCreatureGroupWeight;
    }

    public int getDefaultChestItemCount() {
        return defaultChestItemCount;
    }

    public void setDefaultChestItemCount(int defaultChestItemCount) {
        this.defaultChestItemCount = defaultChestItemCount;
    }

    public int getDefaultWorldHeight() {
        return defaultWorldHeight;
    }

    public void setDefaultWorldHeight(int defaultWorldHeight) {
        this.defaultWorldHeight = defaultWorldHeight;
    }

    public boolean isPreventSandFallWithCactus() {
        return preventSandFallWithCactus;
    }

    public void setPreventSandFallWithCactus(boolean preventSandFallWithCactus) {
        this.preventSandFallWithCactus = preventSandFallWithCactus;
    }

    public SkyGridWorld getWorld(String name) {
        return worlds.get(name);
    }

    public SkyGridApi addWorlds(Iterator<? extends Map.Entry<? extends String, ? extends SkyGridWorld>> worlds) {
        putAll(this.worlds, worlds);
        return this;
    }

    public Material getMaterial(String name) {
        return Material.getMaterial(name);
    }

    public BlockGroup getBlockGroup(String name) {

        final BlockGroup blockGroup = blockGroups.get(name);
        if (blockGroup != null) {
            return blockGroup;
        }

        final Material material = getMaterial(name);
        if (material != null) {
            final MaterialBlockGroup materialBlockGroup = new MaterialBlockGroup(material);
            addBlockGroup(name, materialBlockGroup);
            return materialBlockGroup;
        }

        return null;
    }

    public SkyGridApi addBlockGroup(String name, BlockGroup blockGroup) {
        this.blockGroups.put(name, blockGroup);
        return this;
    }

    public SkyGridApi addBlockGroups(Iterator<? extends Map.Entry<? extends String, ? extends BlockGroup>> blockGroups) {
        putAll(this.blockGroups, blockGroups);
        return this;
    }

    public EntityType getEntityType(String name) {
        try {
            return EntityType.valueOf(name);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    public CreatureGroup getCreatureGroup(String name) {

        final CreatureGroup creatureGroup = creatureGroups.get(name);
        if (creatureGroup != null) {
            return creatureGroup;
        }

        final EntityType entityType = getEntityType(name);
        if (entityType != null) {
            final EntityTypeCreatureGroup entityTypeCreatureGroup = new EntityTypeCreatureGroup(entityType);
            addCreatureGroup(name, entityTypeCreatureGroup);
            return entityTypeCreatureGroup;
        }

        return null;
    }

    public SkyGridApi addCreatureGroup(String name, CreatureGroup creatureGroup) {
        this.creatureGroups.put(name, creatureGroup);
        return this;
    }

    public SkyGridApi addCreatureGroups(Iterator<? extends Map.Entry<? extends String, ? extends CreatureGroup>> creatureGroups) {
        putAll(this.creatureGroups, creatureGroups);
        return this;
    }

    public RandomChestItems getChestItems(String name) {
        return chestItems.get(name);
    }

    public SkyGridApi addChestItems(String name, RandomChestItems chestItems) {
        this.chestItems.put(name, chestItems);
        return this;
    }

    public SkyGridApi addChestItems(Iterator<Map.Entry<String, RandomChestItems>> chestItems) {
        putAll(this.chestItems, chestItems);
        return this;
    }

    public BlockGroup getPlants(Material material) {
        return plants.get(material);
    }

    public Material getRandomPlant(Material material, Random random) {
        final BlockGroup plant = getPlants(material);
        return plant == null ? null : plant.getRandomBlock(random);
    }

    public SkyGridApi addPlant(Material material, Material plant) {
        return addPlant(material, new MaterialBlockGroup(plant));
    }

    public SkyGridApi addPlant(Material material, BlockGroup plants) {
        this.plants.put(material, plants);
        return this;
    }

    public SkyGridApi addPlants(Iterator<? extends Map.Entry<? extends Material, ? extends BlockGroup>> plants) {
        putAll(this.plants, plants);
        return this;
    }
}
