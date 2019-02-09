package fr.novaria.utils.yaml.node;

import java.math.BigInteger;

public class IntNode extends NumericNode {

    private final int value;

    public IntNode(int value) {
        this.value = value;
    }

    //=========================================================
    // Basic property access
    //=========================================================

    @Override
    public boolean isInt() { return true; }

    //=========================================================
    // Straight property access
    //=========================================================

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public BigInteger bigIntegerValue() {
        return BigInteger.valueOf(value);
    }

    @Override
    public double doubleValue() {
        return value;
    }

    //=========================================================
    // General type coercions
    //=========================================================

    @Override
    public boolean asBoolean(boolean defaultValue)  {
        return value != 0;
    }

    @Override
    public String asText(String defaultValue) {
        return String.valueOf(value);
    }

    //=========================================================
    // Standard methods
    //=========================================================

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof IntNode)) {
            return false;
        }

        return ((IntNode) obj).value == value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
