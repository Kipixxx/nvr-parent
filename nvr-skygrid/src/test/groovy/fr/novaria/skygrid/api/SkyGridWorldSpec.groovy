package fr.novaria.skygrid.api


import fr.novaria.skygrid.api.block.RandomBlockGroup
import fr.novaria.skygrid.api.chest.ChestItem
import fr.novaria.skygrid.api.chest.RandomChestItems
import fr.novaria.utils.base.LocationFunction
import org.bukkit.Material
import spock.lang.Specification
import spock.lang.Subject

class SkyGridWorldSpec extends Specification {

    void "should get highest block y"() {
        given:
        def world = new SkyGridWorld(height: height)

        expect:
        world.getHighestBlockY() == expectedHighestBlockY

        where:
        height || expectedHighestBlockY
        0      || 0
        1      || 0
        5      || 4
        9      || 8
        128    || 124
    }

    void "should iterate over chunk"() {
        given:
        @Subject def world = new SkyGridWorld(height: 1)

        and:
        def function = Mock(LocationFunction)

        when:
        world.chunkIteration(function)

        then:
        1 * function.apply(0, 0, 0)
        1 * function.apply(0, 0, 4)
        1 * function.apply(0, 0, 8)
        1 * function.apply(0, 0, 12)

        1 * function.apply(4, 0, 0)
        1 * function.apply(4, 0, 4)
        1 * function.apply(4, 0, 8)
        1 * function.apply(4, 0, 12)

        1 * function.apply(8, 0, 0)
        1 * function.apply(8, 0, 4)
        1 * function.apply(8, 0, 8)
        1 * function.apply(8, 0, 12)

        1 * function.apply(12, 0, 0)
        1 * function.apply(12, 0, 4)
        1 * function.apply(12, 0, 8)
        1 * function.apply(12, 0, 12)
    }

    void "should get 0 quantity when there is no quantity"() {
        given:
        @Subject def world = new SkyGridWorld()

        expect:
        world.getRandomChestQuantity(new Random()) == 0
    }

    void "should build empty item stack when there is no quantity"() {
        given:
        @Subject def world = new SkyGridWorld()

        expect:
        world.buildItemStacks(new Random()) is SkyGridWorld.EMPTY_ITEM_STACK_ARRAY
    }

    void "should build empty item stack when there is no item"() {
        given:
        @Subject def world = new SkyGridWorld()
        world.addChestQuantity(1, 1)

        expect:
        world.buildItemStacks(new Random()) is SkyGridWorld.EMPTY_ITEM_STACK_ARRAY
    }

    void "should build empty item stack when there is no material in item"() {
        given:
        def emptyItem = new ChestItem(new RandomBlockGroup(), 1)

        def items = new RandomChestItems()
        items.addChestItem(emptyItem, 1)

        @Subject def world = new SkyGridWorld()
        world.addChestQuantity(1, 1)
        world.addChestItems(items, 1)

        expect:
        world.buildItemStacks(new Random()) is SkyGridWorld.EMPTY_ITEM_STACK_ARRAY
    }

    void "should build item stack"() {
        given:
        def items = new RandomChestItems()
        items.addChestItem(Material.CHEST, 1, 1)

        @Subject def world = new SkyGridWorld()
        world.addChestQuantity(1, 1)
        world.addChestItems(items, 1)

        when:
        def stacks = world.buildItemStacks(new Random())

        then:
        stacks.length == 1

        and:
        def stack = stacks[0]
        stack.type == Material.CHEST
        stack.amount == 1
    }
}
