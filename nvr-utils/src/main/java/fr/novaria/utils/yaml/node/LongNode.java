package fr.novaria.utils.yaml.node;

import com.google.common.primitives.Longs;

import java.math.BigInteger;

public class LongNode extends NumericNode {

    private final long value;

    public LongNode(long value) {
        this.value = value;
    }

    //=========================================================
    // Basic property access
    //=========================================================

    @Override
    public boolean isLong() {
        return true;
    }

    //=========================================================
    // Straight property access
    //=========================================================

    @Override
    public int intValue() {
        return (int) value;
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
    public boolean asBoolean(boolean defaultValue) {
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

        if (!(obj instanceof LongNode)) {
            return false;
        }

        return ((LongNode) obj).value == value;
    }

    @Override
    public int hashCode() {
        return Longs.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
