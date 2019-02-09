package fr.novaria.utils.collect

import com.google.common.collect.Iterables
import com.google.common.collect.Iterators
import spock.lang.Specification

class RandomCollectionSpec extends Specification {

    void "should add element"() {
        given:
        def random = Mock(Random)
        def collection = new RandomCollection()

        expect:
        collection.get(random) == null

        when:
        collection.add('a', 2)

        then:
        collection.get(random) == 'a'
    }

    void "should add elements"() {
        given:
        def random = Mock(Random)

        def collection = new RandomCollection()
        collection.addAll(['a': 35D, 'b': 35D, 'c':30D])

        when:
        def result = [
                collection.get(random),
                collection.get(random),
                collection.get(random)
        ]

        then:
        1 * random.nextDouble() >> 0.9
        result[0] == 'c'

        and:
        1 * random.nextDouble() >> 0.1
        result[1] == 'a'

        and:
        1 * random.nextDouble() >> 0.5
        result[2] == 'b'
    }

    void "should iterate over elements"() {
        given:
        def collection = new RandomCollection()
        collection.addAll(map)

        expect:
        Iterators.elementsEqual(collection.iterator(), map.entrySet().iterator())

        where:
        map = ['a': 35D, 'b': 35D, 'c': 30D]
    }
}
