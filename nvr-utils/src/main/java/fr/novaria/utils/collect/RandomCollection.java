package fr.novaria.utils.collect;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import java.util.*;

public class RandomCollection<T> implements Iterable<Map.Entry<T, Double>> {

    private final TreeMap<Double, Map.Entry<T, Double>> map = new TreeMap<>();

    private double total = 0;

    @Override
    public Iterator<Map.Entry<T, Double>> iterator() {
        return Iterators.transform(map.entrySet().iterator(), entryToValue);
    }

    public List<Map.Entry<T, Double>> entries() {
        return Lists.newArrayList(iterator());
    }

    public void add(T element, double weight) {
        if (weight > 0) {
            total += weight;
            map.put(total, new AbstractMap.SimpleEntry<>(element, weight));
        }
    }

    public void addAll(Map<? extends T, ? extends Number> map) {
        addAll(map.entrySet().iterator());
    }

    public void addAll(Iterator<? extends Map.Entry<? extends T, ? extends Number>> iterator) {
        while (iterator.hasNext()) {
            final Map.Entry<? extends T, ? extends Number> next = iterator.next();
            add(next.getKey(), next.getValue().doubleValue());
        }
    }

    public T get(Random random) {
        final double randomWeight = random.nextDouble() * total;
        final Map.Entry<Double, Map.Entry<T, Double>> entry = map.higherEntry(randomWeight);
        return entry == null ? null : entry.getValue().getKey();
    }

    private final Function<Map.Entry<Double, Map.Entry<T, Double>>, Map.Entry<T, Double>> entryToValue = new Function<Map.Entry<Double, Map.Entry<T, Double>>, Map.Entry<T, Double>>() {
        @Override
        public Map.Entry<T, Double> apply(Map.Entry<Double, Map.Entry<T, Double>> input) {
            return input.getValue();
        }
    };

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof RandomCollection)) {
            return false;
        }

        final RandomCollection that = (RandomCollection) obj;
        return map.equals(that.map);
    }

    @Override
    public String toString() {
        return entries().toString();
    }
}
