package fr.novaria.utils.yaml.node;

import fr.novaria.utils.yaml.YamlNode;

public abstract class ValueNode extends YamlNode {

    //=========================================================
    // Container methods
    //=========================================================

    @Override
    public boolean isEmpty() {
        return true;
    }

    //=========================================================
    // Object methods
    //=========================================================

    @Override
    public boolean has(String fieldName) {
        return false;
    }
}
