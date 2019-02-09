package fr.novaria.utils.yaml;

import com.google.common.io.CharStreams;
import fr.novaria.utils.yaml.node.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class YamlUtils {

    public static SectionObjectNode read(InputStream input) throws IOException {
        return read(CharStreams.toString(new InputStreamReader(input, StandardCharsets.UTF_8)));
    }

    public static SectionObjectNode read(String str) {
        final YamlConfiguration config = readConfig(str);
        return new SectionObjectNode(config);
    }

    public static YamlConfiguration readConfig(String str) {
        try {
            final YamlConfiguration config = new YamlConfiguration();
            config.loadFromString(str);
            return config;
        }
        catch (InvalidConfigurationException e) {
            throw new IllegalArgumentException(str);
        }
    }

    public static YamlNode toYamlNode(Object obj) {
        if (obj == null) {
            return NullNode.getInstance();
        }
        if (obj instanceof Boolean) {
            return BooleanNode.valueOf((Boolean) obj);
        }
        else if (obj instanceof Integer) {
            return new IntNode((Integer) obj);
        }
        else if (obj instanceof Long) {
            return new LongNode((Long) obj);
        }
        else if (obj instanceof BigInteger) {
            return new BigIntegerNode((BigInteger) obj);
        }
        else if (obj instanceof Double) {
            return new DoubleNode((Double) obj);
        }
        else if (obj instanceof String) {
            return new TextNode((String) obj);
        }
        else if (obj instanceof List) {
            return new ArrayNode((List) obj);
        }
        else if (obj instanceof ConfigurationSection) {
            return new SectionObjectNode((ConfigurationSection) obj);
        }
        else if (obj instanceof Map) {
            return new MapObjectNode((Map) obj);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    private YamlUtils() { /* Cannot be instantiated */ }
}
