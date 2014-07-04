/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
