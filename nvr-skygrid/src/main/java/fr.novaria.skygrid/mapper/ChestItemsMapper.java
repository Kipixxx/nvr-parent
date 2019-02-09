package fr.novaria.skygrid.mapper;

import com.google.common.base.Function;
import fr.novaria.skygrid.api.SkyGridApi;
import fr.novaria.skygrid.api.block.BlockGroup;
import fr.novaria.skygrid.api.chest.ChestItem;
import fr.novaria.skygrid.api.chest.ChestItems;
import fr.novaria.skygrid.api.chest.ChestQuantity;
import fr.novaria.skygrid.api.chest.RandomChestItems;
import fr.novaria.utils.base.StringUtils;
import fr.novaria.utils.yaml.YamlNode;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

import static com.google.common.collect.Iterators.transform;
import static fr.novaria.skygrid.api.SkyGridApi.CONFIG_SPLITTER;
import static fr.novaria.utils.base.StringUtils.asDouble;
import static fr.novaria.utils.base.StringUtils.asInt;
import static java.util.Objects.requireNonNull;

public class ChestItemsMapper {

    private final SkyGridApi api;

    public ChestItemsMapper(SkyGridApi api) {
        this.api = requireNonNull(api);
    }

    public Iterator<Map.Entry<String, RandomChestItems>> mapByName(YamlNode objectNode) {
        return transform(objectNode.fields(), new Function<Map.Entry<String, YamlNode>, Map.Entry<String, RandomChestItems>>() {
            @Override
            public Map.Entry<String, RandomChestItems> apply(Map.Entry<String, YamlNode> input) {
                final RandomChestItems chestItems = new RandomChestItems().addChestItems(mapArray(input.getValue()));
                return new AbstractMap.SimpleEntry<>(input.getKey(), chestItems);
            }
        });
    }

    public Iterator<Map.Entry<ChestItems, Number>> mapArray(YamlNode arrayNode) {
        final int defaultItemCount = api.getDefaultChestItemCount();
        final double defaultWeight = api.getDefaultBlockGroupWeight();

        return transform(arrayNode.iterator(), new Function<YamlNode, Map.Entry<ChestItems, Number>>() {
            @Override
            public Map.Entry<ChestItems, Number> apply(YamlNode input) {
                final String text = input.asText("");
                final Iterator<String> split = CONFIG_SPLITTER.split(text).iterator();
                final String name = split.next();

                final ChestItems chestItems = api.getChestItems(name);
                if (chestItems != null) {
                    final double weight = split.hasNext() ? asDouble(split.next(), defaultWeight) : defaultWeight;
                    return new AbstractMap.SimpleEntry<ChestItems, Number>(chestItems, weight);
                }

                final BlockGroup blockGroup = api.getBlockGroup(name);
                if (blockGroup != null) {
                    final int count = split.hasNext() ? asInt(split.next(), defaultItemCount) : defaultItemCount;
                    final double weight = split.hasNext() ? asDouble(split.next(), defaultWeight) : defaultWeight;

                    final ChestItem chestItem = new ChestItem(blockGroup, count);
                    return new AbstractMap.SimpleEntry<ChestItems, Number>(chestItem, weight);
                }

                throw new IllegalArgumentException("chest item can't be found; " +
                        "text: " + text);
            }
        });
    }

    public Iterator<Map.Entry<ChestQuantity, Number>> mapQuantities(YamlNode arrayNode) {
        return transform(arrayNode.iterator(), new Function<YamlNode, Map.Entry<ChestQuantity, Number>>() {
            @Override
            public Map.Entry<ChestQuantity, Number> apply(YamlNode input) {
                final String text = input.asText("");
                final Iterator<String> split = CONFIG_SPLITTER.split(text).iterator();

                final int quantity = asInt(split.next(), -1);
                final double weight = asDouble(split.next(), -1);
                return new AbstractMap.SimpleEntry<ChestQuantity, Number>(new ChestQuantity(quantity), weight);
            }
        });
    }
}
