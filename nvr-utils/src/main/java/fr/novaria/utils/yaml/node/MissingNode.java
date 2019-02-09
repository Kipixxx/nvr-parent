package fr.novaria.utils.yaml.node;

import fr.novaria.utils.yaml.YamlNode;

public class MissingNode extends YamlNode {

    private final static MissingNode instance = new MissingNode();

    protected MissingNode() {
    }

    public static MissingNode getInstance() {
        return instance;
    }

    //=========================================================
    // Basic property access
    //=========================================================

    @Override
    public boolean isMissing() {
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
