package fr.novaria.skygrid.bukkit

import fr.novaria.skygrid.api.SkyGridApi
import fr.novaria.skygrid.api.SkyGridWorld
import fr.novaria.skygrid.api.chest.ChestItem
import fr.novaria.skygrid.api.chest.RandomChestItems
import org.bukkit.Chunk
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.Chest
import org.bukkit.block.CreatureSpawner
import org.bukkit.entity.EntityType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import spock.lang.Specification
import spock.lang.Subject

class SkyGridBlockPopulatorSpec extends Specification {

    def random = Mock(Random)
    def block = Mock(Block)
    def chunk = Mock(Chunk)

    def skyGridWorld = new SkyGridWorld()

    def skyGridApi = new SkyGridApi()

    @Subject
    def populator = Spy(SkyGridBlockPopulator, constructorArgs: [skyGridWorld]) as SkyGridBlockPopulator

    void "should populate blocks"() {
        given:
        skyGridWorld.height = height

        when:
        populator.populate(null, random, chunk)

        then:
        expectedPopulates * chunk.getBlock(_ as Integer, _ as Integer, _ as Integer) >> block
        expectedPopulates * populator.populate(block, random) >> {}

        where:
        height = 0
        expectedPopulates = 16
    }

    void "should populate blocks with specific values"() {
        when:
        populator.populate(block, random)

        then:
        1 * block.getType() >> material
        expectedPopulateChest * populator.populateChest(block, random) >> {}
        expectedPopulateSpawner * populator.populateSpawner(block, random) >> {}

        where:
        material         || expectedPopulateChest | expectedPopulateSpawner | expectedPopulatePlant
        Material.CHEST   || 1                     | 0                       | 0
        Material.SPAWNER || 0                     | 1                       | 0
    }

    void "should populate chest"() {
        given:
        def inventory = Mock(Inventory)

        def chest = Mock(Chest) {
            getInventory() >> inventory
        }

        block.getState() >> chest

        if (item != null) {
            def items = new RandomChestItems()
            items.addChestItem(new ChestItem(item, 1), 1)

            skyGridWorld.addChestQuantity(1, 1)
            skyGridWorld.addChestItems(items, 1)
        }

        when:
        populator.populateChest(block, random)

        then:
        expected * inventory.addItem(_ as ItemStack[])

        where:
        item           || expected
        null           || 0
        Material.CHEST || 1
    }

    void "should populate spawner"() {
        given:
        def creatureSpwaner = Mock(CreatureSpawner)

        block.getState() >> creatureSpwaner

        if (creature) {
            skyGridWorld.addCreatureGroup(creature, 1)
        }

        when:
        populator.populateSpawner(block, random)

        then:
        expected * creatureSpwaner.setSpawnedType(creature)

        where:
        creature           || expected
        null               || 0
        EntityType.CHICKEN || 1
    }
}
