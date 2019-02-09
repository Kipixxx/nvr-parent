package fr.novaria.utils.yaml.node;

import java.math.BigInteger;

public class BooleanNode extends ValueNode {

    public final static BooleanNode TRUE = new BooleanNode(true);
    public final static BooleanNode FALSE = new BooleanNode(false);

    public final boolean value;

    protected BooleanNode(boolean value) {
        this.value = value;
    }

    public static BooleanNode valueOf(boolean b) { return b ? TRUE : FALSE; }

    //=========================================================
    // Basic property access
    //=========================================================

    @Override
    public boolean isBoolean() {
        return true;
    }

    //=========================================================
    // Straight property access
    //=========================================================

    @Override
    public boolean booleanValue() {
        return value;
    }

    //=========================================================
    // General type coercions
    //=========================================================

    @Override
    public boolean asBoolean(boolean defaultValue)  {
        return value;
    }

    @Override
    public int asInt(int defaultValue) {
        return value ? 1 : 0;
    }

    @Override
    public long asLong(long defaultValue) {
        return value ? 1L : 0L;
    }

    @Override
    public BigInteger asBigInteger(BigInteger defaultValue) {
        return value ? BigInteger.ONE : BigInteger.ZERO;
    }

    @Override
    public double asDouble(double defaultValue) {
        return value ? 1.0 : 0.0;
    }

    @Override
    public String asText(String defaultValue) {
        return value ? "true" : "false";
    }

    //=========================================================
    // Standard methods
    //=========================================================

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return true;
        }

        if (!(obj instanceof BooleanNode)) {
            return false;
        }

        return ((BooleanNode) obj).value == value;
    }

    @Override
    public int hashCode() {
        return value ? 1231 : 1237;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
