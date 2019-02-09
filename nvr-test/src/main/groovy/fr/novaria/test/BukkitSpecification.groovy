package fr.novaria.test

import org.bukkit.Bukkit
import org.bukkit.Server
import org.spockframework.mock.MockImplementation
import org.spockframework.mock.MockNature
import org.spockframework.mock.MockUtil
import spock.lang.Shared
import spock.lang.Specification

class BukkitSpecification extends Specification {

    @Shared
    MockUtil mockUtil = new MockUtil()

    @Shared
    Server server

    void setupSpec() {
        server = mockUtil.createDetachedMock("serverMock", AbstractServer, MockNature.SPY, MockImplementation.JAVA, [:], getClass().classLoader) as Server
        Bukkit.server = server
    }

    void setup() {
        mockUtil.attachMock(server, this)
    }

    def cleanup() {
        mockUtil.detachMock(server)
    }
}
