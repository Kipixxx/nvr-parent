package fr.novaria.skygrid.api.chest

import org.bukkit.Material
import spock.lang.Specification
import spock.lang.Subject

class ChestItemsSpec extends Specification {

    void "should get null random chest item when there is no chest items"() {
        given:
        @Subject def chestItems = new RandomChestItems()

        expect:
        chestItems.getRandomChestItem(new Random()) == null
    }

    void "should get random chest item"() {
        given:
        def chestItem0 = new ChestItem(Material.AIR, 1)
        def chestItem1 = new ChestItem(Material.BEDROCK, 1)
        def chestItem2 = new ChestItem(Material.CHEST, 1)

        @Subject def chestItems = new RandomChestItems().addChestItems([
                (chestItem0): 1,
                (chestItem1): 1,
                (chestItem2): 1
        ])

        def random = Mock(Random)

        when:
        def result = [
                chestItems.getRandomChestItem(random),
                chestItems.getRandomChestItem(random),
                chestItems.getRandomChestItem(random)
        ]

        then:
        1 * random.nextDouble() >> 0.9
        result[0] == chestItem2

        and:
        1 * random.nextDouble() >> 0.1
        result[1] == chestItem0

        and:
        1 * random.nextDouble() >> 0.5
        result[2] == chestItem1
    }
}
