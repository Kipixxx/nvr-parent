package fr.novaria.skygrid.mapper;

import fr.novaria.skygrid.api.SkyGridApi;
import fr.novaria.utils.yaml.YamlNode;
import fr.novaria.utils.yaml.node.SectionObjectNode;
import org.bukkit.configuration.file.FileConfiguration;

import static fr.novaria.skygrid.api.SkyGridApi.*;
import static fr.novaria.skygrid.api.SkyGridWorld.DEFAULT_WORLD_HEIGHT;

public class ConfigMapper {

    public SkyGridApi map(FileConfiguration fileConfiguration) {
        return map(new SectionObjectNode(fileConfiguration));
    }

    public SkyGridApi map(YamlNode configNode) {
        final SkyGridApi api = new SkyGridApi();

        final YamlNode settingsNode = configNode.path("settings");
        api.setDefaultBlockGroupWeight(settingsNode.asDouble("default-block-group-weight", DEFAULT_BLOCK_GROUP_WEIGHT));
        api.setDefaultChestItemCount(settingsNode.asInt("default-chest-item-count", DEFAULT_CHEST_ITEM_COUNT));
        api.setDefaultCreatureGroupWeight(settingsNode.asDouble("default-creature-group-weight", DEFAULT_CREATURE_GROUP_WEIGHT));
        api.setDefaultWorldHeight(settingsNode.asInt("default-world-height", DEFAULT_WORLD_HEIGHT));
        api.setPreventSandFallWithCactus(settingsNode.asBoolean("prevent-sand-fall-with-cactus", DEFAULT_PREVENT_SAND_FALL_WITH_CACTUS));

        final BlockGroupMapper blockGroupMapper = new BlockGroupMapper(api);
        final ChestItemsMapper chestItemsMapper = new ChestItemsMapper(api);
        final CreatureGroupMapper creatureGroupMapper = new CreatureGroupMapper(api);
        final WorldMapper worldMapper = new WorldMapper(api);

        api.addBlockGroups(blockGroupMapper.mapByName(configNode.path("block-groups")));
        api.addPlants(blockGroupMapper.mapByMaterial(configNode.path("plants")));
        api.addChestItems(chestItemsMapper.mapByName(configNode.path("chest-items")));
        api.addCreatureGroups(creatureGroupMapper.mapByName(configNode.path("creature-groups")));
        api.addWorlds(worldMapper.mapByName(configNode.path("worlds")));
        return api;
    }
}
