package fr.novaria.utils.yaml.node;

import fr.novaria.utils.yaml.YamlNode;
import fr.novaria.utils.yaml.YamlUtils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Iterator;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class SectionObjectNode extends ObjectNode {

    private final ConfigurationSection section;

    public SectionObjectNode(String str) {
        this(YamlUtils.readConfig(str));
    }

    public SectionObjectNode(ConfigurationSection section) {
        this.section = requireNonNull(section);
    }

    @Override
    public Map<String, Object> mapValues() {
        return section.getValues(false);
    }

    //=========================================================
    // Container methods
    //=========================================================

    @Override
    public int size() {
        return section.getKeys(false).size();
    }

    //=========================================================
    // Object methods
    //=========================================================

    @Override
    public YamlNode get(String fieldName, YamlNode defaultNode) {
        final Object value = section.get(fieldName);
        return value == null ? defaultNode : YamlUtils.toYamlNode(value);
    }

    @Override
    public boolean has(String fieldName) {
        return section.contains(fieldName);
    }

    @Override
    public Iterator<String> fieldNames() {
        return section.getKeys(false).iterator();
    }
}
