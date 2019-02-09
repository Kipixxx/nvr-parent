package fr.novaria.utils.yaml.node

import spock.lang.Specification

class TextNodeSpec extends Specification {

    void "should get value"() {
        expect:
        node.textValue() == expectedTextValue

        node.asBoolean(defaultBooleanValue) == expectedBooleanValue
        node.asInt(123) == expectedIntValue
        node.asDouble(123D) == expectedDoubleValue
        node.asText('def') == expectedTextValue

        where:
        node                  | defaultBooleanValue || expectedBooleanValue | expectedIntValue | expectedDoubleValue | expectedTextValue
        new TextNode('abc')   | false               || false                | 123              | 123D                | 'abc'

        new TextNode('0')     | false               || false                | 0                | 0D                  | '0'
        new TextNode('1')     | false               || false                | 1                | 1D                  | '1'

        new TextNode('false') | true                || false                | 123              | 123D                | 'false'
        new TextNode('true')  | false               || true                 | 123              | 123D                | 'true'
    }
}
