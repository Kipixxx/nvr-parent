package fr.novaria.skygrid.api.creature;

import fr.novaria.utils.collect.RandomCollection;
import org.bukkit.entity.EntityType;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class RandomCreatureGroup implements CreatureGroup {

    private final RandomCollection<CreatureGroup> creatureGroups = new RandomCollection<>();

    public RandomCreatureGroup addCreatureGroup(EntityType entityType, double weight) {
        return addCreatureGroup(new EntityTypeCreatureGroup(entityType), weight);
    }

    public RandomCreatureGroup addCreatureGroup(CreatureGroup creatureGroup, double weight) {
        this.creatureGroups.add(creatureGroup, weight);
        return this;
    }

    public RandomCreatureGroup addCreatureGroups(Map<? extends CreatureGroup, ? extends Number> creatureGroups) {
        return addCreatureGroups(creatureGroups.entrySet().iterator());
    }

    public RandomCreatureGroup addCreatureGroups(Iterator<? extends Map.Entry<? extends CreatureGroup, ? extends Number>> creatureGroups) {
        this.creatureGroups.addAll(creatureGroups);
        return this;
    }

    @Override
    public EntityType getRandomCreature(Random random) {
        final CreatureGroup creatureGroup = creatureGroups.get(random);
        return creatureGroup == null ? null : creatureGroup.getRandomCreature(random);
    }

    @Override
    public int hashCode() {
        return creatureGroups.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof RandomCreatureGroup)) {
            return false;
        }

        final RandomCreatureGroup that = (RandomCreatureGroup) obj;
        return creatureGroups.equals(that.creatureGroups);
    }

    @Override
    public String toString() {
        return creatureGroups.toString();
    }
}
