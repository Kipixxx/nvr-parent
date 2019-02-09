package fr.novaria.utils.yaml.node

import spock.lang.Specification

class BooleanNodeSpec extends Specification {

    void "should get value"() {
        expect:
        node.booleanValue() == expectedBooleanValue

        node.asBoolean(defaultValue) == expectedBooleanValue
        node.asInt(123) == expectedIntValue
        node.asLong(123) == expectedLongValue
        node.asBigInteger(BigInteger.valueOf(123)) == expectedBigIntegerValue
        node.asDouble(123D) == expectedDoubleValue
        node.asText('abc') == expectedTextValue

        where:
        node              | defaultValue || expectedBooleanValue | expectedIntValue | expectedLongValue | expectedBigIntegerValue | expectedDoubleValue | expectedTextValue
        BooleanNode.FALSE | true         || false                | 0                | 0                 | BigInteger.ZERO         | 0D                  | 'false'
        BooleanNode.TRUE  | false        || true                 | 1                | 1                 | BigInteger.ONE          | 1D                  | 'true'
    }
}
