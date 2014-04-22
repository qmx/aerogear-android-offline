package org.jboss.aerogear.android.offline;

public interface ExpirableValue<V> {

    public V getValue();

    public boolean isExpired();

}
