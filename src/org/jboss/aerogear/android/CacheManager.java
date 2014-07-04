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
package org.jboss.aerogear.android;

import org.jboss.aerogear.android.core.ConfigurationProvider;
import org.jboss.aerogear.android.impl.offline.CacheConfig;
import org.jboss.aerogear.android.impl.offline.MemoryCacheConfig;
import org.jboss.aerogear.android.impl.offline.MemoryCacheConfigurationProvider;
import org.jboss.aerogear.android.offline.Cache;
import org.jboss.aerogear.android.offline.OnCacheCreatedListener;

import java.util.HashMap;
import java.util.Map;

public final class CacheManager {

    private static Map<String, Cache<?, ?>> caches = new HashMap<String, Cache<?, ?>>();

    private static Map<Class<? extends CacheConfig<?>>, ConfigurationProvider<?>>
            configurationProviderMap = new HashMap<Class<? extends CacheConfig<?>>, ConfigurationProvider<?>>();

    private static OnCacheCreatedListener onCacheCreatedListener = new OnCacheCreatedListener() {
        @Override
        public void onCacheCreated(CacheConfig<?> configuration, Cache<?, ?> cache) {
            caches.put(configuration.getName(), cache);
        }
    };

    static {
        MemoryCacheConfigurationProvider configurationProvider = new MemoryCacheConfigurationProvider();
        CacheManager.registerConfigurationProvider(MemoryCacheConfig.class, configurationProvider);
    }

    private CacheManager() {
    }

    public static <CFG extends CacheConfig<CFG>> void registerConfigurationProvider
            (Class<CFG> configurationClass, ConfigurationProvider<CFG> provider) {
        configurationProviderMap.put(configurationClass, provider);
    }

    public static <CFG extends CacheConfig<CFG>> CFG config(String name, Class<CFG> cacheImplementationClass) {

        @SuppressWarnings("unchecked")
        ConfigurationProvider<? extends CacheConfig<CFG>> provider =
                (ConfigurationProvider<? extends CacheConfig<CFG>>)
                        configurationProviderMap.get(cacheImplementationClass);

        if (provider == null) {
            throw new IllegalArgumentException("Configuration not registered!");
        }

        return provider.newConfiguration()
                .setName(name)
                .addOnCacheCreatedListener(onCacheCreatedListener);

    }

    public static Cache getCache(String name) {
        return caches.get(name);
    }

}
