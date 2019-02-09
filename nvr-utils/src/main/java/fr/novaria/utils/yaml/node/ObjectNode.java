package fr.novaria.utils.yaml.node;

import com.google.common.base.Function;
import fr.novaria.utils.yaml.YamlNode;
import fr.novaria.utils.yaml.YamlUtils;

import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

public abstract class ObjectNode extends ContainerNode {

    public abstract Map<String, Object> mapValues();

    public abstract YamlNode get(String fieldName, YamlNode defaultNode);

    //=========================================================
    // Basic property access
    //=========================================================

    @Override
    public boolean isObject() {
        return true;
    }

    //=========================================================
    // General type coercions
    //=========================================================

    @Override
    public boolean asBoolean(String fieldName, boolean defaultValue) {
        return path(fieldName).asBoolean(defaultValue);
    }

    @Override
    public int asInt(String fieldName, int defaultValue) {
        return path(fieldName).asInt(defaultValue);
    }

    @Override
    public long asLong(String fieldName, long defaultValue) {
        return path(fieldName).asLong(defaultValue);
    }

    @Override
    public BigInteger asBigInteger(String fieldName, BigInteger defaultValue) {
        return path(fieldName).asBigInteger(defaultValue);
    }

    @Override
    public double asDouble(String fieldName, double defaultValue) {
        return path(fieldName).asDouble(defaultValue);
    }

    @Override
    public String asText(String fieldName, String defaultValue) {
        return path(fieldName).asText(defaultValue);
    }

    //=========================================================
    // Object methods
    //=========================================================

    @Override
    public YamlNode get(String fieldName) {
        return get(fieldName, null);
    }

    @Override
    public YamlNode path(String fieldName) {
        return get(fieldName, MissingNode.getInstance());
    }

    @Override
    public Iterator<Map.Entry<String, YamlNode>> fields() {
        final Iterator<Map.Entry<String, Object>> itr = mapValues().entrySet().iterator();
        return new Iterator<Map.Entry<String, YamlNode>>() {
            @Override
            public boolean hasNext() {
                return itr.hasNext();
            }

            @Override
            public Map.Entry<String, YamlNode> next() {
                final Map.Entry<String, Object> next = itr.next();
                final YamlNode value = YamlUtils.toYamlNode(next.getValue());
                return new AbstractMap.SimpleEntry<>(next.getKey(), value);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public <T> Iterator<Map.Entry<String, T>> mapFiels(final Function<YamlNode, ? extends T> function) {
        final Iterator<Map.Entry<String, YamlNode>> fields = fields();
        return new Iterator<Map.Entry<String, T>>() {

            @Override
            public boolean hasNext() {
                return fields.hasNext();
            }

            @Override
            public Map.Entry<String, T> next() {
                final Map.Entry<String, YamlNode> next = fields.next();
                final T mappedValue = function.apply(next.getValue());
                return new AbstractMap.SimpleEntry<>(next.getKey(), mappedValue);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    //=========================================================
    // Standard methods
    //=========================================================

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return true;
        }

        if (!(obj instanceof ObjectNode)) {
            return false;
        }

        return mapValues().equals(((ObjectNode) obj).mapValues());
    }

    @Override
    public int hashCode() {
        return mapValues().hashCode();
    }

    @Override
    public String toString() {
        return mapValues().toString();
    }
}
