package fr.novaria.utils.yaml;

import com.google.common.base.Function;
import fr.novaria.utils.yaml.node.MissingNode;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public abstract class YamlNode implements Iterable<YamlNode> {

    //=========================================================
    // Basic property access
    //=========================================================

    public boolean isNull() {
        return false;
    }

    public boolean isMissing() {
        return false;
    }

    public boolean isBoolean() {
        return false;
    }

    public boolean isInt() {
        return false;
    }

    public boolean isLong() {
        return false;
    }

    public boolean isDouble() {
        return false;
    }

    public boolean isBigInteger() {
        return false;
    }

    public boolean isFloat() {
        return false;
    }

    public boolean isTextual() {
        return false;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isObject() {
        return false;
    }

    //=========================================================
    // Straight property access
    //=========================================================

    public boolean booleanValue() {
        return false;
    }

    public int intValue() {
        return 0;
    }

    public long longValue() {
        return 0;
    }

    public BigInteger bigIntegerValue() {
        return BigInteger.ZERO;
    }

    public double doubleValue() {
        return 0.0;
    }

    public String textValue() {
        return null;
    }

    //=========================================================
    // General type coercions
    //=========================================================

    public boolean asBoolean(boolean defaultValue)  {
        return defaultValue;
    }

    public boolean asBoolean(String fieldName, boolean defaultValue) {
        return defaultValue;
    }

    public int asInt(int defaultValue) {
        return defaultValue;
    }

    public int asInt(String fieldName, int defaultValue) {
        return defaultValue;
    }

    public long asLong(long defaultValue) {
        return defaultValue;
    }

    public long asLong(String fieldName, long defaultValue) {
        return defaultValue;
    }

    public BigInteger asBigInteger(BigInteger defaultValue) {
        return defaultValue;
    }

    public BigInteger asBigInteger(String fieldName, BigInteger defaultValue) {
        return defaultValue;
    }

    public double asDouble(double defaultValue) {
        return defaultValue;
    }

    public double asDouble(String fieldName, double defaultValue) {
        return defaultValue;
    }

    public String asText(String defaultValue) {
        return defaultValue;
    }

    public String asText(String fieldName, String defaultValue) {
        return defaultValue;
    }

    //=========================================================
    // Container methods
    //=========================================================

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    //=========================================================
    // Array methods
    //=========================================================

    public boolean has(int index) {
        return get(index) != null;
    }

    public YamlNode get(int index) {
        return null;
    }

    public YamlNode path(int index) {
        return MissingNode.getInstance();
    }

    @Override
    public Iterator<YamlNode> iterator() {
        return Collections.emptyIterator();
    }

    public <T> Iterator<T> mapElements(Function<YamlNode, ? extends T> function) {
        return Collections.emptyIterator();
    }

    //=========================================================
    // Object methods
    //=========================================================

    public YamlNode get(String fieldName) {
        return null;
    }

    public YamlNode path(String fieldName) {
        return MissingNode.getInstance();
    }

    public boolean has(String fieldName) {
        return get(fieldName) != null;
    }

    public Iterator<String> fieldNames() {
        return Collections.emptyIterator();
    }

    public Iterator<Map.Entry<String, YamlNode>> fields() {
        return Collections.emptyIterator();
    }

    public <T> Iterator<Map.Entry<String, T>> mapFiels(final Function<YamlNode, ? extends T> function) {
        return Collections.emptyIterator();
    }
}
