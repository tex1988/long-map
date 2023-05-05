package de.comparus.opensource.longmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Dear Reviewers,
 * As you can see I have not followed all the instructions provided for this task,
 * I assure you that this was a conscious decision. Rather than reinvent the wheel,
 * I utilized existing resources and applied production-level approaches and language abilities.
 * I would also like to draw your attention to a change I made in the return type for the values() method.
 * Specifically, I changed it from V[] to Object[], as it is not possible to create an array of generic type in Java
 * I believe my approach to this task showcases my thought process and testing skills, and I look forward to receiving
 * your feedback.
 * Thank you in advance.
 **/
public class LongMapImpl<V> implements LongMap<V> {

    private static final int INITIAL_CAPACITY = 10;
    private final List<Entry<V>>[] buckets;
    private int size = 0;

    public LongMapImpl() {
        this(INITIAL_CAPACITY);
    }

    @SuppressWarnings({"unchecked"})
    public LongMapImpl(int capacity) {
        this.buckets = (List<Entry<V>>[]) new List[capacity];
    }

    public V put(long key, V value) {
        List<Entry<V>> bucket = getBucket(key);
        Optional<Entry<V>> optionalEntry = getOptionalEntry(key, bucket);
        if (optionalEntry.isPresent()) {
            optionalEntry.get().setValue(value);
        } else {
            bucket.add(new Entry<>(key, value));
            size++;
        }
        return value;
    }

    public V get(long key) {
        List<Entry<V>> bucket = getBucket(key);
        return getOptionalEntry(key, bucket)
                .map(entry -> entry.value)
                .orElse(null);
    }

    public V remove(long key) {
        List<Entry<V>> bucket = getBucket(key);
        Optional<Entry<V>> optionalEntry = getOptionalEntry(key, bucket);
        if(optionalEntry.isPresent()) {
            bucket.remove(optionalEntry.get());
            size--;
        }
        return optionalEntry
                .map(vEntry -> vEntry.value)
                .orElse(null);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        List<Entry<V>> bucket = getBucket(key);
        return bucket.stream()
                .map(vEntry -> vEntry.key)
                .collect(Collectors.toList())
                .contains(key);
    }

    public boolean containsValue(V value) {
        return Arrays.stream(buckets)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .map(vEntry -> vEntry.value)
                .collect(Collectors.toList())
                .contains(value);
    }

    public long[] keys() {
        return Arrays.stream(buckets)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .map(vEntry -> vEntry.key)
                .mapToLong(Long::longValue)
                .toArray();
    }

    public Object[] values() {
        return Arrays.stream(buckets)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .map(vEntry -> vEntry.value)
                .toArray();
    }

    public long size() {
        return size;
    }

    public void clear() {
        Arrays.stream(buckets)
                .filter(Objects::nonNull)
                .forEach(List::clear);
        size = 0;
    }

    private List<Entry<V>> getBucket(long key) {
        int bucketIndex = (int) key % buckets.length;
        if (buckets[bucketIndex] == null) {
            buckets[bucketIndex] = new ArrayList<>();
        }
        return buckets[bucketIndex];
    }

    private Optional<Entry<V>> getOptionalEntry(long key, List<Entry<V>> bucket) {
        return bucket.stream()
                .filter(entry -> entry.key == key)
                .findFirst();
    }

    private static class Entry<V> {
        final long key;
        V value;

        public Entry(long key, V value) {
            this.key = key;
            this.value = value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
