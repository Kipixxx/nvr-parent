package fr.novaria.skygrid.api.block;

import org.bukkit.Material;

import java.util.Map;
import java.util.Random;

public interface BlockGroup {

    Material getRandomBlock(Random random);
}
