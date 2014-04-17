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

public class MemoryCache<T> implements Cache<T> {

    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private LruCache<String, T> cache;

    public MemoryCache() {
    }

    @Override
    public void init(Callback callback) {
        cache = new LruCache<String, T>(DISK_CACHE_SIZE);
        callback.onSuccess(this);
    }

    @Override
    public void add(String key, T data) {
        cache.put(key, data);
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public T get(String key) {
        return cache.get(key);
    }

}
