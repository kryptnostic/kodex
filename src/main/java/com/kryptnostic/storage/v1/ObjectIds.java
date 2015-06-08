package com.kryptnostic.storage.v1;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * Utility methods for working with object storage ID's.
 * @author rbuckheit
 */
public final class ObjectIds {

    private ObjectIds() { /* not to be instantiated */}

    public static final String CHILD_OBJECT_SEPARATOR = ":";

    public static String getChildObjectId(String objectId, int childIndex) {
        Validate.isTrue(StringUtils.isNotBlank(objectId));
        Validate.isTrue(childIndex >= 0);
        return String.format("%s%s%d", objectId, CHILD_OBJECT_SEPARATOR, childIndex);
    }

    public static boolean isChildObjectId(String objectId) {
        return objectId.split(CHILD_OBJECT_SEPARATOR).length > 1;
    }

    public static String getParentObjectId(String childId) {
        Validate.isTrue(isChildObjectId(childId), "objectId must be a child object Id");
        return childId.split(CHILD_OBJECT_SEPARATOR)[0];
    }
}
