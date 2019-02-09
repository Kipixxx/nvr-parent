package fr.novaria.utils.yaml.node

import com.google.common.collect.Iterators
import spock.lang.Specification

class MapObjectNodeSpec extends Specification {

    void "should get size"() {
        given:
        def object = new MapObjectNode(map)

        expect:
        object.isEmpty() == expectedIsEmpty
        object.size() == expectedSize

        where:
        map                      || expectedIsEmpty | expectedSize
        new HashMap<>()          || true            | 0
        ['a': 1, 'b': 2, 'c': 3] || false           | 3
    }

    void "should get values"() {
        given:
        def object = new MapObjectNode(map)

        expect:
        object.has(fieldName) == expectedHas

        object.get(fieldName) == expectedGet
        object.get(fieldName)?.hashCode() == expectedGet?.hashCode()

        object.path(fieldName) == expectedPath
        object.path(fieldName)?.hashCode() == expectedPath?.hashCode()

        where:
        map                 || expectedHas | expectedGet                     | expectedPath
        [:]                 || false       | null                            | MissingNode.instance
        ['field': null]     || true        | NullNode.instance               | NullNode.instance
        ['field': true]     || true        | new BooleanNode(true)           | new BooleanNode(true)
        ['field': 1]        || true        | new IntNode(1)                  | new IntNode(1)
        ['field': 'text']   || true        | new TextNode('text')            | new TextNode('text')
        ['field': [1]]      || true        | new ArrayNode([1])              | new ArrayNode([1])
        ['field': ['a': 1]] || true        | new SectionObjectNode('{a: 1}') | new SectionObjectNode('{a: 1}')
        ['field': ['a': 1]] || true        | new MapObjectNode(['a': 1])     | new MapObjectNode(['a': 1])

        fieldName = 'field'
    }

    void "should list values"() {
        given:
        def object = new MapObjectNode(map)

        expect:
        Iterators.elementsEqual(object.fieldNames(), expectedNodes.keySet().iterator())
        Iterators.elementsEqual(object.fields(), expectedNodes.entrySet().iterator())
        Iterators.elementsEqual(object.mapFiels({ it.intValue() }), map.entrySet().iterator())

        where:
        map = [a: 1, b: 2, c: 3]
        expectedNodes = [a: new IntNode(1), b: new IntNode(2), c: new IntNode(3)]
    }
}
