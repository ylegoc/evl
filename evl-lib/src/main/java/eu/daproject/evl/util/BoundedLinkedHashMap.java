package eu.daproject.evl.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class defining a bounded linked hash map used to create default bounded caches. It is the extension of the {@link LinkedHashMap}.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class BoundedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
	 
  	private static final long serialVersionUID = -3028806889484108479L;
	
  	private final int maxEntries;
 
    public BoundedLinkedHashMap(int maxEntries) {
        super();
        this.maxEntries = maxEntries;
    }
 
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > maxEntries;
    }
 
}
