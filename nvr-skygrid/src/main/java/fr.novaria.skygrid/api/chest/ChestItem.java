package fr.novaria.skygrid.api.chest;

import fr.novaria.skygrid.api.block.BlockGroup;
import fr.novaria.skygrid.api.block.MaterialBlockGroup;
import org.bukkit.Material;

import java.util.AbstractMap;
import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChestItem implements ChestItems {

    private final BlockGroup blockGroup;

    private final int count;

    public ChestItem(Material material, int count) {
        this(new MaterialBlockGroup(material), count);
    }

    public ChestItem(BlockGroup blockGroup, int count) {
        this.blockGroup = checkNotNull(blockGroup);
        this.count = count;
    }

    public Material getRandomBlock(Random random) {
        return blockGroup.getRandomBlock(random);
    }

    public int getRandomCount(Random random) {
        return count <= 1 ? 1 : random.nextInt(count) + 1;
    }

    @Override
    public ChestItem getRandomChestItem(Random random) {
        return this;
    }

    @Override
    public int hashCode() {
        return blockGroup.hashCode() ^ count;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ChestItem)) {
            return false;
        }

        final ChestItem that = (ChestItem) obj;
        return count == that.count &&
                blockGroup.equals(that.blockGroup);
    }

    @Override
    public String toString() {
        return "{" +
                "blockGroup=" + blockGroup + ", " +
                "count=" + count +
                "}";
    }
}
