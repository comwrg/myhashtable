// Reference: https://yikun.github.io/2015/04/01/Java-HashMap%E5%B7%A5%E4%BD%9C%E5%8E%9F%E7%90%86%E5%8F%8A%E5%AE%9E%E7%8E%B0/

import java.util.ArrayList;

public class MyHashtable<K, V> {
    private ArrayList<K> keys = new ArrayList<>();
    private ArrayList<V> values = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> table = new ArrayList<>();
    private int size = 0;
    private int capacity = 1;

    public MyHashtable(int initialCapacity) {
        if (initialCapacity <= 0)
            initialCapacity = 1;
        while (capacity < initialCapacity)
            capacity <<= 1;
        resizeCapacity();
    }

    private void resizeCapacity() {
        while (table.size() < capacity)
            table.add(new ArrayList<>());
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        int valueIndex = get(key, bucketIndex);
        if (valueIndex < 0)
            return null;
        return values.get(valueIndex);
    }

    private int get(K key, int bucketIndex) {
        for (int i : table.get(bucketIndex)) {
            if (key.equals(keys.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public V put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        int valueIndex = get(key, bucketIndex);
        if (valueIndex < 0) {
            if (size * 0.75 > capacity)
                resize();
            keys.add(key);
            values.add(value);
            table.get(bucketIndex).add(size);
            ++size;
            return null;
        } else {
            V oldValue = values.get(valueIndex);
            values.set(valueIndex, value);
            return oldValue;
        }
    }

    private void resize() {
        int oldCapacity = capacity;
        capacity <<= 1;
        resizeCapacity();
        for (int i = 0; i < oldCapacity; ++i) {
            ArrayList<Integer> bucket = table.get(i);
            for (int j = bucket.size()-1; j >= 0; --j) {
                int keyIndex = bucket.get(j);
                K key = keys.get(keyIndex);
                int h = hash(key);
                if ((h & capacity) > 0) {
                    // need move
                    table.get(i+oldCapacity).add(keyIndex);
                    bucket.remove(j);
                }
            }
        }
    }

    private int hash(K key) {
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    private int getBucketIndex(K key) {
        return (capacity-1) & hash(key);
    }
}

