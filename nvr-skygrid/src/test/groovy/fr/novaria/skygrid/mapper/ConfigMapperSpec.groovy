package fr.novaria.skygrid.mapper

import fr.novaria.skygrid.api.SkyGridWorld
import fr.novaria.skygrid.api.block.RandomBlockGroup
import fr.novaria.skygrid.api.chest.RandomChestItems
import fr.novaria.skygrid.api.creature.RandomCreatureGroup
import fr.novaria.utils.yaml.node.SectionObjectNode
import org.bukkit.Material
import org.bukkit.entity.EntityType
import spock.lang.Specification

class ConfigMapperSpec extends Specification {

    void "should parse settings"() {
        given:
        def node = new SectionObjectNode("""
settings:
    default-block-group-weight: $defaultBlockGroupWeight
    default-chest-item-count: $defaultChestItemCount
    default-creature-group-weight: $defaultCreatureGroupWeight
    prevent-sand-fall-with-cactus: $preventSandFallWithCactus
    
worlds:
    world:
        height: 125
        block-groups: 
            - CHEST/88
            - B0
        chest-items:
            - DIRT/20/99
            - WATER/25
            - LAVA
            - C0/30
            - C1
        chest-quantities:
            - 4/2
        creature-spawners:
            - SKELETON/35
            - CG0
        
block-groups:
    B0:
        - CHEST/5
    B1:
        - B0
        
chest-items:
    C0:
        - AIR/44/55
        - BEDROCK/66
    C1:
        - B1
    C2:
        - C0/77
        - C1
        
plants:
    DIRT:
        - POPPY/10
        - B1
        
creature-groups:
    CG0:
        - ZOMBIE/15
    CG1:
        - CG0

""")

        when:
        def api = new ConfigMapper().map(node)

        then:
        with(api) {
            it.defaultBlockGroupWeight == defaultBlockGroupWeight
            it.defaultChestItemCount == defaultChestItemCount
            it.defaultCreatureGroupWeight == defaultCreatureGroupWeight
            it.preventSandFallWithCactus == preventSandFallWithCactus

            getBlockGroup('B0') == new RandomBlockGroup()
                    .addBlockGroup(Material.CHEST, 5)

            getBlockGroup('B1') == new RandomBlockGroup()
                    .addBlockGroup(getBlockGroup('B0'), defaultBlockGroupWeight)

            getChestItems('C0') == new RandomChestItems()
                    .addChestItem(Material.AIR, 44, 55)
                    .addChestItem(Material.BEDROCK, 66, defaultBlockGroupWeight)

            getChestItems('C1') == new RandomChestItems()
                    .addChestItem(getBlockGroup('B1'), defaultChestItemCount, defaultBlockGroupWeight)

            getChestItems('C2') == new RandomChestItems()
                    .addChestItem(getChestItems('C0'), 77)
                    .addChestItem(getChestItems('C1'), defaultBlockGroupWeight)

            getPlants(Material.DIRT) == new RandomBlockGroup()
                    .addBlockGroup(Material.POPPY, 10)
                    .addBlockGroup(getBlockGroup('B1'), defaultBlockGroupWeight)

            getCreatureGroup('CG0') == new RandomCreatureGroup()
                    .addCreatureGroup(EntityType.ZOMBIE, 15)

            getCreatureGroup('CG1') == new RandomCreatureGroup()
                    .addCreatureGroup(getCreatureGroup('CG0'), defaultCreatureGroupWeight)

            getWorld('world') == new SkyGridWorld(125)
                    .addBlockGroup(Material.CHEST, 88)
                    .addBlockGroup(getBlockGroup('B0'), defaultBlockGroupWeight)
                    .addChestItems(Material.DIRT, 20, 99)
                    .addChestItems(Material.WATER, 25, defaultBlockGroupWeight)
                    .addChestItems(Material.LAVA, defaultChestItemCount, defaultBlockGroupWeight)
                    .addChestItems(getChestItems('C0'), 30)
                    .addChestItems(getChestItems('C1'), defaultBlockGroupWeight)
                    .addChestQuantity(4, 2)
                    .addCreatureGroup(EntityType.SKELETON, 35)
                    .addCreatureGroup(getCreatureGroup('CG0'), defaultCreatureGroupWeight)
        }

        where:
        defaultBlockGroupWeight = 11
        defaultChestItemCount = 22
        defaultCreatureGroupWeight = 33
        preventSandFallWithCactus = true
    }
}
