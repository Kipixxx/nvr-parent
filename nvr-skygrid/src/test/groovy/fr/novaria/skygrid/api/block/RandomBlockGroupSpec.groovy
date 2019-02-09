package fr.novaria.skygrid.api.block

import org.bukkit.Material
import spock.lang.Specification
import spock.lang.Subject

class RandomBlockGroupSpec extends Specification {

    void "should get null random block when there is no material"() {
        given:
        @Subject def blockGroup = new RandomBlockGroup()

        expect:
        blockGroup.getRandomBlock(new Random()) == null
    }

    void "should get random block"() {
        given:
        @Subject def blockGroup = new RandomBlockGroup().addBlockGroups([
                (new MaterialBlockGroup(Material.AIR))    : 1,
                (new MaterialBlockGroup(Material.BEDROCK)): 1,
                (new MaterialBlockGroup(Material.CHEST))  : 1
        ])

        def random = Mock(Random)

        when:
        def result = [
                blockGroup.getRandomBlock(random),
                blockGroup.getRandomBlock(random),
                blockGroup.getRandomBlock(random)
        ]

        then:
        1 * random.nextDouble() >> 0.9
        result[0] == Material.CHEST

        and:
        1 * random.nextDouble() >> 0.1
        result[1] == Material.AIR

        and:
        1 * random.nextDouble() >> 0.5
        result[2] == Material.BEDROCK
    }
}
