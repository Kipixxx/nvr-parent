package fr.novaria.utils.yaml.node

import com.google.common.collect.Iterators
import spock.lang.Specification

class ArrayNodeSpec extends Specification {

    void "should get size"() {
        given:
        def array = new SectionObjectNode(yaml).get(fieldName) as ArrayNode

        expect:
        array.isEmpty() == expectedIsEmpty
        array.size() == expectedSize

        where:
        yaml               || expectedIsEmpty | expectedSize
        'field: []'        || true            | 0
        'field: [1, 2, 3]' || false           | 3

        fieldName = 'field'
    }

    void "should get values"() {
        given:
        def array = new SectionObjectNode(yaml).get(fieldName) as ArrayNode

        expect:
        array.has(index) == expectedHas

        array.get(index) == expectedGet
        array.get(index)?.hashCode() == expectedGet?.hashCode()

        array.path(index) == expectedPath
        array.path(index)?.hashCode() == expectedPath?.hashCode()

        where:
        yaml                           || expectedHas | expectedGet                                               | expectedPath
        'field: []'                    || false       | null                                                      | MissingNode.instance
        'field: [null]'                || true        | NullNode.instance                                         | NullNode.instance
        'field: [true]'                || true        | new BooleanNode(true)                                     | new BooleanNode(true)
        'field: [1]'                   || true        | new IntNode(1)                                            | new IntNode(1)
        'field: [2147583647]'          || true        | new LongNode(2147583647)                                  | new LongNode(2147583647)
        'field: [9323372036854775807]' || true        | new BigIntegerNode(new BigInteger('9323372036854775807')) | new BigIntegerNode(new BigInteger('9323372036854775807'))
        'field: [1.1]'                 || true        | new DoubleNode(1.1)                                       | new DoubleNode(1.1)
        'field: [text]'                || true        | new TextNode('text')                                      | new TextNode('text')
        'field: [[1]]'                 || true        | new ArrayNode([1])                                        | new ArrayNode([1])
        'field: [{a: 1}]'              || true        | new SectionObjectNode('{a: 1}')                           | new SectionObjectNode('{a: 1}')
        'field: [{a: 1}]'              || true        | new MapObjectNode(['a': 1])                               | new MapObjectNode(['a': 1])

        fieldName = 'field'
        index = 0
    }

    void "should list fields"() {
        given:
        def array = new SectionObjectNode(yaml).get(fieldName) as ArrayNode

        expect:
        Iterators.elementsEqual(array.iterator(), expectedNodes.iterator())
        Iterators.elementsEqual(array.mapElements({ it.intValue() }), expectedValues.iterator())

        where:
        yaml = 'field: [1, 2, 3]'
        fieldName = 'field'
        expectedNodes = [new IntNode(1), new IntNode(2), new IntNode(3)]
        expectedValues = [1, 2, 3]
    }
}
