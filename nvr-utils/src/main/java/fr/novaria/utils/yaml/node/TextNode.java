package fr.novaria.utils.yaml.node;

import fr.novaria.utils.base.StringUtils;

import static java.util.Objects.requireNonNull;

public class TextNode extends ValueNode {

    private final String value;

    public TextNode(String value) {
        this.value = requireNonNull(value);
    }

    //=========================================================
    // Basic property access
    //=========================================================

    @Override
    public boolean isTextual() {
        return true;
    }

    //=========================================================
    // Straight property access
    //=========================================================

    @Override
    public String textValue() {
        return value;
    }

    //=========================================================
    // General type coercions
    //=========================================================

    @Override
    public boolean asBoolean(boolean defaultValue)  {
        if ("true".equals(value)) {
            return true;
        }
        else if ("false".equals(value)) {
            return false;
        }
        else {
            return defaultValue;
        }
    }

    @Override
    public int asInt(int defaultValue) {
        return StringUtils.asInt(value, defaultValue);
    }

    @Override
    public double asDouble(double defaultValue) {
        return StringUtils.asDouble(value, defaultValue);
    }

    @Override
    public String asText(String defaultValue) {
        return value;
    }

    //=========================================================
    // Standard methods
    //=========================================================

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof TextNode)) {
            return false;
        }

        return value.equals(((TextNode) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
