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
import org.jboss.aerogear.android.Callback;
import org.jboss.aerogear.android.offline.Cache;
import org.jboss.aerogear.android.offline.ExpirableValue;
import org.jboss.aerogear.android.offline.ExpirableCache;

public abstract class AbstractMemoryExpirableCache<K, V, W extends ExpirableValue<V>> implements ExpirableCache<K, V> {

    private final LruCache<K, W> cache;

    public AbstractMemoryExpirableCache(LruCache<K, W> cache) {
        this.cache = cache;
    }

    protected abstract W newExpirableValue(V value);

    @Override
    public void init(Callback<? extends Cache> callback) {
    }

    @Override
    public void add(K key, V value) {
        cache.put(key, newExpirableValue(value));
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        ExpirableValue<V> expirableValue = getCachedValue(key);
        return expirableValue == null || expirableValue.isExpired() ? null : expirableValue.getValue();
    }

    @Override
    public boolean isExpired(K key) {
        ExpirableValue<V> expirableValue = getCachedValue(key);
        return expirableValue == null || expirableValue.isExpired();
    }

    @Override
    public ExpirableValue<V> getCachedValue(K key) {
        return cache.get(key);
    }

}
