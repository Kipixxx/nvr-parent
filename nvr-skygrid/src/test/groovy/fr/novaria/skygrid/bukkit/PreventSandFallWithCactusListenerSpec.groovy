package fr.novaria.skygrid.bukkit

import fr.novaria.skygrid.bukkit.PreventSandFallWithCactusListener
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.BlockState
import org.bukkit.entity.EntityType
import org.bukkit.event.entity.EntityChangeBlockEvent
import spock.lang.Specification
import spock.lang.Subject

class PreventSandFallWithCactusListenerSpec extends Specification {

    @Subject
    def listener = new PreventSandFallWithCactusListener()

    void "should cancel event"() {
        given:
        def state = Mock(BlockState)

        def blockUp = Mock(Block) {
            getType() >> materialUp
        }

        def block = Mock(Block) {
            getState() >> state
            getType() >> materialFrom
            getRelative(BlockFace.UP) >> blockUp
        }

        def event = Mock(EntityChangeBlockEvent) {
            getBlock() >> block
            getEntityType() >> entityType
            getTo() >> materialTo
        }

        when:
        listener.onSandFall(event)

        then:
        cancelled * event.setCancelled(true)
        cancelled * state.update(false, false)

        where:
        entityType               | materialTo       | materialFrom     | materialUp       || cancelled
        EntityType.FALLING_BLOCK | Material.AIR     | Material.SAND    | Material.CACTUS  || 1
        EntityType.CHICKEN       | Material.AIR     | Material.SAND    | Material.CACTUS  || 0
        EntityType.FALLING_BLOCK | Material.BEDROCK | Material.SAND    | Material.CACTUS  || 0
        EntityType.FALLING_BLOCK | Material.AIR     | Material.BEDROCK | Material.CACTUS  || 0
        EntityType.FALLING_BLOCK | Material.AIR     | Material.SAND    | Material.BEDROCK || 0
    }
}
