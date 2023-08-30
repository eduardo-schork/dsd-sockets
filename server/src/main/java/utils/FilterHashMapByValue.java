package utils;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterHashMapByValue {
    public static <K, V> Map<K, V> filterByValue(Map<K, V> map, Predicate<V> predicate) {
        return map.entrySet()
                .stream()
                .filter(entry -> predicate.test(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
