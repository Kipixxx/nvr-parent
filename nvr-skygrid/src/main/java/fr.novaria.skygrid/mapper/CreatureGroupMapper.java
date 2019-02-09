package fr.novaria.skygrid.mapper;

import com.google.common.base.Function;
import fr.novaria.skygrid.api.SkyGridApi;
import fr.novaria.skygrid.api.creature.CreatureGroup;
import fr.novaria.skygrid.api.creature.RandomCreatureGroup;
import fr.novaria.utils.yaml.YamlNode;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

import static com.google.common.collect.Iterators.transform;
import static fr.novaria.skygrid.api.SkyGridApi.CONFIG_SPLITTER;
import static fr.novaria.utils.base.StringUtils.asDouble;
import static java.util.Objects.requireNonNull;

public class CreatureGroupMapper {

    private final SkyGridApi api;

    public CreatureGroupMapper(SkyGridApi api) {
        this.api = requireNonNull(api);
    }

    public Iterator<Map.Entry<String, CreatureGroup>> mapByName(YamlNode objectNode) {
        return transform(objectNode.fields(), new Function<Map.Entry<String, YamlNode>, Map.Entry<String, CreatureGroup>>() {
            @Override
            public Map.Entry<String, CreatureGroup> apply(Map.Entry<String, YamlNode> input) {
                final CreatureGroup creatureGroup = new RandomCreatureGroup().addCreatureGroups(mapArray(input.getValue()));
                return new AbstractMap.SimpleEntry<>(input.getKey(), creatureGroup);
            }
        });
    }

    public Iterator<Map.Entry<CreatureGroup, Double>> mapArray(YamlNode arrayNode) {
        final double defaultWeight = api.getDefaultCreatureGroupWeight();

        return transform(arrayNode.iterator(), new Function<YamlNode, Map.Entry<CreatureGroup, Double>>() {
            @Override
            public Map.Entry<CreatureGroup, Double> apply(YamlNode input) {
                final String text = input.asText("");
                final Iterator<String> split = CONFIG_SPLITTER.split(text).iterator();

                final CreatureGroup creatureGroup = api.getCreatureGroup(split.next());
                if (creatureGroup == null) {
                    throw new IllegalArgumentException("creature group can't be found; " +
                            "text: " + text);
                }

                final double weight = split.hasNext() ? asDouble(split.next(), defaultWeight) : defaultWeight;
                return new AbstractMap.SimpleEntry<>(creatureGroup, weight);
            }
        });
    }
}
