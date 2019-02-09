package fr.novaria.utils.yaml.node;

import java.math.BigInteger;

import static java.util.Objects.requireNonNull;

public class BigIntegerNode extends NumericNode {

    private final BigInteger value;

    public BigIntegerNode(BigInteger value) {
        this.value = requireNonNull(value);
    }

    //=========================================================
    // Basic property access
    //=========================================================

    @Override
    public boolean isBigInteger() {
        return true;
    }

    //=========================================================
    // Straight property access
    //=========================================================

    @Override
    public int intValue() {
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value.longValue();
    }

    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public BigInteger bigIntegerValue() {
        return value;
    }

    //=========================================================
    // General type coercions
    //=========================================================

    @Override
    public boolean asBoolean(boolean defaultValue)  {
        return !BigInteger.ZERO.equals(value);
    }

    @Override
    public String asText(String defaultValue) {
        return value.toString();
    }

    //=========================================================
    // Standard methods
    //=========================================================

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof BigIntegerNode)) {
            return false;
        }

        return value.equals(((BigIntegerNode) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
