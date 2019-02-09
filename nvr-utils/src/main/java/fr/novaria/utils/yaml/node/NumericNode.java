package fr.novaria.utils.yaml.node;

import java.math.BigInteger;

public abstract class NumericNode extends ValueNode {

    //=========================================================
    // Straight property access
    //=========================================================

    @Override
    public abstract int intValue();

    @Override
    public abstract long longValue();

    @Override
    public abstract BigInteger bigIntegerValue();

    @Override
    public abstract double doubleValue();

    //=========================================================
    // Straight property access
    //=========================================================

    @Override
    public int asInt(int defaultValue) {
        return intValue();
    }

    @Override
    public long asLong(long defaultValue) {
        return longValue();
    }

    @Override
    public BigInteger asBigInteger(BigInteger defaultValue) {
        return bigIntegerValue();
    }

    @Override
    public double asDouble(double defaultValue) {
        return doubleValue();
    }
}
