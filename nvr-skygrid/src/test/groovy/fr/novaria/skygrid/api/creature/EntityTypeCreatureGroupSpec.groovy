package fr.novaria.skygrid.api.creature

import org.bukkit.entity.EntityType
import spock.lang.Specification

class EntityTypeCreatureGroupSpec extends Specification {

    void "should raise an exception when creature group is create with null entity type"() {
        when:
        new EntityTypeCreatureGroup(null)

        then:
        thrown(NullPointerException)
    }

    void "should get entity type"() {
        given:
        def creatureGroup = new EntityTypeCreatureGroup(entity)

        expect:
        creatureGroup.getRandomCreature(new Random()) == entity

        where:
        entity = EntityType.CHICKEN
    }
}
