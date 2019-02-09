package fr.novaria.skygrid.api.creature;

import org.bukkit.entity.EntityType;

import java.util.Random;

public interface CreatureGroup {

    EntityType getRandomCreature(Random random);
}
