package fr.novaria.utils.yaml

import fr.novaria.utils.yaml.node.ArrayNode
import fr.novaria.utils.yaml.node.BooleanNode
import fr.novaria.utils.yaml.node.IntNode
import fr.novaria.utils.yaml.node.MissingNode
import fr.novaria.utils.yaml.node.NullNode
import fr.novaria.utils.yaml.node.SectionObjectNode
import fr.novaria.utils.yaml.node.TextNode
import org.bukkit.configuration.file.YamlConfiguration
import spock.lang.Specification

class YamlNodeSpec extends Specification {

    void "should indicate what the node is"() {
        expect:
        node.isMissing() == exptectedIsMissing
        node.isBoolean() == expectedIsBoolean
        node.isInt() == expectedIsInt
        node.isTextual() == exptectedIsTextual
        node.isArray() == exptectedIsArray
        node.isObject() == exptectedIsObject

        where:
        node                                           || expectedIsNull | exptectedIsMissing | expectedIsBoolean | expectedIsInt | exptectedIsTextual | exptectedIsArray | exptectedIsObject
        NullNode.instance                              || true           | false              | false             | false         | false              | false            | false
        MissingNode.instance                           || false          | true               | false             | false         | false              | false            | false
        new BooleanNode(true)                          || false          | false              | true              | false         | false              | false            | false
        new IntNode(1)                                 || false          | false              | false             | true          | false              | false            | false
        new TextNode("txt")                            || false          | false              | false             | false         | true               | false            | false
        new ArrayNode([])                              || false          | false              | false             | false         | false              | true             | false
        new SectionObjectNode(new YamlConfiguration()) || false          | false              | false             | false         | false              | false            | true
    }
}
