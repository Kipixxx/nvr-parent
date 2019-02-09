package fr.novaria.skygrid.api

import fr.novaria.skygrid.api.SkyGridApi
import fr.novaria.skygrid.api.block.RandomBlockGroup
import fr.novaria.skygrid.api.creature.RandomCreatureGroup
import org.bukkit.Material
import org.bukkit.entity.EntityType
import spock.lang.Specification
import spock.lang.Subject

class SkyGridApiSpec extends Specification {

    @Subject
    def api = new SkyGridApi()

    void "should find block group by name"() {
        expect:
        api.getBlockGroup(name) == null

        when:
        api.addBlockGroup(name, blockGroup)

        then:
        api.getBlockGroup(name).is(blockGroup)

        where:
        name = 'BLOCK_GROUP'
        blockGroup = new RandomBlockGroup()
    }

    void "should find block group by material name"() {
        expect:
        api.getBlockGroup('BLOCK_GROUP_NOT_FOUND') == null

        and:
        api.getBlockGroup(material.name()) != null

        where:
        material = Material.BEDROCK
    }

    void "should find block group by name then by material name"() {
        given:
        api.addBlockGroup(name, blockGroup)

        expect:
        api.getBlockGroup(name).is(blockGroup)

        where:
        name = Material.BEDROCK.name()
        blockGroup = new RandomBlockGroup()
    }

    void "should find entity type by name"() {
        expect:
        api.getEntityType('ENTITY_TYPE_NOT_FOUND') == null

        and:
        api.getEntityType(entityType.name()) != null

        where:
        entityType = EntityType.CHICKEN
    }

    void "should find creature group by name"() {
        expect:
        api.getCreatureGroup(name) == null

        when:
        api.addCreatureGroup(name, creatureGroup)

        then:
        api.getCreatureGroup(name).is(creatureGroup)

        where:
        name = 'CREATURE_GROUP'
        creatureGroup = new RandomCreatureGroup()
    }

    void "should find creature group by entity type name"() {
        expect:
        api.getCreatureGroup('CREATURE_GROUP_NOT_FOUND') == null

        and:
        api.getCreatureGroup(entityType.name()) != null

        where:
        entityType = EntityType.CHICKEN
    }

    void "should find creature group by name then by entity type name"() {
        given:
        api.addCreatureGroup(name, creatureGroup)

        expect:
        api.getCreatureGroup(name).is(creatureGroup)

        where:
        name = EntityType.CHICKEN.name()
        creatureGroup = new RandomCreatureGroup()
    }
}
