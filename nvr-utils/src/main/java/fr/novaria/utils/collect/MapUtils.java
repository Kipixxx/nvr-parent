package fr.novaria.utils.collect;

import java.util.Iterator;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public final class MapUtils {

    public static <K, V> void putAll(Map<K, V> map, Iterator<? extends Map.Entry<? extends K, ? extends V>> iterator) {
        checkNotNull(map);
        checkNotNull(iterator);

        while (iterator.hasNext()) {
            final Map.Entry<? extends K, ? extends V> next = iterator.next();
            map.put(next.getKey(), next.getValue());
        }
    }

    private MapUtils() { /* Cannot be instantiated */ }
}
