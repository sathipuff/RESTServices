package com.sathi.pi.model;

import javafx.util.Pair;

import java.time.Duration;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ExpiringHashMap<K, V> implements Map {
    private Map<K, Pair<LocalTime, V>> map = new ConcurrentHashMap<>();
    private final Duration expirationDuration;

    public ExpiringHashMap(Duration expirationDuration) {
        this.expirationDuration = expirationDuration;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        pruneExpiredRecords();
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        pruneExpiredRecords();
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        Pair<LocalTime, V> entry = map.get((K) key);
        if(entry != null && entry.getKey().isAfter(LocalTime.now().minus(expirationDuration))){
            return entry.getValue();
        }
        return null;
    }

    @Override
    public V put(Object key, Object value) {
        map.put((K)key, new Pair(LocalTime.now(), (V)value));
        return (V)value;
    }

    @Override
    public K remove(Object key) {
        map.remove(key);
        return (K)key;
    }

    @Override
    public void putAll(Map m) {
        ((Map<K,V>)m).entrySet()
                .forEach(entry -> this.put(entry.getKey(), entry.getValue()));
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values()
                .stream()
                .map(Pair::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public Set<Entry<K,V>> entrySet() {
        return map.entrySet().stream()
                .map(entrySet ->new AbstractMap.SimpleEntry<>(entrySet.getKey(), entrySet.getValue().getValue()))
                .collect(Collectors.toSet());
    }

    private void pruneExpiredRecords() {
        LocalTime now = LocalTime.now();
        map.entrySet().stream()
                .filter(entrySet -> entrySet.getValue().getKey().isAfter(now.minus(expirationDuration)))
                .forEach(entrySet-> map.remove(entrySet.getKey()));
    }


}
