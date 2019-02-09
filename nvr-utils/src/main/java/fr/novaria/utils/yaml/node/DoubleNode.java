package fr.novaria.utils.yaml.node;

import com.google.common.primitives.Doubles;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DoubleNode extends NumericNode {

    private final double value;

    public DoubleNode(double value) {
        this.value = value;
    }

    //=========================================================
    // Basic property access
    //=========================================================

    @Override
    public boolean isDouble() {
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
        return (long) value;
    }

    @Override
    public BigInteger bigIntegerValue() {
        return bigDecimalValue().toBigInteger();
    }

    @Override
    public double doubleValue() {
        return value;
    }

    public BigDecimal bigDecimalValue() {
        return BigDecimal.valueOf(value);
    }

    //=========================================================
    // General type coercions
    //=========================================================

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

        if (!(obj instanceof DoubleNode)) {
            return false;
        }

        return ((DoubleNode) obj).value == value;
    }

    @Override
    public int hashCode() {
        return Doubles.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
