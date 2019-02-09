package fr.novaria.utils.yaml.node;

import fr.novaria.utils.yaml.YamlNode;

public class NullNode extends YamlNode {

    private final static NullNode instance = new NullNode();

    protected NullNode() {
    }

    public static NullNode getInstance() {
        return instance;
    }

    //=========================================================
    // Basic property access
    //=========================================================

    @Override
    public boolean isNull() {
        return true;
    }

    //=========================================================
    // Object methods
    //=========================================================

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }
}
