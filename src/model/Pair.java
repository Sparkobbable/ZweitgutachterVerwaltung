package model;

public class Pair<K, V> {

    private final K key;
    private final V value;

    public static <K, V> Pair<K, V> createPair(K key, V value) {
        return new Pair<K, V>(key, value);
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

}