package gr.ihu.ict.zotero.publications.importer.util;

import io.vavr.control.Try;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class MapSafeResolverUtil {
    public static <T> Try<T> safeResolveMapValue(final String key, final Map<String, T> map, final T defaultValue) {
        return Try.of(() -> map.get(key))
                .map(value -> {
                    if (Objects.isNull(value)) {
                        return defaultValue;
                    }

                    return value;
                })
                .recover(exception -> defaultValue);
    }

    public static <T> String safeResolveMapValueAsString(final String key,
                                                         final Map<String, T> map,
                                                         final T defaultValue) {
        return (String) safeResolveMapValue(key, map, defaultValue)
                .getOrElse(defaultValue);
    }

    public static <T> Map<String, Object> safeResolveMapValueAsMapOfStringObject(final String key,
                                                                                 final Map<String, java.lang.Object> map,
                                                                                 final HashMap<String, java.lang.Object> defaultValue) {
        return (Map<String, Object>) safeResolveMapValue(key, map, defaultValue)
                .getOrElse(defaultValue);
    }

    public static List<Map<String, Object>> safeResolveMapValueAsListOfMapStringObject(final String key,
                                                                                       final Map<String, Object> map,
                                                                                       final List<Map<String, Object>> defaultValue) {
        return (List<Map<String, Object>>) safeResolveMapValue(key,map,defaultValue)
                .getOrElse(defaultValue);
    }
}
