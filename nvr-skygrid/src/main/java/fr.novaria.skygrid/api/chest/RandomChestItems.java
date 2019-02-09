package fr.novaria.skygrid.api.chest;

import fr.novaria.skygrid.api.block.BlockGroup;
import fr.novaria.utils.collect.RandomCollection;
import org.bukkit.Material;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class RandomChestItems implements ChestItems {

    private final RandomCollection<ChestItems> chestItems = new RandomCollection<>();

    public RandomChestItems addChestItem(Material material, int count, double weight) {
        return addChestItem(new ChestItem(material, count), weight);
    }

    public RandomChestItems addChestItem(BlockGroup blockGroup, int count, double weight) {
        return addChestItem(new ChestItem(blockGroup, count), weight);
    }

    public RandomChestItems addChestItem(ChestItems chestItem, double weight) {
        this.chestItems.add(chestItem, weight);
        return this;
    }

    public RandomChestItems addChestItems(Map<? extends ChestItems, ? extends Number> chestItems) {
        return addChestItems(chestItems.entrySet().iterator());
    }

    public RandomChestItems addChestItems(Iterator<? extends Map.Entry<? extends ChestItems, ? extends Number>> chestItems) {
        this.chestItems.addAll(chestItems);
        return this;
    }

    @Override
    public ChestItem getRandomChestItem(Random random) {
        final ChestItems chestItems = this.chestItems.get(random);
        return chestItems == null ? null : chestItems.getRandomChestItem(random);
    }

    @Override
    public int hashCode() {
        return chestItems.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof RandomChestItems)) {
            return false;
        }

        final RandomChestItems that = (RandomChestItems) obj;
        return chestItems.equals(that.chestItems);
    }

    @Override
    public String toString() {
        return chestItems.toString();
    }
}
