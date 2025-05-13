package com.cloudfun.base.tool.object.compare.contants;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/28 16:53
 */
public enum CompareType {

    NONE(0),
    ADD(1),
    CHANGE(2),
    DELETE(3);

    private final int type;

    CompareType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static CompareType getCompareType(Integer type) {
        if (type == null) {
            return null;
        }
        for (CompareType compareType : CompareType.values()) {
            if (compareType.getType() == type) {
                return compareType;
            }
        }
        return null;
    }

    public static Boolean isAdd(Integer type) {
        if (type == null) {
            return false;
        }
        return type == ADD.getType();
    }

    public static Boolean isChange(Integer type) {
        if (type == null) {
            return false;
        }
        return type == CHANGE.getType();
    }

    public static Boolean isDelete(Integer type) {
        if (type == null) {
            return false;
        }
        return type == DELETE.getType();
    }


}
