package fr.novaria.utils.yaml.node

import spock.lang.Specification

class BigIntegerNodeSpec extends Specification {

    void "should get value"() {
        expect:
        node.intValue() == expectedIntValue

        node.asBoolean(false) == expectedBooleanValue
        node.asInt(defaultValue) == expectedIntValue
        node.asLong(defaultValue) == expectedLongValue
        node.asBigInteger(BigInteger.valueOf(defaultValue)) == expectedBigIntegerValue
        node.asDouble(defaultValue) == expectedDoubleValue
        node.asText('abc') == expectedTextValue

        where:
        node                                        | defaultValue || expectedBooleanValue | expectedIntValue | expectedLongValue | expectedBigIntegerValue | expectedDoubleValue | expectedTextValue
        new BigIntegerNode(BigInteger.ZERO)         | 123          || false                | 0                | 0                 | BigInteger.valueOf(0)   | 0D                  | '0'
        new BigIntegerNode(BigInteger.valueOf(123)) | 0            || true                 | 123              | 123               | BigInteger.valueOf(123) | 123D                | '123'
    }
}
