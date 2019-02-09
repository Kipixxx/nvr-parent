package fr.novaria.skygrid;

import fr.novaria.skygrid.api.SkyGridApi;
import fr.novaria.skygrid.bukkit.PreventSandFallWithCactusListener;
import fr.novaria.skygrid.bukkit.SkyGridChunkGenerator;
import fr.novaria.skygrid.mapper.ConfigMapper;
import fr.novaria.skygrid.api.SkyGridWorld;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyGrid extends JavaPlugin {

    private SkyGridApi api;

    @Override
    public void onEnable() {
        onEnable(new ConfigMapper().map(getConfig()));
    }

    public void onEnable(SkyGridApi api) {
        this.api = api;

        if (api.isPreventSandFallWithCactus()) {
            getServer().getPluginManager().registerEvents(new PreventSandFallWithCactusListener(), this);
        }
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {

        final SkyGridWorld skyGridWorld = api.getWorld(worldName);
        if (skyGridWorld == null) {
            return null;
        }

        return new SkyGridChunkGenerator(skyGridWorld, api);
    }
}
