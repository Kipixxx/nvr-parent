package fr.novaria.skygrid.api.chest

import com.google.common.collect.Sets
import fr.novaria.skygrid.api.block.MaterialBlockGroup
import fr.novaria.skygrid.api.block.RandomBlockGroup
import org.bukkit.Material
import spock.lang.Specification

class ChestItemSpec extends Specification {

    void "should get random material"() {
        given:
        def blockGroup = new RandomBlockGroup().addBlockGroups([
                (new MaterialBlockGroup(Material.AIR))     : 2,
                (new MaterialBlockGroup(Material.BEDROCK)) : 4,
                (new MaterialBlockGroup(Material.OAK_WOOD)): 6
        ])

        def chestItem = new ChestItem(blockGroup, 0)
        def random = new Random()

        when:
        def result = [] as HashSet
        10000.times { result.add(chestItem.getRandomBlock(random)) }

        then:
        result == Sets.newHashSet(Material.AIR, Material.BEDROCK, Material.OAK_WOOD)
    }

    void "should get random count"() {
        given:
        def chestItem = new ChestItem(new RandomBlockGroup(), 5)
        def random = new Random()

        when:
        def result = [] as HashSet
        10000.times { result.add(chestItem.getRandomCount(random)) }

        then:
        result == Sets.newHashSet(1, 2, 3, 4, 5)
    }
}
