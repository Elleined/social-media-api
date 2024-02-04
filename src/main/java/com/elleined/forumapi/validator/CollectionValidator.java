package com.elleined.forumapi.validator;

import java.util.Collection;

public interface CollectionValidator {

    static boolean isValid(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    static boolean isNotValid(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
