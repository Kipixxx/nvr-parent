package fr.novaria.skygrid.api.chest

import spock.lang.Specification

class ChestQuantitySpec extends Specification {

    void "should raise an exception when quantity is not positive"() {
        when:
        new ChestQuantity(-1)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == 'quantity must be positive'
    }

    void "should get random quantity"() {
        given:
        def chestQuantity = new ChestQuantity(10)
        def random = new Random()

        when:
        def result = [] as HashSet
        10000.times { result.add(chestQuantity.getRandomCount(random)) }

        then:
        result == [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] as HashSet
    }
}
