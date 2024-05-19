package com.elleined.socialmediaapi.dto.hashtag;

import com.elleined.socialmediaapi.dto.DTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class HashTagDTO extends DTO {
    private String keyword;
    private Set<Integer> postIds;

    @Builder
    public HashTagDTO(int id,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt,
                      String keyword,
                      Set<Integer> postIds) {
        super(id, createdAt, updatedAt);
        this.keyword = keyword;
        this.postIds = postIds;
    }
}
