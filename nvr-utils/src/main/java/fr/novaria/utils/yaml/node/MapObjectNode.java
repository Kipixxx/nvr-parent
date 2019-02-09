package fr.novaria.utils.yaml.node;

import fr.novaria.utils.yaml.YamlNode;
import fr.novaria.utils.yaml.YamlUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class MapObjectNode extends ObjectNode {

    private final Map<String, Object> map;

    public MapObjectNode(Map<String, Object> map) {
        this.map = requireNonNull(map);
    }

    @Override
    public Map<String, Object> mapValues() {
        return Collections.unmodifiableMap(map);
    }

    //=========================================================
    // Container methods
    //=========================================================

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    //=========================================================
    // Object methods
    //=========================================================

    @Override
    public YamlNode get(String fieldName, YamlNode defaultNode) {

        if (!map.containsKey(fieldName)) {
            return defaultNode;
        }

        final Object value = map.get(fieldName);
        return YamlUtils.toYamlNode(value);
    }

    @Override
    public boolean has(String fieldName) {
        return map.containsKey(fieldName);
    }

    @Override
    public Iterator<String> fieldNames() {
        return map.keySet().iterator();
    }
}
