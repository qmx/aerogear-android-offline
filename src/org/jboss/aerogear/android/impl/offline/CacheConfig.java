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

import org.jboss.aerogear.Config;
import org.jboss.aerogear.android.offline.OnCacheCreatedListener;

import java.util.Collection;
import java.util.HashSet;

public abstract class CacheConfig<CFG extends CacheConfig<CFG>> implements Config<CFG> {

    private String name;
    private Collection<OnCacheCreatedListener> listeners;

    public CacheConfig() {
        listeners = new HashSet<OnCacheCreatedListener>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CFG setName(String name) {
        this.name = name;
        return (CFG) this;
    }

    public Collection<OnCacheCreatedListener> getOnCacheCreatedListeners() {
        return listeners;
    }

    public CFG addOnCacheCreatedListener(OnCacheCreatedListener listener) {
        this.listeners.add(listener);
        return (CFG) this;
    }

    public CFG setOnCacheCreatedListeners(Collection<OnCacheCreatedListener> listeners) {
        listeners.addAll(listeners);
        return (CFG) this;
    }

}
