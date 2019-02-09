package fr.novaria.skygrid.api.block;

import org.bukkit.Material;

import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

public class MaterialBlockGroup implements BlockGroup {

    private final Material material;

    public MaterialBlockGroup(Material material) {
        this.material = checkNotNull(material);
    }

    @Override
    public Material getRandomBlock(Random random) {
        return material;
    }

    @Override
    public int hashCode() {
        return material.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof MaterialBlockGroup)) {
            return false;
        }

        final MaterialBlockGroup that = (MaterialBlockGroup) obj;
        return material.equals(that.material);
    }

    @Override
    public String toString() {
        return material.toString();
    }
}
