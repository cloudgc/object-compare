package com.cloudfun.base.tool.object.compare.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/5/8 9:37
 */
public class ObjectUtil {

    /**
     * object is empty
     *
     * <p>
     * support String, Map, Collection, Object[]
     *
     * @param object object
     * @return isEmpty
     */
    public static boolean isEmpty(Object object) {
        return switch (object) {
            case null -> true;
            case String s -> s.isEmpty();
            case Map<?, ?> map -> map.isEmpty();
            case Collection<?> collection -> collection.isEmpty();
            case Object[] objects -> objects.length == 0;
            default -> false;
        };

    }

    public static boolean isSame(Object origin, Object target) {
        if (origin == null || target == null) {
            return false;
        }
        if (origin == target) {
            return true;
        }

        if (origin instanceof Comparable<?>) {
            int i = ((Comparable) origin).compareTo(target);
            return i == 0;
        }

        return origin.equals(target);
    }


}
