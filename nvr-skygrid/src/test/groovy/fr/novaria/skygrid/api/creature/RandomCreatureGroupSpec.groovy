package fr.novaria.skygrid.api.creature

import org.bukkit.entity.EntityType
import spock.lang.Specification
import spock.lang.Subject

class RandomCreatureGroupSpec extends Specification {

    void "should get null random creature group when there is no entity type"() {
        given:
        @Subject def creatureGroup = new RandomCreatureGroup()

        expect:
        creatureGroup.getRandomCreature(new Random()) == null
    }

    void "should get random creature"() {
        given:
        @Subject def creatureGroup = new RandomCreatureGroup()
        creatureGroup.addCreatureGroups([
                (new EntityTypeCreatureGroup(EntityType.ARROW))  : 1,
                (new EntityTypeCreatureGroup(EntityType.BAT))    : 1,
                (new EntityTypeCreatureGroup(EntityType.CHICKEN)): 1
        ])

        def random = Mock(Random)

        when:
        def result = [
                creatureGroup.getRandomCreature(random),
                creatureGroup.getRandomCreature(random),
                creatureGroup.getRandomCreature(random)
        ]

        then:
        1 * random.nextDouble() >> 0.9
        result[0] == EntityType.CHICKEN

        and:
        1 * random.nextDouble() >> 0.1
        result[1] == EntityType.ARROW

        and:
        1 * random.nextDouble() >> 0.5
        result[2] == EntityType.BAT
    }
}
