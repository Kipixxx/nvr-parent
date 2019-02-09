package fr.novaria.skygrid.api.creature;

import org.bukkit.entity.EntityType;

import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

public class EntityTypeCreatureGroup implements CreatureGroup {

    private final EntityType entityType;

    public EntityTypeCreatureGroup(EntityType entityType) {
        this.entityType = checkNotNull(entityType);
    }

    @Override
    public EntityType getRandomCreature(Random random) {
        return entityType;
    }

    @Override
    public int hashCode() {
        return entityType.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof EntityTypeCreatureGroup)) {
            return false;
        }

        final EntityTypeCreatureGroup that = (EntityTypeCreatureGroup) obj;
        return entityType == that.entityType;
    }

    @Override
    public String toString() {
        return entityType.toString();
    }
}
