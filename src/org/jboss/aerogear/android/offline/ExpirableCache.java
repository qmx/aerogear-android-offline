package org.jboss.aerogear.android.offline;

public interface ExpirableCache<K, V> extends Cache<K, V> {

    ExpirableValue<V> getCachedValue(K key);

    boolean isExpired(K key);

}
