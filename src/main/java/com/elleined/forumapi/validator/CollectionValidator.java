package com.elleined.forumapi.validator;

import java.util.Collection;

public interface CollectionValidator {

    default boolean isValid(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    default boolean isNotValid(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
