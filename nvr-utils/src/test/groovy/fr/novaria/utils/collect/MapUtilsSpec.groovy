package fr.novaria.utils.collect

import spock.lang.Specification

class MapUtilsSpec extends Specification {

    void "should pull all elements"() {
        given:
        def map = [:] as Map<Integer, String>

        when:
        MapUtils.putAll(map, [1: 'A'].entrySet().iterator())

        then:
        map == [1: 'A']
    }
}
