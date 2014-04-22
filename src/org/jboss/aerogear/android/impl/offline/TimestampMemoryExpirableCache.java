package org.jboss.aerogear.android.impl.offline;

import android.support.v4.util.LruCache;
import org.jboss.aerogear.android.offline.ExpirableValue;

import java.util.Date;

public class TimestampMemoryExpirableCache<K, V>
        extends AbstractMemoryExpirableCache<K, V, TimestampMemoryExpirableCache.TimestampExpirableValue<V>> {

    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB

    public TimestampMemoryExpirableCache() {
        super(new LruCache<K, TimestampExpirableValue<V>>(DISK_CACHE_SIZE));
    }

    @Override
    protected TimestampExpirableValue<V> newExpirableValue(V value) {
        return new TimestampExpirableValue(value);
    }

    public static class TimestampExpirableValue<V> implements ExpirableValue<V> {
        private final long cacheExpireAt;
        private final V value;

        public TimestampExpirableValue(long timeToExpire, V value) {
            this.cacheExpireAt = new Date().getTime() + timeToExpire;
            this.value = value;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public boolean isExpired() {
            return new Date().getTime() >= cacheExpireAt;
        }
    }

}
