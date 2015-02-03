package com.kaidi.fordrinking.util;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaidi on 15-2-3.
 */
public class DataShare {

    private Map<String, WeakReference<Object>> data = new HashMap<String, WeakReference<Object>>();

    public void save(String key, Object object) {
        data.put(key, new WeakReference<Object>(object));
    }

    public Object retrieve(String key) {
        WeakReference<Object> objectWeakReference = data.get(key);
        if (objectWeakReference != null) {
            return objectWeakReference.get();
        }
        return null;
    }

    private static final DataShare share = new DataShare();
    public static DataShare getInstance() {
        return share;
    }
}
