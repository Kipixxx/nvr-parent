package fr.novaria.skygrid.mapper;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import fr.novaria.skygrid.api.SkyGridApi;
import fr.novaria.skygrid.api.block.BlockGroup;
import fr.novaria.skygrid.api.block.RandomBlockGroup;
import fr.novaria.utils.yaml.YamlNode;
import org.bukkit.Material;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

import static com.google.common.collect.Iterators.transform;
import static fr.novaria.skygrid.api.SkyGridApi.CONFIG_SPLITTER;
import static fr.novaria.utils.base.StringUtils.asDouble;
import static java.util.Objects.requireNonNull;

public class BlockGroupMapper {

    private final SkyGridApi api;

    public BlockGroupMapper(SkyGridApi api) {
        this.api = requireNonNull(api);
    }

    public Iterator<Map.Entry<Material, BlockGroup>> mapByMaterial(YamlNode objectNode) {
        return transform(mapByName(objectNode), new Function<Map.Entry<String, BlockGroup>, Map.Entry<Material, BlockGroup>>() {
            @Override
            public Map.Entry<Material, BlockGroup> apply(Map.Entry<String, BlockGroup> input) {
                final String name = input.getKey();

                final Material material = api.getMaterial(name);
                if (material == null) {
                    throw new IllegalArgumentException("material can't be found; " +
                            "name: " + name);
                }

                return new AbstractMap.SimpleEntry<>(material, input.getValue());
            }
        });
    }

    public Iterator<Map.Entry<String, BlockGroup>> mapByName(YamlNode objectNode) {
        return transform(objectNode.fields(), new Function<Map.Entry<String, YamlNode>, Map.Entry<String, BlockGroup>>() {
            @Override
            public Map.Entry<String, BlockGroup> apply(Map.Entry<String, YamlNode> input) {
                final BlockGroup blockGroup = new RandomBlockGroup().addBlockGroups(mapArray(input.getValue()));
                return new AbstractMap.SimpleEntry<>(input.getKey(), blockGroup);
            }
        });
    }

    public Iterator<Map.Entry<BlockGroup, Double>> mapArray(YamlNode arrayNode) {
        final double defaultWeight = api.getDefaultBlockGroupWeight();

        return transform(arrayNode.iterator(), new Function<YamlNode, Map.Entry<BlockGroup, Double>>() {
            @Override
            public Map.Entry<BlockGroup, Double> apply(YamlNode textNode) {
                final String text = textNode.asText("");
                final Iterator<String> split = CONFIG_SPLITTER.split(text).iterator();

                final BlockGroup blockGroup = api.getBlockGroup(split.next());
                if (blockGroup == null) {
                    throw new IllegalArgumentException("block group can't be found; " +
                            "text: " + text);
                }

                final double weight = split.hasNext() ? asDouble(split.next(), defaultWeight) : defaultWeight;
                return new AbstractMap.SimpleEntry<>(blockGroup, weight);
            }
        });
    }
}
