package fr.novaria.utils.yaml.node;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import fr.novaria.utils.yaml.YamlNode;
import fr.novaria.utils.yaml.YamlUtils;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ArrayNode extends ContainerNode {

    private final List<Object> list;

    public ArrayNode(List<Object> list) {
        this.list = requireNonNull(list);
    }

    //=========================================================
    // Basic property access
    //=========================================================

    @Override
    public boolean isArray() {
        return true;
    }

    //=========================================================
    // Container methods
    //=========================================================

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    //=========================================================
    // Array methods
    //=========================================================

    @Override
    public boolean has(int index) {
        return index >= 0 && index < list.size();
    }

    @Override
    public YamlNode get(int index) {
        return get(index, null);
    }

    public YamlNode get(int index, YamlNode defaultNode) {

        if (!has(index)) {
            return defaultNode;
        }

        final Object value = list.get(index);
        return YamlUtils.toYamlNode(value);
    }

    @Override
    public YamlNode path(int index) {
        return get(index, MissingNode.getInstance());
    }

    @Override
    public Iterator<YamlNode> iterator() {
        final Iterator<Object> itr = list.iterator();
        return new Iterator<YamlNode>() {

            @Override
            public boolean hasNext() {
                return itr.hasNext();
            }

            @Override
            public YamlNode next() {
                final Object next = itr.next();
                return YamlUtils.toYamlNode(next);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public <T> Iterator<T> mapElements(Function<YamlNode, ? extends T> function) {
        return Iterators.transform(iterator(), function);
    }

    //=========================================================
    // Standard methods
    //=========================================================

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return true;
        }

        if (!(obj instanceof ArrayNode)) {
            return false;
        }

        return list.equals(((ArrayNode) obj).list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
