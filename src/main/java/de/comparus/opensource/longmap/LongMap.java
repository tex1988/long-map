package de.comparus.opensource.longmap;

public interface LongMap<V> {
    V put(long key, V value);
    V get(long key);
    V remove(long key);

    boolean isEmpty();
    boolean containsKey(long key);
    boolean containsValue(V value);

    long[] keys();
    Object[] values();

    long size();
    void clear();
}
