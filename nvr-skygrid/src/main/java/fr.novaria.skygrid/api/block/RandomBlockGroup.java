package fr.novaria.skygrid.api.block;

import fr.novaria.utils.collect.RandomCollection;
import org.bukkit.Material;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class RandomBlockGroup implements BlockGroup {

    private final RandomCollection<BlockGroup> blockGroups = new RandomCollection<>();

    @Override
    public Material getRandomBlock(Random random) {
        final BlockGroup blockGroup = blockGroups.get(random);
        return blockGroup == null ? null : blockGroup.getRandomBlock(random);
    }

    public RandomBlockGroup addBlockGroup(Material material, double weight) {
        return addBlockGroup(new MaterialBlockGroup(material), weight);
    }

    public RandomBlockGroup addBlockGroup(BlockGroup blockGroup, double weight) {
        blockGroups.add(blockGroup, weight);
        return this;
    }

    public RandomBlockGroup addBlockGroups(Map<? extends BlockGroup, ? extends Number> blockGroups) {
        return addBlockGroups(blockGroups.entrySet().iterator());
    }

    public RandomBlockGroup addBlockGroups(Iterator<? extends Map.Entry<? extends BlockGroup, ? extends Number>> blockGroups) {
        this.blockGroups.addAll(blockGroups);
        return this;
    }

    @Override
    public int hashCode() {
        return blockGroups.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof RandomBlockGroup)) {
            return false;
        }

        final RandomBlockGroup that = (RandomBlockGroup) obj;
        return blockGroups.equals(that.blockGroups);
    }

    @Override
    public String toString() {
        return blockGroups.toString();
    }
}
