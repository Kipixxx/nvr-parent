package fr.novaria.skygrid.bukkit

import fr.novaria.skygrid.api.SkyGridApi
import fr.novaria.skygrid.api.SkyGridWorld
import fr.novaria.test.BukkitSpecification
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import spock.lang.Subject

class SkyGridChunkGeneratorSpec extends BukkitSpecification {

    def world = Mock(World)

    def random = Mock(Random)

    def biome = Mock(ChunkGenerator.BiomeGrid)

    def skyGridWorld = new SkyGridWorld()

    def skyGridApi = new SkyGridApi()

    @Subject
    def chunkGenerator = new SkyGridChunkGenerator(skyGridWorld, skyGridApi)

    def "should set blocks"() {
        given:
        def chunkData = Mock(ChunkGenerator.ChunkData)
        server.createChunkData(world) >> chunkData

        skyGridWorld.height = 4

        if (material) {
            skyGridWorld.addBlockGroup(material, 1)
        }

        if (plant) {
            skyGridApi.addPlant(material, plant)
        }

        when:
        chunkGenerator.generateChunkData(world, random, 0, 0, biome)

        then:
        expectedMaterial * chunkData.setBlock(_ as Integer, _ as Integer, _ as Integer, material)
        expectedPlant * chunkData.setBlock(_ as Integer, _ as Integer, _ as Integer, plant)

        where:
        material      | plant          || expectedMaterial | expectedPlant
        null          | null           || 0                | 0
        Material.DIRT | null           || 16               | 0
        null          | Material.POPPY || 0                | 0
        Material.DIRT | Material.POPPY || 16               | 16
    }

    def "should get populator"() {
        when:
        def populators = chunkGenerator.getDefaultPopulators()

        then:
        populators.size() == 1
        populators[0] instanceof SkyGridBlockPopulator
    }

    def "should get fixed spawn location"() {
        given:
        skyGridWorld.height = height

        when:
        def location = chunkGenerator.getFixedSpawnLocation(world, random)

        then:
        location.x == 0.5D
        location.y == expectedLocationY
        location.z == 0.5D

        where:
        height || expectedLocationY
        0      || 1D
        4      || 1D
        128    || 125D
    }
}
