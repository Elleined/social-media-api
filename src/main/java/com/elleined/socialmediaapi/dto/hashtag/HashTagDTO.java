package com.elleined.socialmediaapi.dto.hashtag;

import com.elleined.socialmediaapi.dto.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class HashTagDTO extends DTO {
    private String keyword;
    private Set<Integer> postIds;
}
