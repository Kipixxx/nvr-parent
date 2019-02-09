package fr.novaria.skygrid.mapper;

import com.google.common.base.Function;
import fr.novaria.skygrid.api.SkyGridApi;
import fr.novaria.skygrid.api.SkyGridWorld;
import fr.novaria.utils.yaml.YamlNode;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

import static com.google.common.collect.Iterators.transform;

public class WorldMapper {

    private final BlockGroupMapper blockGroupMapper;

    private final CreatureGroupMapper creatureGroupMapper;

    private final ChestItemsMapper chestItemsMapper;

    private final SkyGridApi api;

    public WorldMapper(SkyGridApi api) {
        this.blockGroupMapper = new BlockGroupMapper(api);
        this.creatureGroupMapper = new CreatureGroupMapper(api);
        this.chestItemsMapper = new ChestItemsMapper(api);
        this.api = api;
    }

    public Iterator<Map.Entry<String, SkyGridWorld>> mapByName(YamlNode objectNode) {
        final int defaultHeight = api.getDefaultWorldHeight();

        return transform(objectNode.fields(), new Function<Map.Entry<String, YamlNode>, Map.Entry<String, SkyGridWorld>>() {
            @Override
            public Map.Entry<String, SkyGridWorld> apply(Map.Entry<String, YamlNode> input) {
                final YamlNode worldNode = input.getValue();

                final SkyGridWorld world = new SkyGridWorld();
                world.setHeight(worldNode.asInt("height", defaultHeight));
                world.addBlockGroups(blockGroupMapper.mapArray(worldNode.path("block-groups")));
                world.addChestItems(chestItemsMapper.mapArray(worldNode.path("chest-items")));
                world.addChestQuantities(chestItemsMapper.mapQuantities(worldNode.path("chest-quantities")));
                world.addCreatureGroups(creatureGroupMapper.mapArray(worldNode.path("creature-spawners")));
                return new AbstractMap.SimpleEntry<>(input.getKey(), world);
            }
        });
    }
}
