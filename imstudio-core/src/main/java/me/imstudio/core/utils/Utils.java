package me.imstudio.core.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    private Utils() {
    }

    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
    }

    public static double clamp(double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }

    public static <T> boolean checkNotNull(@Nullable T object) {
        return object != null;
    }

    public static <T> boolean checkNotEmpty(List<T> data) {
        return data != null && data.size() > 0;
    }

    public static boolean checkNotEmpty(String... strings) {
        if (strings != null && strings.length > 0) {
            for (String string : strings) {
                if (TextUtils.isEmpty(string))
                    return false;
            }
        }
        return true;
    }

    public static final class MapBuilder{

        private final HashMap<String, String> map;
        private static MapBuilder instance;

        private MapBuilder() {
            map = new HashMap<>();
        }

        public static MapBuilder self() {
            if (instance == null)
                instance = new MapBuilder();
            instance.clearAll();
            return instance;
        }

        public MapBuilder put(String key, String value) {
            if (value != null)
                map.put(key, value);
            return this;
        }

        public MapBuilder putAll(Map<String, String> data) {
            if (data != null && data.size() > 0)
                map.putAll(data);
            return this;
        }

        public HashMap<String, String> build() {
            return map;
        }

        public void clearAll() {
            map.clear();
        }

    }

}