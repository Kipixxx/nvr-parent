package fr.novaria.skygrid.api.block

import org.bukkit.Material
import spock.lang.Specification

class MaterialBlockGroupSpec extends Specification {

    void "should raise an exception when block group is create with null material"() {
        when:
        new MaterialBlockGroup(null)

        then:
        thrown(NullPointerException)
    }

    void "should get material"() {
        given:
        def blockGroup = new MaterialBlockGroup(material)

        expect:
        blockGroup.getRandomBlock(new Random()) == material

        where:
        material = Material.AIR
    }
}
