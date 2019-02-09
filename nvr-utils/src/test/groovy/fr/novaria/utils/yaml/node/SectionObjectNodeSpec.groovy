package fr.novaria.utils.yaml.node


import com.google.common.collect.Iterators
import spock.lang.Specification

class SectionObjectNodeSpec extends Specification {

    void "should get size"() {
        given:
        def object = new SectionObjectNode(yaml)

        expect:
        object.isEmpty() == expectedIsEmpty
        object.size() == expectedSize

        where:
        yaml                 || expectedIsEmpty | expectedSize
        ''                   || true            | 0
        '{a: 1, b: 2, c: 3}' || false           | 3
    }

    void "should get values"() {
        given:
        def object = new SectionObjectNode(yaml)

        expect:
        object.has(fieldName) == expectedHas

        object.get(fieldName) == expectedGet
        object.get(fieldName)?.hashCode() == expectedGet?.hashCode()

        object.path(fieldName) == expectedPath
        object.path(fieldName)?.hashCode() == expectedPath?.hashCode()

        where:
        yaml                         || expectedHas | expectedGet                                               | expectedPath
        ''                           || false       | null                                                      | MissingNode.instance
        'field: null'                || false       | null                                                      | MissingNode.instance
        'field: true'                || true        | new BooleanNode(true)                                     | new BooleanNode(true)
        'field: 1'                   || true        | new IntNode(1)                                            | new IntNode(1)
        'field: 2147583647'          || true        | new LongNode(2147583647)                                  | new LongNode(2147583647)
        'field: 9323372036854775807' || true        | new BigIntegerNode(new BigInteger('9323372036854775807')) | new BigIntegerNode(new BigInteger('9323372036854775807'))
        'field: 1.1'                 || true        | new DoubleNode(1.1)                                       | new DoubleNode(1.1)
        'field: text'                || true        | new TextNode('text')                                      | new TextNode('text')
        'field: [1]'                 || true        | new ArrayNode([1])                                        | new ArrayNode([1])
        'field: {a: 1}'              || true        | new SectionObjectNode('{a: 1}')                           | new SectionObjectNode('{a: 1}')
        'field: {a: 1}'              || true        | new MapObjectNode(['a': 1])                               | new MapObjectNode(['a': 1])

        fieldName = 'field'
    }

    void "should list values"() {
        given:
        def object = new SectionObjectNode(yaml)

        expect:
        Iterators.elementsEqual(object.fieldNames(), expectedNodes.keySet().iterator())
        Iterators.elementsEqual(object.fields(), expectedNodes.entrySet().iterator())
        Iterators.elementsEqual(object.mapFiels({ it.intValue() }), expectedValues.entrySet().iterator())

        where:
        yaml = '{a: 1, b: 2, c: 3}'
        expectedNodes = [a: new IntNode(1), b: new IntNode(2), c: new IntNode(3)]
        expectedValues = [a: 1, b: 2, c: 3]
    }
}
