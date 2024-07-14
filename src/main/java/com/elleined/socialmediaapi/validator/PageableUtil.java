package com.elleined.socialmediaapi.validator;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface PageableUtil {

    static <T extends PrimaryKeyIdentity> Page<T> toPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        List<T> pageContent = list.subList(start, end);
        return new PageImpl<>(pageContent, pageable, list.size());
    }

    static <T extends PrimaryKeyIdentity> Page<T> toPage(Set<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        List<T> pageContent = new ArrayList<>(list).subList(start, end);
        return new PageImpl<>(pageContent, pageable, list.size());
    }
}
